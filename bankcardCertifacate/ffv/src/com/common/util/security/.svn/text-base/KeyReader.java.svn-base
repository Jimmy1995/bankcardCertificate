package com.common.util.security;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class KeyReader {

	@SuppressWarnings("rawtypes")
	public static PrivateKey readPrivateKeyfromPKCS12StoredFile(String resourceName,
			String password) throws Exception {
		InputStream istream = null;
		istream = new FileInputStream(resourceName);
		KeyStore keystore = KeyStore.getInstance("PKCS12");
		char[] a = password.toCharArray();
		keystore.load(istream, a);
		Enumeration enumeration = keystore.aliases();
		PrivateKey key = null;
		for (int i = 0; enumeration.hasMoreElements(); i++) {
			if (i >= 1) {
				System.out.println("此文件中含有多个证书!");
			}
			key = (PrivateKey) keystore.getKey(enumeration.nextElement()
					.toString(), password.toCharArray());
			if (key != null)
				return key;
		}
		return key;

	}
	
	 /**
     * <p>
     * 根据证书获得公钥
     * </p>
     * 
     * @param certificatePath 证书存储路径
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String certificatePath)
            throws Exception {
    	CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate)certificateFactory.generateCertificate(new FileInputStream(certificatePath));
        PublicKey publicKey = certificate.getPublicKey();
        return publicKey;
    }

}