package com.road.fire.entity.cfg;
 
import java.util.List;

import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-06-01 03:07:46 
 */
@EntityMap(table = "t_s_skill")
public class SkillCfg extends  AbstractEntityBean<Integer,SkillCfg>{
	
	/**技能ID(位数分别表示：首位技能所属生物类型{1英雄}，2~4位英雄编号，5位所属英雄技能类型编号，6位增序)*/
	@PropertyMap(column="SkillId",primarykey=true)
	private  Integer skillId;
	/**技能名称*/
	@PropertyMap(column="SkillName")
	private  String skillName;
	/**描述*/
	@PropertyMap(column="Description")
	private  String description;
	/**buff列表"|"分割*/
	@PropertyMap(column="Buffs",splitRex="|")
	private  List<Integer> buffs;
	/**图标*/
	@PropertyMap(column="Icon")
	private  String icon;
	/**技能操作类型(0:普通按钮1:按钮+摇杆2:普通按钮+弹起释放)*/
	@PropertyMap(column="SkillOptType")
	private  Integer skillOptType;
	/**技能冷却时间*/
	@PropertyMap(column="CoolTime")
	private  Integer coolTime;
	/**技能公共冷却时间(所有技能不可以点击)*/
	@PropertyMap(column="GroupCoolTime")
	private  Integer groupCoolTime;
	/**技能吟唱时间(可以打断)*/
	@PropertyMap(column="ReadTime")
	private  Integer readTime;
	/**技能僵直时间(可以打断)*/
	@PropertyMap(column="RigidTime")
	private  Integer rigidTime;
	/**施展时间（动作帧数）*/
	@PropertyMap(column="SpreadTime")
	private  Integer spreadTime;
	/**技能持续时间*/
	@PropertyMap(column="DurationTime")
	private  Integer durationTime;
	/**技能类型(1机关枪,2手雷)*/
	@PropertyMap(column="Type")
	private  Integer type;
	/**伤害类型（1:物理伤害，2:治疗，3复活，4只对英雄伤害,5延迟计算伤害）*/
	@PropertyMap(column="HurtType")
	private  Integer hurtType;
	/**伤害值*/
	@PropertyMap(column="HurtValue")
	private  Integer hurtValue;
	/**伤害值（改百分比）*/
	@PropertyMap(column="HurtRatioValue")
	private  Integer hurtRatioValue;
	/**破坏力(转换率万分比)*/
	@PropertyMap(column="BreachPowerRatio")
	private  Integer breachPowerRatio;
	/**伤害范围类型（0范围攻击，1圆形范围手雷，2矩形镭射炮，3扇形,4双摇杆）*/
	@PropertyMap(column="HurtRangeType")
	private  Integer hurtRangeType;
	/**辅助锁定角度*/
	@PropertyMap(column="LockDegree")
	private  Integer lockDegree;
	/**伤害角度*/
	@PropertyMap(column="HurtRangeDegree")
	private  Integer hurtRangeDegree;
	/**伤害类型参数(例如摇杆操作显示扇形夹角|躲避技能使用的时间总长)*/
	@PropertyMap(column="HurtRangeTypeParams")
	private  Integer hurtRangeTypeParams;
	/**攻击距离（米*1000）*/
	@PropertyMap(column="AttackDistance")
	private  Integer attackDistance;
	/**子弹飞行距离(攻击距离=飞行距离+子弹出生点偏移值)*/
	@PropertyMap(column="FlyDistance")
	private  Integer flyDistance;
	/**圆的伤害半径（米*1000）*/
	@PropertyMap(column="DamageRadius")
	private  Integer damageRadius;
	/**矩形伤害范围x|z|w|h（米*1000）*/
	@PropertyMap(column="HurtRange")
	private  String hurtRange;
	/**目标类型(0.自己,1.友方,2.敌方,3.所有)*/
	@PropertyMap(column="TargetType")
	private  Integer targetType;
	/**目标个数*/
	@PropertyMap(column="TargetMax")
	private  Integer targetMax;
	/**击退值*/
	@PropertyMap(column="BeatBack")
	private  Integer beatBack;
	/**是否启用辅助锁定*/
	@PropertyMap(column="AssistLock")
	private  Boolean assistLock;
	/**召唤模板ID*/
	@PropertyMap(column="CallTemplateId")
	private  String callTemplateId;
	/**召唤生物移动速度*/
	@PropertyMap(column="CallLivingSpeed")
	private  Integer callLivingSpeed;
	/**施展动作index*/
	@PropertyMap(column="SpreadAction")
	private  String spreadAction;
	/**是否震屏(1是0否)*/
	@PropertyMap(column="ShakeScreen")
	private  Boolean shakeScreen;
	/**震屏数据(x方向震动次数|x方向振幅|y方向震动次数|y方向振幅)*/
	@PropertyMap(column="ShakeData")
	private  String shakeData;
	/**技能光效类型(1射击类型2抛射类型)*/
	@PropertyMap(column="EffectType")
	private  Integer effectType;
	/**吟唱时光效*/
	@PropertyMap(column="EffectsRead")
	private  String effectsRead;
	/**技能开始效果*/
	@PropertyMap(column="EffectsBegin")
	private  String effectsBegin;
	/**技能施展效果(近战延时释放)*/
	@PropertyMap(column="EffectsRelease")
	private  String effectsRelease;
	/**技能击中光效（最后一位-1表示加血和扣血播放不同的特效）*/
	@PropertyMap(column="EffectsHit")
	private  String effectsHit;
	/**技能光效扩展(手持手雷/镭射炮枪口跟随光效)*/
	@PropertyMap(column="EffectsExt")
	private  String effectsExt;
	/**吟唱音效*/
	@PropertyMap(column="ReadMusic")
	private  String readMusic;
	/**施展音效*/
	@PropertyMap(column="Music")
	private  String music;
	/**是否显示攻击范围*/
	@PropertyMap(column="ShowAttackRange")
	private  Integer showAttackRange;
	/**单发子弹数*/
	@PropertyMap(column="BulletCount")
	private  Integer bulletCount;
	/**单发子弹之间间隔（单位毫秒）*/
	@PropertyMap(column="DelayInterval")
	private  Double delayInterval;
	
    
    public void setSkillId(Integer skillId){
        this.skillId = skillId;
    }

    public  Integer getSkillId(){
        return skillId;
    }
    public void setSkillName(String skillName){
        this.skillName = skillName;
    }

    public  String getSkillName(){
        return skillName;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public  String getDescription(){
        return description;
    }
    
    public List<Integer> getBuffs() {
		return buffs;
	}

	public void setBuffs(List<Integer> buffs) {
		this.buffs = buffs;
	}

	public void setIcon(String icon){
        this.icon = icon;
    }

    public  String getIcon(){
        return icon;
    }
    public void setSkillOptType(Integer skillOptType){
        this.skillOptType = skillOptType;
    }

    public  Integer getSkillOptType(){
        return skillOptType;
    }
    public void setCoolTime(Integer coolTime){
        this.coolTime = coolTime;
    }

    public  Integer getCoolTime(){
        return coolTime;
    }
    public void setGroupCoolTime(Integer groupCoolTime){
        this.groupCoolTime = groupCoolTime;
    }

    public  Integer getGroupCoolTime(){
        return groupCoolTime;
    }
    public void setReadTime(Integer readTime){
        this.readTime = readTime;
    }

    public  Integer getReadTime(){
        return readTime;
    }
    public void setRigidTime(Integer rigidTime){
        this.rigidTime = rigidTime;
    }

    public  Integer getRigidTime(){
        return rigidTime;
    }
    public void setSpreadTime(Integer spreadTime){
        this.spreadTime = spreadTime;
    }

    public  Integer getSpreadTime(){
        return spreadTime;
    }
    public void setDurationTime(Integer durationTime){
        this.durationTime = durationTime;
    }

    public  Integer getDurationTime(){
        return durationTime;
    }
    public void setType(Integer type){
        this.type = type;
    }

    public  Integer getType(){
        return type;
    }
    public void setHurtType(Integer hurtType){
        this.hurtType = hurtType;
    }

    public  Integer getHurtType(){
        return hurtType;
    }
    public void setHurtValue(Integer hurtValue){
        this.hurtValue = hurtValue;
    }

    public  Integer getHurtValue(){
        return hurtValue;
    }
    public void setHurtRatioValue(Integer hurtRatioValue){
        this.hurtRatioValue = hurtRatioValue;
    }

    public  Integer getHurtRatioValue(){
        return hurtRatioValue;
    }
    public void setBreachPowerRatio(Integer breachPowerRatio){
        this.breachPowerRatio = breachPowerRatio;
    }

    public  Integer getBreachPowerRatio(){
        return breachPowerRatio;
    }
    public void setHurtRangeType(Integer hurtRangeType){
        this.hurtRangeType = hurtRangeType;
    }

    public  Integer getHurtRangeType(){
        return hurtRangeType;
    }
    public void setLockDegree(Integer lockDegree){
        this.lockDegree = lockDegree;
    }

    public  Integer getLockDegree(){
        return lockDegree;
    }
    public void setHurtRangeDegree(Integer hurtRangeDegree){
        this.hurtRangeDegree = hurtRangeDegree;
    }

    public  Integer getHurtRangeDegree(){
        return hurtRangeDegree;
    }
    public void setHurtRangeTypeParams(Integer hurtRangeTypeParams){
        this.hurtRangeTypeParams = hurtRangeTypeParams;
    }

    public  Integer getHurtRangeTypeParams(){
        return hurtRangeTypeParams;
    }
    public void setAttackDistance(Integer attackDistance){
        this.attackDistance = attackDistance;
    }

    public  Integer getAttackDistance(){
        return attackDistance;
    }
    public void setFlyDistance(Integer flyDistance){
        this.flyDistance = flyDistance;
    }

    public  Integer getFlyDistance(){
        return flyDistance;
    }
    public void setDamageRadius(Integer damageRadius){
        this.damageRadius = damageRadius;
    }

    public  Integer getDamageRadius(){
        return damageRadius;
    }
    public void setHurtRange(String hurtRange){
        this.hurtRange = hurtRange;
    }

    public  String getHurtRange(){
        return hurtRange;
    }
    public void setTargetType(Integer targetType){
        this.targetType = targetType;
    }

    public  Integer getTargetType(){
        return targetType;
    }
    public void setTargetMax(Integer targetMax){
        this.targetMax = targetMax;
    }

    public  Integer getTargetMax(){
        return targetMax;
    }
    public void setBeatBack(Integer beatBack){
        this.beatBack = beatBack;
    }

    public  Integer getBeatBack(){
        return beatBack;
    }
    public void setAssistLock(Boolean assistLock){
        this.assistLock = assistLock;
    }

    public  Boolean getAssistLock(){
        return assistLock;
    }
    public void setCallTemplateId(String callTemplateId){
        this.callTemplateId = callTemplateId;
    }

    public  String getCallTemplateId(){
        return callTemplateId;
    }
    public void setCallLivingSpeed(Integer callLivingSpeed){
        this.callLivingSpeed = callLivingSpeed;
    }

    public  Integer getCallLivingSpeed(){
        return callLivingSpeed;
    }
    public void setSpreadAction(String spreadAction){
        this.spreadAction = spreadAction;
    }

    public  String getSpreadAction(){
        return spreadAction;
    }
    public void setShakeScreen(Boolean shakeScreen){
        this.shakeScreen = shakeScreen;
    }

    public  Boolean getShakeScreen(){
        return shakeScreen;
    }
    public void setShakeData(String shakeData){
        this.shakeData = shakeData;
    }

    public  String getShakeData(){
        return shakeData;
    }
    public void setEffectType(Integer effectType){
        this.effectType = effectType;
    }

    public  Integer getEffectType(){
        return effectType;
    }
    public void setEffectsRead(String effectsRead){
        this.effectsRead = effectsRead;
    }

    public  String getEffectsRead(){
        return effectsRead;
    }
    public void setEffectsBegin(String effectsBegin){
        this.effectsBegin = effectsBegin;
    }

    public  String getEffectsBegin(){
        return effectsBegin;
    }
    public void setEffectsRelease(String effectsRelease){
        this.effectsRelease = effectsRelease;
    }

    public  String getEffectsRelease(){
        return effectsRelease;
    }
    public void setEffectsHit(String effectsHit){
        this.effectsHit = effectsHit;
    }

    public  String getEffectsHit(){
        return effectsHit;
    }
    public void setEffectsExt(String effectsExt){
        this.effectsExt = effectsExt;
    }

    public  String getEffectsExt(){
        return effectsExt;
    }
    public void setReadMusic(String readMusic){
        this.readMusic = readMusic;
    }

    public  String getReadMusic(){
        return readMusic;
    }
    public void setMusic(String music){
        this.music = music;
    }

    public  String getMusic(){
        return music;
    }
    public void setShowAttackRange(Integer showAttackRange){
        this.showAttackRange = showAttackRange;
    }

    public  Integer getShowAttackRange(){
        return showAttackRange;
    }
    public void setBulletCount(Integer bulletCount){
        this.bulletCount = bulletCount;
    }

    public  Integer getBulletCount(){
        return bulletCount;
    }
    public void setDelayInterval(Double delayInterval){
        this.delayInterval = delayInterval;
    }

    public  Double getDelayInterval(){
        return delayInterval;
    }
}