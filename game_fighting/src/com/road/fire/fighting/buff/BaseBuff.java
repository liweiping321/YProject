package com.road.fire.fighting.buff;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.polling.PollingTimer;
import com.game.polling.PollingUpdate;
import com.road.fire.entity.cfg.BuffCfg;
import com.road.fire.entity.cfg.EffectCfg;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.effect.BaseEffect;
import com.road.fire.fighting.effect.EffectFactory;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.skill.BaseSkill;
import com.road.fire.fighting.util.PhyIncrId;
/**
 * 
 * @author lip.li
 *
 */
public class BaseBuff implements PollingUpdate {
	
	private static final Logger LOG=LogManager.getLogger(BaseBuff.class);
 
	private Map<Integer,BaseEffect> effectMap=new HashMap<Integer,BaseEffect>(6);
	/**模板数据*/
	private final BuffCfg buffCfg;
	/**BUFF周期执行时间*/
	private PollingTimer intervalTimer;

	/**释放对象*/
	private Living<?> source;
	
	private BuffContainer buffContainer;
	
	private final int buffPhyId;
	/**游戏对象*/
	private BaseGame baseGame;
	/**在地图X坐标*/
	private int x;
	/**在地图Y坐标*/
	private int y;
	
	private boolean stop;
 
	/**释放技能*/
	private BaseSkill<?> skill;
	
	public BaseBuff(BuffCfg buffCfg,Living<?> source){
		this.buffCfg=buffCfg;
		this.buffPhyId=PhyIncrId.incrAndGetPhyId();
		this.source=source;
	}
	
	public BaseBuff(BuffCfg cfgBuff,int x,int y,Living<?> source){
		 this(cfgBuff, source);
		 this.x=x;
		 this.y=y;
	}
	
	public void start(BaseGame baseGame){
		this.baseGame=baseGame;
		
		long now=baseGame.getFrameTime();
		intervalTimer=new PollingTimer();
		long endTime=buffCfg.getLastTime()>=0?now+buffCfg.getLastTime():buffCfg.getLastTime();
		intervalTimer.start(buffCfg.getIntervalTime(), now, endTime);
		try{
			initEffect();
		}catch(Exception e){
			LOG.error(String.format("cfgBuff initEffect error, skillId:%d, buffID: %d", skill.getSkillId(), buffCfg.getBuffId()), e);
		}
	 
	}
	
	
	private void initEffect() {
		if(buffCfg.getEffectCfgs()!=null){
			for(EffectCfg cfgEffect:buffCfg.getEffectCfgs()){
				BaseEffect effect = EffectFactory.createEffect(cfgEffect);
				if (effect != null) {
					effectMap.put(effect.getEffectId(), effect);
					effect.start(this);
				}
			}
		}
		
	}
	/**
	 * buff结束
	 */
	public void stop(){
		if(stop){
			return ;
		}
		stop=buffContainer.removeBuff(this);
		
		for(BaseEffect effect:effectMap.values()){
			try {
				effect.stop();
			} catch (Exception e) {
				LOG.error(String.format("effect stop error, skillId:%d, buffID: %d ,effectId: %d", skill.getSkillId(), buffCfg.getBuffId()),effect.getEffectId(), e);
			}
		}
		effectMap.clear();
	}

	@Override
	public void update(long now) {
		
		if (intervalTimer.isIntervalOk(now)) {
			for(BaseEffect effect:effectMap.values()){
				try {
					effect.update(now);
				} catch (Exception e) {
					 LOG.error(e.getMessage(), e);
				}
			}
		}
		// 执行时间到了，BUFF停止移除
		if (intervalTimer.isOk(now)) {
			stop();
			return;
		}

	}

	public Map<Integer, BaseEffect> getEffectMap() {
		return effectMap;
	}

	public void setEffectMap(Map<Integer, BaseEffect> effectMap) {
		this.effectMap = effectMap;
	}

	public Living<?> getTarget() {
		return buffContainer.getOwner();
	}
 

	public Living<?> getSource() {
		return source;
	}

	public void setSource(Living<?> source) {
		this.source = source;
	}

	public BuffContainer getBuffContainer() {
		return buffContainer;
	}

	public void setBuffContainer(BuffContainer buffContainer) {
		this.buffContainer = buffContainer;
	}

	public BaseGame getBaseGame() {
		return baseGame;
	}

	public void setBaseGame(BaseGame baseGame) {
		this.baseGame = baseGame;
	}

	public int getX() {
		if(null==getTarget()){
			return x;
		}
		return getTarget().getX();
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		if(null==getTarget()){
			return y;
		}
		return getTarget().getY();
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public BaseSkill<?> getSkill() {
		return skill;
	}

	public void setSkill(BaseSkill<?> skill) {
		this.skill = skill;
	}

 

	public int getBuffPhyId() {
		return buffPhyId;
	}

	public int getBuffId() {
	 
		return buffCfg.getBuffId();
	}
	
	public boolean isDebuff(){
		return buffCfg.getDebuff();
	}

	public int getBuffType() {
	 
		return buffCfg.getBuffType();
	}

	public int getSkillId() {
		if(skill==null){
			return 0;
		}
		return skill.getSkillId();
	}

	public BuffCfg getBuffCfg() {
		return buffCfg;
	}
	
	public int getLastTime(){
		int lastTime=(int)(intervalTimer.getEndTime()-baseGame.getFrameTime());
		return lastTime;
	}
	public int getTargetId(){
		if(null==getTarget()){
			return 0;
		}
		return getTarget().getPhyId();
	}
	
	public int getSourceId(){
		if(null==source){
			return 0;
		}
		return source.getPhyId();
	}

	public boolean hasEffect(int effectId) {
		return effectMap.containsKey(effectId);
	}

	public boolean hasEffectType(int effectType) {
		for(BaseEffect baseEffect:effectMap.values()){
			if(baseEffect.getEffectType()==effectType){
				return true;
			}
		}
		return false;
	}
}
