package com.x2x.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	private MessageDigest md;
	private static MD5 md5;

	private MD5() {
		try {
			md = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("没有这种算法");
		}
	}

	// 产生一个MD5实例
	public static MD5 getInstance() {
		if (null != md5)
			return md5;
		else {
			makeInstance();
			return md5;
		}
	}

	// 保证同一时间只有一个线程在使用MD5加密
	private static synchronized void makeInstance() {
		if (null == md5)
			md5 = new MD5();
	}

	public String createMD5(String pass) {
		if(pass==null){
			pass="";
		}
		md.update(pass.getBytes());
		byte[] b = md.digest();
		return byteToHexString(b);
	}
	   /**
	    * 把byte[]数组转换成十六进制字符串表示形式
	    * @param b    要转换的byte[]
	    * @return 十六进制字符串表示形式
	    */
	private static String byteToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String temp = "";// 用字节表示就是 16 个字节
		// 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16 进制需要 32 个字符,// i表示转换结果中对应的字符位置
		//从第一个字节开始，对 MD5 的每一个字节 转换成 16 进制字符的转换
		for (int i = 0; i < b.length; i++) {
			temp = Integer.toHexString(b[i] & 0Xff);
			if (temp.length() == 1)
				temp = "0" + temp;
			sb.append(temp);
		}
		return sb.toString();
	}
	   /**
	    * 对文件全文生成MD5摘要
	    * @param file  要加密的文件
	    * @return MD5摘要码
	    */
	public static String getMD5(File file) {
	      FileInputStream fis = null;
	      try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        fis = new FileInputStream(file);
	        byte[] buffer = new byte[2048];
	        int length = -1;
	        while ((length = fis.read(buffer)) != -1) {
	           md.update(buffer, 0, length);
	        }
	        byte[] b = md.digest();
	        return byteToHexString(b);
	      } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	      } finally {
	        try {
	        	if(fis!=null)
	           fis.close();
	        } catch (IOException ex) {
	           ex.printStackTrace();
	        }
	      }
	   }

	//public static void main(String[] args) {
		//System.out.println(MD5.getInstance().createMD5(getMD5(new File("c:/a.txt"))));
	//}
}
