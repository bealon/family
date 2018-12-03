package com.polyman.family.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.polyman.common.base.bean.BizException;
import com.polyman.common.base.enums.BaseExcCodesEnum;
import com.polyman.common.base.enums.ConstantEnums;
import com.polyman.common.base.utils.BeanUtil;
import com.polyman.family.bean.family.JoinFamilyRequest;
import com.polyman.family.bean.person.AddOneSelfRequest;
import com.polyman.family.bean.person.AddPersonRelationRequest;
import com.polyman.family.bean.person.AddPersonRequest;
import com.polyman.family.bean.person.DeletePersonRelationRequest;
import com.polyman.family.bean.person.DeletePersonRequest;
import com.polyman.family.bean.person.QueryOneSelfRequest;
import com.polyman.family.bean.person.QueryPersonRelationRequest;
import com.polyman.family.bean.person.UpdatePersonRequest;
import com.polyman.family.common.enums.P2PRelationEnum;
import com.polyman.family.config.PersonConfig;
import com.polyman.family.dto.PersonBean;
import com.polyman.family.entity.neo4j.Family;
import com.polyman.family.entity.neo4j.Person;
import com.polyman.family.entity.neo4j.relationships.P2PRelation;
import com.polyman.family.repository.FamilyRepository;
import com.polyman.family.repository.P2PRelationRepository;
import com.polyman.family.repository.PersonRepository;
import com.polyman.family.service.FamilyService;
import com.polyman.family.service.PersonService;

@Service("personService")
public class PersonServiceImpl implements PersonService {

	
	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
	
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private FamilyRepository familyRepository;
	
	@Autowired
	private P2PRelationRepository p2PRelationRepository;
	
	@Autowired
	private FamilyService familyService;
	
	@Autowired
	private PersonConfig personConfig;



	@Override
	@Transactional(rollbackFor=Exception.class)
	public Person saveOneSelf(AddOneSelfRequest request) throws BizException {
		logger.info("PersonService saveUser request={}",request);
		Person person=new Person();
		BeanUtil.copyProperties(request, person);
		person.setNativeId(UUID.randomUUID().toString().replace("-", ""));
		person.setCreateDate(new Date());
		person.setSearchDepth(personConfig.getSearchDepth());
		person.setFamilySearchFlag(personConfig.getFamilySearchFlag());
		person=personRepository.save(person);
		return person;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Person updatePerson(String opPersonNativeId,UpdatePersonRequest request) throws BizException {
		logger.info("PersonService updatePerson request={}",request);
		Person person=personRepository.findByNativeId(request.getNativeId());
		if(null == person){
			throw new BizException(BaseExcCodesEnum.ENTITY_NOT_EXISTS,new Object[]{"用户"});
		}
		//修改人必须是家族管理员
		if(!opPersonNativeId.equals(request.getNativeId())){
			Person opPerson= personRepository.queryOpInSameFamily(opPersonNativeId,request.getNativeId());
			if(null == opPerson){
				throw new BizException(BaseExcCodesEnum.SERVERL_ATTACT);
			}
		}
		Map<String,Object> map=new HashMap<>();
		map.put("nativeId",request.getNativeId());
		map.put("nickName",StringUtils.hasLength(request.getNickName()) ? request.getNickName() : person.getNickName());
		map.put("headUrl",StringUtils.hasLength(request.getHeadUrl()) ? request.getHeadUrl() : person.getHeadUrl());
		map.put("password",StringUtils.hasLength(request.getPassword()) ? request.getPassword() : person.getPassword());
		map.put("realName",request.getRealName());
		map.put("phone",StringUtils.hasLength(request.getPhone()) ? request.getPhone() : person.getPhone());
		
		Date birthday= request.getBirthday() !=null  ? request.getBirthday() : person.getBirthday();
		if(null != birthday){
			map.put("birthday",birthday);
		}
		map.put("bornPlace",StringUtils.hasLength(request.getBornPlace()) ? request.getBornPlace() : person.getBornPlace());
		map.put("idCard",StringUtils.hasLength(request.getIdCard()) ? request.getIdCard() : person.getIdCard());
		map.put("desc",StringUtils.hasLength(request.getDesc()) ? request.getDesc() : person.getDesc());
		map.put("workPlace",StringUtils.hasLength(request.getWorkPlace()) ? request.getWorkPlace() : person.getWorkPlace());
		map.put("searchDepth",null != request.getSearchDepth()  ? request.getSearchDepth() : person.getSearchDepth());
		map.put("familySearchFlag",null != request.getFamilySearchFlag()  ? request.getFamilySearchFlag() : person.getFamilySearchFlag());
		map.put("createDate",person.getCreateDate());
		map.put("updateDate",new Date());
		person=personRepository.updatePerson(request.getNativeId(),map);
		return person;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Person addPerson(String opPersonNativeId,AddPersonRequest request) throws BizException {
		logger.info("PersonService updatePerson opPersonNativeId={},request={}",opPersonNativeId,request);
		//创建加入人
		Person person=new Person();
		BeanUtil.copyProperties(request, person);
		String personNativeId=UUID.randomUUID().toString().replace("-", "");
		person.setNativeId(personNativeId);
		person.setCreateDate(new Date());
		person.setSearchDepth(personConfig.getSearchDepth());
		person.setFamilySearchFlag(personConfig.getFamilySearchFlag());
		person=personRepository.save(person);
		
		//建立人跟家庭关系,
		JoinFamilyRequest joinFamilyRequest=new JoinFamilyRequest();
		joinFamilyRequest.setPersonNativeId(personNativeId);
		for(String familyNativeId:request.getFamilyNativeIds()){
			joinFamilyRequest.setFamilyNativeId(familyNativeId);
			familyService.joinFamily(opPersonNativeId,joinFamilyRequest);
		}
		
	    if(StringUtils.hasLength(request.getPersonNativeId())){
	    	Person relevancyPerson=personRepository.findByNativeId(request.getPersonNativeId());
			if(null == relevancyPerson){
				throw new BizException(BaseExcCodesEnum.ENTITY_NOT_EXISTS,new Object[]{"用户"});
			}
			//建立人与人的关系
			_createPersonRelation(person,relevancyPerson, request.getP2pRelation());
	    }
		return person;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Person bulidePersonRelation(String opPersonNativeId, AddPersonRelationRequest request) throws BizException {
		logger.info("PersonService bulidePersonRelation opPersonNativeId={},request={}",opPersonNativeId,request);
		
		Person relevancyPersonOne=personRepository.findByNativeId(request.getPersonNativeIdone());
		if(null == relevancyPersonOne){
			throw new BizException(BaseExcCodesEnum.ENTITY_NOT_EXISTS,new Object[]{"用户"});
		}
		Person relevancyPersonTwo=personRepository.findByNativeId(request.getPersonNativeIdtwo());
		if(null == relevancyPersonTwo){
			throw new BizException(BaseExcCodesEnum.ENTITY_NOT_EXISTS,new Object[]{"用户"});
		}
		
		List<Family> familyList=familyRepository.findFamilyByPersonId(opPersonNativeId);
		_validateFamilyList(familyList,request.getFamilyNativeIds());
		
		familyList=familyRepository.findFamilyByPersonId(request.getPersonNativeIdone());
		_validateFamilyList(familyList,request.getFamilyNativeIds());
		
		familyList=familyRepository.findFamilyByPersonId(request.getPersonNativeIdtwo());
		_validateFamilyList(familyList,request.getFamilyNativeIds());
		
		_updatePersonRelation( relevancyPersonOne, relevancyPersonTwo, request.getP2pRelation());
		
		return relevancyPersonOne;
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deletePersonRelation(String opPersonNativeId, DeletePersonRelationRequest request) throws BizException {
		logger.info("PersonService deletePersonRelation opPersonNativeId={},request={}",opPersonNativeId,request);
		//操作人不是本人，必须是管理员
		if(!opPersonNativeId.equals(request.getPersonNativeIdone()) 
				&& !opPersonNativeId.equals(request.getPersonNativeIdtwo())){
			Person opPerson= personRepository.queryOpInSameFamily(opPersonNativeId,request.getPersonNativeIdone());
			if(null == opPerson){
				throw new BizException(BaseExcCodesEnum.SERVERL_ATTACT);
			}
		}
		personRepository.deletePersonRelation(request.getPersonNativeIdone(), request.getPersonNativeIdtwo());
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deletePersonByFamily(String opPersonNativeId, DeletePersonRequest request) throws BizException {
		logger.info("PersonService deletePersonByFamily opPersonNativeId={},request={}",opPersonNativeId,request);
		//操作人不是本人，必须是管理员
		if(!opPersonNativeId.equals(request.getPersonNativeId())){
			Person opPerson= personRepository.queryOpInSameFamily(opPersonNativeId,request.getPersonNativeId());
			if(null == opPerson){
				throw new BizException(BaseExcCodesEnum.SERVERL_ATTACT);
			}
		}
		for(String familyNativeId:request.getFamilyNativeIdList()){
			personRepository.deletePersonByFamily(request.getPersonNativeId(),familyNativeId);
		}
	}
	
	@Override
	public void deletePersonAllRelation(String opPersonNativeId) throws BizException {
		logger.info("PersonService deletePersonByFamily opPersonNativeId={}",opPersonNativeId);
		personRepository.deletePersonAllRelation(opPersonNativeId);
	}
	

	@Override
	public List<PersonBean> querySameList(QueryOneSelfRequest request) throws BizException {
		return null;
	}


	@Override
	public List<P2PRelation> queryPersonRelation(String opPersonNativeId, QueryPersonRelationRequest request)
			throws BizException {
		logger.info("PersonService deletePersonByFamily opPersonNativeId={},request={}",opPersonNativeId,request);
		List<P2PRelation> ls=p2PRelationRepository.findP2PShortRelationByNameInSameFamily(opPersonNativeId, request.getRealNameOne(), request.getRealNameTwo());
		return ls;
	}
	
	private void _validateFamilyList(List<Family> familyList,List<String> familyNativeIds) throws BizException {
		List<String> source=new ArrayList<>();
		for(Family family:familyList){
			source.add(family.getNativeId());
		}
		if(!source.containsAll(familyNativeIds)){
			throw new BizException(BaseExcCodesEnum.SERVERL_ATTACT);
		}
	}

	/**
	 * creater 是  beCreater 的 p2pRelation
	 * @param creater
	 * @param beCreater 一定是存在的人
	 * @param p2pRelation
	 * @throws BizException
	 */
	private void _createPersonRelation(Person source,Person target,String p2pRelation)throws BizException {
		if(P2PRelationEnum.FATHER.getDesc().equals(p2pRelation)
				|| P2PRelationEnum.MOTHER.getDesc().equals(p2pRelation)){
			_createSuper(source,target,p2pRelation);
		}else if(P2PRelationEnum.HUSBAND.getDesc().equals(p2pRelation)
				|| P2PRelationEnum.WIFE.getDesc().equals(p2pRelation)){
			_createSameLevel(source,target);
		}else if(P2PRelationEnum.SON.getDesc().equals(p2pRelation) 
				|| P2PRelationEnum.DAUGHTER.getDesc().equals(p2pRelation)){
			_createSubordinate(source,target); 
		}else if(P2PRelationEnum.BAS.getDesc().equals(p2pRelation)
				|| P2PRelationEnum.BROTHER.getDesc().equals(p2pRelation)
				|| P2PRelationEnum.YBROTHER.getDesc().equals(p2pRelation)
				|| P2PRelationEnum.SISTER.getDesc().equals(p2pRelation)
				|| P2PRelationEnum.YSISTER.getDesc().equals(p2pRelation)){
			_createBrotherAndSister(source,target,p2pRelation); 
		}else{
			throw new BizException(BaseExcCodesEnum.ENTITY_NOT_EXISTS,new Object[]{"关系"});
		}
	}
	
	private void _updatePersonRelation(Person source,Person target,String p2pRelation)throws BizException {
		List<P2PRelation> relations= p2PRelationRepository.findP2PRelation(source.getNativeId(), target.getNativeId());
		if(relations.size()==0){
			_createPersonRelation(source,target,p2pRelation);
		}else if(relations.size()==2){
			P2PRelation p2PRelation1=relations.get(0);
			P2PRelation p2PRelation2=relations.get(1);
			if(!p2PRelation1.getUser().getNativeId().equals(source.getNativeId())){
				 p2PRelation1=relations.get(1);
				 p2PRelation2=relations.get(0);
			}
			p2PRelation2.setName(p2pRelation);
            if(P2PRelationEnum.FATHER.getDesc().equals(p2pRelation)||
            		P2PRelationEnum.MOTHER.getDesc().equals(p2pRelation) ){
            	if(target.getSex().equals(ConstantEnums.SEX.MAN.getDesc())){
            		p2PRelation1.setName(P2PRelationEnum.SON.getDesc());
            	}else{
            		p2PRelation1.setName(P2PRelationEnum.DAUGHTER.getDesc());
            	}
			}else if(P2PRelationEnum.HUSBAND.getDesc().equals(p2pRelation) ||
					P2PRelationEnum.WIFE.getDesc().equals(p2pRelation)){
				if(target.getSex().equals(ConstantEnums.SEX.MAN.getDesc())){
            		p2PRelation1.setName(P2PRelationEnum.HUSBAND.getDesc());
            	}else{
            		p2PRelation1.setName(P2PRelationEnum.WIFE.getDesc());
            	}
			}else if(P2PRelationEnum.SON.getDesc().equals(p2pRelation) ||
					P2PRelationEnum.DAUGHTER.getDesc().equals(p2pRelation)){
				if(target.getSex().equals(ConstantEnums.SEX.MAN.getDesc())){
            		p2PRelation1.setName(P2PRelationEnum.FATHER.getDesc());
            	}else{
            		p2PRelation1.setName(P2PRelationEnum.MOTHER.getDesc());
            	}
			}else {
				if(p2pRelation.equals(P2PRelationEnum.BAS.getDesc())){	
					p2PRelation1.setName(p2pRelation);
				}else if(p2pRelation.equals(P2PRelationEnum.BROTHER.getDesc()) || p2pRelation.equals(P2PRelationEnum.SISTER.getDesc())){
					if(target.getSex().equals(ConstantEnums.SEX.MAN.getDesc())){
						p2PRelation1.setName(P2PRelationEnum.YBROTHER.getDesc());
					}else{
						p2PRelation1.setName(P2PRelationEnum.YSISTER.getDesc());
					}
				}else if(p2pRelation.equals(P2PRelationEnum.YBROTHER.getDesc()) || p2pRelation.equals(P2PRelationEnum.YSISTER.getDesc())){
					if(target.getSex().equals(ConstantEnums.SEX.MAN.getDesc())){
						p2PRelation1.setName(P2PRelationEnum.BROTHER.getDesc());
					}else{
						p2PRelation1.setName(P2PRelationEnum.SISTER.getDesc());
					}
				}
			}
            p2PRelationRepository.save(p2PRelation1);
            p2PRelationRepository.save(p2PRelation2);
		}
		else{
			throw new BizException(BaseExcCodesEnum.SYSTEM_ERROR);
		}
		
	}
	/**
	 * 创建父母亲关系
	 * @param source  父辈
	 * @param target  子辈
	 * @param p2pRelation
	 * @throws BizException
	 */
	private void _createSuper(Person source,Person target,String p2pRelation) throws BizException {
		//构建自己和父母关系
		_bulidSuperAndChildren(target,source);
		//建立父母之间关系
		String alike;
		if(P2PRelationEnum.FATHER.getDesc().equals(p2pRelation)){
			alike=P2PRelationEnum.MOTHER.getDesc();
		}else{
			alike=P2PRelationEnum.FATHER.getDesc();
		}
		List<Person> superPersons=personRepository.findP2PRelationPersonList(target.getNativeId(), alike);
		if(null != superPersons){
			 for(Person superPerson1:superPersons){
				 _bulidHusbandAndWife(source,superPerson1);
			 }
		}
		//建立父母与兄弟姐妹关系
		List<Person> persons=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.BAS.getDesc());
		for(Person childrenTemp: persons){
			_bulidSuperAndChildren(childrenTemp,source);
		}
		
		persons=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.BROTHER.getDesc());
		for(Person childrenTemp: persons){
			_bulidSuperAndChildren(childrenTemp,source);
		}
		persons=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.YBROTHER.getDesc());
		for(Person childrenTemp: persons){
			_bulidSuperAndChildren(childrenTemp,source);
		}
		persons=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.SISTER.getDesc());
		for(Person childrenTemp: persons){
			_bulidSuperAndChildren(childrenTemp,source);
		}
		persons=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.YSISTER.getDesc());
		for(Person childrenTemp: persons){
			_bulidSuperAndChildren(childrenTemp,source);
		}
	}
	
	/**
	 * 创建夫妻关系	
	 * @param source
	 * @param target
	 * @throws BizException
	 */
	private void _createSameLevel(Person source,Person target) throws BizException {
		//构建夫妻关系
		_bulidHusbandAndWife(source,target);
		//构建配偶与子女关系
		//查找儿子
		List<Person> persons=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.SON.getDesc());
		for(Person childrenTemp: persons){
			_bulidSuperAndChildren(childrenTemp,source);
		}
		//查找女儿
		persons=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.DAUGHTER.getDesc());
		for(Person childrenTemp: persons){
			_bulidSuperAndChildren(childrenTemp,source);
		}
	}
	/**
	 * 创建子女关系
	 * @param source
	 * @param target
	 * @throws BizException,
	 */
	private void _createSubordinate(Person source,Person target) throws BizException {
		//构建自己和子女关系
		 _bulidSuperAndChildren(source,target);
		 
		//构建配偶与子女关系
		String alike=null;
		if(target.getSex().equals(ConstantEnums.SEX.MAN.getDesc())){
			alike=P2PRelationEnum.WIFE.getDesc();
		}else{
			alike=P2PRelationEnum.HUSBAND.getDesc();
		}
		
		List<Person> superPersons=personRepository.findP2PRelationPersonList(target.getNativeId(), alike);
		if(null != superPersons){
			 for(Person superPerson1:superPersons){
				 _bulidSuperAndChildren(source,superPerson1);
			 }
		}		
		//构建其他子女和新增孩子关系，排除自己
		List<Person> persons=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.SON.getDesc());
		for(Person childrenTemp: persons){
			 if(!childrenTemp.getNativeId().equals(source.getNativeId())){
				 _bulidBrotherAndSister(source,childrenTemp);
			 }
		}
		persons=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.DAUGHTER.getDesc());
		for(Person childrenTemp: persons){
			if(!childrenTemp.getNativeId().equals(source.getNativeId())){
				_bulidBrotherAndSister(source,childrenTemp);
			 }
		}	
	}
	
	/**
	 * 创建兄弟姐妹关系
	 * @param superPerson
	 * @param children
	 * @throws BizException
	 */
	private void _createBrotherAndSister(Person source,Person target,String type) throws BizException {
		//构建当前兄弟姐妹关系
		_bulidBrotherAndSister(source,target,type);
		//构建父母与添加人关系
		List<Person> fathers=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.FATHER.getDesc());
		if(null != fathers){
			 for(Person father:fathers){
				 _bulidSuperAndChildren(source,father);
			 }
		}		
		
		List<Person> mathers=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.MOTHER.getDesc());
		if(null != mathers){
			 for(Person mather:mathers){
				 _bulidSuperAndChildren(source,mather);
			 }
		}
		//构建其他兄弟姐妹关系  ,考虑生日未填写情况，无法判断
		List<Person> persons=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.BAS.getDesc());
		for(Person childrenTemp: persons){
			if(!childrenTemp.getNativeId().equals(target.getNativeId())){
				_bulidBrotherAndSister(source,childrenTemp);
			}
		}
		
		persons=personRepository.findP2PRelationPersonList(target.getNativeId(), P2PRelationEnum.BROTHER.getDesc());
		for(Person childrenTemp: persons){
			if(!childrenTemp.getNativeId().equals(target.getNativeId())){
				_bulidBrotherAndSister(source,childrenTemp);
			}
		}
		
		persons=personRepository.findP2PRelationPersonList(source.getNativeId(), P2PRelationEnum.YBROTHER.getDesc());
		for(Person childrenTemp: persons){
			if(!childrenTemp.getNativeId().equals(target.getNativeId())){
				_bulidBrotherAndSister(source,childrenTemp);
			}
		}
		
		persons=personRepository.findP2PRelationPersonList(source.getNativeId(), P2PRelationEnum.SISTER.getDesc());
		for(Person childrenTemp: persons){
			if(!childrenTemp.getNativeId().equals(target.getNativeId())){
				_bulidBrotherAndSister(source,childrenTemp);
			}
		}
		
		persons=personRepository.findP2PRelationPersonList(source.getNativeId(), P2PRelationEnum.YSISTER.getDesc());
		for(Person childrenTemp: persons){
			if(!childrenTemp.getNativeId().equals(target.getNativeId())){
				_bulidBrotherAndSister(source,childrenTemp);
			}
		}
		
	}

	/**
	 * 构建配偶
	 * @param source
	 * @param target
	 * @throws BizException
	 */
	private void _bulidHusbandAndWife (Person source,Person target) throws BizException {
		
		List<P2PRelation> relations= p2PRelationRepository.findP2PRelation(source.getNativeId(), target.getNativeId());
		if(relations.size() != 0){
			return ;
		}
		
		P2PRelation p2PRelation1=new P2PRelation();
		P2PRelation p2PRelation2=new P2PRelation();
		
		p2PRelation1.setUser(source);
		p2PRelation1.setRelative(target);
		
		p2PRelation2.setUser(target);
		p2PRelation2.setRelative(source);
		
		if(source.getSex().equals(ConstantEnums.SEX.MAN.getDesc())){
			p2PRelation1.setName(P2PRelationEnum.WIFE.getDesc());
			p2PRelation2.setName(P2PRelationEnum.HUSBAND.getDesc());
		}else{
			p2PRelation1.setName(P2PRelationEnum.HUSBAND.getDesc());
			p2PRelation2.setName(P2PRelationEnum.WIFE.getDesc());
		}
		p2PRelationRepository.save(p2PRelation1);
		p2PRelationRepository.save(p2PRelation2);
	}
	/**
	 * 构建父母与子女关系
	 * @param children
	 * @param superPerson
	 * @throws BizException
	 */
	private void _bulidSuperAndChildren(Person children,Person superPerson) throws BizException {
		List<P2PRelation> relations= p2PRelationRepository.findP2PRelation(children.getNativeId(), superPerson.getNativeId());
		if(relations.size() != 0){
			return ;
		}
		
		P2PRelation p2PRelation=new P2PRelation();
		p2PRelation.setUser(children);
		p2PRelation.setRelative(superPerson);
		if(superPerson.getSex().equals(ConstantEnums.SEX.MAN.getDesc())){
			p2PRelation.setName(P2PRelationEnum.FATHER.getDesc());
		}else{
			p2PRelation.setName(P2PRelationEnum.MOTHER.getDesc());
		}
		p2PRelationRepository.save(p2PRelation);
		
		p2PRelation=new P2PRelation();
		p2PRelation.setUser(superPerson);
		p2PRelation.setRelative(children);
		if(children.getSex().equals(ConstantEnums.SEX.MAN.getDesc())){
			p2PRelation.setName(P2PRelationEnum.SON.getDesc());
		}else{
			p2PRelation.setName(P2PRelationEnum.DAUGHTER.getDesc());
		}
		p2PRelationRepository.save(p2PRelation);
	}
   /**
    * 构建兄弟姐妹关系
    * @param source
    * @param target
    * @throws BizException
    */
	private void _bulidBrotherAndSister(Person source,Person target) throws BizException {
		if(null != source.getBirthday() && null != target.getBirthday()){
	
		}else{
			_bulidBrotherAndSister(source,target,P2PRelationEnum.BAS.getDesc());
		}
	}
	
	private void _bulidBrotherAndSister(Person source,Person target,String type) throws BizException {
		
		List<P2PRelation> relations= p2PRelationRepository.findP2PRelation(source.getNativeId(), target.getNativeId());
		if(relations.size() != 0){
			return ;
		}
		
		P2PRelation p2PRelation1=new P2PRelation();
		P2PRelation p2PRelation2=new P2PRelation();
		
		p2PRelation1.setUser(source);
		p2PRelation1.setRelative(target);
		
		p2PRelation2.setUser(target);
		p2PRelation2.setRelative(source);
		
		p2PRelation2.setName(type);
		
		if(type.equals(P2PRelationEnum.BAS.getDesc())){	
			p2PRelation1.setName(type);
		}else if(type.equals(P2PRelationEnum.BROTHER.getDesc()) || type.equals(P2PRelationEnum.SISTER.getDesc())){
			if(target.getSex().equals(ConstantEnums.SEX.MAN.getDesc())){
				p2PRelation1.setName(P2PRelationEnum.YBROTHER.getDesc());
			}else{
				p2PRelation1.setName(P2PRelationEnum.YSISTER.getDesc());
			}
		}else if(type.equals(P2PRelationEnum.YBROTHER.getDesc()) || type.equals(P2PRelationEnum.YSISTER.getDesc())){
			if(target.getSex().equals(ConstantEnums.SEX.MAN.getDesc())){
				p2PRelation1.setName(P2PRelationEnum.BROTHER.getDesc());
			}else{
				p2PRelation1.setName(P2PRelationEnum.SISTER.getDesc());
			}
		}
		p2PRelationRepository.save(p2PRelation1);
		p2PRelationRepository.save(p2PRelation2);
	}

	

	






}
