package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:24 
 */
@EntityMap(table = "t_s_weapon")
public class WeaponCfg extends  AbstractEntityBean<Integer,WeaponCfg>{
	
	/**主键ID*/
	@PropertyMap(column="WeaponId",primarykey=true)
	private  Integer weaponId;
	/**武器名称*/
	@PropertyMap(column="Name")
	private  String name;
	/**技能Id*/
	@PropertyMap(column="SkillId")
	private  Integer skillId;
	/**攻击转行率(万分比)*/
	@PropertyMap(column="AttackRate")
	private  Integer attackRate;
	/**暴击率(万分比)*/
	@PropertyMap(column="CritRate")
	private  Integer critRate;
	/**暴击伤害*/
	@PropertyMap(column="CritHurt")
	private  Integer critHurt;
	/**换弹夹时间*/
	@PropertyMap(column="ReloadTime")
	private  Integer reloadTime;
	/**弹夹容量*/
	@PropertyMap(column="ClipSize")
	private  Integer clipSize;
	/**破坏力*/
	@PropertyMap(column="BreachPower")
	private  Integer breachPower;
	
    
    public void setWeaponId(Integer weaponId){
        this.weaponId = weaponId;
    }

    public  Integer getWeaponId(){
        return weaponId;
    }
    public void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return name;
    }
    public void setSkillId(Integer skillId){
        this.skillId = skillId;
    }

    public  Integer getSkillId(){
        return skillId;
    }
    public void setAttackRate(Integer attackRate){
        this.attackRate = attackRate;
    }

    public  Integer getAttackRate(){
        return attackRate;
    }
    public void setCritRate(Integer critRate){
        this.critRate = critRate;
    }

    public  Integer getCritRate(){
        return critRate;
    }
    public void setCritHurt(Integer critHurt){
        this.critHurt = critHurt;
    }

    public  Integer getCritHurt(){
        return critHurt;
    }
    public void setReloadTime(Integer reloadTime){
        this.reloadTime = reloadTime;
    }

    public  Integer getReloadTime(){
        return reloadTime;
    }
    public void setClipSize(Integer clipSize){
        this.clipSize = clipSize;
    }

    public  Integer getClipSize(){
        return clipSize;
    }
    public void setBreachPower(Integer breachPower){
        this.breachPower = breachPower;
    }

    public  Integer getBreachPower(){
        return breachPower;
    }
}