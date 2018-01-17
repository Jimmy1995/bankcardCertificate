package com.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjUtlis {
	public static Object clone(Object objx) {
        Object obj = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(objx);
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try{
	        	ois.close();
	        	bis.close();
	        	oos.close();
	        	bos.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        return obj;
    }
}
