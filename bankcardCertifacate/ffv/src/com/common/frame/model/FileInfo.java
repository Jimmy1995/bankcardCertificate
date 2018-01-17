package com.common.frame.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

public class FileInfo implements Serializable{
	private static final long serialVersionUID = -5346590316606128147L;
	// Fields
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name="idGenerator", strategy="native") //这个是hibernate的注解
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略
	@Column(name="fileId")
	private Long fileId;
	@Column(name="fileName")
	private String fileName;
	@Column(name="description",length=255)
	private String description;
	@Column(name="filePath",length=250)
	private String filePath;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime",nullable=false)
	private Date createTime=new Date();//注册时间
	@Column(name="ect",length=250)
	private String ect;
	@Column(name="ect1",length=250)
	private String ect1;
	@Column(name="ect2",length=250)
	private String ect2;
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getEct() {
		return ect;
	}
	public void setEct(String ect) {
		this.ect = ect;
	}
	public String getEct1() {
		return ect1;
	}
	public void setEct1(String ect1) {
		this.ect1 = ect1;
	}
	public String getEct2() {
		return ect2;
	}
	public void setEct2(String ect2) {
		this.ect2 = ect2;
	}
	public FileInfo(Long fileId, String fileName, String description,
			String filePath, Date createTime, String ect, String ect1,
			String ect2) {
		super();
		this.fileId = fileId;
		this.fileName = fileName;
		this.description = description;
		this.filePath = filePath;
		this.createTime = createTime;
		this.ect = ect;
		this.ect1 = ect1;
		this.ect2 = ect2;
	}
	public FileInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileInfo other = (FileInfo) obj;
		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		return true;
	}
	
	
	
}
