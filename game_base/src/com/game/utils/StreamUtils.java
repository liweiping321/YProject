package com.game.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


/**
 * 数据流工具
 * @author jason.lin
 *
 */
public class StreamUtils {

	/**
	 * 写入到文件
	 * @param data
	 * @param path
	 * @return
	 */
	public static boolean writeToFile(byte[] data, String path){
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(path));
			fos.write(data);
			return true;
		} catch (Exception e) {
			LogUtils.error("path:" + path, e);
		} finally {
			close(fos);
		}
		return false;
	}
	
	/**
	 * 写入到文件
	 * @param data
	 * @param path
	 * @return
	 */
	public static byte[] readFromFile(String path){
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(path));
			byte[] data = new byte[fis.available()];
			fis.read(data);
			return data;
		} catch (Exception e) {
			LogUtils.error("path:" + path, e);
		} finally {
			close(fis);
		}
		return null;
	}
	
	
	/**
	 * 将对象转换成字节数组
	 * @param obj
	 * @return
	 */
	public static byte[] changeToBytes(Object obj){
	    ByteArrayOutputStream bo = null;
	    ObjectOutputStream oo = null;
	    try {  
	        bo = new ByteArrayOutputStream();  
	        oo = new ObjectOutputStream(bo);  
	        oo.writeObject(obj);  
	        return bo.toByteArray();  
	    } catch (Exception e) {
			LogUtils.error("obj: " + obj, e);
		} finally {
			close(bo);
			close(oo);
		}
	    return null;  
	}
	
	/***
	 * 将字节转换成对象
	 * @param bytes
	 * @param T
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T changeToObj(byte[] bytes, Class<T> T){
		ByteArrayInputStream bi = null;
		ObjectInputStream oi = null;
		try {
			System.err.println("changeToObj:"+bytes.length);
			bi = new ByteArrayInputStream(bytes);  
			oi = new ObjectInputStream(bi);  
			return (T)oi.readObject();
		} catch (Exception e) {
			LogUtils.error("class " + T.getName(), e);
		} finally {
			close(bi);
			close(oi);
		}
		return null;
	}
	
	/**
	 * 关闭流
	 * @param stream
	 */
	public static void close(InputStream stream){
		try {
			if(stream != null){
				stream.close();
			}
		} catch (IOException e) {
			LogUtils.error(null, e);
		}
	}
	
	/**
	 * 关闭流
	 * @param stream
	 */
	public static void close(OutputStream stream){
		try {
			if(stream != null){
				stream.close();
			}
		} catch (IOException e) {
			LogUtils.error(null, e);
		}
	}
}
