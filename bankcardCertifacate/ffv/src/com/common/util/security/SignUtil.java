package com.common.util.security;

import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SignUtil {
	static {
		org.apache.xml.security.Init.init();
		try {
			Constants.setSignatureSpecNSprefix("ds");
		} catch (XMLSecurityException e) {
			System.out
					.println("org.apache.xml.security.utils.Constants初始化出错！");
			throw new RuntimeException(e);
		}
	}

	/**
	 * 签名
	 * @param doc
	 * @param privateKey
	 * @param tranName
	 * @param ifFullXml
	 * @return
	 */
	public static String signXml(Document doc, PrivateKey privateKey,
			String tranName, boolean ifFullXml) {
		try {
			XMLSignature sig = new XMLSignature(doc, null,XMLSignature.ALGO_ID_SIGNATURE_RSA);
			Node messageNode = doc.getElementsByTagName(tranName).item(0);
			messageNode.appendChild(sig.getElement());
			Transforms transforms = new Transforms(doc);
			transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
			if (ifFullXml) {
				sig.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);
			} else {
				sig.addDocument("#" + tranName, transforms,Constants.ALGO_ID_DIGEST_SHA1);
			}

			sig.sign(privateKey);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			XMLUtils.outputDOM(doc, os);
			String result=os.toString("utf-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] hexToByte(String inbuf) {
		int i;
		int len = inbuf.length() / 2;
		byte outbuf[] = new byte[len];
		for (i = 0; i < len; i++) {
			String tmpbuf = inbuf.substring(i * 2, i * 2 + 2);
			outbuf[i] = (byte) Integer.parseInt(tmpbuf, 16);
		}
		return outbuf;
	}

	public static String byteToHex(byte[] inbuf) {
		int i;
		String byteStr;
		StringBuffer strBuf = new StringBuffer();

		for (i = 0; i < inbuf.length; i++) {
			byteStr = Integer.toHexString(inbuf[i] & 0x00ff);
			if (byteStr.length() != 2) {
				strBuf.append('0').append(byteStr);
			} else {
				strBuf.append(byteStr);
			}
		}
		return new String(strBuf);
	}
	/**
	 * 验签
	 * @param doc
	 * @param pubKey
	 * @throws Exception
	 */
	public static boolean checking(Document doc,PublicKey pubKey) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		NodeList nl = doc.getElementsByTagNameNS(javax.xml.crypto.dsig.XMLSignature.XMLNS, "Signature");
		if (nl.getLength() == 0) {
			throw new Exception("没有加密节点");
		}
		Node signatureNode = nl.item(0);

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
		javax.xml.crypto.dsig.XMLSignature signature = fac.unmarshalXMLSignature(new DOMStructure(signatureNode));
		DOMValidateContext valCtx = new DOMValidateContext(pubKey,signatureNode);
		boolean coreValidity = signature.validate(valCtx);
		if (coreValidity == false) {
			System.err.println("签名验证失败");
			boolean sv = signature.getSignatureValue().validate(valCtx);
			System.out.println("Signature validation status: " + sv);
			/*List refs = signature.getSignedInfo().getReferences();
			for (int i = 0; i < refs.size(); i++) {
				Reference ref = (Reference) refs.get(i);
				boolean refValid = ref.validate(valCtx);
				System.out.println("Reference[" + i + "] validity status: "+ refValid);
			}*/
			return false;
		} else {
			System.out.println("签名验证通过");
			return true;
		}
	}
	public static void main(String[] args) throws Exception{
		KeyReader keyReader = new KeyReader();
		PrivateKey priKey = keyReader.readPrivateKeyfromPKCS12StoredFile("c:/123.pfx","123456");
		String reqXml="<btreq><name>longzy</name><age>123</age></btreq>";
		Document doc = DocumentUtil.parseXmlData(reqXml);
		NodeList nodeList = doc.getChildNodes();
		Node node = nodeList.item(0);
		String tagName = node.getNodeName();
		System.out.println(tagName+"------------");
		reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+SignUtil.signXml(doc, priKey, tagName, true);
		System.out.println("==================签章===================");
		System.out.println(reqXml);
		
		System.out.println("==================验章===================");
		PublicKey publicKey=keyReader.getPublicKey("c:/123.cer");
		Document doc1 = DocumentUtil.parseXmlData(reqXml);
		SignUtil.checking(doc1,publicKey);
	}
}
