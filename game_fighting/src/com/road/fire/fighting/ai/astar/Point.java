package com.road.fire.fighting.ai.astar;

 

/**
 * 记录数组下标点
 * @author spring
 *
 */
public class Point{
	
	private short x = 0;
	
	private short y = 0;
	
	private int g = 0;  //g值
	
	private int h = 0;  //h值
	
	private int f = 0;   // g + h
	
	private boolean bevel = false;  //斜角坐标
	
	private Point parent = null;
	
	public static final int BEVEL = 14; //斜角计算使用常量

	public static final int NOTBEVEL= 10; //正常角度计算使用常量
	
	private boolean isBlock = false;
	
	
	public void setBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}

	public boolean isBlock() {
		return isBlock;
	}

	public Point(short x, short y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(int x,int y){
		this.x=(short) x;
		this.y=(short) y;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * 处理H值
	 * @param p 目标节点
	 */
	public void calculateH(Point p) {
		if (p == null)
			return;
		int xh = Math.abs(x - p.x);
		int yh = Math.abs(y - p.y);
		h = (xh + yh) * 10;
	}
	
	/**
	 * 计算G值
	 * @param p 上一个节点
	 */
	public void calculateG(Point p) {
		if (p == null) 
			return;
		g = isBevel() ? p.getG() + Point.BEVEL
				: p.getG() + Point.NOTBEVEL;
	}

	public Integer toKey() {
		return ArithmeticUtils.shortToInt(x,y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public boolean isBevel() {
		return bevel;
	}

	public void setBevel(boolean bevel) {
		this.bevel = bevel;
	}


	public short getX() {
		return x;
	}

	public short getY() {
		return y;
	}
	
	public int getG() {
		return g;
	}

	public int getH() {
		return h;
	}

	public Point getParent() {
		return parent;
	}

	public int getF() {
		return f = g + h;
	}

	public void setF(int f) {
		this.f = f;
	}

	public void setParent(Point parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Point[x=" + x + ",y=" + y + "]";
	}
	
	
}
