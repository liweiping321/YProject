package com.road.fire.fighting.phy;
 

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.delay.DelayTaskQueue;
import com.game.polling.PollingUpdate;
import com.game.property.IPropertyManager;
import com.game.property.PropertyConstants;
import com.game.property.PropertyValue;
import com.google.protobuf.GeneratedMessage;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.phy.scene.GameScene;
import com.road.fire.fighting.util.PhyIncrId;
 

/**
 * 地图上所物件的基类，关注位置等物理信息
 */

public abstract class Physics<T> implements PollingUpdate {
	
	protected static final Logger LOG=LogManager.getLogger(Physics.class);
	
	protected BaseGame game;

	protected final int phyId; // 物件ID

	protected int x;

	protected int y;

	protected int tileX;

	protected int tileY;
	/** 朝向 */
	protected int faceDir;
	/** 移动方向 */
	protected int moveDir;

	protected int bornX;

	protected int bornY;

	/** 延迟任务队列 */
	protected final DelayTaskQueue taskQueue = new DelayTaskQueue();

	/** 属性管理器 */
	protected IPropertyManager<T> propertyManager;

	/** 阵营类型 **/
	protected final int campType;
	/** 临时距离 */
	protected int teampDistance;
	/** 下次复活时间点 */
	private long reliveTime;
	/** 临时值，有时要用到，没意义*/
	private int temp;

	public Physics(int x, int y, BaseGame game, int campType) {
		super();
		this.phyId = PhyIncrId.incrAndGetPhyId();
		this.x = x;
		this.y = y;
		this.bornX = x;
		this.bornY = y;
		this.campType = campType;
		this.game = game;
		tileX = game.getScene().getTileX(x);
		tileY = game.getScene().getTileY(y);
	}

	public IPropertyManager<T> getPropertyManager() {
		return propertyManager;
	}

	public void setPropertyManager(IPropertyManager<T> propertyManager) {
		this.propertyManager = propertyManager;
	}

	public int getCurrExp() {
		return propertyManager.getIntValue(PropertyConstants.CurrExp);
	}

	public int getLevel() {
		return propertyManager.getIntValue(PropertyConstants.Level);
	}

	public int getCurrHp() {
		return propertyManager.getIntValue(PropertyConstants.CurrHp);
	}

	public int getMaxHp() {
		return propertyManager.getIntValue(PropertyConstants.MaxHp);
	}

	public int getAttack() {
		return propertyManager.getIntValue(PropertyConstants.Attack);
	}

	public int getDefense() {
		return propertyManager.getIntValue(PropertyConstants.Defense);
	}

	public int getMoveSpeed() {
		return propertyManager.getIntValue(PropertyConstants.MoveSpeed);
	}

	public int getEyeShot() {
		return propertyManager.getIntValue(PropertyConstants.EyeShot);
	}

	public int getMoveReduce() {
		return propertyManager.getIntValue(PropertyConstants.MoveReduce);
	}

	public int getBreachPower() {
		return propertyManager.getIntValue(PropertyConstants.BreachPower);
	}

	public int getClipSize() {
		return propertyManager.getIntValue(PropertyConstants.ClipSize);
	}

	public int getCurrClipSize() {
		return propertyManager.getIntValue(PropertyConstants.currClipSize);
	}

	public int getReloadTime() {
		return propertyManager.getIntValue(PropertyConstants.ReloadTime);
	}

	public void reduceClipSize() {
		propertyManager.setIntValue(PropertyConstants.currClipSize, getCurrClipSize() - 1);
		propertyManager.sendPropertyValue(true, PropertyConstants.currClipSize);
	}

	public int reduceHp(int hurtValue, boolean update) {

		int currHp = Math.max(0, getCurrHp() - hurtValue);
		PropertyValue propVlaue = propertyManager.getProperty(PropertyConstants.CurrHp);
		propVlaue.setIntValue(currHp);

		if (update) {
			propertyManager.sendPropertyValue(true, PropertyConstants.CurrHp);
		} else {
			propVlaue.setUpdate(false);
		}

		return propVlaue.getIntValue();
	}

	public int addHp(int hp, boolean update) {
		PropertyValue propVlaue = propertyManager.getProperty(PropertyConstants.CurrHp);

		int currHp = propVlaue.getIntValue() + hp;
		currHp = Math.min(currHp, getMaxHp());
		propVlaue.setIntValue(currHp);

		int addHp = propVlaue.getChangValue();
		if (update) {
			propertyManager.sendPropertyValue(true, PropertyConstants.CurrHp);
		} else {
			propVlaue.setUpdate(false);
		}
		return addHp;
	}

	public void sendMsg(int code) {

	}

	public void sendMsg(int code, GeneratedMessage msg) {

	}

	public void broadcastMsg(int code, Living<?> except) {
		game.sendMsg(code, except);
	}

	public void broadcastMsg(int code, GeneratedMessage msg, Living<?> except) {
		game.sendMsg(code, msg, except);
	}

	public void broadcastMsg(int code) {
		game.sendMsg(code);
	}

	public void broadcastMsg(int code, GeneratedMessage msg) {
		game.sendMsg(code, msg);
	}

	public abstract String getName();

	public abstract int getTemplateId();

	public GameScene getScene() {
		return game.getScene();
	}

	public BaseGame getGame() {
		return game;
	}

	public int getCampType() {
		return campType;
	}

	public abstract int getPhType();

	public int getPhyId() {
		return phyId;
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

	public int getTileX()
	{
		return tileX;
	}

	public void setTileX(int tileX)
	{
		this.tileX = tileX;
	}

	public int getTileY()
	{
		return tileY;
	}

	public void setTileY(int tileY)
	{
		this.tileY = tileY;
	}

	public int getFaceDir() {
		return faceDir;
	}

	public void setFaceDir(int faceDir) {
		this.faceDir = faceDir;
	}

	public int getMoveDir() {
		return moveDir;
	}

	public void setMoveDir(int moveDir) {
		this.moveDir = moveDir;
	}

	public int getBornX() {
		return bornX;
	}

	public void setBornX(int bornX) {
		this.bornX = bornX;
	}

	public int getBornY() {
		return bornY;
	}

	public void setBornY(int bornY) {
		this.bornY = bornY;
	}

	public DelayTaskQueue getTaskQueue() {
		return taskQueue;
	}

	public void setCurrHp(int currHp) {
		propertyManager.setIntValue(PropertyConstants.CurrHp, currHp);
	}

	/** 当前帧时间 */
	public long getFrameTime() {
		return game.getFrameTime();
	}

	/** 伤害半径 */
	public int getDamageRadius() {
		return 1000;
	}

	public int getTempDistance() {
		return teampDistance;
	}

	public void setTeampDistance(int teampDistance) {
		this.teampDistance = teampDistance;
	}

	public int getTemp()
	{
		return temp;
	}

	public void setTemp(int temp)
	{
		this.temp = temp;
	}

	public long getReliveTime() {
		return reliveTime;
	}

	public void setReliveTime(long reliveTime) {
		this.reliveTime = reliveTime;
	}
	/**两个物件中是否有障碍物*/
 
	public boolean hasObstacle(Physics<?> target,boolean checkBox){
		return game.getScene().hasObstacle1(x, y, target.x, target.y,checkBox);
}

}
