package com.polyman.family.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.polyman.common.base.bean.BizException;
import com.polyman.common.base.enums.BaseExcCodesEnum;
import com.polyman.common.base.utils.DateUtil;
import com.polyman.family.bean.family.AddFamilyRequest;
import com.polyman.family.bean.family.JoinFamilyRequest;
import com.polyman.family.bean.family.QueryFamilyRequest;
import com.polyman.family.bean.family.UpdateFamilyRequest;
import com.polyman.family.common.enums.P2FRelationEnum;
import com.polyman.family.entity.neo4j.Family;
import com.polyman.family.entity.neo4j.Person;
import com.polyman.family.entity.neo4j.relationships.F2PRelation;
import com.polyman.family.entity.neo4j.relationships.P2FRelation;
import com.polyman.family.repository.F2PRelationRepository;
import com.polyman.family.repository.FamilyRepository;
import com.polyman.family.repository.P2FRelationRepository;
import com.polyman.family.repository.PersonRepository;
import com.polyman.family.service.FamilyService;
/**
 * 家族服务类
 * @author bealon
 *
 */
@Service("familyService")
public class FamilyServiceImpl implements FamilyService {

	
	private static final Logger logger = LoggerFactory.getLogger(FamilyServiceImpl.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private FamilyRepository familyRepository;
	
	@Autowired
	private P2FRelationRepository p2FRelationRepository;
	
	@Autowired
	private F2PRelationRepository f2PRelationRepository;


	@Override
	@Transactional(rollbackFor=Exception.class)
	public P2FRelation createFamily(String opPersonNativeId, AddFamilyRequest request) throws BizException {
		logger.info("FamilyServiceImpl createFamily request={}",request);
		
		Person person=personRepository.findByNativeId(opPersonNativeId);
		if(null == person){
			throw new BizException(BaseExcCodesEnum.ENTITY_NOT_EXISTS,new Object[]{"用户"});
		}
		
		Family family=new Family();
		BeanUtils.copyProperties(request, family);
		family.setNativeId(UUID.randomUUID().toString().replace("-", ""));
		family.setCreateDate(new Date());
		family=familyRepository.save(family);
		
		P2FRelation p2FRelation=new P2FRelation();
		List<String> name=new ArrayList<>();
		name.add(P2FRelationEnum.ADMIN.getDesc());
		name.add(P2FRelationEnum.CREATOR.getDesc());
		name.add(P2FRelationEnum.IS.getDesc());
		
		p2FRelation.setUser(person);
		p2FRelation.setName(name);
		p2FRelation.setFamily(family);
		p2FRelation=p2FRelationRepository.save(p2FRelation);
		
		F2PRelation f2PRelation=new F2PRelation();
		f2PRelation.setUser(person);
		f2PRelation.setFamily(family);
		f2PRelationRepository.save(f2PRelation);
		
		return p2FRelation;
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public void joinFamily(String opPersonNativeId,JoinFamilyRequest request) throws BizException {
		logger.info("FamilyServiceImpl joinFamily opPersonNativeId={},request={}",opPersonNativeId,request);
		//管理员权限
		Person opPerson=personRepository.isFamilyCreatorPerson(opPersonNativeId, request.getFamilyNativeId());
		if(null == opPerson ){
			throw new BizException(BaseExcCodesEnum.SERVERL_ATTACT);
		}
		
		Person person=personRepository.findByNativeId(request.getPersonNativeId());
		if(null == person){
			throw new BizException(BaseExcCodesEnum.ENTITY_NOT_EXISTS,new Object[]{"用户"});
		}
		Family family=familyRepository.findByNativeId(request.getFamilyNativeId());
		if(null == family){
			throw new BizException(BaseExcCodesEnum.ENTITY_NOT_EXISTS,new Object[]{"家庭"});
		}
		//判断是否已经加入家族
		Family hasFamily=familyRepository.queryInSameFamily(opPersonNativeId, request.getPersonNativeId(), request.getFamilyNativeId());
		if(null != hasFamily){
			return ;
		}
		
		P2FRelation p2FRelation=new P2FRelation();
		List<String> name=new ArrayList<>();
		name.add(P2FRelationEnum.IS.getDesc());
		
		p2FRelation.setUser(person);
		p2FRelation.setName(name);
		p2FRelation.setFamily(family);
		p2FRelation=p2FRelationRepository.save(p2FRelation);
		
		F2PRelation f2PRelation=new F2PRelation();
		f2PRelation.setUser(person);
		f2PRelation.setFamily(family);
		f2PRelationRepository.save(f2PRelation);
	}


	@Override
	public List<Family> findFamilyByPersonId(String opPersonNativeId,String ...personNativeIds) throws BizException {
		logger.info("FamilyServiceImpl joinFamily opPersonNativeId={},personNativeId={}",opPersonNativeId,personNativeIds);
		List<Family> result=new ArrayList<>();
		
		boolean flag=false;
		for(String personNativeId:personNativeIds){
			if(opPersonNativeId.equals(personNativeId)){
				flag=true;
			}
			List<Family> personFamily=familyRepository.findFamilyByPersonId(personNativeId);
			if(result.size()==0){
				result.addAll(personFamily);
			}else{
				result.retainAll(personFamily);
			}
		}
		if(flag){
			return result;
		}
		List<Family> opFamily=familyRepository.findFamilyByPersonId(opPersonNativeId);
		result.retainAll(opFamily);
		return result;
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public Family updateFamily(String opPersonNativeId, UpdateFamilyRequest request) throws BizException {
		logger.info("FamilyServiceImpl updateFamily opPersonNativeId={},request={}",opPersonNativeId,request);
		Family family=familyRepository.findByNativeId(request.getNativeId());
		if(null == family){
			throw new BizException(BaseExcCodesEnum.ENTITY_NOT_EXISTS,new Object[]{"家族"});
		}
		//修改人必须是家族管理员
		Person opPerson=personRepository.isFamilyCreatorPerson(opPersonNativeId, request.getNativeId());
		if(null == opPerson ){
			throw new BizException(BaseExcCodesEnum.SERVERL_ATTACT);
		}
		
		Map<String,Object> map=new HashMap<>();
		map.put("nativeId",request.getNativeId());
		map.put("name",StringUtils.hasLength(request.getName()) ? request.getName() : family.getName());
		map.put("birthland",StringUtils.hasLength(request.getBirthland()) ? request.getBirthland() : family.getBirthland());
		map.put("desc",StringUtils.hasLength(request.getDesc()) ? request.getDesc() : family.getDesc());
		map.put("createDate",family.getCreateDate());
		map.put("updateDate",new Date());
		//返回是原来对象，在客户端执行是新对象，估计是框架问题，重新查询
		family=familyRepository.updateFamily(request.getNativeId(),map);
		return family;
	}


	@Override
	public List<Family> queryFamilyList(QueryFamilyRequest request) throws BizException {
		logger.info("FamilyServiceImpl queryFamilyList request={}",request);
		int skip=(request.getPageNo()-1)*request.getPageSize();
		int limit=request.getPageSize();
		List<Family> list=familyRepository.queryFamilyList(request.getName(), request.getBirthland(), skip, limit);
		return list;
	}


	@Override
	public void deleteFamily(String opPersonNativeId, List<String> request) {
		logger.info("FamilyServiceImpl queryFamilyList opPersonNativeId={},request={}",opPersonNativeId,request);
        for(String familyNativeId:request){
        	familyRepository.deleteFamily(opPersonNativeId,familyNativeId);
        }
	}

}
