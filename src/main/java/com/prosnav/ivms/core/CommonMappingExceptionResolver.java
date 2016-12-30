/**
 * 
 */
package com.prosnav.ivms.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @author wangnan
 *
 */
public class CommonMappingExceptionResolver extends
		SimpleMappingExceptionResolver {
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
	    if(exception instanceof MultipartException) {
	    	response.setStatus(500);
	        ModelAndView model = new ModelAndView();
	        model.addObject("message", "Max filesize exceeded, please ensure file size is too large.");
	        model.setView(new MappingJackson2JsonView());;
	        return model;
	    } else {
	        return super.resolveException(request, response, handler, exception);
	    }
	}
}
