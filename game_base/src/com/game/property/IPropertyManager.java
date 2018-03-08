package com.game.property;

import java.util.Collection;
import java.util.Map;

/**
 *属性管理接口 
 * @author lip.li
 *
 */
public interface IPropertyManager<T>  {
	/**获取客户端可见属性列表*/
	public Collection<PropertyValue> getViewPropertyValues();
	/** 获取属性列表 **/
	public Map<Integer, PropertyValue> getPropertyMap();
	/** 给一个整型属性加值 **/
	public void addIntValue(int key, int value);
	/** 给一个长整型属性加值 **/
	public void addLongValue(int key, long value);
	/** 给一个整型属性加外部值(不受百分比加成影响) **/
	public void addIntOutValue(int key, int value);
	/** 给一个长整型属性加值外部值(不受百分比加成影响)  **/
	public void addLongOutValue(int key, long value);
	/** 给一个整型属性加百分比值 **/
	public void addIntPercentValue(int key, int value);
	/** 给一个长整型属性加百分比值**/
	public void addLongPercnetValue(int key, int value);
	
	/** 给一个整型属性减值 **/
	public void subIntValue(int key, int value);

	/** 给一个长整型属性减值 **/
	public void subLongValue(int key, long value);
	
	/** 给一个整型属性减外部值(不受百分比加成影响) **/
	public void subIntOutValue(int key, int value);

	/** 给一个长整型属性减值(不受百分比加成影响) **/
	public void subLongOutValue(int key, long value);
	
	/** 给一个整型属性减百分比值 **/
	public void subIntPercentValue(int key, int value);

	/** 给一个长整型属性减百分比值 **/
	public void subLongPercentValue(int key, int value);

	/** 获取属性对象 **/
	public PropertyValue getProperty(int key);

	/** 获取长整型属性值 **/
	public long getLongValue(int key);
	
	/** 获取整型属性值 **/
	public int getIntValue(int key);

	/** 设置整型属性值 **/
	public void setIntValue(int key, int value);

	/** 设置长整型属性值 **/
	public void setLongValue(int key, long value);

	/** 初始化属性值 **/
	public void initializePropertyValue();

	/** 获取显示属性数量 **/
	public int getViewPropCount();

	/** 发送指定的属性 **/
	public void sendPropertyValue(boolean broadcast, int... keys);

	/** 发送变化的属性 */
	public void sendUpdatePropertyValue(boolean broadcast);
	
	public   T getOwner();

}
