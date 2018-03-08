package com.road.fire.fighting.phy.scene;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.game.cfg.entity.ConfigsCfg;
import com.game.utils.ProbabilityUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.road.fire.cfg.provider.BornPositionCfgProvider;
import com.road.fire.cfg.provider.SceneCfgProvider;
import com.road.fire.entity.cfg.BornPositionCfg;
import com.road.fire.entity.cfg.SceneCfg;
import com.road.fire.fighting.consts.TileType;
import com.road.fire.fighting.phy.Physics;
import com.road.fire.fighting.phy.living.Living;

/***
 * 游戏场景
 * 
 * @author lip.li
 *
 */
public class GameScene {

	private final int sceneId;

	private SceneCfg sceneCfg;

	private Map<Integer, List<BornPositionCfg>> cmpBornPositions;

	private MapData mapData;

	private int minX;

	private int minY;

	private int maxX;

	private int maxY;

	/** 和地图长宽相同，用于标记是否有物体 */
	private byte[][] physicsArray;
	private int arrayWidth;
	private int arrayHeight;

	/** 键为格子位置，格式为x-y，值为该格子上的living */
	private Map<String, Multimap<Class, Living>> multimap = new HashMap<>();

	public GameScene(int sceneId) {
		this.sceneId = sceneId;
		init();
	}

	private void init() {
		sceneCfg = SceneCfgProvider.getInstance().getConfigVoByKey(sceneId);

		cmpBornPositions = BornPositionCfgProvider.getInstance().getBornPositions(sceneId);

		mapData = MapDataMgr.getInstance().getMapData(sceneCfg.getMapId());
		physicsArray = new byte[mapData.getWidth()][mapData.getHeight()];
		arrayWidth = mapData.getWidth();
		arrayHeight = mapData.getHeight();
		minX = 0 - mapData.getShiftX();
		minY = 0 - mapData.getShiftY();

		maxX = mapData.getCell() * mapData.getWidth() - mapData.getShiftX();
		maxY = mapData.getCell() * mapData.getHeight() - mapData.getShiftY();

	}

	public SceneCfg getSceneCfg() {
		return sceneCfg;
	}

	public List<BornPositionCfg> getCmpBornPositions(int cmpType) {

		return cmpBornPositions.get(cmpType);
	}

	public int getSceneId() {
		return sceneId;
	}

	public boolean canWalking(int x, int y, Living owner) {
		if (x < minX || y < minY) {
			return true;
		}
		if (x >= maxX || y >= maxY) {
			return true;
		}
		x = getTileX(x);
		y = getTileY(y);

		// refreshPosition(x, y, owner);

		byte titleType = mapData.getTile(x, y);

		return titleType != TileType.OBSTACLE && titleType != TileType.WATER && titleType != TileType.BOX;

	}

	public void initPosition(int currTileX, int currTileY, Living owner) {
		physicsArray[currTileX][currTileY] = 1;
		String key = getPositionKey(currTileX, currTileY);
		if (!multimap.containsKey(key)) {
			multimap.put(key, ArrayListMultimap.<Class, Living> create());
		}
		multimap.get(getPositionKey(currTileX, currTileY)).put(owner.getClass(), owner);
	}

	public void refreshPosition(int currTileX, int currTileY, Living owner) {
		if (owner.getTileX() != currTileX || owner.getTileY() != currTileY) {
			removePosition(owner);
			// initPosition(currTileX, currTileY, owner);
			owner.setTileX(currTileX);
			owner.setTileY(currTileY);
		}
	}

	/**
	 * 死亡时移除
	 */
	public void removePosition(Physics owner) {
		String key = getPositionKey(owner.getTileX(), owner.getTileY());
		if (multimap.containsKey(key) && multimap.get(key).containsEntry(owner.getClass(), owner)) {
			multimap.get(key).remove(owner.getClass(), owner);
			if (multimap.get(key).size() == 0) {
				physicsArray[owner.getTileX()][owner.getTileY()] = 0;
			}
		}
	}

	private final ArrayList<Living> emptyList = new ArrayList<>();

	/**
	 * 查找具体格子里某种类型的物体
	 * 
	 * @return 返回一个集合, 不会返回空
	 */
	public Collection<Living> getCollectionAtPosition(int x, int y, Class clazz) {

		if (x >= 0 && y >= 0 && x < arrayWidth && y < arrayHeight) {
			if (physicsArray[x][y] == 1) {
				return multimap.get(getPositionKey(x, y)).get(clazz);
			}
		}
		return emptyList;
	}

	private String getPositionKey(int tileX, int tileY) {
		return tileX + "-" + tileY;
	}

	/**
	 * 是否障碍点
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isObstacle(int x, int y) {
		if (x < minX || y < minY) {
			return true;
		}
		if (x >= maxX || y >= maxY) {
			return true;
		}

		x = getTileX(x);
		y = getTileY(y);

		return mapData.isObstacleTile(x, y);
	}

	public int getTileX(int x) {

		return (x + mapData.getShiftX()) / ConfigsCfg.getCellSzie();

	}

	public int getTileY(int y) {

		return (y + mapData.getShiftY()) / ConfigsCfg.getCellSzie();
		// return y1%cell==0?(y1/cell):(y1/cell+1);
	}

	/** 设置格子的类型 */
	public void setTitleType(int titleX, int titleY, byte type) {
		mapData.setTile(titleX, titleY, type);
	}

	public int[] getAroundPoint(int x, int y, int radius) {
		x = getTileX(x);
		y = getTileY(y);
		int[] point = mapData.getAroundPoint(x, y, radius);
		if (point != null) {
			point[0] = point[0] * ConfigsCfg.getCellSzie() - mapData.getShiftX();
			point[1] = point[1] * ConfigsCfg.getCellSzie() - mapData.getShiftY();
		}
		return point;
	}

	private final int[] shift = { -1, 0, 1 };

	public int[] getRandomPoint(Living living) {
		int x = living.getTileX();
		int y = living.getTileY();

		int[] point = new int[2];
		point[0] = x + shift[ProbabilityUtil.randomIntValue(1, shift.length) - 1];
		point[1] = y + shift[ProbabilityUtil.randomIntValue(1, shift.length) - 1];
		while (mapData.isObstacleTile(point[0], point[1])) {
			point[0] = x + shift[ProbabilityUtil.randomIntValue(1, shift.length)];
			point[1] = y + shift[ProbabilityUtil.randomIntValue(1, shift.length)];
		}

		point[0] = point[0] * ConfigsCfg.getCellSzie() - mapData.getShiftX();
		point[1] = point[1] * ConfigsCfg.getCellSzie() - mapData.getShiftY();

		return point;
	}

	/**
	 * 将客户端传过来的坐标转化为服务器坐标
	 */
	public int getShiftX(int x) {
		return x + mapData.getShiftX();
	}

	DecimalFormat df = new DecimalFormat("##.###");

	/**
	 * 将客户端传过来的坐标转化为服务器坐标
	 */
	public int getShiftY(int y) {
		return y + mapData.getShiftY();
	}

	public boolean hasObstacle1(int x1, int y1, int x2, int y2, boolean checkBox) {
		int tx1 = getTileX(x1);
		int ty1 = getTileY(y1);

		int tx2 = getTileX(x2);
		int ty2 = getTileY(y2);

		// 变化量
		int step = Math.max(Math.abs(tx1 - tx2), Math.abs(ty1 - ty2));
		// 增量
		float increx = ((tx2 - tx1) / (float) step);
		float increy = ((ty2 - ty1) / (float) step);
		// 当前坐标
		float c = tx1;
		float r = ty1;
		for (int i = 1; i <= step; i++) {
			c += increx;
			r += increy;
			int tx3 = (int) Math.ceil(c);
			int ty3 = (int) Math.ceil(r);

			if ((checkBox && mapData.isBoxTile(tx3, ty3)) || mapData.isObstacleTile(tx3, ty3)) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 判断两点之间是否有障碍
	 */
	public boolean hasObstacle(int x1, int y1, int x2, int y2) {
		x1 = getShiftX(x1);
		x2 = getShiftX(x2);
		y1 = getShiftY(y1);
		y2 = getShiftY(y2);

		double k = (y1 - y2) / (double) (x1 - x2);
		double b = y1 - k * x1;

		if (x1 > x2) {
			int t = x1;
			x1 = x2;
			x2 = t;
			t = y1;
			y1 = y2;
			y2 = t;
		}

		int startX, startY, endX, endY;
		startX = x1;
		startY = y1;
		endX = nextX(x1);
		endY = nextY(endX, k, b);

		if (endX > x2) {
			endX = x2;
			endY = y2;
		}

		while (startX < endX) {
			try {
				if (isVerticalObstacle(startX, startY, endX, endY, k)) {
					return true;
				}
				startX = endX;
				startY = endY;
				endX = nextX(startX);
				endY = nextY(endX, k, b);

				if (endX > x2 && startX < x2) {
					endX = x2;
					endY = y2;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return true;
			}
		}

		return false;
	}

	public int nextX(int currentX) {
		return (currentX + mapData.getCell()) / mapData.getCell() * mapData.getCell();
	}

	/**
	 * 得到起始点的线与地图格子边界交点坐标
	 */
	public int nextY(int nextX, double k, double b) {
		return (int) (k * nextX + b);
	}

	/**
	 * 判断同一列指定段是否有障碍物
	 * 
	 * @return 真表示有障碍
	 */
	public boolean isVerticalObstacle(int x1, int y1, int x2, int y2, double k) throws Exception {
		int tileX1 = x1 / mapData.getCell();
		int tileY1 = y1 / mapData.getCell();
		int tileX2 = x2 / mapData.getCell();
		int tileY2 = y2 / mapData.getCell();

		// (x1, y1)为一列地图里左边的点，（x2, y2）为这一列右边的点
		if (tileX2 - tileX1 == 1 || tileX1 == tileX2) {
			if (k > 0) {
				for (; tileY1 <= tileY2; tileY1++) {
					if (tileY1 * mapData.getCell() < y2 && mapData.isObstacleTile(tileX1, tileY1)) {
						return true;
					}
				}
			} else if (k < 0) {
				for (; tileY2 <= tileY1; tileY2++) {
					if (tileY2 * mapData.getCell() < y1 && mapData.isObstacleTile(tileX1, tileY2)) {
						return true;
					}
				}
			} else {
				if (x1 % mapData.getCell() != 0) {
					for (; tileY1 <= tileY2; tileY1++) {
						if (mapData.isObstacleTile(tileX1, tileY1)) {
							return true;
						}
					}
				}
			}
			return false;
		} else {

			throw new Exception("传入的坐标不对，必须为同一列里的左右两点");
		}
	}

}
