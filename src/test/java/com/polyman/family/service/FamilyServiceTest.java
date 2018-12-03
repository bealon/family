package com.polyman.family.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.polyman.common.base.bean.BizException;
import com.polyman.family.FamilyNetApplication;
import com.polyman.family.bean.family.AddFamilyRequest;
import com.polyman.family.entity.neo4j.relationships.P2FRelation;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =FamilyNetApplication.class)
public class FamilyServiceTest {

	@Autowired
	FamilyService familyService;
	
	@Test
	@Transactional
	@Rollback(false)
	public void createFamily() throws BizException {
		AddFamilyRequest request=new AddFamilyRequest();
		request.setBirthland("湖南省常德市澧县");
		request.setDesc("随便写写");
		request.setName("快乐家族1");
		P2FRelation p2FRelation=familyService.createFamily("7ebd75a8034d4224adb49ba93b40ad72", request);
		System.out.println("=========="+JSON.toJSONString(p2FRelation));
	}

}
