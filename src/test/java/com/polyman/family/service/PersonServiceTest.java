package com.polyman.family.service;

import java.util.Calendar;

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
import com.polyman.family.bean.person.AddOneSelfRequest;
import com.polyman.family.bean.person.AddPersonRequest;
import com.polyman.family.bean.person.UpdatePersonRequest;
import com.polyman.family.entity.neo4j.Person;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =FamilyNetApplication.class)
public class PersonServiceTest {

	@Autowired
	PersonService personService;
	
	@Test
	@Transactional
	@Rollback(false)
	public void saveOneSelf() throws BizException {
		AddOneSelfRequest request=new AddOneSelfRequest();
		request.setPhone("15974161167");
		request.setSex("男");
		Person person=personService.saveOneSelf(request);
		System.out.println(JSON.toJSONString(person));
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void updatePerson() throws BizException {
		UpdatePersonRequest request=new UpdatePersonRequest();
		request.setNativeId("7ebd75a8034d4224adb49ba93b40ad72");
		request.setPhone("15974161167");
		request.setSearchDepth(6);
		request.setFamilySearchFlag(0);
		Person person=personService.updatePerson("",request);
		System.out.println(JSON.toJSONString(person));
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void addPerson() throws BizException {
		AddPersonRequest addPersonRequest=new AddPersonRequest();
		addPersonRequest.setRealName("");
		addPersonRequest.setSex("");
		addPersonRequest.setBornPlace("");
		addPersonRequest.setPersonNativeId("");
		addPersonRequest.setP2pRelation("");
		
		Person person=personService.addPerson("", addPersonRequest);
		System.out.println(JSON.toJSONString(person));
		
	}

}
