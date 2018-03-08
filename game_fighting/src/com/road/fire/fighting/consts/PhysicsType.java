package com.road.fire.fighting.consts;

/**
 * 地图上物件的类型,只有战斗中才会存在
 */
public enum PhysicsType {
	
	
	HERO(1), // 玩家类，生物的子类，由人控制其逻辑
	MONSTER(2), //怪物
	BULLET(3),//子弹
	DROPGOODS(4)//场景掉落物
	;
	private byte value;

	private PhysicsType(int value) {
		this.value = (byte) value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	public static PhysicsType parse(int value) {
		for (PhysicsType type : PhysicsType.values()) {
			if (type.getValue() == value) {
				return type;
			}
		}

		return PhysicsType.MONSTER;
	}

}
