package com.road.fire.fighting.consts;

/**
 * 怪物类型
 * @author lip.li
 *
 */
public interface MonsterConsts {
	/**箱子*/
	public static final int TypeBox=1;
	/**普通怪物*/
	public static final int TypeCommonMonster=2;
	
	
	/**延迟出生(资源load完后延迟出生)*/
	public static final int BornTypeDelay=0;
	/**资源初始化时出生*/
	public static final int BornTypeInit=1;
	/**外部控制出生*/
	public static final int BornTypeOut=2;
	
	/**伤害公式计算掉血*/
	public static final int HurtFormulaHp=0;
	/**伤害公式计算破坏力*/
	public static final int HurtFormulaBreach=0;
	
}
