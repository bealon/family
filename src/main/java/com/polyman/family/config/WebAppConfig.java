package com.polyman.family.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;


@Configuration
public class WebAppConfig extends WebMvcConfigurationSupport {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				// .allowedOrigins("http://192.168.1.97")
				.allowedOrigins("*").allowedMethods("GET", "POST").allowedHeaders("*").allowCredentials(true)
				.maxAge(3600);
	}


	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(fastJsonHttpMessageConverter());
	}

	@Bean
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(
				SerializerFeature.PrettyFormat, 
				SerializerFeature.WriteNullStringAsEmpty, 
		        SerializerFeature.WriteNullListAsEmpty,
		        SerializerFeature.WriteMapNullValue,
		        SerializerFeature.WriteDateUseDateFormat,
		        SerializerFeature.WriteNullBooleanAsFalse
				);
		// 处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);

		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		return fastConverter;
	}

}
