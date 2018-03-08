
package com.game.db.base;

 

/**
 * 
 * @author lip.li
 * @date 2017年1月4日
 * @param <PK>
 */
public interface IEntityBean<PK> {
	public PK getPrimaryKeyValue();

	public String getPrimaryKeyName();

	public String getTableName();
	// public PK idAutoIncrement();
}
