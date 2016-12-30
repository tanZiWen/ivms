/**
 * 
 */
package com.prosnav.ivms.vo;

import com.prosnav.ivms.model.FileModel;

/**
 * @author wangnan
 *
 */
public class FileView extends FileModel {
	
	private String modifierName;
	private String modifierTimeStr;
	
	public FileModel toModel() {
		FileModel f = new FileModel();
		copyTo(f);
		return f;
	}
	
	public void fromModel(FileModel f) {
		copyFrom(f);
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	public String getModifierTimeStr() {
		return modifierTimeStr;
	}

	public void setModifierTimeStr(String modifierTimeStr) {
		this.modifierTimeStr = modifierTimeStr;
	}
}
