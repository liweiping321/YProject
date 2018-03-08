package com.road.fire.fighting.effect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.polling.PollingUpdate;
import com.road.fire.entity.cfg.EffectCfg;
import com.road.fire.fighting.buff.BaseBuff;
 

public abstract class BaseEffect implements PollingUpdate {
	
	private static final Logger LOG=LogManager.getLogger(BaseEffect.class);
	protected EffectCfg effectCfg;
	
	protected BaseBuff buff;
	
	
	public BaseEffect( EffectCfg effectCfg) {
		super();
		this.effectCfg = effectCfg;
	}
 
	public boolean start(BaseBuff buff) {
		LOG.debug("效果开始，effectId:{},type:{}",effectCfg.getEffectId(),effectCfg.getEffectType());
		this.buff = buff;
	 
		onStart();
		return true;
	}
	public abstract void onStart();
 
	public void update(long now) {}
	

	/**
	 * effect结束处理
	 */
	public void stop() {
		onStop();
	}

	protected abstract void onStop();

	/**
	 * 是否结束
	 * 
	 * @return
	 */
	public boolean isStop() {
		return buff.isStop();
	}

	 

	protected int getBuffID() {
		if (buff == null)
			return 0;
		return buff.getBuffCfg().getBuffId();
	}
	
	
	 
	public EffectCfg getEffectCfg() {
		return effectCfg;
	}

	public void setEffectCfg(EffectCfg effectCfg) {
		this.effectCfg = effectCfg;
	}

	public BaseBuff getBuff() {
		return buff;
	}
	public void setBuff(BaseBuff buff) {
		this.buff = buff;
	}
	
	public int getEffectId(){
		return effectCfg.getEffectId();
	}

	public int getEffectType() {
		 
		return effectCfg.getEffectType();
	}

}
