package com.polyman.family.web.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import com.polyman.common.base.utils.XssShieldUtil;


@ControllerAdvice(basePackages = "com.polyman.family.web.controller")
public class XssRequestBodyAdvice implements RequestBodyAdvice {

	
	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		InputStream in = inputMessage.getBody();
		HttpHeaders header=inputMessage.getHeaders();
		String refer=header.getFirst(HttpHeaders.REFERER);

		String json =StreamUtils.copyToString(in, Charset.forName("utf-8"));
        if (!StringUtils.hasText(json)) {
            return inputMessage;
        }
        json = XssShieldUtil.stripXss(json);
/*		if(refer.indexOf("/unionpay/content/")<0) {
	        json = XssShieldUtil.stripXss(json);
		}*/
        try {
            return new MyHttpInputMessage(inputMessage,json);
        } catch (Exception e) {
            e.printStackTrace();
            return inputMessage;
        }
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return body;
	}

	
	class MyHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public MyHttpInputMessage(HttpInputMessage inputMessage,String Json) throws Exception {
            this.headers = inputMessage.getHeaders();
            this.body=new ByteArrayInputStream(Json.getBytes("UTF-8"));
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
