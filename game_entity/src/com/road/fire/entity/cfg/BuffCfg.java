package com.road.fire.entity.cfg;
 
import java.util.ArrayList;
import java.util.List;

import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-04-25 02:37:37 
 */
@EntityMap(table = "t_s_buff")
public class BuffCfg extends  AbstractEntityBean<Integer,BuffCfg>{
	
	/**主键ID*/
	@PropertyMap(column="BuffId",primarykey=true)
	private  Integer buffId;
	/**buff类型*/
	@PropertyMap(column="BuffType")
	private  Integer buffType;
	/**BUFF名字*/
	@PropertyMap(column="Name")
	private  String name;
	/**描述*/
	@PropertyMap(column="Description")
	private  String description;
	/**作用对象(1目标，2释放者)*/
	@PropertyMap(column="ObjType")
	private  Integer objType;
	/**参考作用效果表*/
	@PropertyMap(column="Effects",splitRex="|")
	private  List<Integer> effects;
	/**持续时间*/
	@PropertyMap(column="LastTime")
	private  Integer lastTime;
	/**间隔时间(毫秒)*/
	@PropertyMap(column="IntervalTime")
	private  Integer intervalTime;
	/**是否显示(1是0否)*/
	@PropertyMap(column="Show")
	private  Boolean show;
	/**buff图标*/
	@PropertyMap(column="BuffIcon")
	private  Integer buffIcon;
	/**叠加次数*/
	@PropertyMap(column="LapCount")
	private  Integer lapCount;
	/**0增益buff，1debuff*/
	@PropertyMap(column="Debuff")
	private  Boolean debuff;
	/**buff偏移坐标X*/
	@PropertyMap(column="BuffX")
	private  Integer buffX;
	/**buff偏移坐标Y*/
	@PropertyMap(column="BuffY")
	private  Integer buffY;
	/**buff光效*/
	@PropertyMap(column="BuffEffectsBegin")
	private  String buffEffectsBegin;
	/**buff结束光效*/
	@PropertyMap(column="BuffEffectsEnd")
	private  String buffEffectsEnd;
	/**buff持续光效*/
	@PropertyMap(column="BuffAbidance")
	private  String buffAbidance;
	/**buff持续音效*/
	@PropertyMap(column="BuffMusic")
	private  String buffMusic;
	/**出生音效*/
	@PropertyMap(column="BirthMusic")
	private  String birthMusic;
	/**死亡音效*/
	@PropertyMap(column="DieMusic")
	private  String dieMusic;
	
	private  List<EffectCfg>  effectCfgs=new ArrayList<>();
	
    
    
    public Integer getBuffId() {
		return buffId;
	}

	public void setBuffId(Integer buffId) {
		this.buffId = buffId;
	}

	public void setBuffType(Integer buffType){
        this.buffType = buffType;
    }

    public  Integer getBuffType(){
        return buffType;
    }
    public void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return name;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public  String getDescription(){
        return description;
    }
    public void setObjType(Integer objType){
        this.objType = objType;
    }

    public  Integer getObjType(){
        return objType;
    }
   
    public List<Integer> getEffects() {
		return effects;
	}

	public void setEffects(List<Integer> effects) {
		this.effects = effects;
	}

	public List<EffectCfg> getEffectCfgs() {
		return effectCfgs;
	}

	public void setEffectCfgs(List<EffectCfg> effectCfgs) {
		this.effectCfgs = effectCfgs;
	}

	public void setLastTime(Integer lastTime){
        this.lastTime = lastTime;
    }

    public  Integer getLastTime(){
        return lastTime;
    }
    public void setIntervalTime(Integer intervalTime){
        this.intervalTime = intervalTime;
    }

    public  Integer getIntervalTime(){
        return intervalTime;
    }
    public void setShow(Boolean show){
        this.show = show;
    }

    public  Boolean getShow(){
        return show;
    }
    public void setBuffIcon(Integer buffIcon){
        this.buffIcon = buffIcon;
    }

    public  Integer getBuffIcon(){
        return buffIcon;
    }
    public void setLapCount(Integer lapCount){
        this.lapCount = lapCount;
    }

    public  Integer getLapCount(){
        return lapCount;
    }
    public void setDebuff(Boolean debuff){
        this.debuff = debuff;
    }

    public  Boolean getDebuff(){
        return debuff;
    }
    public void setBuffX(Integer buffX){
        this.buffX = buffX;
    }

    public  Integer getBuffX(){
        return buffX;
    }
    public void setBuffY(Integer buffY){
        this.buffY = buffY;
    }

    public  Integer getBuffY(){
        return buffY;
    }
    public void setBuffEffectsBegin(String buffEffectsBegin){
        this.buffEffectsBegin = buffEffectsBegin;
    }

    public  String getBuffEffectsBegin(){
        return buffEffectsBegin;
    }
    public void setBuffEffectsEnd(String buffEffectsEnd){
        this.buffEffectsEnd = buffEffectsEnd;
    }

    public  String getBuffEffectsEnd(){
        return buffEffectsEnd;
    }
    public void setBuffAbidance(String buffAbidance){
        this.buffAbidance = buffAbidance;
    }

    public  String getBuffAbidance(){
        return buffAbidance;
    }
    public void setBuffMusic(String buffMusic){
        this.buffMusic = buffMusic;
    }

    public  String getBuffMusic(){
        return buffMusic;
    }
    public void setBirthMusic(String birthMusic){
        this.birthMusic = birthMusic;
    }

    public  String getBirthMusic(){
        return birthMusic;
    }
    public void setDieMusic(String dieMusic){
        this.dieMusic = dieMusic;
    }

    public  String getDieMusic(){
        return dieMusic;
    }
    
}