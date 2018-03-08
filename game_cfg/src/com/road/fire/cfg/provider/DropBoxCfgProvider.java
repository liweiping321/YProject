package com.road.fire.cfg.provider;

import java.util.TreeMap;

import com.game.cfg.BaseCfgEntiyProvider;
import com.road.fire.entity.cfg.DropBoxCfg;
import com.road.fire.entity.cfg.DropItem;

/**
 *@author 工具生成
 *@date 2017-05-17 06:18:59 
 */
public class DropBoxCfgProvider extends BaseCfgEntiyProvider<Integer,DropBoxCfg> {
	
 
	@Override
	public void afterReload(DropBoxCfg value) {
		int probability=0;
		TreeMap<Integer,DropItem> dropItemMap = new TreeMap<Integer,DropItem>();
		
		if (value.getGoodsId1() > 0&&value.getProbability1()>0) {
			probability= probability+value.getProbability1();
			dropItemMap.put(probability,new DropItem(value.getGoodsId1(), value.getGoodsNum1(), value.getProbability1()));
		}
		if (value.getGoodsId2() > 0&&value.getProbability2()>0) {
			probability= probability+value.getProbability2();
			dropItemMap.put(probability,new DropItem(value.getGoodsId2(), value.getGoodsNum2(), value.getProbability2()));
		}
		if (value.getGoodsId3() > 0&&value.getProbability3()>0) {
			probability= probability+value.getProbability3();
			dropItemMap.put(probability,new DropItem(value.getGoodsId3(), value.getGoodsNum3(), value.getProbability3()));
		}
		if (value.getGoodsId4() > 0&&value.getProbability4()>0) {
			probability= probability+value.getProbability4();
			dropItemMap.put(probability,new DropItem(value.getGoodsId4(), value.getGoodsNum4(), value.getProbability4()));
		}
		if (value.getGoodsId5() > 0&&value.getProbability5()>0) {
			probability= probability+value.getProbability5();
			dropItemMap.put(probability,new DropItem(value.getGoodsId5(), value.getGoodsNum5(), value.getProbability5()));
		}
	 
		value.setDropItems(dropItemMap);

	}
	
	public Class<?> getCfgClass(){
		
		return DropBoxCfg.class;
	}

	private static final DropBoxCfgProvider instance=new DropBoxCfgProvider();

	public static DropBoxCfgProvider getInstance() {
		return instance;
	}
	
	
}
