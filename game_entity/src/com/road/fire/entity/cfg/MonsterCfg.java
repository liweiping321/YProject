package com.road.fire.entity.cfg;
 
import java.util.Set;

import com.game.consts.GameConsts;
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-05-12 02:50:48 
 */
@EntityMap(table = "t_s_monster")
public class MonsterCfg extends  AbstractEntityBean<Integer,MonsterCfg>{
	
	/**怪物模板id*/
	@PropertyMap(column="MonsterId",primarykey=true)
	private  Integer monsterId;
	/**实体类型(1.箱子,2普通怪物)*/
	@PropertyMap(column="Type")
	private  Integer type;
	/**名字*/
	@PropertyMap(column="Name")
	private  String name;
	/**基础攻击技能*/
	@PropertyMap(column="CommonSkillId")
	private  Integer commonSkillId;
	/**技能释放顺序(用|分隔)*/
	@PropertyMap(column="SkillIds",splitRex="|")
	private  Set<Integer> skillIds;
	/**攻击间隔*/
	@PropertyMap(column="AttackCd")
	private  Integer attackCd;
	/**掉落列表(用|隔开)*/
	
	@PropertyMap(column="DropIds",splitRex="|")
	private  Set<Integer> dropIds;
	/**禁用效果列表(用|隔开)*/
	@PropertyMap(column="BanEffects")
	private  String banEffects;
	/**伤害公式计算类型(0.掉血，1.破坏力)*/
	@PropertyMap(column="HurtFormulaType")
	private  Integer hurtFormulaType;
	/**生命上限*/
	@PropertyMap(column="MaxHp")
	private  Integer maxHp;
	/**攻击*/
	@PropertyMap(column="Attack")
	private  Integer attack;
	/**破坏力*/
	@PropertyMap(column="BreachPower")
	private  Integer breachPower;
	/**防御*/
	@PropertyMap(column="Defense")
	private  Integer defense;
	/**抗爆值*/
	@PropertyMap(column="OpposeCrit")
	private  Integer opposeCrit;
	/**视野*/
	@PropertyMap(column="EyeShot")
	private  Integer eyeShot;
	/**暴击率*/
	@PropertyMap(column="CritRate")
	private  Integer critRate;
	/**移动速度*/
	@PropertyMap(column="MoveSpeed")
	private  Integer moveSpeed;
	/**AI类型(0.待机,1巡逻)*/
	@PropertyMap(column="AiType")
	private  Integer aiType;
	/**是否主动攻击*/
	@PropertyMap(column="Active")
	private  Boolean active;
	/**警戒范围(以怪物 当前点 为中心点) 主动攻击怪和角色间距离进入警戒范围将引发怪物的主动攻击*/
	@PropertyMap(column="Alert")
	private  Integer alert;
	/** 巡逻范围(以怪物 出生点 为中心点) ,怪物初始化时在这个范围内移动*/
	@PropertyMap(column="Patrol")
	private  Integer patrol;
	/***/
	@PropertyMap(column="Pursuit")
	private  Integer pursuit;
	/**伤害半径*/
	@PropertyMap(column="DamageRadius")
	private  Integer damageRadius;
	/**死亡持续时间(毫秒)*/
	@PropertyMap(column="DieTime")
	private  Integer dieTime;
	/**死亡触发技能*/
	@PropertyMap(column="DieTriggerSkill")
	private  Integer dieTriggerSkill;
	/**怪物模型ID*/
	@PropertyMap(column="TemplateId")
	private  Integer templateId;
	
    
    public void setMonsterId(Integer monsterId){
        this.monsterId = monsterId;
    }

    public  Integer getMonsterId(){
        return monsterId;
    }
    public void setType(Integer type){
        this.type = type;
    }

    public  Integer getType(){
        return type;
    }
    public void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return name;
    }
    public void setCommonSkillId(Integer commonSkillId){
        this.commonSkillId = commonSkillId;
    }

    public  Integer getCommonSkillId(){
        return commonSkillId;
    }
    public void setSkillIds(Set<Integer> skillIds){
        this.skillIds = skillIds;
    }

    public  Set<Integer> getSkillIds(){
        return skillIds;
    }
    public void setAttackCd(Integer attackCd){
        this.attackCd = attackCd;
    }

    public  Integer getAttackCd(){
        return attackCd;
    }
    public void setDropIds(Set<Integer> dropIds){
        this.dropIds = dropIds;
    }

    public  Set<Integer> getDropIds(){
        return dropIds;
    }
    public void setBanEffects(String banEffects){
        this.banEffects = banEffects;
    }

    public  String getBanEffects(){
        return banEffects;
    }
    public void setHurtFormulaType(Integer hurtFormulaType){
        this.hurtFormulaType = hurtFormulaType;
    }

    public  Integer getHurtFormulaType(){
        return hurtFormulaType;
    }
    public void setMaxHp(Integer maxHp){
        this.maxHp = maxHp;
    }

    public  Integer getMaxHp(){
        return maxHp;
    }
    public void setAttack(Integer attack){
        this.attack = attack;
    }

    public  Integer getAttack(){
        return attack;
    }
    public void setDefense(Integer defense){
        this.defense = defense;
    }

    public  Integer getDefense(){
        return defense;
    }
    public void setOpposeCrit(Integer opposeCrit){
        this.opposeCrit = opposeCrit;
    }

    public  Integer getOpposeCrit(){
        return opposeCrit;
    }
    public void setEyeShot(Integer eyeShot){
        this.eyeShot = eyeShot;
    }

    public  Integer getEyeShot(){
        return eyeShot;
    }
    public void setCritRate(Integer critRate){
        this.critRate = critRate;
    }

    public  Integer getCritRate(){
        return critRate;
    }
    public void setMoveSpeed(Integer moveSpeed){
        this.moveSpeed = moveSpeed;
    }

    public  Integer getMoveSpeed(){
        return moveSpeed;
    }
    public void setAiType(Integer aiType){
        this.aiType = aiType;
    }

    public  Integer getAiType(){
        return aiType;
    }
    public void setActive(Boolean active){
        this.active = active;
    }

    public  Boolean getActive(){
        return active;
    }
    public void setAlert(Integer alert){
        this.alert = alert;
    }

    public  Integer getAlert(){
        return alert;
    }
    public void setPatrol(Integer patrol){
        this.patrol = patrol;
    }

    public  Integer getPatrol(){
        return patrol;
    }
    public void setPursuit(Integer pursuit){
        this.pursuit = pursuit;
    }

    public  Integer getPursuit(){
        return pursuit;
    }
    public void setDamageRadius(Integer damageRadius){
        this.damageRadius = damageRadius;
    }

    public  Integer getDamageRadius(){
        return damageRadius;
    }
    public void setTemplateId(Integer templateId){
        this.templateId = templateId;
    }

    public  Integer getTemplateId(){
        return templateId;
    }

	public Integer getBreachPower() {
		return breachPower;
	}

	public void setBreachPower(Integer breachPower) {
		this.breachPower = breachPower;
	}

	public Integer getDieTime() {
		return dieTime;
	}

	public void setDieTime(Integer dieTime) {
		this.dieTime = dieTime;
	}

	public Integer getDieTriggerSkill() {
		return dieTriggerSkill;
	}

	public void setDieTriggerSkill(Integer dieTriggerSkill) {
		this.dieTriggerSkill = dieTriggerSkill;
	}
    
    
 
}