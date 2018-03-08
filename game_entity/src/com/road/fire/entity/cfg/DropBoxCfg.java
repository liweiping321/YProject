package com.road.fire.entity.cfg;
 
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.game.consts.GameConsts;
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;
import com.game.utils.ProbabilityUtil;

/**
 *@author 工具生成
 *@date 2017-05-04 10:03:58 
 */
@EntityMap(table = "t_s_drop_box")
public class DropBoxCfg extends  AbstractEntityBean<Integer,DropBoxCfg>{
	
	/**掉落Id*/
	@PropertyMap(column="DropBoxId",primarykey=true)
	private  Integer dropBoxId;
	/**掉落名称*/
	@PropertyMap(column="Name")
	private  String name;
	/**掉落概率类型(0.固定概率掉落多个，1整体概率掉落一个)*/
	@PropertyMap(column="DropRateType")
	private  Integer dropRateType;
	/**掉落项1*/
	@PropertyMap(column="GoodsId1")
	private  Integer goodsId1;
	/**数量1*/
	@PropertyMap(column="GoodsNum1")
	private  Integer goodsNum1;
	/**概率1(万分比)*/
	@PropertyMap(column="Probability1")
	private  Integer probability1;
	/**掉落项2*/
	@PropertyMap(column="GoodsId2")
	private  Integer goodsId2;
	/**数量2*/
	@PropertyMap(column="GoodsNum2")
	private  Integer goodsNum2;
	/**概率2(万分比)*/
	@PropertyMap(column="Probability2")
	private  Integer probability2;
	/**掉落项3*/
	@PropertyMap(column="GoodsId3")
	private  Integer goodsId3;
	/**数量3*/
	@PropertyMap(column="GoodsNum3")
	private  Integer goodsNum3;
	/**概率3(万分比)*/
	@PropertyMap(column="Probability3")
	private  Integer probability3;
	/**掉落项4*/
	@PropertyMap(column="GoodsId4")
	private  Integer goodsId4;
	/**数量4*/
	@PropertyMap(column="GoodsNum4")
	private  Integer goodsNum4;
	/**概率4(万分比)*/
	@PropertyMap(column="Probability4")
	private  Integer probability4;
	/**掉落项5*/
	@PropertyMap(column="GoodsId5")
	private  Integer goodsId5;
	/**数量5*/
	@PropertyMap(column="GoodsNum5")
	private  Integer goodsNum5;
	/**概率5(万分比)*/
	@PropertyMap(column="Probability5")
	private  Integer probability5;
	/**掉落扩展(goodsId1|goodsNum1|probability1;goodsId2|goodsNum2|probability2)*/
	@PropertyMap(column="ExtendMaps")
	private  String extendMaps;
	/**描述*/
	@PropertyMap(column="Description")
	private  String description;
 
	
	private TreeMap<Integer,DropItem> dropItems=new TreeMap<Integer,DropItem>();
	
    
     
    public void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return name;
    }
    public void setDropRateType(Integer dropRateType){
        this.dropRateType = dropRateType;
    }

    public  Integer getDropRateType(){
        return dropRateType;
    }
    public void setGoodsId1(Integer goodsId1){
        this.goodsId1 = goodsId1;
    }

    public  Integer getGoodsId1(){
        return goodsId1;
    }
    public void setGoodsNum1(Integer goodsNum1){
        this.goodsNum1 = goodsNum1;
    }

    public  Integer getGoodsNum1(){
        return goodsNum1;
    }
    public void setProbability1(Integer probability1){
        this.probability1 = probability1;
    }

    public  Integer getProbability1(){
        return probability1;
    }
    public void setGoodsId2(Integer goodsId2){
        this.goodsId2 = goodsId2;
    }

    public  Integer getGoodsId2(){
        return goodsId2;
    }
    public void setGoodsNum2(Integer goodsNum2){
        this.goodsNum2 = goodsNum2;
    }

    public  Integer getGoodsNum2(){
        return goodsNum2;
    }
    public void setProbability2(Integer probability2){
        this.probability2 = probability2;
    }

    public  Integer getProbability2(){
        return probability2;
    }
    public void setGoodsId3(Integer goodsId3){
        this.goodsId3 = goodsId3;
    }

    public  Integer getGoodsId3(){
        return goodsId3;
    }
    public void setGoodsNum3(Integer goodsNum3){
        this.goodsNum3 = goodsNum3;
    }

    public  Integer getGoodsNum3(){
        return goodsNum3;
    }
    public void setProbability3(Integer probability3){
        this.probability3 = probability3;
    }

    public  Integer getProbability3(){
        return probability3;
    }
    public void setGoodsId4(Integer goodsId4){
        this.goodsId4 = goodsId4;
    }

    public  Integer getGoodsId4(){
        return goodsId4;
    }
    public void setGoodsNum4(Integer goodsNum4){
        this.goodsNum4 = goodsNum4;
    }

    public  Integer getGoodsNum4(){
        return goodsNum4;
    }
    public void setProbability4(Integer probability4){
        this.probability4 = probability4;
    }

    public  Integer getProbability4(){
        return probability4;
    }
    public void setGoodsId5(Integer goodsId5){
        this.goodsId5 = goodsId5;
    }

    public  Integer getGoodsId5(){
        return goodsId5;
    }
    public void setGoodsNum5(Integer goodsNum5){
        this.goodsNum5 = goodsNum5;
    }

    public  Integer getGoodsNum5(){
        return goodsNum5;
    }
    public void setProbability5(Integer probability5){
        this.probability5 = probability5;
    }

    public  Integer getProbability5(){
        return probability5;
    }
    public void setExtendMaps(String extendMaps){
        this.extendMaps = extendMaps;
    }

    public  String getExtendMaps(){
        return extendMaps;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public  String getDescription(){
        return description;
    }

	public Integer getDropBoxId() {
		return dropBoxId;
	}

	public void setDropBoxId(Integer dropBoxId) {
		this.dropBoxId = dropBoxId;
	}

	public TreeMap<Integer, DropItem> getDropItems() {
		return dropItems;
	}

	public void setDropItems(TreeMap<Integer, DropItem> dropItems) {
		this.dropItems = dropItems;
	}
	
	public List<DropItem> drop() {
		List<DropItem> dropItemList=new ArrayList<DropItem>();
		if(dropRateType==GameConsts.DropTypeFix){
			for(DropItem dropItem:dropItems.values()){
				if(ProbabilityUtil.defaultIsGenerate(dropItem.getProbability())){
					if(dropItem.getNum()>0){
						dropItemList.add(dropItem);
					}
					
				}
			}
		}else{
			if(dropItems.size()>0){
				int maxKey=dropItems.lastKey();
				if(ProbabilityUtil.defaultIsGenerate(maxKey)){
					int key=ProbabilityUtil.randomIntValue(1,maxKey);
					DropItem  goodsItem=dropItems.ceilingEntry(key).getValue();
					if(goodsItem.getNum()>0){
						dropItemList.add(goodsItem);
					}
					
				}
				
			}
		}
		return dropItemList;
	}
}