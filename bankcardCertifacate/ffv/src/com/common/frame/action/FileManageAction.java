package com.common.frame.action;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.base.BaseAction;
import com.common.frame.model.FileInfo;
import com.common.frame.service.IFileInfoManageService;
import com.common.util.DateUtil;
import com.common.util.FileUtils;
import com.common.web.filter.init.InitManage;
@Namespace("/manage")
@Results({@Result(name = "error", location = "/manage/login.jsp") })
public class FileManageAction extends BaseAction<FileInfo>{
	private static final long serialVersionUID = 1967784742700949147L;
	private File uploadify;//jquery uploadify 上传用的,得到文件
	private String uploadifyFileName;//jquery uploadify 上传用的，得到文件名字
	@Autowired
	private IFileInfoManageService fileInfoManageService;
	//@Resource(name="JBPMHandler")
	//private JBPMHandler jbpmUtil;//jbpm工作流工具类
	@Override
	public String list() throws Exception {
		writeJsonReturn("",fileInfoManageService.query(obj));
		return null;
	}
	/**
	 * 上传附件
	 */
	public void upload() throws Exception{
		String path1=DateUtil.date()+"";
		String newName=UUID.randomUUID()+"";
		obj.setFilePath(path1+File.separator+newName);
		obj.setFileName(uploadifyFileName);
		obj.setCreateTime(new Date());
		boolean flag=FileUtils.saveFile(InitManage.getProperty("uploadRoot")+File.separator+path1,newName, uploadify);
		if(flag){
			fileInfoManageService.save(obj);
			writeJsonReturn("上传成功！", obj.getFileId());
		}
	}
	/**
	 * 根据FileInfo表的ID下载附件
	 */
	public void download() {
		FileInfo fileInfo=fileInfoManageService.findById(obj.getFileId());
		String path=InitManage.getProperty("uploadRoot")+File.separator+fileInfo.getFilePath();//得到路径
		String fileName=fileInfo.getFileName();//文件名字
		FileUtils.download(new File(path), fileName, response);
	}
	
	
	public void showImg(){
		FileInfo fileInfo=fileInfoManageService.findById(obj.getFileId());
		String path=InitManage.getProperty("uploadRoot")+File.separator+fileInfo.getFilePath();//得到路径
		FileInputStream in=null;
		try {
			in = new FileInputStream(new File(path));
			getResponse().reset(); // 清空输出流
			response.setHeader("Pragma","No-cache");
			response.setHeader("Cache-Control","no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			BufferedImage image = ImageIO.read(in);
			if("true".equals(getParameter("thum"))){//取缩略图
				int y=70;
				if(!StringUtils.isEmpty(getParameter("y"))){//是否指定了高度
					y=Integer.parseInt(getParameter("y"));
				}
				Double xx=y*((double)image.getWidth()/image.getHeight());//根据图片原来的宽高比，算出宽度
				int x=Integer.parseInt(new java.text.DecimalFormat("0").format(xx));
				BufferedImage bi_scale = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB );
				Graphics2D g = bi_scale.createGraphics();
				g.drawImage( image, 0, 0, x, y, null );
				g.dispose();
				ImageIO.write(bi_scale, "JPEG", response.getOutputStream());
			}else{// 输出图象到页面
				ImageIO.write(image, "JPEG", response.getOutputStream());
			}
		} catch (Exception e) {
			try {
				if(in!=null)in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * 部署jbpm流程文件
	
	public void deployZipNew(){
		//System.out.println(uploadify.getPath());
		String id=jbpmUtil.deployZipNew(uploadify.getPath(), uploadifyFileName);//返回流程定义id
		Struts2Utils.renderText(id);
	} */
	/**
	 * @return the uploadify
	 */
	public File getUploadify() {
		return uploadify;
	}
	/**
	 * @param uploadify the uploadify to set
	 */
	public void setUploadify(File uploadify) {
		this.uploadify = uploadify;
	}
	/**
	 * @return the uploadifyFileName
	 */
	public String getUploadifyFileName() {
		return uploadifyFileName;
	}
	/**
	 * @param uploadifyFileName the uploadifyFileName to set
	 */
	public void setUploadifyFileName(String uploadifyFileName) {
		this.uploadifyFileName = uploadifyFileName;
	}
}
