package com.road.fire.fighting.comparator;

import com.road.fire.fighting.phy.living.Living;

import java.util.Comparator;

public class NearestEnemyComparator implements Comparator<Living<?>> {

	@Override
	public int compare(Living<?> o1, Living<?> o2) {
		 
		return o1.getTempDistance()-o2.getTempDistance();
	}

}
