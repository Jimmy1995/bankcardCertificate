package utils;


import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;


public class APISecurityUtils {
	
	
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * <p>
	 * 生成密钥对(公钥和私钥)
	 * </p>
	 * bitsize: 1024/2048/...
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair(int bitsize) throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(bitsize);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

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
		}
		signature.update(data);
		return new String(Base64Utils.encode(signature.sign()));
	}

	public static String signSafe(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		try {
			signature.initSign(privateK);
		} catch (Exception e) {
		}
		signature.update(data);
		return new String(Base64Utils.encodeSafe(signature.sign()));
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

	public static byte[] decryptByPrivateKey(byte[] data, String modulus, String exponent, Provider provider)
			throws Exception {
		return decrypt(data, getPrivateKey(modulus, exponent), provider);
	}

	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(publicKey.getBytes());
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		return decrypt(encryptedData, publicK, null);
	}

	public static byte[] decryptByPublicKey(byte[] data, String modulus, String exponent, Provider provider)
			throws Exception {
		return decrypt(data, getPublicKey(modulus, exponent), provider);
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

	public static byte[] encryptByPublicKey(byte[] data, String modulus, String exponent, Provider provider)
			throws Exception {
		return encrypt(data, getPublicKey(modulus, exponent), provider);
	}

	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		return encrypt(data, keyFactory.generatePrivate(pkcs8KeySpec), null);
	}

	public static byte[] encryptByPrivateKey(byte[] data, String modulus, String exponent, Provider provider)
			throws Exception {
		return encrypt(data, getPrivateKey(modulus, exponent), provider);
	}

	/**
	 * <p>
	 * 获取私钥
	 * </p>
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return new String(Base64Utils.encode(key.getEncoded()));
	}

	/**
	 * <p>
	 * 获取公钥
	 * </p>
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return new String(Base64Utils.encode(key.getEncoded()));
	}

	//----------------------------------------------------------------------------------------------------
	/**
	 * 使用模和指数生成RSA公钥
	 * 
	 * @param modulus
	 *            模
	 * @param pubicExponent
	 *            指数
	 * @return
	 */
	public static RSAPublicKey getPublicKey(String modulus, String pubicExponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(pubicExponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 生成私钥
	 * 
	 * @param modulus
	 *            模
	 * @param privateExponent
	 * @return RSAPrivateKey
	 * @throws Exception
	 */
	public static RSAPrivateKey getPrivateKey(String modulus, String privateExponent) throws Exception {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(privateExponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			return null;
		}
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

	public static void main(String[] args) {
		String data = "{\"amount\":\"1500\",\"mchntCd\":\"027430187654321\",\"payeeComments\":\"测试收款码\",\"qrValidCount\":\"1\",\"qrValidTime\":\"60\"}";		
		System.out.println(data);
		String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCtFGOpRgGkjaQPFfRvqEBismsjzBppiTvXs7hvYBaBup54iVoPYYbFeE3SRLK3zOxIzJ8qradABZqUIOIxchYbTPjSoaR7wLDgb9pk1kmfB71HmofG9puySPTvpTVkCMacLZEehbpO4xx2j19Gv/p+xlBqBVIseNzTp82cjkpz7QIDAQAB";
		String prikey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK0UY6lGAaSNpA8V9G+oQGKyayPMGmmJO9ezuG9gFoG6nniJWg9hhsV4TdJEsrfM7EjMnyqtp0AFmpQg4jFyFhtM+NKhpHvAsOBv2mTWSZ8HvUeah8b2m7JI9O+lNWQIxpwtkR6Fuk7jHHaPX0a/+n7GUGoFUix43NOnzZyOSnPtAgMBAAECgYBcQ5o9Ckyl47upLxL20sI/2syycIND7xwviGaxOI/G6CzCJLYVrO+jJNaXWHfM8ziiNjJDFf8qadJVVJI/uYl+cy91TyoiC2O5CFkSsNSgl5S/E/5Uc0wrv151NX6om8SLzeJF4dESu7ivuvUmSAZYhmmH9cGsLt/vjRNxTQRVAQJBAOJHJcRLATJn3j5hhueaLVrGICEAdxFTKPmLfbUx1kWBcsOU+Ibq9W/DVh3WRzA9sY7linsfpOBKLkwrtNGQM/kCQQDD0GOoERKFVRxeAk/g6g4bBHiZ9xmZqDavxPpwIdD73FmGQS1LBomaMGsKvGZ2fxG1ylImaWKaERqfb4CtYtSVAkBGdzOaqmToBpKeSI7TZx8CqrpsrJFn0sbq13bBS5DXulU79RNkKJ1gPat+xTEMI9o8jt0ONK+KrW83h1DbBhY5AkEAsCtCHakOcqq6FNIbr4ykGCaTomGvxJCUctrTPiMOdCow2Rq2dzNwhSpeg5Aw1xdHhbh65FgX/+i3fQ3CRTwPaQJATndht+4F+iiZtnamuHshoutV4lHGxVxaG0T0ycDrZMiHJKvRFDlBPq2XhhBMt8pqFZcrGNfQ63xhkk5PJFgerw==";
		//String sign = "JSK1uPT44S+n2usFId/qWo99CKBKt+8gEVvshGZ3hw+ZKGPP1iB3GdnGTyQy71k0l6RS3kszX78F/GP/lYigAcaUhbWwjPE/ObQeEF+Mf4rGAcbHSlAMHIuf/4Evfc25mXW+CpSndJjakqYZGBQPJrcMSbuywAZswk1grWBddMA=";
		try {
			//String sign1 = APISecurityUtils.sign(data.getBytes("utf-8"), prikey);
			byte[] endata=encryptByPrivateKey(data.getBytes(), prikey);
			System.out.println(new String(decryptByPublicKey(endata, pubkey)));
			//System.out.println(APISecurityUtils.verify(data.getBytes("utf-8"), pubkey, sign));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}