package com.road.fire.fighting.ai.astar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
/**
 * A*算法
 * 
 * @author spring
 * 
 */
public class AStarNewArithmetic {

	private static final Logger LOG=LogManager.getLogger(ArithmeticUtils.class);

	/**
	 * 路径搜索
	 * @param map
	 * @param ox
	 * @param oy
	 * @param tx
	 * @param ty
	 * @param cacheMap
	 * @return
	 */
	public static short[] search( short ox, short oy,
			short tx, short ty, int wholeMapWidth, int wholeMapHeight,
			short[][] arr,byte[][] cacheMap) {

		if (arr[tx][ty]==1) {
			LOG.warn("A*算法中传入的点是障碍物点");
			return null; // 目标点是障碍物的话直接返回
		}
		
		//new往后靠
		Point[] openArr = new Point[1100];
		int openArrCnt = 0;
		//Object block = map.get(ArithmeticUtils.shortToInt(tx, ty));
		
		Point original = new Point(ox, oy);
		Point target = new Point(tx, ty);
		Map<Integer, Point> openList = new HashMap<Integer, Point>();// 开启列表
		Map<Integer, Point> closeList = new HashMap<Integer, Point>();// 关闭列表
		List<Point> roadPath = new ArrayList<Point>();// 找好的路径
		original.calculateH(target); // 计算H值
		openList.put(original.toKey(), original); // 添加到开启列表中
		addPoint(openArr, original, 0);
		openArrCnt++;
		int count = 0;
		// 循环做以下事情
		while (true) {
			if (count > 5800) {
				break;
			}
			
			// 检查开启列表是否为空，为空代表死路，直接返回
			if (openList.isEmpty()) {
				return null;
			}
			
			if (openArrCnt > 1000) {// 开启列表大于1000个返回null
				return null;
			}
			
			Point minPoint = delPoint(openArr, openArrCnt - 1);
			openArrCnt--;
			openList.remove(minPoint.toKey()); // 将该点从开启列表里删除
			closeList.put(minPoint.toKey(), minPoint);// 再加到关闭列表里
			// 判断该关闭的节点是否为目标点
			if (minPoint.equals(target)) {
				roadPath.add(minPoint);
				Point tmpP = minPoint;
				while (true) {
					if (tmpP.getParent() == null) { // 已经到了起始的位置
						short[] returnArr = new short[2 * roadPath.size()];
						int index = 0;
						for (Iterator<Point> iterator = roadPath.iterator(); iterator
								.hasNext();) {
							Point pointInPath = iterator.next();
							returnArr[2 * index] = pointInPath.getX();
							returnArr[2 * index + 1] = pointInPath.getY();
							index++;
						}
						openArr = null;
						return returnArr;
					}
					tmpP = tmpP.getParent();
					roadPath.add(tmpP);
				}
			}

			// 判断是否为障碍物或者在关闭列表或者已经出界里，如果是就跳过,不继续处理
			short minX = minPoint.getX();
			short minY = minPoint.getY();
			//int key = ArithmeticUtils.shortToInt(minX, minY);
			byte arroundPoints = cacheMap[minX][minY];

			boolean block1 = false;
			boolean block2 = false;
			boolean block3 = false;
			boolean block4 = false;
			boolean block5 = false;
			boolean block6 = false;
			boolean block7 = false;
			boolean block8 = false;
			block1 = (((arroundPoints & Direct.up) >> 7) == 1) ? true
					: false;
			openArrCnt = check(minX, (short) (minY - 1), wholeMapWidth,
					wholeMapHeight, minPoint, target, false, closeList,
					openList, openArr, openArrCnt, block1);
			block2 = (((arroundPoints & Direct.down) >> 6) == 1) ? true
					: false;
			openArrCnt = check(minX, (short) (minY + 1), wholeMapWidth,
					wholeMapHeight, minPoint, target, false, closeList,
					openList, openArr, openArrCnt, block2);
			block3 = (((arroundPoints & Direct.left) >> 5) == 1) ? true
					: false;
			openArrCnt = check((short) (minX - 1), minY, wholeMapWidth,
					wholeMapHeight, minPoint, target, false, closeList,
					openList, openArr, openArrCnt, block3);
			block4 = (((arroundPoints & Direct.right) >> 4) == 1) ? true
					: false;
			openArrCnt = check((short) (minX + 1), minY, wholeMapWidth,
					wholeMapHeight, minPoint, target, false, closeList,
					openList, openArr, openArrCnt, block4);
			block5 = (((arroundPoints & Direct.up_left) >> 3) == 1) ? true
					: false;
			openArrCnt = check((short) (minX - 1), (short) (minY - 1),
					wholeMapWidth, wholeMapHeight, minPoint, target, true,
					closeList, openList, openArr, openArrCnt, block5, block1,
					block3);
			block6 = ((((arroundPoints & Direct.up_right) >> 2) == 1)) ? true
					: false;
			openArrCnt = check((short) (minX + 1), (short) (minY - 1),
					wholeMapWidth, wholeMapHeight, minPoint, target, true,
					closeList, openList, openArr, openArrCnt, block6, block1,
					block4);
			block7 = ((((arroundPoints & Direct.down_left) >> 1) == 1)) ? true
					: false;
			openArrCnt = check((short) (minX - 1), (short) (minY + 1),
					wholeMapWidth, wholeMapHeight, minPoint, target, true,
					closeList, openList, openArr, openArrCnt, block7, block3,
					block2);
			block8 = (((arroundPoints & Direct.down_right) == 1)) ? true
					: false;
			openArrCnt = check((short) (minX + 1), (short) (minY + 1),
					wholeMapWidth, wholeMapHeight, minPoint, target, true,
					closeList, openList, openArr, openArrCnt, block8, block4,
					block2);

			count++;
		}

		openArr = null;
		return null;
	}

	
	/**
	 * @param x
	 * @param y
	 * @param wholeMapWidth
	 * @param wholeMapHeight
	 * @param minPoint
	 * @param target
	 * @param isBevel
	 * @param closeList
	 * @param openList
	 * @param openPoints
	 * @param cnt
	 * @param block
	 * @return
	 */
	public static int check(short x, short y, int wholeMapWidth,
			int wholeMapHeight, Point minPoint, Point target, boolean isBevel,
			Map<Integer, Point> closeList, Map<Integer, Point> openList,
			Point[] openPoints, int cnt, boolean... block) {
		// 先判断是否出边界，然后在判断是否是障碍物
		Integer key = ArithmeticUtils.shortToInt(x, y);
		if (x < 0 || x > (wholeMapWidth - 1) || y < 0
				|| y > (wholeMapHeight - 1) || block[0]
				|| closeList.get(key) != null) {
			return cnt;
		}

		// 斜角处理，判断它的上下或者左右是否有障碍物，有就跳过
		if (isBevel && (block[1] || block[2])) {
			return cnt;
		}

		// 再检查是否在开启列表里，如果不是就加到开启列表里，如果是就重新评估
		Point nodePoint = openList.get(key);
		if (nodePoint != null) {
			// 在开启列表里，重新评估g
			int newG = nodePoint.isBevel() ? minPoint.getG() + Point.BEVEL
					: minPoint.getG() + Point.NOTBEVEL;
			if (newG < nodePoint.getG()) {
				// 更改G以及F值和父节点
				nodePoint.calculateG(minPoint);
				nodePoint.setParent(minPoint);
			}
		} else {
			// 未在开启列表里，计算该节点的g、h并加入到开启列表里
			nodePoint = new Point(x, y);
			nodePoint.calculateG(minPoint);
			nodePoint.calculateH(target);
			nodePoint.setParent(minPoint);// 记录当前的点的父节点
			openList.put(nodePoint.toKey(), nodePoint); // 加到开启列表里
			addPoint(openPoints, nodePoint, cnt);
			return ++cnt;
		}
		return cnt;
	}

	/**
	 * 检查周边的障碍物
	 * 
	 * @param xIn X坐标
	 * @param yIn Y坐标
	 * @param xlen X长度
	 * @param ylen Y长度
	 * @param arr 地图格子
	 * @return
	 */
	public static byte checkBlockAroundPoint(short xIn, short yIn, int xlen,
			int ylen, byte[][] arr) {
		byte b = 0;
		if (checkVertical(yIn, -1, ylen))
			b = getByte(xIn, yIn - 1, arr, b,  Direct.up);
		if (checkVertical(yIn, 1, ylen))
			b = getByte(xIn, yIn + 1, arr, b, Direct.down);
		if (checkVertical(xIn, -1, xlen))
			b = getByte(xIn - 1, yIn, arr, b, Direct.left);
		if (checkVertical(xIn, 1, xlen))
			b = getByte(xIn + 1, yIn, arr, b, Direct.right);
		if (checkVertical(xIn, -1, xlen) && checkVertical(yIn, -1, ylen))
			b = getByte(xIn - 1, yIn - 1, arr, b, Direct.up_left);
		if (checkVertical(xIn, 1, xlen) && checkVertical(yIn, -1, ylen))
			b = getByte(xIn + 1, yIn - 1, arr, b, Direct.up_right);
		if (checkVertical(xIn, -1, xlen) && checkVertical(yIn, 1, ylen))
			b = getByte(xIn - 1, yIn + 1, arr, b, Direct.down_left
					);
		if (checkVertical(xIn, 1, xlen) && checkVertical(yIn, 1, ylen))
			b = getByte(xIn + 1, yIn + 1, arr, b, Direct.down_right
					);
		return b;
	}

	/**
	 * 格子周边状态缓存计算
	 * @param xIn X坐标
	 * @param yIn Y坐标
	 * @param arr 格子
	 * @param b 状态标识缓存
	 * @param value 方向数
	 * @return
	 */
	private static byte getByte(int xIn, int yIn, byte[][] arr, byte b,
			int value) {
		if (xIn < 0 || xIn > arr.length - 1 || yIn < 0
				|| yIn > arr[0].length - 1) {
			LOG.warn("寻路缓存的过程中数组出现越界的情况，请检查");
		}
		if (arr[xIn][yIn] == 1)
			b = (byte) (b | value);//有1则为1
		return b;
	}

	/**
	 * 边界检测
	 * 
	 * @param o X或Y坐标
	 * @param direct 方向控制+-1
	 * @param len X或Y长度
	 * @return
	 */
	private static boolean checkVertical(int o, int direct, int len) {
		int tmp = o + direct;
		if (tmp < 0 || tmp > len - 1)
			return false;
		else
			return true;
	}

	/**
	 * 消除点
	 * 
	 * @param $openArr
	 * @param $index
	 * @return
	 */
	private static Point delPoint(Point[] $openArr, int $index) {
		Point rePoint = $openArr[0];
		$openArr[0] = $openArr[$index];
		$openArr[$index] = null;
		int _min = 0;
		int _len = $index;
		int _mid1 = 0;
		int _mid2 = 0;
		Point _midV1 = null;
		Point _midV2 = null;
		Point _minV = null;
		while ((_min + 1) * 2 < _len) {
			_mid1 = (_min + 1) * 2;
			_mid2 = _mid1 + 1;
			_midV1 = $openArr[_mid1 - 1];
			_midV2 = (_mid2 == _len) ? null : $openArr[_mid2 - 1];
			_minV = $openArr[_min];

			// 2
			if (_midV2 == null) {
				if (_minV.getF() > _midV1.getF()) {
					$openArr[_min] = _midV1;
					$openArr[_mid1 - 1] = _minV;
					_min = _mid1 - 1;
				} else {
					break;
				}
			} else {
				if (_minV.getF() < _midV1.getF()
						&& _minV.getF() < _midV2.getF())
					break;

				// 3
				if (_midV1.getF() <= _midV2.getF()) {
					if (_minV.getF() > _midV1.getF()) {
						$openArr[_min] = _midV1;
						$openArr[_mid1 - 1] = _minV;
						_min = _mid1 - 1;
					} else {
						$openArr[_min] = _midV2;
						$openArr[_mid2 - 1] = _minV;
						_min = _mid2 - 1;
					}
				} else {
					if (_minV.getF() > _midV2.getF()) {
						$openArr[_min] = _midV2;
						$openArr[_mid2 - 1] = _minV;
						_min = _mid2 - 1;
					} else {
						$openArr[_min] = _midV1;
						$openArr[_mid1 - 1] = _minV;
						_min = _mid1 - 1;
					}
				}
			}
		}

		return rePoint;
	}

	/**
	 * 上滤
	 * 
	 * @param $openArr
	 * @param $newV
	 * @param $index
	 */
	private static void addPoint(Point[] $openArr, Point $newV, int $index) {
		$openArr[$index] = $newV;
		int _len = $index + 1;
		int _mid = _len / 2;
			Point _temp = null;
			Point _midV = null;
			while (_mid > 0) {
				_temp = $openArr[_len - 1];
				_midV = $openArr[_mid - 1];
				if (_temp.getF() < _midV.getF()) {
					$openArr[_len - 1] = _midV;
					$openArr[_mid - 1] = _temp;
					_len = _mid;
					_mid = _len / 2;
				} else {
					break;
				}
			}
	}
}
