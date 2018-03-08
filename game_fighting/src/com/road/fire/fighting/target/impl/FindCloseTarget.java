package com.road.fire.fighting.target.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.game.cfg.entity.ConfigsCfg;
import com.road.fire.fighting.consts.FightingConsts;
import com.road.fire.fighting.phy.living.Hero;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.util.DistanceUtils;

/**
 * @author jianpeng.zhang
 * @since 2017/5/17.
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class FindCloseTarget {

	/** 查找离我最近的朋友 */
	public static <T extends Living<?>> T findNearestFriend(boolean checkObstacle,boolean checkBox,Living<?> owner,
			Class<?>... clazzs) {
		return findNearestLiving(checkObstacle,checkBox,owner, FightingConsts.TargetTypeFriend, clazzs);
	}

	/** 查找离我最近的敌人 */
	public static <T extends Living<?>> T findNearestEnemy(boolean checkObstacle,boolean checkBox,Living<?> owner,
			Class<?>... clazzs) {
		return findNearestLiving(checkObstacle,checkBox,owner, FightingConsts.TargetTpeEnemy, clazzs);
	}

	/** 范围查找离我最近的朋友 */
	public static <T extends Living<?>> T findNearestFriend(boolean checkObstacle,boolean checkBox,Living<?> owner,
			int radius, Class<?>... clazzs) {
		return findNearestLiving(checkObstacle,checkBox,owner, FightingConsts.TargetTypeFriend, radius,
				clazzs);
	}

	/** 范围查找离我最近的敌人 */
	public static <T extends Living<?>> T findNearestEnemy(boolean checkObstacle,boolean checkBox,Living<?> owner,
			int radius, Class<?>... clazzs) {
		return findNearestLiving(checkObstacle,checkBox,owner, FightingConsts.TargetTpeEnemy, radius,
				clazzs);
	}

	/** 以我为中心范围查找我的朋友列表 */
	public static <T extends Living<?>> List<T> findNearestFriends(boolean checkObstacle,boolean checkBox,
			Living<?> owner, int radius, Class<?>... clazzs) {
		return findNearestLivings(checkObstacle,checkBox,owner, FightingConsts.TargetTypeFriend, radius,
				clazzs);
	}

	/** 以我为中心范围查找我的敌人列表 */
	public static <T extends Living<?>> List<T> findNearestEnemies(boolean checkObstacle,boolean checkBox,
			Living<?> owner, int radius, Class<?>... clazzs) {
		return findNearestLivings(checkObstacle,checkBox,owner, FightingConsts.TargetTpeEnemy, radius,
				clazzs);
	}

	/** 查找在我碰撞半径内查找目标类型最近的生物 */
	public static <T extends Living<?>> T findNearestLiving(boolean checkObstacle,boolean checkBox,
			Living<?> owner,int targetType, Class<?>... clazzs) {

		return findNearestLiving(checkObstacle,checkBox,owner, targetType, owner.getDamageRadius(), 0,
				0, clazzs);
	}

	/** 以我为中心范围查找目标类型最近的生物 */
	public static <T extends Living<?>> T findNearestLiving(boolean checkObstacle,boolean checkBox,
			Living<?> owner,int targetType, int radius, Class<?>... clazzs) {

		return findNearestLiving(checkObstacle,checkBox,owner, targetType, radius, 0, 0, clazzs);
	}

	public static <T extends Living<?>> List<T> findNearestLivings(boolean checkObstacle,boolean checkBox,
			Living<?> owner, int targetType, int radius, Class<?>... clazzs) {
		return findNearestLivings(checkObstacle,checkBox,owner, targetType, radius, 0, 0, clazzs);
	}

	/** 查找离我最近的目标类型生物(高优先级类型在前面) */
	public static <T extends Living<?>> T findNearestLiving(boolean checkObstacle,boolean checkBox,
			Living<?> owner,int targetType, int radius, int downAngle, int upAngle,
			Class<?>... clazzs) {
		if(targetType==FightingConsts.TargetTypeSelf){
		}
		T target = null;

		for ( Class clazz : clazzs) {
			//找我上次攻击的目标 
			target= (T) findLockAttackTarget(checkObstacle, checkBox, owner, targetType, radius, downAngle, upAngle, clazz);

			
			if(null==target){
				Collection<T> livings = owner.getLivingManager().getLivings(clazz);

				for (T living : livings) {
					boolean match = targetMatch(checkObstacle,checkBox,owner, targetType, radius,
							downAngle, upAngle, living);
					if (match) {
						radius = living.getTempDistance() * ConfigsCfg.getCellSzie();
						target = living;
					}
				}
			}

			if (target != null) {
				break;
			}
		}
		return target;
	}
	/**查找锁定攻击目标*/
	public static <T extends Living<?>> T findLockAttackTarget(boolean checkObstacle,boolean checkBox,Living<?> owner,
			int targetType, int radius, int downAngle, int upAngle,Class<?> clazz) {
		Living<?> target=owner.getAttackTarget();
		if(null!=target){
			//生物类型匹配
			if(target.getClass()==clazz){
				boolean match = targetMatch(checkObstacle,checkBox,owner, targetType, radius,
						downAngle, upAngle, target);
				
				if(match){
					return (T) target;
				}
			}
		}
		return null;
	}

	/** 范围查找目标生物列表 */
	private static <T extends Living<?>> List<T> findNearestLivings(boolean checkObstacle, boolean checkBox,
			Living<?> owner, int targetType, int radius, int downAngle, int upAngle, Class<?>... clazzs) {
 
		List<T> targetList = new ArrayList<>();
		for (Class clazz : clazzs) {

			Collection<T> livings = owner.getLivingManager().getLivings(clazz);

			for (T target : livings) {
				boolean match = targetMatch(checkObstacle,checkBox,owner, targetType, radius,
						downAngle, upAngle, target);
				if (match) {
					targetList.add(target);
				}
			}
		}
		return targetList;
	}

	static boolean targetMatch(boolean checkObstacle, boolean checkBox, Living<?> owner, int targetType, int radius,
			int downAngle, int upAngle, Living<?> target) {
		if (target.isDie()) {
			return false;
		}
		if (!targetTypeMatch(owner, target, targetType)) {
			return false;
		}

		boolean range = DistanceUtils.isInRange(owner, target, radius);
		
		if (range) {
			
			if(checkObstacle){

				if(target.getClass()!=Hero.class){
					checkBox=false;
				}
				boolean hasObstacle=owner.hasObstacle(target,checkBox);
				if(hasObstacle){
					return false;
				}
			}
			// 验证扇形
			boolean isAngle = (downAngle == upAngle)
					|| DistanceUtils.isInAngle(owner, target, downAngle,
							upAngle);

			return isAngle;

		}
		return false;
	}

	/** 检查目标是否匹配 */
	private static boolean targetTypeMatch(Living<?> source, Living<?> target,
			int targetType) {
		if(source.getCampType()==FightingConsts.CampTypeMiddle){
			return true;
		}
		if (targetType == FightingConsts.TargetTpeEnemy) {
			if (source.getCampType() == target.getCampType()) {
				return false;
			}
		} else if (targetType == FightingConsts.TargetTypeFriend) {
			if (source.getCampType() != target.getCampType()) {
				return false;
			}
		}
		return true;
	}


}
