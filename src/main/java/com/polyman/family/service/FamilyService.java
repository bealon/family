package com.polyman.family.service;

import java.util.List;

import com.polyman.common.base.bean.BizException;
import com.polyman.family.bean.family.AddFamilyRequest;
import com.polyman.family.bean.family.JoinFamilyRequest;
import com.polyman.family.bean.family.QueryFamilyRequest;
import com.polyman.family.bean.family.UpdateFamilyRequest;
import com.polyman.family.entity.neo4j.Family;
import com.polyman.family.entity.neo4j.relationships.P2FRelation;

public interface FamilyService {

	/**
	 * 创建家族
	 * @param personNativeId
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public P2FRelation createFamily(String opPersonNativeId,AddFamilyRequest request) throws BizException;
	
	/**
	 * 加入家族
	 * @param personNativeId
	 * @param familyNativeId
	 * @return
	 * @throws BizException
	 */
	public void joinFamily(String opPersonNativeId,JoinFamilyRequest request) throws BizException;
	
	/**
	 * 查找能够加入的家族列表
	 * @param familyNativeIds
	 * @return
	 * @throws BizException
	 */
	public List<Family> findFamilyByPersonId(String opPersonNativeId,String ...personNativeIds) throws BizException;
	
	/**
	 * 修改家族
	 * @param opPersonNativeId
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public Family updateFamily(String opPersonNativeId,UpdateFamilyRequest request) throws BizException;
	
	/**
	 * 查询家族列表
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public List<Family> queryFamilyList(QueryFamilyRequest request) throws BizException;
	
	/**
	 * 删除家族
	 * @param opPersonNativeId
	 * @param request
	 */
	public void deleteFamily(String opPersonNativeId,List<String> request) throws BizException;;
	
}
