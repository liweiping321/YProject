package com.road.fire.fighting.talent;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.game.polling.PollingUpdate;
import com.game.protobuf.MsgCode;
import com.game.utils.ProbabilityUtil;
import com.road.fire.FtMsgUtil;
import com.road.fire.cfg.provider.TalentCfgProvider;
import com.road.fire.entity.cfg.TalentCfg;
import com.road.fire.fighting.consts.TalentConsts;
import com.road.fire.fighting.phy.living.Hero;

public class TalentManager implements PollingUpdate {
	/**通用天赋池*/
	private Map<Integer,TalentCfg> commonPool=new HashMap<>();
	
	/**角色天赋池*/
	private Map<Integer,TalentCfg> heroPool=new HashMap<>();

	/**角色身上 当前拥有天赋*/
	private Map<Integer,BaseTalent> currTalents=new HashMap<>();
	
	/**选择池*/
	private TreeMap<Integer,TalentCfg> selectPool=new TreeMap<>();
	
	private Hero owner;
	
	private int talentPoint;
	
	public TalentManager(Hero owner){
		this.owner=owner;
		init();
	}
	
	public void init(){
		//初始化通用天赋池
		initTalentPool(commonPool,TalentConsts.CommonHeroId);
		//初始职业天赋池
		initTalentPool(heroPool, owner.getHeroCfg().getHeroId());
	}
	
	private void initTalentPool(Map<Integer,TalentCfg>  pool,int heroId) {
		Map<Integer,TalentCfg>  commonCfgs=TalentCfgProvider.getInstance().getHeroTalentCfgs(heroId);
		if(null!=commonCfgs){
			for(TalentCfg talentCfg:commonCfgs.values()){
				if(talentCfg.getLevel()==TalentConsts.MinLevel){
					pool.put(talentCfg.getTalentId(), talentCfg);
				}
			}
		}
	}

	@Override
	public void update(long now) {
		for(BaseTalent talent:currTalents.values()){
			talent.update(now);
		}
		
	}

	public void addTalentPoint(int talentPoint) {
		this.talentPoint=this.talentPoint+talentPoint;
		if(selectPool.size()>0){
			owner.sendMsg(MsgCode.TalentUpLevelListResp, FtMsgUtil.builderTalentUpLevelListResp(selectPool.keySet()));
			return ;
		}
		
		initSelectPool();
	}
	/**初始化选择池*/
	private void initSelectPool() {
		if(commonPool.size()+heroPool.size()<=0){
			return ;
		}
		
		randomTalent();
		
		owner.sendMsg(MsgCode.TalentUpLevelListResp, FtMsgUtil.builderTalentUpLevelListResp(selectPool.keySet()));
		
		talentPoint--;
	}

	@SuppressWarnings("unchecked")
	private void randomTalent() {
		if(commonPool.size()+heroPool.size()==0){
			return ;
		}
	    if(selectPool.size()>=TalentConsts.SelectPoolSize){
	    	return ;
	    }
	    
	    if(selectPool.size()>=commonPool.size()+heroPool.size()){
	    	return ;
	    }
	    if(selectPool.size()==0){
	    	 //随机职业天赋
		    if(heroPool.size()>0){
		    	randomTalent(heroPool);
		    }else{
		    	randomTalent(commonPool);
		    }
	    }else if(selectPool.size()==1){
	    	randomTalent(commonPool);
	    }else if(selectPool.size()==2){
	    	randomTalent(heroPool,commonPool);
	    }
	   
	    randomTalent();
		
	}

	private void randomTalent(@SuppressWarnings("unchecked") Map<Integer, TalentCfg> ...maps) {
		TreeMap<Integer, TalentCfg> treeMap = new TreeMap<Integer, TalentCfg>();
		int weight = 0;
		for (Map<Integer, TalentCfg> map : maps) {
			for (TalentCfg talentCfg : map.values()) {
				if(selectPool.containsKey(talentCfg.getTalentId())){
					continue;
				}
				weight = weight + talentCfg.getWeight();
				treeMap.put(weight, talentCfg);
			}
		}

		int key = ProbabilityUtil.randomIntValue(1, treeMap.lastKey());
		TalentCfg talentCfg = treeMap.ceilingEntry(key).getValue();
		
		selectPool.put(talentCfg.getTalentId(), talentCfg);
	}

	public void upLevel(int talentId) {
		
		TalentCfg talentCfg= selectPool.get(talentId);
		 if(talentCfg==null){
			 return ;
		 }
		 
		BaseTalent preTalent=currTalents.remove(talentCfg.getPreTalentId());
		if(preTalent!=null){
			preTalent.onStop(owner.getFrameTime());
		}
		
		BaseTalent  baseTalent=TalentFactory.getInstace().createTalent(talentId, owner);
		currTalents.put(talentId, baseTalent);
		baseTalent.onStart(owner.getFrameTime());
		
		resetPool(talentCfg);
		

		selectPool.clear();
		if(talentPoint>0){
			initSelectPool();
		}
	}

	private void resetPool(TalentCfg talentCfg) {
		TalentCfg nextTalentCfg=TalentCfgProvider.getInstance().getConfigVoByKey(talentCfg.getNextTalentId());
		if(talentCfg.getHeroId()>0){
			heroPool.remove(talentCfg.getTalentId());
			
			if(nextTalentCfg!=null){
				heroPool.put(nextTalentCfg.getTalentId(), nextTalentCfg);
			}
		}else{
			commonPool.remove(talentCfg.getTalentId());
			if(nextTalentCfg!=null){
				commonPool.put(nextTalentCfg.getTalentId(), nextTalentCfg);
			}
		}
	}

}
