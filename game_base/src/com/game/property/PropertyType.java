package com.game.property;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public enum PropertyType {
	
    /**当前经验**/
	currExp(PropertyConstants.CurrExp,true),
	/**等级**/
	level(PropertyConstants.Level,true),
	/**下级所需要的经验**/
	nextLevelExp(PropertyConstants.NextLevelExp,true),
	/**当前血量**/
	currHp(PropertyConstants.CurrHp,true),
	/**整体血量上限**/
	maxHp(PropertyConstants.MaxHp,true),
	/**整体攻击上限**/
	attack(PropertyConstants.Attack,false),
	/**整体防御**/
	defense(PropertyConstants.Defense,false),
	/**整体命中**/
	hit(PropertyConstants.Hit,false),
	/**整体闪避**/
	dodge(PropertyConstants.Dodge,false),
	/**整体暴击**/
	crit(PropertyConstants.Crit,false),
	/** 移动速度*/
	moveSpeed(PropertyConstants.MoveSpeed,true),
	/**抗爆值**/
	opposeCrit(PropertyConstants.OpposeCrit,false),
	/**视野*/
	eyeShot(PropertyConstants.EyeShot,true),
	/**暴击率*/
	critRate(PropertyConstants.CritRate,false),
	/**移动衰减值*/
	moveReduce(PropertyConstants.MoveReduce,true),
	/**换弹夹时间*/
	reloadTime(PropertyConstants.ReloadTime,true),
	/**破坏力*/
	breachPower(PropertyConstants.BreachPower,true),
	/**弹夹容量*/
	ClipSize(PropertyConstants.ClipSize,true),
	/**弹夹容量*/
	CurrClipSize(PropertyConstants.currClipSize,true),
	;
	
	 private static final Logger LOG=LogManager.getLogger(PropertyType.class);
	
	private PropertyType(int key,boolean isView){
		this.key=key;
		this.isView=isView;
		this.valueType=PropertyValue.VALUE_TYPE_INT;
	}
	
	private PropertyType(int key,boolean isView,byte ValueType){
		this.key=key;
		this.isView=isView;
		this.valueType=ValueType;
	}
	private PropertyType(int key,boolean isView, int targetKey){
		this.key=key;
		this.isView=isView;
		this.valueType=PropertyValue.VALUE_TYPE_INT;
		this.targetKey=targetKey;
	}
	
	private PropertyType(int key,boolean isView,byte ValueType,int targetKey){
		this.key=key;
		this.isView=isView;
		this.valueType=ValueType;
		this.targetKey=targetKey;
	}
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getValueType() {
		return valueType;
	}

	public void setValueType(byte valueType) {
		this.valueType = valueType;
	}

	public boolean isView() {
		return isView;
	}

	public void setView(boolean isView) {
		this.isView = isView;
	}
	
	public int getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(int targetKey) {
		this.targetKey = targetKey;
	}

	private int key;
	private byte valueType;
	private boolean isView;
	private int targetKey;
	
	public static PropertyType getType(int key){
		PropertyType[] types=values();
		for (PropertyType propertyType : types) {
			if(propertyType.getKey()==key){
				return propertyType;
			}
		}
		LOG.error("PropertyType is  null !key="+key);
		return null;
	}
}
