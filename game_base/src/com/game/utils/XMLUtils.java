package com.game.utils;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class XMLUtils {
	
	public static <T> T readXml(String fileName,Class<T> clazz)throws Exception{
		 Serializer serializer = new Persister();
		 return serializer.read(clazz, new File(fileName));
	}
}
