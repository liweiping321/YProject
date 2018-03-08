package com.game.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.mina.filter.codec.ProtocolCodecFactory;


/**
 * jar加载工具
 * @author jason.lin
 */
public class JarLoaderUtils {

	/**
	 * 在给定的jar路径下，找到某个类的实现对象
	 * @param path
	 * @param T
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public static <T> T load(String path, Class<T> T){
		URLClassLoader loader = null;
		JarFile jfile = null;
		try {
			File file = new File(path);
			URL url = file.toURL();
			loader = new URLClassLoader(new URL[]{url});
			
			jfile = new JarFile(path);;     
			Enumeration entries = jfile.entries();;     
			while(entries.hasMoreElements()){   
				// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
				JarEntry entry = (JarEntry)entries.nextElement();
				String name = entry.getName();
				name = name.replace("/", ".");
				if(!name.endsWith(".class")){
					continue;
				}
				
				name = name.substring(0, name.length() - ".class".length());
				Class<?> clazz = loader.loadClass(name);
				if(T.isAssignableFrom(clazz)){
					return (T)clazz.newInstance();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error("findImpl", e);
		}finally {
			if(loader != null){
				try {
					loader.close();
				} catch (IOException e) {
				}
			}
			
			if(jfile != null){
				try {
					jfile.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		ProtocolCodecFactory factory = load("E:\\1103\\TSServer\\testGame\\jar\\testGame.jar", ProtocolCodecFactory.class);
		System.out.println(factory != null);
	}
	
}
