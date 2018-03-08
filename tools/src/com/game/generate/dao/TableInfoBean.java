package com.game.generate.dao;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author lip.li
 * @date 2017年1月9日
 */
public class TableInfoBean {
	     private String tableName; 
	    private String tableComment; 
	    private List<ColumnInfoBean> columnList=new ArrayList<ColumnInfoBean>();
		public String getTableName() {
			return tableName;
		}
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		public String getTableComment() {
			return tableComment;
		}
		public void setTableComment(String tableComment) {
			this.tableComment = tableComment;
		}
		public List<ColumnInfoBean> getColumnList() {
			return columnList;
		}
		public void setColumnList(List<ColumnInfoBean> columnList) {
			this.columnList = columnList;
		}
}
