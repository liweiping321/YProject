package com.road.fire.fighting.phy.scene;

import com.alibaba.fastjson.JSON;
import com.road.fire.fighting.ai.astar.AStarNewArithmetic;
import com.road.fire.fighting.consts.TileType;


/**
 * 游戏地图
 * @author lip.li
 *
 */
public class MapData implements Cloneable {
	
	/***所有的逻辑坐标格**/
	private byte[][] blockTiles;
	/***缓存每个格子的周边逻辑格的状态***/
	private byte[][] cacheMap;
	
	private int cell;
	
	private int shiftX;
	
	private int shiftY;
	
	private short width;
	 
	private short height;
	
	 
	public byte[][] getBlockTiles() {
		return blockTiles;
	}
 
	public void setBlockTiles(byte[][] blockTiles) {
		this.blockTiles = blockTiles;
	}
 
	public byte[][] getCacheMap() {
		return cacheMap;
	}
	 
	public void setCacheMap(byte[][] cacheMap) {
		this.cacheMap = cacheMap;
	}

	public int getCell() {
		return cell;
	}

	public void setCell(int cell) {
		this.cell = cell;
	}

	public int getShiftX() {
		return shiftX;
	}

	public void setShiftX(int shiftX) {
		this.shiftX = shiftX;
	}

	public int getShiftY() {
		return shiftY;
	}

	public void setShiftY(int shiftY) {
		this.shiftY = shiftY;
	}

	public short getWidth() {
		return width;
	}

	public void setWidth(short width) {
		this.width = width;
	}

	public short getHeight() {
		return height;
	}

	public void setHeight(short height) {
		this.height = height;
	}
	
	/**
	 * 缓存所有格子周边的状态
	 */
	public void initAllPoint() {
		cacheMap = new byte[height][width];
		int xlen = blockTiles.length;
		for (short i = 0; i < xlen; i++) {
			int ylen = blockTiles[i].length;
			for (short j = 0; j < ylen; j++) {
				byte value = AStarNewArithmetic.checkBlockAroundPoint(i, j,xlen, ylen, blockTiles);
				cacheMap[i][j] = value;
			}
		}
	}
   
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return JSON.parseObject(JSON.toJSONString(this),MapData.class);
		 
	}
	
	/**
	 * 得到地图某一块的数据
	 * 
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 * @return 该tile坐标的数据
	 */
	public byte getTile(int x, int y) {
		if (x < 0 || x > width || y < 0 || y > height) {
			return -1;
		}

		 
		
		return blockTiles[y][x];
	}
	
	public boolean isObstacleTile(int x, int y) {
		if (x < 0 || x > width || y < 0 || y > height) {
			return false;
		}
		return blockTiles[y][x]==TileType.OBSTACLE;
	}
	
	public boolean isBoxTile(int x, int y) {
		if (x < 0 || x > width || y < 0 || y > height) {
			return false;
		}
		return blockTiles[y][x]==TileType.BOX;
	}

	/**
	 * 设置某一地图块的值
	 * 
	 * @param x
	 * @param y
	 * @param value
	 */
	public void setTile(int x, int y, byte value) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return;
		}
		 blockTiles[y][x]=value;
	}
	
	public int[] getAroundPoint(int x,int y,int radius){
		for (int i = 0; i < 8; i++) {
			int nextx = x;
			int nexty = y;
			switch (i) {
			case 0:
				nextx = nextx + radius;
				break;
			case 1:
				nexty = nexty + radius;
				break;
			case 2:
				nextx = nextx - radius;
				break;
			case 3:
				nexty = nexty - radius;
				break;
			case 4:
				nextx = nextx + radius;
				nexty = nexty + radius;
				break;
			case 5:
				nextx = nextx + radius;
				nexty = nexty - radius;
				break;
			case 6:
				nextx = nextx - radius;
				nexty = nexty + radius;
				break;
			case 7:
				nextx = nextx - radius;
				nexty = nexty - radius;
				break;
			default:
				break;
			}
			if(!isObstacleTile(nextx, nexty)){
				return new int[]{ nextx, nexty};
			}
			
		}
		return  null;

	}
}
