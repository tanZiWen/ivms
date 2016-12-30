/**
 * 
 */
package com.prosnav.ivms.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.prosnav.ivms.model.FileModel;
import com.prosnav.ivms.model.News;
import com.prosnav.ivms.repository.ivm.FileRepository;
import com.prosnav.ivms.service.FileService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.FileView;

/**
 * @author wangnan
 *
 */
@Service
public class FileServiceImpl extends AbstractService implements FileService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${file.upload.dir}")
	private String defaultFilePath;
	
	@Autowired
	private FileRepository fileRepository;

	@Override
	public Map<String, Object> list(Map<String, Object> params,
			PageView pv) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("listFile");
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Criteria condition = Criteria.where("deleted").is(false);
		if(params != null) {
			if(params.get("fileView") != null) {
				FileView fileView = (FileView) params.get("fileView");
				if(!StringUtils.isEmpty(fileView.getTitle())) {
					Pattern pattern = Pattern.compile(fileView.getTitle(), Pattern.CASE_INSENSITIVE);
					condition.and("title").regex(pattern);
				}
				if(fileView.getSource() != null) {
					condition.and("source").is(fileView.getSource());
				}
				if(fileView.getType() != null) {
					condition.and("type").is(fileView.getType());
				}
			}
			if((int)params.get("relatedType") != 0 && !StringUtils.isEmpty(params.get("relatedName"))) {
				condition.and("relateds").elemMatch(Criteria.where("type").is((int)params.get("relatedType")).and("name").is(params.get("relatedName")));
			}else if((int)params.get("relatedType") != 0 && StringUtils.isEmpty(params.get("relatedName"))) {
				condition.and("relateds").elemMatch(Criteria.where("type").is((int)params.get("relatedType")));
			}else if((int)params.get("relatedType") == 0 && !StringUtils.isEmpty(params.get("relatedName"))){
				condition.and("relateds").elemMatch(Criteria.where("name").is(params.get("relatedName")));
			}
		}
		
		Page<FileModel> page = fileRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		
		List<FileView> files = new ArrayList<FileView>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(FileModel f : page.getContent()) {
			FileView fv = new FileView();
			fv.fromModel(f);
			fv.setModifierTimeStr(sdf.format(f.getModifyTime()));
			files.add(fv);
		}
		
		result.put("files", files);
		result.put("pager", pv);
		
		return result;
	}

	@Override
	public boolean save(FileView fileView, InputStream fileStream) {
		FileOutputStream fo = null;
		try {
			Date date = new Date();
			FileModel fm = fileView.toModel();
			if(fm.getId() == null || fm.getId() < 0) {
				fm.setId(fileRepository.nextId(idg));
				fm.setCreator(null);
				fm.setCreateTime(date);
			}
			fm.setModifier(null);
			fm.setModifyTime(date);
			
			fm = fileRepository.save(fm);
			
			if(fileStream != null) {
				File outFile = getAttachmentFile(fm);
				if(outFile == null) {
					return false;
				}
				if(outFile.exists()) {
					outFile.delete();
					fm.setFileUploaded(false);
					fm = fileRepository.save(fm);
				}
				fo = new FileOutputStream(outFile);
				IOUtils.copy(fileStream, fo);
				fm.setFileUploaded(true);
				fm = fileRepository.save(fm);
			}
			
			fileView.fromModel(fm);
			return true;
		} catch (Exception e) {
			logger.error("save file error.", e);
		} finally {
			IOUtils.closeQuietly(fo);
		}
		return false;
	}

	@Override
	public FileView get(Long id) {
		FileView fileView = new FileView();
		fileView.fromModel(fileRepository.findOne(id));
		return fileView;
	}

	@Override
	public boolean remove(Long id) {
		FileModel fm = fileRepository.findOne(id);
		fm.setDeleted(true);
		fileRepository.save(fm);
		return true;
	}

	@Override
	public InputStream getAttachmentStream(FileView fileView) {
		File attachmentFile = getAttachmentFile(fileView);
		if(attachmentFile != null && attachmentFile.exists()) {
			 try {
				return new FileInputStream(attachmentFile);
			} catch (FileNotFoundException e) {
				logger.error("get attachment stream failed.", e);
				return null;
			}
		}
		return null;
	}

	/**
	 * @param fm
	 * @return
	 */
	private File getAttachmentFile(FileModel fm) {
		String filePathStr = defaultFilePath;
		File filePath = new File(filePathStr);
		if(!filePath.exists()) {
			logger.error("save file error. folder not exists : " + filePath.getAbsolutePath());
			return null;
		}
		File outFile = new File(filePath, String.valueOf(fm.getId()) + ".file");
		return outFile;
	}
}
