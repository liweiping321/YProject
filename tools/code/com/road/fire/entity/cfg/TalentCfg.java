package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:22 
 */
@EntityMap(table = "t_s_talent")
public class TalentCfg extends  AbstractEntityBean<Integer,TalentCfg>{
	
	/**主键ID*/
	@PropertyMap(column="TalentId",primarykey=true)
	private  Integer talentId;
	/**名称*/
	@PropertyMap(column="Name")
	private  String name;
	/**等级*/
	@PropertyMap(column="Level")
	private  Integer level;
	/**对应职业(0通用 ，其它英雄ID)*/
	@PropertyMap(column="HeroId")
	private  Integer heroId;
	/**下级天赋ID(<=0表示已经是最高等级)*/
	@PropertyMap(column="NextTalentId")
	private  Integer nextTalentId;
	/**天赋类型(0.属性加成,1.BUFF,2.技能)*/
	@PropertyMap(column="Type")
	private  Integer type;
	/**天赋明细类型*/
	@PropertyMap(column="DetailType")
	private  Integer detailType;
	/**属性值*/
	@PropertyMap(column="PropValue")
	private  Integer propValue;
	/**升级权重*/
	@PropertyMap(column="Weight")
	private  Integer weight;
	/**间隔执行时间*/
	@PropertyMap(column="IntervalTime")
	private  Integer intervalTime;
	/**天赋升级消耗类型*/
	@PropertyMap(column="CostType")
	private  Integer costType;
	/**天赋升级消耗数值*/
	@PropertyMap(column="CostValue")
	private  Integer costValue;
	/**effect参数1*/
	@PropertyMap(column="Param1")
	private  Integer param1;
	/**effect参数2*/
	@PropertyMap(column="Param2")
	private  Integer param2;
	/**effect参数3*/
	@PropertyMap(column="Param3")
	private  Integer param3;
	/**effect参数4*/
	@PropertyMap(column="Param4")
	private  String param4;
	/**天赋图标*/
	@PropertyMap(column="Icon")
	private  String icon;
	
    
    public void setTalentId(Integer talentId){
        this.talentId = talentId;
    }

    public  Integer getTalentId(){
        return talentId;
    }
    public void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return name;
    }
    public void setLevel(Integer level){
        this.level = level;
    }

    public  Integer getLevel(){
        return level;
    }
    public void setHeroId(Integer heroId){
        this.heroId = heroId;
    }

    public  Integer getHeroId(){
        return heroId;
    }
    public void setNextTalentId(Integer nextTalentId){
        this.nextTalentId = nextTalentId;
    }

    public  Integer getNextTalentId(){
        return nextTalentId;
    }
    public void setType(Integer type){
        this.type = type;
    }

    public  Integer getType(){
        return type;
    }
    public void setDetailType(Integer detailType){
        this.detailType = detailType;
    }

    public  Integer getDetailType(){
        return detailType;
    }
    public void setPropValue(Integer propValue){
        this.propValue = propValue;
    }

    public  Integer getPropValue(){
        return propValue;
    }
    public void setWeight(Integer weight){
        this.weight = weight;
    }

    public  Integer getWeight(){
        return weight;
    }
    public void setIntervalTime(Integer intervalTime){
        this.intervalTime = intervalTime;
    }

    public  Integer getIntervalTime(){
        return intervalTime;
    }
    public void setCostType(Integer costType){
        this.costType = costType;
    }

    public  Integer getCostType(){
        return costType;
    }
    public void setCostValue(Integer costValue){
        this.costValue = costValue;
    }

    public  Integer getCostValue(){
        return costValue;
    }
    public void setParam1(Integer param1){
        this.param1 = param1;
    }

    public  Integer getParam1(){
        return param1;
    }
    public void setParam2(Integer param2){
        this.param2 = param2;
    }

    public  Integer getParam2(){
        return param2;
    }
    public void setParam3(Integer param3){
        this.param3 = param3;
    }

    public  Integer getParam3(){
        return param3;
    }
    public void setParam4(String param4){
        this.param4 = param4;
    }

    public  String getParam4(){
        return param4;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }

    public  String getIcon(){
        return icon;
    }
}