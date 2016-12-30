package com.prosnav.ivms.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApplicationWebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Bean
	public FilterRegistrationBean characterEncodingFilterRegistrationBean() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setEncoding("UTF-8");
	    characterEncodingFilter.setForceEncoding(true);
	    registrationBean.setFilter(characterEncodingFilter);
	    registrationBean.setOrder(0);
	    return registrationBean;
	}
	
	@Bean
	public FilterRegistrationBean simpleCORSFilterRegistrationBean() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    SimpleCORSFilter filter = new SimpleCORSFilter();
	    registrationBean.setFilter(filter);
	    registrationBean.setOrder(1);
	    return registrationBean;
	}
	
	@Bean
	public FilterRegistrationBean authFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		AuthFilter filter = new AuthFilter();
		registrationBean.addInitParameter("exclude", env.getRequiredProperty("session.exclude"));
		registrationBean.setFilter(filter);
		registrationBean.setOrder(2);
		return registrationBean;	
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
	
	@Bean
	public HandlerExceptionResolver exceptionResolver() {
		return new CommonMappingExceptionResolver();
	}
}
