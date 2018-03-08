package com.game.generate.dao;
/**
 * 
 * @author lip.li
 * @date 2017年1月9日
 */
public class StringUtil {
	
	public static boolean isEmpty(String value) {
		return null == value || "".equals(value);
	}

	public static String lowerCapitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1))
				.toString();
	}
	
	public static String upperCapitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return "";
		}
		return new StringBuffer(strLen).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1))
				.toString();
	}
 
	public static String  dbColumnToJavaAttribute(String columnName){
		   String value=lowerCapitalize(columnName);
		   String [] values=value.split("_");
		   if(values.length>1){
			   for(int i =1;i<values.length;i++){
				   values[i]=new StringBuffer(   values[i].length()).append(Character.toUpperCase(  values[i].charAt(0))).append(   values[i].substring(1))
							.toString();
			   }
		   }
		  value="";
		  for(String str:values){
			  value=value+str;
		  }
		   return value;
	}
	
	public static String  tableToClassName(String tableName){
		  if(tableName.startsWith("t_")){
			  tableName=tableName.replaceFirst("t_", "");
		  }
		   String value=upperCapitalize(tableName);
		   String [] values=value.split("_");
		   if(values.length>1){
			   for(int i =1;i<values.length;i++){
				   values[i]= upperCapitalize(values[i]);
			   }
		   }
		  value="";
		  for(String str:values){
			  value=value+str;
		  }
		   return value;
	}
	
	public static void main(String[] args) {
		System.out.println(tableToClassName("t_bsss_cc"));
	}
}
