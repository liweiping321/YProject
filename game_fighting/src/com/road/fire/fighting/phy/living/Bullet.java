
package com.road.fire.fighting.phy.living;

import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.consts.PhysicsType;
/**
 * 子弹
 * @author lip.li
 *
 */
public abstract class Bullet extends Living<Bullet> {

	public Bullet(int x, int y, BaseGame game,int campType) {
		super( x, y, game,campType);
		 
	}

	@Override
	public int getPhType() {
	 
		return PhysicsType.BULLET.getValue();
	}
 
}
