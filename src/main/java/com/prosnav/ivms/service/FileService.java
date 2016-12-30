/**
 * 
 */
package com.prosnav.ivms.service;

import java.io.InputStream;
import java.util.Map;

import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.FileView;



/**
 * @author wangnan
 *
 */
public interface FileService {
	Map<String, Object> list(Map<String, Object> params, PageView pv);
	boolean save(FileView fileView, InputStream fileStream);
	FileView get(Long id);
	boolean remove(Long id);
	InputStream getAttachmentStream(FileView fileView);
}
