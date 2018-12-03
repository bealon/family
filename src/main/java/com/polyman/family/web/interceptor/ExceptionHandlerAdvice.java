package com.polyman.family.web.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.polyman.common.base.utils.ResultBuilder;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

	/**
	 * Handle exceptions thrown by handlers.
	 */
	@ExceptionHandler(Exception.class)
	public Object exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		logger.error("exception:" + ex.getLocalizedMessage(), ex);
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().write(JSON.toJSONString((ResultBuilder.genExpResult(ex))));
		} catch (IOException e) {
			logger.error("异常返回出错" + e.getMessage(),e);	    
		}
		return new ModelAndView();
	}
}
