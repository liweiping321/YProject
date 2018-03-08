package com.road.fire.fighting;

import com.game.polling.PollingUpdate;
import com.game.utils.LogUtils;
import com.road.fire.fighting.phy.living.Living;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author jianpeng.zhang
 * @since 2017/5/22.
 */
public class ReliveMgr implements PollingUpdate
{
	/** 遍历中的集合 */
	private List<Living> traversalList = new ArrayList<>();

	public void add2ReliveList(Living living) {
		if (living == null) {
			LogUtils.warn("复活的对象不能为空");
			return;
		}
		if (living.getReliveTime() + 1 < living.getGame().getFrameTime()) {
			LogUtils.warn("传入的复活时间有问题");
			return;
		}
		traversalList.add(living);
	}

	@Override
	public void update(long now)
	{
		Iterator<Living> iterator = traversalList.iterator();
		while (iterator.hasNext()) {
			Living living = iterator.next();
			if (now >= living.getReliveTime() && living.onRelive()) {
				iterator.remove();
			}
		}
	}
}
