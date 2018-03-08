package com.road.fire.entity.cfg;

import com.game.utils.ProbabilityUtil;

public class DropItem {
	private int goodsId;
	
	private int num;
	
	private int probability;

	public DropItem(int goodsId, int num, int probability) {
		super();
		this.goodsId = goodsId;
		this.num = num;
		this.probability = probability;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}
	
	public boolean drop(){
		return ProbabilityUtil.defaultIsGenerate(probability);
	}
	
	public boolean isDropGoods(){
		return goodsId>10000;
	}

}
