package com.prosnav.ivms.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtil {
	static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);
    /** 
     *  
     * <p>Title: ObjTOSerialize</p> 
     * <p>Description: 序列化一个对象</p> 
     * @param obj 
     * @return 
     * @author guangshuai.wang 
     */  
    public static String ObjTOSerialize(Object obj){  
        ObjectOutputStream oos = null;  
        ByteArrayOutputStream byteOut = null;  
        try{  
            byteOut = new ByteArrayOutputStream();  
            oos = new ObjectOutputStream(byteOut);  
            oos.writeObject(obj);  
            byte[] bytes = byteOut.toByteArray();  
            return new String(bytes);  
        }catch (Exception e) {  
        	logger.error("对象序列化失败");  
        }  
        return null;  
    }  
    /** 
     *  
     * <p>Title: unSerialize</p> 
     * <p>Description: 反序列化</p> 
     * @param bytes 
     * @return 
     * @author guangshuai.wang 
     */  
    public static Object unSerialize(String str){  
        ByteArrayInputStream in = null;  
        try{  
            in = new ByteArrayInputStream(str.getBytes());  
            ObjectInputStream objIn = new ObjectInputStream(in);  
            return objIn.readObject();  
        }catch (Exception e) {  
        	logger.error("反序列化失败");  
        }  
        return null;  
    }  
}
