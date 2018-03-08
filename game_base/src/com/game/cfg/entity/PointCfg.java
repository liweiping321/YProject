package com.game.cfg.entity;

public class PointCfg {
	private int x;
	
	private int y;
	
	public PointCfg( String pointStr,String regex){
		String [] items=pointStr.split(regex);
		
		x=Integer.parseInt(items[0]);
		y=Integer.parseInt(items[1]);
	}
	
	public PointCfg(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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
	
	
}
