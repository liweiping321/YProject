 
package com.game.generate.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author lip.li
 * @date 2017年1月9日
 */
public class TypeUtils {
	   private static Map<Integer , String> jdbcJavaTypeMaps = new HashMap<Integer , String>() ;  
	   
	   private static Map<String , String> primitiveWrapperTypes = new HashMap<String , String>() ; 
	   
	   private static Map<String,String> importTypes=new HashMap<String,String>();
	      
	    static {  
	        initJdbcJavaTypeMaps(); 
	        
	        initPrimitiveWrapperTypes();
	        
	        initImportTypes();
	    }

		private static void initJdbcJavaTypeMaps() {
			
	        jdbcJavaTypeMaps.put(Types.BIGINT, "Long");  
	        jdbcJavaTypeMaps.put(Types.BINARY, "byte[]");  
	        jdbcJavaTypeMaps.put(Types.BIT, "Boolean");
	        jdbcJavaTypeMaps.put(Types.CHAR, "String");  
	        jdbcJavaTypeMaps.put(Types.DECIMAL, "Double");  
	        jdbcJavaTypeMaps.put(Types.DOUBLE, "Double");  
	        jdbcJavaTypeMaps.put(Types.INTEGER, "Integer");  
	        jdbcJavaTypeMaps.put(Types.LONGVARBINARY, "byte[]");  
	        jdbcJavaTypeMaps.put(Types.LONGVARCHAR, "String"); 
	        jdbcJavaTypeMaps.put(Types.NCHAR, "String"); 
	        jdbcJavaTypeMaps.put(Types.VARCHAR, "String"); 
	        jdbcJavaTypeMaps.put(Types.NVARCHAR , "String"); 
	        jdbcJavaTypeMaps.put(Types.LONGVARCHAR , "String"); 
	        jdbcJavaTypeMaps.put(Types.LONGNVARCHAR  , "String"); 
	        
	        jdbcJavaTypeMaps.put(Types.NUMERIC  , "Double"); 
	        jdbcJavaTypeMaps.put(Types.REAL  , "Float"); 
	        jdbcJavaTypeMaps.put(Types.SMALLINT  , "Short"); 
	        jdbcJavaTypeMaps.put(Types.TIMESTAMP  , "Date"); 
	        jdbcJavaTypeMaps.put(Types.VARBINARY  , "byte[]"); 
	        jdbcJavaTypeMaps.put(Types.VARCHAR  , "String"); 
	        jdbcJavaTypeMaps.put(Types.TINYINT  , "Short"); 
	        
	        jdbcJavaTypeMaps.put(Types.TIME, "Time"); 
	        jdbcJavaTypeMaps.put(Types.DATE  , "java.sql.Date");
		}  
	      
	    /**
		 * 
		 */
		private static void initImportTypes() {
			importTypes.put("Date", "java.util.Date");
			importTypes.put("Time", "java.sql.Time");
		}

		/**
		 * 
		 */
		private static void initPrimitiveWrapperTypes() {
			primitiveWrapperTypes.put("int", "Integer");
			primitiveWrapperTypes.put("short", "Short");
			primitiveWrapperTypes.put("byte", "Byte");
			primitiveWrapperTypes.put("boolean", "Boolean");
			primitiveWrapperTypes.put("char", "Character");
			primitiveWrapperTypes.put("long", "Long");
			primitiveWrapperTypes.put("float", "Float");
			primitiveWrapperTypes.put("double", "Double");
			
		}

		public static  String getPojoType(int dataType ) {  
	        String  pojoType=jdbcJavaTypeMaps.get(dataType) ;  
	        
	        if(pojoType==null){
	        	pojoType="Object";
	        	
	        }
	        
	        return pojoType;	
	    } 
	    
	    
	    public static String  primitive2Wrapper(String primitiveType){
	    	String wrapperType=primitiveWrapperTypes.get(primitiveType);
	    	if(wrapperType==null){
	    		wrapperType=primitiveType;
	    	}
	    	return wrapperType;
	    }
	    
	    public static String getImportType(String javaType){
	    	return importTypes.get(javaType);
	    }
	    
	    
}
