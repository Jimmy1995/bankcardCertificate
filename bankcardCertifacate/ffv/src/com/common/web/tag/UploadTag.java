package com.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class UploadTag extends TagSupport {
	private static final long serialVersionUID = 6595078155562483844L;
	private String fileDesc;
	private String fileType;
	private Integer fileSize;
	private String tagId;
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doEndTag();
	}
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doStartTag();
	}
	/**
	 * @return the fileDesc
	 */
	public String getFileDesc() {
		return fileDesc;
	}
	/**
	 * @param fileDesc the fileDesc to set
	 */
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * @return the fileSize
	 */
	public Integer getFileSize() {
		return fileSize;
	}
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * @return the tagId
	 */
	public String getTagId() {
		return tagId;
	}
	/**
	 * @param tagId the tagId to set
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
}
