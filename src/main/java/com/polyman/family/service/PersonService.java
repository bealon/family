package com.polyman.family.service;

import java.util.List;

import com.polyman.common.base.bean.BizException;
import com.polyman.family.bean.person.AddOneSelfRequest;
import com.polyman.family.bean.person.AddPersonRelationRequest;
import com.polyman.family.bean.person.AddPersonRequest;
import com.polyman.family.bean.person.DeletePersonRelationRequest;
import com.polyman.family.bean.person.DeletePersonRequest;
import com.polyman.family.bean.person.QueryOneSelfRequest;
import com.polyman.family.bean.person.QueryPersonRelationRequest;
import com.polyman.family.bean.person.UpdatePersonRequest;
import com.polyman.family.dto.PersonBean;
import com.polyman.family.entity.neo4j.Person;
import com.polyman.family.entity.neo4j.relationships.P2PRelation;

public interface PersonService {
    /**
     * 用户注册添加
     * @param request
     * @return
     * @throws BizException
     */
	public Person saveOneSelf(AddOneSelfRequest request) throws BizException;
	/**
	 * 修改用户信息
	 * @param opPersonNativeId
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public Person updatePerson(String opPersonNativeId,UpdatePersonRequest request) throws BizException;
	/**
	 * 添加用户，加入家庭，创建关系
	 * @param opPersonNativeId
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public Person addPerson(String opPersonNativeId,AddPersonRequest request) throws BizException;
	/**
	 * 建立2个人关系
	 * @param opPersonNativeId
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public Person bulidePersonRelation(String opPersonNativeId,AddPersonRelationRequest request)throws BizException;
	
	/**
	 * 删除2个人的关系
	 * @param opPersonNativeId
	 * @param request
	 * @throws BizException
	 */
	public void deletePersonRelation(String opPersonNativeId,DeletePersonRelationRequest request)throws BizException;
	/**
	 * 脱离家庭
	 * @param opPersonNativeId
	 * @param request
	 * @throws BizException
	 */
	public void deletePersonByFamily(String opPersonNativeId,DeletePersonRequest request)throws BizException;
	/**
	 * 删除所有
	 * @param opPersonNativeId
	 * @throws BizException
	 */
	public void deletePersonAllRelation (String opPersonNativeId)throws BizException;
	/**
	 * 查询同名
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public List<PersonBean> querySameList(QueryOneSelfRequest request) throws BizException;
	
	/**
	 * 查询2个人之间的关系
	 * @param opPersonNativeId
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public List<P2PRelation> queryPersonRelation(String opPersonNativeId,QueryPersonRelationRequest request) throws BizException;
}
