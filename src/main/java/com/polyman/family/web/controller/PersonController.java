package com.polyman.family.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polyman.common.base.bean.BaseBusinessController;
import com.polyman.common.base.bean.BizException;
import com.polyman.common.base.utils.ResultDto;
import com.polyman.family.bean.person.AddPersonRelationRequest;
import com.polyman.family.bean.person.AddPersonRequest;
import com.polyman.family.bean.person.DeletePersonRelationRequest;
import com.polyman.family.bean.person.DeletePersonRequest;
import com.polyman.family.bean.person.QueryPersonRelationRequest;
import com.polyman.family.bean.person.UpdatePersonRequest;
import com.polyman.family.entity.neo4j.relationships.P2PRelation;
import com.polyman.family.service.PersonService;


@RestController
@RequestMapping("/person")
public class PersonController extends BaseBusinessController {
	
	@Autowired
	PersonService personService;  
	

	 /**
     * 添加用户
     * @param genre
     * @return
     * @throws BizException 
     */
    @PostMapping("/add")  
    public ResultDto addPerson(@Valid @RequestBody AddPersonRequest request) throws BizException{
    	personService.addPerson("ab454ba02c524b9688e81c8ecc24b8a4", request);
    	return success();
    }
    
    /**
     * 修改用户
     * @param genre
     * @return
     * @throws BizException 
     */
    @PostMapping("/update")  
    public ResultDto updatePerson(@Valid @RequestBody UpdatePersonRequest request) throws BizException{
    	personService.updatePerson("ab454ba02c524b9688e81c8ecc24b8a4", request);
    	return success();
    } 
    
    
    
    /**
     * 关联用户
     * @param genre
     * @return
     * @throws BizException 
     */
    @PostMapping("/bind")  
    public ResultDto bulidePersonRelation(@Valid @RequestBody AddPersonRelationRequest request) throws BizException{      	
    	personService.bulidePersonRelation("ab454ba02c524b9688e81c8ecc24b8a4", request);
    	return success();
    } 
    
    /**
     * 删除家族关系
     * @param genre
     * @return
     * @throws BizException 
     */
    @PostMapping("/deletePersonByFamily")  
    public ResultDto deletePersonByFamily(@Valid @RequestBody DeletePersonRequest request) throws BizException{      	
    	personService.deletePersonByFamily("ab454ba02c524b9688e81c8ecc24b8a4", request);
    	return success();
    } 
    
    
    /**
     * 删除2个人关系
     * @param genre
     * @return
     * @throws BizException 
     */
    @PostMapping("/deletePersonRelation")  
    public ResultDto deletePersonRelation(@Valid @RequestBody DeletePersonRelationRequest request) throws BizException{      	
    	personService.deletePersonRelation("ab454ba02c524b9688e81c8ecc24b8a4", request);
    	return success();
    }
    
    
    /**
     * 删除所有关系
     * @param genre
     * @return
     * @throws BizException 
     */
    @PostMapping("/deletePerson")  
    public ResultDto deletePerson() throws BizException{      	
    	personService.deletePersonAllRelation("ac67d2479681456bb993de0f96e51d16");
    	return success();
    } 
    
    /**
     * 查询2人相同家族下的关系
     * @param request
     * @return
     * @throws BizException
     */
    @PostMapping("/queryPersonRelation")  
    public ResultDto queryPersonRelation(@Valid @RequestBody QueryPersonRelationRequest request) throws BizException{      	
    	List<P2PRelation> res=personService.queryPersonRelation("ab454ba02c524b9688e81c8ecc24b8a4",request);
    	return success(res);
    } 
    

	
}
