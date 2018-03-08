package com.game.property;

import com.game.consts.GameConsts;

 

/**
 * 
 * @author weiping.li
 * 
 */
public class PropertyValue {

	/** 整型ing **/
	public static final byte VALUE_TYPE_INT = 0;
	/** 长整型long **/
	public static final byte VALUE_TYPE_LONG = 1;
	/** 属性键值 **/
	private int key;
	/** 属性客户端是否可见 **/
	private boolean isView;
	/** 属性是否需要跟新 **/
	private boolean isUpdate;
	/** 属性整型值(进行百分比加成) **/
	private int intValue;
	/** 属性长整型值(进行百分比加成) **/
	private long longValue;
	/** 值类型 **/
	private int valueType;
	/** 不进行百分比加成 */
	private int intOutValue;
	/** 属性百分比加成 */
	private int intPercentValue;
	/** 不进行百分比加成 */
	private long longOutValue;
	/** 属性百分比加成 */
	private long longPercentValue;
	/** 变化的值 **/
	private int changValue;

	public PropertyValue(int key, boolean isView, boolean isUpdate, int intValue, long longValue, int valueType,
			int intOutValue, int intPercentValue, long longOutValue, long longPercentValue, int changValue) {
		super();
		this.key = key;
		this.isView = isView;
		this.isUpdate = isUpdate;
		this.intValue = intValue;
		this.longValue = longValue;
		this.valueType = valueType;
		this.intOutValue = intOutValue;
		this.intPercentValue = intPercentValue;
		this.longOutValue = longOutValue;
		this.longPercentValue = longPercentValue;
		this.changValue = changValue;
	}

	public static PropertyValue createIntProperValue(int key, int value) {
		PropertyValue propertyValue = new PropertyValue(key, VALUE_TYPE_INT);
		propertyValue.addIntValue(value);
		return propertyValue;
	}

	public int getChangValue() {
		return changValue;
	}

	public void setChangValue(int changValue) {
		this.changValue = changValue;
	}

	public static PropertyValue createLongPropertyValue(int key, long value) {
		PropertyValue propertyValue = new PropertyValue(key, VALUE_TYPE_LONG);
		propertyValue.addLongValue(value);
		return propertyValue;
	}

	public PropertyValue(int key, int valueType) {
		this.key = key;
		this.isView = PropertyType.getType(key).isView();
		this.valueType = valueType;
		this.intPercentValue=GameConsts.HundredThousand;
		this.longPercentValue=GameConsts.HundredThousand;
	}

	public PropertyValue(int key, boolean isView, int valueType) {
		this.key = key;
		this.isView = isView;
		this.valueType = valueType;
		this.intPercentValue=GameConsts.HundredThousand;
		this.longPercentValue=GameConsts.HundredThousand;
	}

	public int getKey() {
		return key;
	}

	public void setKey(byte key) {
		this.key = key;
	}

	public boolean isView() {
		return isView;
	}

	public void setView(boolean isView) {
		this.isView = isView;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
		if (!isUpdate) {
			changValue = 0;
		}
	}

	public int getIntValue() {
		return (int) ((double)intValue * (double)intPercentValue * GameConsts.OneInOneHundredThousand + intOutValue);
	}

	public void setIntValue(int intValue) {
		if (this.intValue == intValue) {
			return;
		}
		changValue = (int) ((intValue - this.intValue) * intPercentValue * GameConsts.OneInOneHundredThousand);
		this.intValue = intValue;

		isUpdate = true;
	}

	public long getLongValue() {
		return (long) (longValue * longPercentValue * GameConsts.OneInOneHundredThousand + longOutValue);
	}

	public void setLongValue(long longValue) {
		if (this.longValue == longValue) {
			return;
		}
		changValue = (int) ((int) (longValue - this.longValue) * longPercentValue * GameConsts.OneInOneHundredThousand);
		this.longValue = longValue;
		isUpdate = true;
	}

	public int getValueType() {
		return valueType;
	}

	public void setValueType(int valueType) {
		this.valueType = valueType;
	}

	public void addIntValue(int value) {
		if (value == 0) {
			return;
		}
		intValue = intValue + value;
		changValue = (int) (value * intPercentValue * GameConsts.OneInOneHundredThousand);
		isUpdate = true;
	}
	public void addIntOutValue(int value) {
		if (value == 0) {
			return;
		}
		intOutValue = intOutValue + value;
		changValue = value;
		isUpdate = true;
	}
	public void addIntPercentValue(int value){
		if (value == 0) {
			return;
		}
		intPercentValue=intPercentValue+value;
		changValue = (int) (value*GameConsts.OneInOneHundredThousand*intValue);
		isUpdate = true;
	}
	
	public void subIntOutValue(int value) {
		if (value == 0) {
			return;
		}
		intOutValue = intOutValue - value;
		changValue = value;
		isUpdate = true;
	}
	public void subIntPercentValue(int value){
		if (value == 0) {
			return;
		}
		intPercentValue=intPercentValue-value;
		changValue = (int) (-value*GameConsts.OneInOneHundredThousand*intValue);
		isUpdate = true;
	}
	 

	public void addLongValue(long value) {
		if (value == 0) {
			return;
		}
		longValue = longValue + value;
		changValue = (int) ((int) value * longPercentValue * GameConsts.OneInOneHundredThousand);
		isUpdate = true;
	}

	public void subIntValue(int value) {
		if (value == 0) {
			return;
		}
		changValue = (int) (-value * intPercentValue * GameConsts.OneInOneHundredThousand);
		intValue = intValue - value;
		isUpdate = true;
	}

	public void subLongValue(long value) {
		if (value == 0) {
			return;
		}
		changValue = (int) (-(int) value * longPercentValue * GameConsts.OneInOneHundredThousand);
		longValue = longValue - value;
		isUpdate = true;
	}
	public void addLongOutValue(long value) {
		if (value == 0) {
			return;
		}
		longOutValue = longOutValue + value;
		changValue = (int) value;
		isUpdate = true;
	}
	public void addLongPercentValue(int value){
		if (value == 0) {
			return;
		}
		longPercentValue=longPercentValue+value;
		changValue = (int) (value*GameConsts.OneInOneHundredThousand*longValue);
		isUpdate = true;
	}
	
	public void subLongOutValue(long value) {
		if (value == 0) {
			return;
		}
		longOutValue = longOutValue - value;
		changValue = (int) value;
		isUpdate = true;
	}
	public void subLongPercentValue(int value){
		if (value == 0) {
			return;
		}
		longPercentValue=longPercentValue-value;
		changValue = (int) (-value*GameConsts.OneInOneHundredThousand*longValue);
		isUpdate = true;
	}
 

	public PropertyValue copy() {
		PropertyValue propertyValue = new PropertyValue(key, isView, isUpdate, intValue, longValue, valueType,
				intOutValue, intPercentValue, longOutValue, longPercentValue, changValue);
		return propertyValue;
	}

	@Override
	public String toString() {
		return "PropertyValue [key=" + key + ", isView=" + isView + ", isUpdate=" + isUpdate + ", intValue=" + intValue
				+ ", longValue=" + longValue + ", valueType=" + valueType + ", intOutValue=" + intOutValue
				+ ", intPercentValue=" + intPercentValue + ", longOutValue=" + longOutValue + ", longPercentValue="
				+ longPercentValue + ", changValue=" + changValue + "]";
	}
 

}
