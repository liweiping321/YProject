package com.road.fire.fighting.phy.scene;
/**
 * 地图点 
 * @author lip.li
 *
 */
public class MapPoint {
	
	private int x;
	
	private int y;
	
	private byte titleType;
	
	
	public MapPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public MapPoint(int x, int y, byte titleType) {
		super();
		this.x = x;
		this.y = y;
		this.titleType = titleType;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public byte getTitleType() {
		return titleType;
	}

	public void setTitleType(byte titleType) {
		this.titleType = titleType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapPoint other = (MapPoint) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public boolean eq(int x,int y){
		return this.x==x&&this.y==y;
	}
	
}
