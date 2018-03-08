package com.game.generate.dao;
/**
 * 
 * @author lip.li
 * @date 2017年1月9日
 */
public class ColumnInfoBean {
	private String columnName;
	private String attributeName;
	private String columnComment;
	private int SqlType;
	private boolean  generatedKey;
	
	private boolean primarykey;
	
	private String javaType;
	
	private String upperattributeName;
	public ColumnInfoBean()
	{
		
	}
	public ColumnInfoBean(String columnName, String columnComment, int sqlType,boolean generatedKey,boolean primarykey) {
		super();
		this.columnName = columnName;
		this.columnComment = columnComment==null?"not Comment ":columnComment;
		this.SqlType =sqlType;
		this.attributeName=StringUtil.dbColumnToJavaAttribute(columnName);
		this.generatedKey=generatedKey;
		this.primarykey=primarykey;
		this.javaType=TypeUtils.getPojoType(sqlType);
		this.upperattributeName=StringUtil.upperCapitalize(attributeName);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	
	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
 
	public int getSqlType() {
		return SqlType;
	}

	public void setSqlType(int sqlType) {
		SqlType = sqlType;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public boolean isGeneratedKey() {
		return generatedKey;
	}

	public void setGeneratedKey(boolean generatedKey) {
		this.generatedKey = generatedKey;
	}

	public boolean isPrimarykey() {
		return primarykey;
	}

	public void setPrimarykey(boolean primarykey) {
		this.primarykey = primarykey;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getUpperattributeName() {
		return upperattributeName;
	}

	public void setUpperattributeName(String upperattributeName) {
		this.upperattributeName = upperattributeName;
	}
	
	

}
