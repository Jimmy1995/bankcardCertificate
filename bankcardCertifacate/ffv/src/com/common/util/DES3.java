package com.common.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class DES3 {
	// 向量
	private final static String iv = "01234567";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";
	/** 
	 * 3DES解密
	 * @param encryptText 加密文本
	 * @return key
	 * @throws Exception
	 */
	public static String decode(String encryptText, String key) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
		return new String(decryptData, encoding);
	}
	/**
	 * 3DES加密
	 * @param src  待加密的文本
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encode(String src,String key) throws Exception{  
        DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(encoding));  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");  
        SecretKey securekey = keyFactory.generateSecret(dks);  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, securekey,ips);  
        byte[] b=cipher.doFinal(src.getBytes());  
        return Base64.encode(b).replaceAll("\r", "").replaceAll("\n", "");  
    }
}
