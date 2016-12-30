/**
 * 
 */
package com.prosnav.ivms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author wangnan
 *
 */
@Document(collection="file")
public class FileModel extends BaseBean implements IModel {
	
	@Id
	private Long id;
	private String title;
	private Integer source;
	private Integer type;
	private String fileName;
	private String fileContentType;
	private boolean fileUploaded = false;
	private Long fileSize;
	private String notes;
	private Long creator;
	private Date createTime;
	private Long modifier;
	private Date modifyTime;
	private boolean deleted = false;
	private List<FileModel.Related> relateds = new ArrayList<FileModel.Related>();
	
	public static class Related extends BaseBean {
		
		private Integer type;
		private Long relId;
		private String name;
		
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public Long getRelId() {
			return relId;
		}
		public void setRelId(Long relId) {
			this.relId = relId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	

	/* (non-Javadoc)
	 * @see com.prosnav.ivms.model.IModel#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see com.prosnav.ivms.model.IModel#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<FileModel.Related> getRelateds() {
		return relateds;
	}

	public void setRelateds(List<FileModel.Related> relateds) {
		this.relateds = relateds;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifier() {
		return modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public boolean isFileUploaded() {
		return fileUploaded;
	}

	public void setFileUploaded(boolean fileUploaded) {
		this.fileUploaded = fileUploaded;
	}

	@Override
	public String toString() {
		return "FileModel [id=" + id + ", title=" + title + ", source="
				+ source + ", type=" + type + ", fileName=" + fileName
				+ ", fileContentType=" + fileContentType + ", fileUploaded="
				+ fileUploaded + ", fileSize=" + fileSize + ", notes=" + notes
				+ ", creator=" + creator + ", createTime=" + createTime
				+ ", modifier=" + modifier + ", modifyTime=" + modifyTime
				+ ", deleted=" + deleted + ", relateds=" + relateds + "]";
	}
}
