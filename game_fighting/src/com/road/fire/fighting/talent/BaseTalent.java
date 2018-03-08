package com.road.fire.fighting.talent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.polling.PollingTimer;
import com.road.fire.cfg.provider.TalentCfgProvider;
import com.road.fire.entity.cfg.TalentCfg;
import com.road.fire.fighting.phy.living.Hero;

/**
 * 天赋基础类
 * @author lip.li
 *
 */
public abstract class BaseTalent{
	protected static final Logger LOG=LogManager.getLogger(BaseTalent.class);
	
	protected Hero owner;
	
	private int talentId;
	
	private PollingTimer timer=null;
	
	public BaseTalent(Hero owner,int talentId){
		this.owner=owner;
		this.talentId=talentId;
	
		initTimer(owner);
	}



	private void initTimer(Hero owner) {
		TalentCfg talentCfg=getTalentCfg();
		if(talentCfg.getIntervalTime()>0){
			timer=new PollingTimer(talentCfg.getIntervalTime(), owner.getFrameTime());
		}
	}
	
	
	
	public abstract void onStart(long now);
	
	public abstract  void onStop(long now);
	
	public void update(long now){
		if(timer!=null&&timer.isIntervalOk(now)){
			onUpdate(now);
		}
	}
	public  void onUpdate(long now){
		
	}

	public Hero getOwner() {
		return owner;
	}

	public void setOwner(Hero owner) {
		this.owner = owner;
	}

	public int getTalentId() {
		return talentId;
	}

	public void setTalentId(int talentId) {
		this.talentId = talentId;
	}
	
	public TalentCfg getTalentCfg(){
		TalentCfg talentCfg=TalentCfgProvider.getInstance().getConfigVoByKey(talentId);
		return talentCfg;
	}
}
