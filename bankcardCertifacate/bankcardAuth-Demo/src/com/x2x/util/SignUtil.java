package com.x2x.util;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SignUtil {
	private static Logger logger = Logger.getLogger(SignUtil.class);
	
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;
	
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;
	
	//商户私钥（用于请求数据签名、响应数据解密）   
	public final static String prikey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMRzrGTTKdIoUrtFA8sZje0+AUBrgoLd1MN31uVoIjVSP7i0ekIEWkpEwCYM+Uz61yfQKmHNQPKlGsmpzhIfIwtlqb24/LZIx+Mo4gEbzC9JyPZigObPhkPLF0tmOgECBe396SwaHAFlfAnJy/eG1e65Fwaj/4c0h3bZxzoOUc6rAgMBAAECgYBvO9/9JuOjL1nI14cSVOHb1yP2SemYF+sE0rJHKvNTpcKW3vnqr3RAYTU7VlVClTWGQYP95K5Ftd65GQCpmh/Isk2FqWNbd8Ts1NufmicNedjYf7Z4wgAkStn2ZQA89GTdRSWPeAIWGF6a/gYHfV2FYI0CN626x+lqaQEmqwaooQJBAOD+Jeag0hgQeunw2Qjar6NzfRzotOaUizH/L2nAUy0EjB1yW0nXpAoOLFjTSByUFlajFCUGjbgFXxC+9crEjC0CQQDfhpaWY0DcWvyq8jrFcBuOVnNyB57vXiBj5x6PSnNFVSZQCg8cziH0mtl2mAJQpFdHwIEvhsvqXO8Q1ny/+BU3AkAKZhSwXRx4ukJED7qoOxtFDbBppqO9yH3KeMXjOF1fxkcHkWKAvjO5tz/7dwBtObbymCT1NSFVsQHcz0ai8YSdAkB7WAVYz0RHVF4A5tHPiWFrVgE2d0YYyFQPTMXsRCT/qVEO8b4NIyJRm/FMI+2DGmyfR3cCBtwXY1j4baNBO2BZAkEAt4wFZA/ZozOPOmgHqGJMnohJy8lmbEwWLBncUnTQR0FHh8ZN9YQQNr8jWouBAAkD0Rruvvgr0OEatJgpnzsTBQ==";
	//平台公钥(平台和商户互相交换了公钥，用于请求数据加密，响应数据验签)
	public final static String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgg9VQS2M4Ztg04QmhAqoZfIDzMwU3tiAqGnVAPQEc4WwJ7z41hKqXvHaHSt8E2Rx/a1qWwId7DpdPFWBhYFnAraqNC+tnSsCeO+W0aZv/NXR912SsfKJMiWkdBn7jIddbd23kuUjzKNKV7HgTbDKiehU1f9z/vKZB/1ELbf6oaQIDAQAB";
	
	/**
	 * <p>
	 * 用私钥对信息生成数字签名
	 * </p>
	 * 
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		try {
			signature.initSign(privateK);
		} catch (Exception e) {
			logger.warn("{}", e);
		}
		signature.update(data);
		return new String(Base64Utils.encode(signature.sign()));
	}
	
	/**
	 * 加密
	 * @param data
	 * @param mchid
	 * @return
	 */
	public static String encodeData(String dataxml,String mchid){
		if(StringUtils.isBlank(dataxml))return "";
		String subMchPubKey=pubkey;//TODO:商户用平台公钥加密数据
		try {
			byte[] datastr = encryptByPublicKey(dataxml.toString().getBytes("UTF-8"), subMchPubKey);
			return new String(Base64Utils.encode(datastr),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("加密失败");
		}
	}
	
	/**
	 *	解密 
	 * @return
	 */
	public static String decodeData(String dataxml){
		if(StringUtils.isBlank(dataxml))return "";
		try {
			byte[] data=decryptByPrivateKey(Base64Utils.decode(dataxml.getBytes("utf-8")), prikey);
			return new String(data,"utf-8");
		} catch (Exception e) {
			throw new BaseException("解密失败");
		}
	}
	
	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(publicKey.getBytes());
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		return encrypt(data, keyFactory.generatePublic(x509KeySpec), null);
	}
	
	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		return decrypt(encryptedData, privateK, null);
	}
	
	/**
	 * <p>
	 * 校验数字签名
	 * </p>
	 * 
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		byte[] keyBytes = Base64Utils.decode(publicKey.getBytes());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64Utils.decode(sign.getBytes()));
	}
	
	public static byte[] encrypt(byte[] data, Key key, Provider provider) throws Exception {
		Cipher cipher = null;
		ByteArrayOutputStream out = null;
		try {

			// 对数据加密 
			if (provider == null) cipher = Cipher.getInstance(KEY_ALGORITHM);
			else
				cipher = Cipher.getInstance(KEY_ALGORITHM, provider);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			int blockSize = cipher.getBlockSize();
			if (blockSize <= 0) blockSize = MAX_ENCRYPT_BLOCK;

			//int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			int inputLen = data.length;
			out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > blockSize) {
					cache = cipher.doFinal(data, offSet, blockSize);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * blockSize;
			}
			byte[] encryptedData = out.toByteArray();
			return encryptedData;
		} finally {
			cipher = null;
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
				out = null;
			}
			provider = null;
		}
	}
	
	public static byte[] decrypt(byte[] encryptedData, Key key, Provider provider) throws Exception {
		Cipher cipher = null;
		ByteArrayOutputStream out = null;
		try {

			if (provider == null) cipher = Cipher.getInstance(KEY_ALGORITHM);
			else
				cipher = Cipher.getInstance(KEY_ALGORITHM, provider);

			cipher.init(Cipher.DECRYPT_MODE, key);

			int blockSize = cipher.getBlockSize();
			if (blockSize <= 0) blockSize = MAX_DECRYPT_BLOCK;

			int inputLen = encryptedData.length;
			out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > blockSize) {
					cache = cipher.doFinal(encryptedData, offSet, blockSize);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * blockSize;
			}
			byte[] decryptedData = out.toByteArray();

			return decryptedData;
		} finally {
			cipher = null;
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
				out = null;
			}
			provider = null;
		}
	}
	
	
	/** 
     * xml转map 不带属性 
     * @param xmlStr 
     * @param needRootKey 是否需要在返回的map里加根节点键 
     * @return 
     * @throws DocumentException 
     */  
	public static Map xml2map(String xmlStr, boolean needRootKey)
			throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		Map<String, Object> map = (Map<String, Object>) xml2map(root);
		if (root.elements().size() == 0 && root.attributes().size() == 0) {
			return map;
		}
		if (needRootKey) {
			// 在返回的map里加根节点键（如果需要）
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put(root.getName(), map);
			return rootMap;
		}
		return map;
	}
	
	/** 
     * xml转map 不带属性 
     * @param e 
     * @return 
     */ 
	private static Map xml2map(Element e) {
		Map map = new LinkedHashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = xml2map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!(obj instanceof List)) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj instanceof List) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!(obj instanceof List)) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj instanceof List) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}
	

}
