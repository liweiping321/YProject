package com.game.property;

import com.game.consts.GameConsts;

import java.util.*;

 

/**
 * 属性管理器
 * 
 * @author weiping.li
 * 
 */
public abstract class PropertyManagerImpl<T> implements IPropertyManager<T> {
	
	protected final T owner;
	
	public PropertyManagerImpl(T owner){
		this.owner=owner;
	}
	 
 
	protected Map<Integer, PropertyValue> propertyMap = new TreeMap<Integer, PropertyValue>();

	 
	public Collection<PropertyValue> getViewPropertyValues(){
		Collection<PropertyValue> propertyValues=new ArrayList<PropertyValue>();
		for(PropertyValue propertyValue:propertyMap.values()){
			if(propertyValue.isView()){
				propertyValues.add(propertyValue);
			}
			
		}
		return propertyValues;
	}
	@Override
	public Map<Integer, PropertyValue> getPropertyMap() {
		return propertyMap;
	}

	@Override
	public void addIntValue(int key, int value) {
		PropertyValue propValue = getProperty(key);
		propValue.addIntValue(value);
	}
	
	public void addValues(Map<Integer,Integer> properties){
		for(Map.Entry<Integer, Integer> entry:properties.entrySet()){
			this.addValue(entry.getKey(), entry.getValue());
		}
	}
	
	public void subValues(Map<Integer,Integer> properties){
		for(Map.Entry<Integer, Integer> entry:properties.entrySet()){
			this.subValue(entry.getKey(), entry.getValue());
		}
	}
	public void addValue(PropertyType type,Number value){
		if(type.getValueType()==PropertyValue.VALUE_TYPE_INT){
			this.addIntValue(type.getKey(), value.intValue());
		}else if(type.getValueType()==PropertyValue.VALUE_TYPE_LONG){
			this.addLongValue(type.getKey(), value.longValue());
		}
	}
	
	public void addValue(int propertyKey,Number value){
		PropertyValue propValue = getProperty(propertyKey);
		if(propValue.getValueType()==PropertyValue.VALUE_TYPE_INT){
			propValue.addIntValue(value.intValue());
		}else if(propValue.getValueType()==PropertyValue.VALUE_TYPE_LONG){
			propValue.addLongValue(value.longValue());
		}
	}
	public void subValue(int propertyKey,Number value){
		PropertyValue propValue = getProperty(propertyKey);
		if(propValue.getValueType()==PropertyValue.VALUE_TYPE_INT){
			propValue.subIntValue(value.intValue());
		}else if(propValue.getValueType()==PropertyValue.VALUE_TYPE_LONG){
			propValue.subLongValue(value.longValue());
		}
	}
	public void setValue(PropertyType type,Number value){
		if(type.getValueType()==PropertyValue.VALUE_TYPE_INT){
			this.setIntValue(type.getKey(), value.intValue());
		}else if(type.getValueType()==PropertyValue.VALUE_TYPE_LONG){
			this.setLongValue(type.getKey(), value.longValue());
		}
	}
	
	public void subValue(PropertyType type,Number value){
		if(type.getValueType()==PropertyValue.VALUE_TYPE_INT){
			this.subIntValue(type.getKey(), value.intValue());
		}else if(type.getValueType()==PropertyValue.VALUE_TYPE_LONG){
			this.subLongValue(type.getKey(), value.longValue());
		}
	}
	
	public void addPropertyValue(PropertyValue propertyValue){
		propertyMap.put(propertyValue.getKey(), propertyValue);
	}
	@Override
	public void addLongValue(int key, long value) {
		PropertyValue propValue = getProperty(key);
		propValue.addLongValue(value);

	}
	/**增加基础百分比值*/
	public void addPercentIntValue(int key, int value,int percentValue){
		PropertyType propertyType=PropertyType.getType(key);
		int targetKey=propertyType.getTargetKey();
		if(targetKey>0){
			addIntValue(targetKey, (int) (value*percentValue*GameConsts.OneInOneHundredThousand));
		}
	}
	
	/**减去基础百分比值*/
	public void subPercentIntValue(int key, int value,int percentValue){
		PropertyType propertyType=PropertyType.getType(key);
		int targetKey=propertyType.getTargetKey();
		if(targetKey>0){
			subIntValue(targetKey, (int) (value*percentValue*GameConsts.OneInOneHundredThousand));
		}
	}

	/**增加基础百分比值*/
	public void addPercentLongValue(int key, long value,int percentValue){
		PropertyType propertyType=PropertyType.getType(key);
		int targetKey=propertyType.getTargetKey();
		if(targetKey>0){
			addLongValue(targetKey, (int) (value*percentValue*GameConsts.OneInOneHundredThousand));
		}
	}

	@Override
	public void subIntValue(int key, int value) {
		PropertyValue propValue = getProperty(key);
		propValue.subIntValue(value);
		resetCurrProperty(key, propValue);
	}
	private void resetCurrProperty(int key, PropertyValue propValue) {
		// if(key==PropertyType.maxHp.getKey()){
		// 	int currHp=getIntValue(PropertyType.currHp.getKey());
		// 	setIntValue(PropertyType.currHp.getKey(), Math.min(currHp, propValue.getIntValue()));
		// }
	}

	@Override
	public void subLongValue(int key, long value) {
		PropertyValue propValue = getProperty(key);

		propValue.subLongValue(value);
	}

	@Override
	public PropertyValue getProperty(int key) {
		PropertyValue propValue = propertyMap.get(key);
		if (propValue == null) {
			propValue = new PropertyValue(key, PropertyType.getType(key).getValueType());
			propertyMap.put(propValue.getKey(), propValue);
		}
		return propValue;
	}
	public PropertyValue getCloneProperty(PropertyType keyType){
		PropertyValue propertyValue=getProperty(keyType.getKey());
		return propertyValue.copy();
	}

 
	public void initializePropertyValue() {
	}
	 
 
	@Override 
	public int getViewPropCount() {
		int count = 0;
		for (PropertyValue propValue : propertyMap.values()) {
			if (propValue.isView()) {
				count++;
			}
		}
		return count;
	}

	@Override
	public long getLongValue(int key) {
		PropertyValue propValue = getProperty(key);
		return propValue.getLongValue();
	}

	@Override
	public int getIntValue(int key) {
		PropertyValue propValue = getProperty(key);
		return propValue.getIntValue();
	}

	
	public List<PropertyValue> getUpdatePropertyValues(){
		List<PropertyValue> propValues=new ArrayList<PropertyValue>();
		for(PropertyValue propertyValue:propertyMap.values()){
			if(propertyValue.isUpdate()){
				propValues.add(propertyValue);
			}
		}
		return propValues;
	}
	
	public List<PropertyValue> getPropertyValues(int ...keys){
		List<PropertyValue> propValues=new ArrayList<PropertyValue>();
		for(int key:keys){
			propValues.add(propertyMap.get(key));
		}
		return propValues;
	}
	@Override
	public void setIntValue(int key, int value) {
		PropertyValue propValue = getProperty(key);
		propValue.setIntValue(value);

	}

	@Override
	public void setLongValue(int key, long value) {
		PropertyValue propValue = getProperty(key);
		propValue.setLongValue(value);

	}
	@Override
	public void addIntOutValue(int key, int value) {
		PropertyValue propValue = getProperty(key);
		propValue.addIntOutValue(value);
		
	}
	@Override
	public void addLongOutValue(int key, long value) {
		PropertyValue propValue = getProperty(key);
		propValue.addLongOutValue(value);
	}
	@Override
	public void addIntPercentValue(int key, int value) {
		PropertyValue propValue = getProperty(key);
		propValue.addIntPercentValue(value);
	}
	@Override
	public void addLongPercnetValue(int key, int value) {
		PropertyValue propValue = getProperty(key);
		propValue.addLongPercentValue(value);
		
	}
	@Override
	public void subIntOutValue(int key, int value) {
		PropertyValue propValue = getProperty(key);
		propValue.subIntOutValue(value);
		
		resetCurrProperty(key, propValue);
		
	}
	@Override
	public void subLongOutValue(int key, long value) {
		PropertyValue propValue = getProperty(key);
		propValue.subLongOutValue(value);
		
	}
	@Override
	public void subIntPercentValue(int key, int value) {
		PropertyValue propValue = getProperty(key);
		propValue.subIntPercentValue(value);
		resetCurrProperty(key, propValue);
		
	}
	@Override
	public void subLongPercentValue(int key, int value) {
		PropertyValue propValue = getProperty(key);
		propValue.subLongPercentValue(value);
	}
	 
	public T getOwner() {
		return owner;
	}
	
	 

}
