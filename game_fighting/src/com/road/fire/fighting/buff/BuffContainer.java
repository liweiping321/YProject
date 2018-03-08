package com.road.fire.fighting.buff;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.polling.PollingUpdate;
import com.game.protobuf.MsgCode;
import com.road.fire.FtMsgUtil;
import com.road.fire.cfg.provider.BuffCfgProvider;
import com.road.fire.entity.cfg.BuffCfg;
import com.road.fire.fighting.BaseGame;
import com.road.fire.fighting.phy.living.Living;
import com.road.fire.fighting.skill.BaseSkill;
/**
 * BUFF容器
 * @author lip.li
 *
 */
@SuppressWarnings("rawtypes")
public class BuffContainer implements PollingUpdate{
	
	private static final Logger LOG=LogManager.getLogger(BuffContainer.class);
	
	private final List<BaseBuff> buffs=new CopyOnWriteArrayList<>();
	
	
	private final Living owner;
	
	private final BaseGame game;
	

	public BuffContainer(Living owner, BaseGame game) {
		super();
		this.owner = owner;
		this.game = game;
	}
	/**获取BUFF列表*/
	public List<BaseBuff> getAllBuff(){
		return buffs;
	}
	/**添加BUFF **/
	public BaseBuff addBuff(int cfgBuffId,Living source,BaseSkill skill,int ...xy){
	 
		BuffCfg cfgBuff=BuffCfgProvider.getInstance().getConfigVoByKey(cfgBuffId);
		Objects.requireNonNull(cfgBuff);
		
		BaseBuff baseBuff =null;
		if (xy == null || xy.length == 0) {
			baseBuff= new BaseBuff(cfgBuff, source);
		} else {
			baseBuff = new BaseBuff(cfgBuff, xy[0], xy[1], source);
		}
		baseBuff.setSkill(skill);
		addStart(baseBuff);
		
		return baseBuff;
	}
	/**添加BUFF*/
	public void addStart(BaseBuff buff){
		buffs.add(buff);
		buff.setBuffContainer(this);
		buff.start(game);
		
		owner.broadcastMsg(MsgCode.BuffUpdateResp,FtMsgUtil.builderBuffUpdateResp(buff));
	}
	/**是否拥有某个BUFF*/
	public boolean isHaveBuffByID(int buffID) {
		for (BaseBuff temp : buffs) {
			if (temp.getBuffId() == buffID)
				return true;
		}
		return false;
	}
	/**是否拥有某个BUFF*/
	public boolean isHaveBuffByPhyID(int buffPhyId) {
		for (BaseBuff temp : buffs) {
			if (temp.getBuffPhyId() == buffPhyId)
				return true;
		}
		return false;
	}

	
	/**
	 * 是否拥有debuff
	 * 
	 * @return
	 */
	public boolean isHaveDebuff() {
		for (BaseBuff temp : buffs) {
			if (temp.isDebuff())
				return true;
		}
		return false;
	}
	
	/**
	 * 清除BUFF
	 */
	public void clearBuff() {
		for (BaseBuff baseBuff : buffs) {
			baseBuff.stop();
		}
	}
	
	@Override
	public void update(long now) {
		for (BaseBuff buff : buffs) {
			try {
				buff.update(now);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}

	}

	public boolean removeBuff(BaseBuff buff) {
	 
		boolean remove= buffs.remove(buff);
		
		if(remove){
			owner.broadcastMsg(MsgCode.BuffRemoveResp,FtMsgUtil.builderBuffRemoveResp(buff.getBuffPhyId()));
		}
		LOG.info("移除BUFF,BUFFID：{},phId:{},time:{},remove:{}",buff.getBuffId(), buff.getBuffPhyId(),game.getFrameTime(),remove);
		
		return remove;
	}
	
	/**
	 * 根据BUFFID，停止BUFF
	 */
	public void stopBuffByBuffID(int buffID) {
		for (BaseBuff buff : buffs) {
			if (buff.getBuffId() == buffID)
				buff.stop();
		}
	}

	/**
	 * 停止指定技能下所以BUFF
	 * 
	 * @param skillId
	 */
	public void stopBuffBySkillId(int skillId) {
		 
		for (BaseBuff buff : buffs) {
			if (buff.getSkillId() == skillId)
				buff.stop();
		}
	}

	public boolean isHaveBuffByBuffType(int buffType) {
		for (BaseBuff temp : buffs) {
			if (temp.getBuffType() == buffType)
				return true;
		}
		return false;
	}

	public BaseBuff getBuffByBuffId(int buffId) {
		for (BaseBuff buff:buffs) {
			if (buff.getBuffId() == buffId) {
				return buff;
			}
		}
		return null;
	}

	/**
	 * 添加buff
	 * 
	 * @param buffId
	 * @param target
	 */
	public BaseBuff addBuff(int buffId, Living source) {
	  return  addBuff(buffId, source, null);
	}
	
	public BaseBuff addBuff(int buffId) {
		return   addBuff(buffId, owner, null);
	}
	
	public boolean hasEffect(int effectId){
		for (BaseBuff buff:buffs) {
			if (buff.hasEffect(effectId)) {
				return true;
			}
		}
		return false;
	}
	public boolean hasEffectType(int effectType) {
		for (BaseBuff buff:buffs) {
			if (buff.hasEffectType(effectType)) {
				return true;
			}
		}
		return false;
	}
	public Living getOwner() {
		return owner;
	}
	
}
