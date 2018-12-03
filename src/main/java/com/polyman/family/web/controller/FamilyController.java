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
import com.polyman.family.bean.family.AddFamilyRequest;
import com.polyman.family.bean.family.JoinFamilyRequest;
import com.polyman.family.bean.family.QueryFamilyRequest;
import com.polyman.family.bean.family.UpdateFamilyRequest;
import com.polyman.family.entity.neo4j.Family;
import com.polyman.family.entity.neo4j.relationships.P2FRelation;
import com.polyman.family.service.FamilyService;


@RestController
@RequestMapping("/family")
public class FamilyController extends BaseBusinessController {
	
	@Autowired
	FamilyService familyService;  
	
      
    /**
     * 创建一个家族节点
     * @param genre
     * @return
     * @throws BizException 
     */
    @PostMapping("/save")  
    public ResultDto saveFamily(@Valid @RequestBody AddFamilyRequest request) throws BizException{      	
    	P2FRelation p2FRelation=familyService.createFamily("ab454ba02c524b9688e81c8ecc24b8a4", request);
    	return success(p2FRelation.getFamily());
    }
    
    @PostMapping("/update")  
    public ResultDto updateFamily(@Valid @RequestBody UpdateFamilyRequest request) throws BizException{      	
    	Family family=familyService.updateFamily("ab454ba02c524b9688e81c8ecc24b8a4", request);
    	return success(family);
    }
    
    @PostMapping("/join")  
    public ResultDto joinFamily(@Valid @RequestBody JoinFamilyRequest request) throws BizException{      	
    	familyService.joinFamily("ab454ba02c524b9688e81c8ecc24b8a4", request);
    	return success();
    }
    
    @PostMapping("/queryList")  
    public ResultDto queryList(@Valid @RequestBody QueryFamilyRequest request) throws BizException{      	
    	List<Family> ls=familyService.queryFamilyList(request);
    	return success(ls);
    } 
    
    @PostMapping("/deleteFamily")  
    public ResultDto deleteFamily(@RequestBody List<String> request) throws BizException{      	
    	familyService.deleteFamily("ab454ba02c524b9688e81c8ecc24b8a4",request);
    	return success();
    }
    
}
