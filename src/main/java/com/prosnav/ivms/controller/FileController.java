/**
 * 
 */
package com.prosnav.ivms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prosnav.ivms.model.FileModel;
import com.prosnav.ivms.service.FileService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.FileView;

/**
 * @author wangnan
 *
 */
@RestController
public class FileController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${multipart.maxFileSize}")
	private String maxFileSize;
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value="/file/{id}", method=RequestMethod.GET)
	public Map<String, Object> getFile(@PathVariable Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("file", fileService.get(id));
		return resultMap;
	}
	
	@RequestMapping(value="/files", method=RequestMethod.GET)
	public Map<String, Object> searchFiles(HttpServletRequest request, @ModelAttribute FileView fileView, @RequestParam String relatedName, @RequestParam int relatedType) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("searchFiles");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fileView", fileView);
		params.put("relatedName", relatedName);
		params.put("relatedType", relatedType);
		PageView page = new PageView(request);
		
		Map<String, Object> result = fileService.list(params, page);
		return result;
	}
	
	@RequestMapping(value="/file", method=RequestMethod.POST)
	public String saveFile(
			@RequestPart FileView fileView, 
			@RequestPart(value="file", required=false) MultipartFile file, 
			HttpServletRequest request) {
		
		String result = "";
		boolean success = true;
		try {
			InputStream fileStream = null;
			if(file != null) {
				fileView.setFileName(file.getOriginalFilename().replaceAll(",", " "));
				fileView.setFileContentType(file.getContentType());
				fileView.setFileSize(file.getSize());
				fileStream = file.getInputStream();
				if(fileView.getTitle() != null) {
					fileView.setTitle(fileView.getTitle().replaceAll(",", " "));
				}
			}
			success = fileService.save(fileView, fileStream);
		} catch (IOException e) {
			success = false;
			logger.error("upload file " + file.getName() + " failed.", e);
		}
		if(success) {
			result = "Successed save file " + fileView.getTitle();
		} else {
			result = "Failed save file " + fileView.getTitle();
		}
		return result;
	}
	
	@RequestMapping(value="/file/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> removeFile(@PathVariable Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(fileService.remove(id)) {
			resultMap.put("message", "Remove file successed");
		} else {
			resultMap.put("message", "Remove file failed");
		}
		return resultMap;
	}
	
	@RequestMapping(value="/file/{id}/attachment", method={RequestMethod.GET, RequestMethod.POST})
	public void download(@PathVariable Long id, HttpServletResponse response) {
		response.reset();
		boolean download = true;
		FileView fileView = fileService.get(id);
		InputStream fileStream = null;
		OutputStream os = null;
		
		try {
			if(fileView != null) {
				fileStream = fileService.getAttachmentStream(fileView);
				if(fileStream == null) {
					download = false;
				} 
			} else {
				download = false;
			}
			
			String name = null;
			String contentType = null;
			if(!download) {
				name = "noting";
			} else {
				name = fileView.getFileName();
				contentType = fileView.getFileContentType();
			}
			if(StringUtils.isEmpty(name)) {
				name = fileView.getTitle();
			}
			if(StringUtils.isEmpty(contentType)) {
				contentType = "application/octet-stream";
			}
			try {
				response.setHeader("Content-Disposition", "attachment; filename=" + new String(name.getBytes("UTF8"), "ISO8859-1" ));
			} catch (UnsupportedEncodingException e) {
				logger.error("encoding file name failed", e);
				response.setHeader("Content-Disposition", "attachment; filename=" + name);
			}  
	        response.setContentType(contentType + "; charset=utf-8"); 
        
			os = response.getOutputStream();
			IOUtils.copy(fileStream, os);
			os.flush();
		} catch (IOException e) {
			logger.error("down attachment failed.", e);
		} finally {  
            IOUtils.closeQuietly(os); 
            IOUtils.closeQuietly(fileStream);
        } 
	}
}
