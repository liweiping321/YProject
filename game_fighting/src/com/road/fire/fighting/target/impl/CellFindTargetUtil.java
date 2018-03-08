package com.road.fire.fighting.target.impl;

import java.util.Collection;

import com.game.cfg.entity.ConfigsCfg;
import com.road.fire.fighting.phy.living.Living;

/**
 * @author jianpeng.zhang
 * @since 2017/5/26.
 */
public class CellFindTargetUtil {

	private static Living sss(int currX, int currY, Living owner, int m, int targetType, int radius, int downAngle,
			int upAngle, int index, Class<?>... clazzs) {

		Living target;
		for (int i = 0; i < index; i++) {
			target = ss(currX, currY, owner, m, targetType, radius, downAngle, upAngle, clazzs[i]);
			if (target != null) {
				target.setTemp(i);
				return target;
			}
		}
		return null;
	}

	private static Living ss(int currX, int currY, Living owner, int m, int targetType, int radius, int downAngle,
			int upAngle, Class<?> clazz) {
		Living target = null;
		Living tempTarget;
		// 查找某种类型一圈中最近的目标
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < m; k++) {
				switch (j) {
				case 0:
					currX++;
					break;
				case 1:
					currY++;
					break;
				case 2:
					currX--;
					break;
				case 3:
					currY--;
					break;
				}
				tempTarget = findTarget(currX, currY, owner, targetType, radius, downAngle, upAngle, clazz);
				if (tempTarget != null) {
					radius = tempTarget.getTempDistance() * ConfigsCfg.getCellSzie();
					target = tempTarget;
				}
			}
		}
		return target;
	}

	/**
	 * @param owner
	 *            以此living为中心对附近格子进行寻找
	 * @param n
	 *            表示遍历多少层格子，living所在格子为第一层
	 */
	public static Living findClosestTarget(boolean checkObstacle, Living owner, int n, int targetType, int radius,
			int downAngle, int upAngle, Class<?>... clazzs) {
		if (owner == null) {
			return null;
		}

		int tileX = owner.getTileX();
		int tileY = owner.getTileY();

		Living target = null;
		Living tempTarget;

		// 用于标记单前找到第几优先级的目标，找到后此目标以及之后的优先级的不需要再查找
		int index = clazzs.length;

		for (Class clazz : clazzs) {
			target = FindCloseTarget.findLockAttackTarget(checkObstacle, true, owner, targetType, radius, downAngle, upAngle,
					clazz);
			if (target != null) {
				return target;
			}
		}

		// 一圈一圈的找，从里到外
		for (int i = 0; i < n; i++) {
			int currX = tileX - i;
			int currY = tileY - i;

			int m = 2 * i;

			if (m == 0) {
				for (int j = 0; j < clazzs.length; j++) {
					target = findTarget(currX, currY, owner, targetType, radius, downAngle, upAngle, clazzs[j]);
					// 因为查找的目标有优先级，所以找到的目标可能是第2级或更之后的
					if (target != null) {
						index = j;
						break;
					}
				}
			} else {

				tempTarget = sss(currX, currY, owner, m, targetType, radius, downAngle, upAngle, index, clazzs);
				if (tempTarget != null) {
					target = tempTarget;
					index = tempTarget.getTemp();
				}

			}
			// 查找一圈完毕
			if (target != null && index == 0) {
				// 因为是找最近的,且优先级最高的，所以找到就返回
				return target;
			}
		}

		return target;
	}

	/**
	 * 找到格子里指定类型的living并且在查找范围之内
	 */
	private static Living findTarget(int x, int y, Living owner, int targetType, int radius, int downAngle, int upAngle,
			Class<?>... clazzs) {
		Living target = null;
		for (Class clazz : clazzs) {
			Collection<Living> livings = owner.getScene().getCollectionAtPosition(x, y, clazz);
			if (livings.size() != 0) {
				int tempRadius = radius;
				for (Living living : livings) {

					if (FindCloseTarget
							.targetMatch(true, true, owner, targetType, tempRadius, downAngle, upAngle, living))
					{
						target = living;
						tempRadius = living.getTempDistance() * ConfigsCfg.getCellSzie();
					}
				}
			}
		}
		return target;
	}
}
