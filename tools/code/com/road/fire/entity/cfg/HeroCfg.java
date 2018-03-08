package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
@EntityMap(table = "t_s_hero")
public class HeroCfg extends  AbstractEntityBean<Integer,HeroCfg>{
	
	/**主角模板id*/
	@PropertyMap(column="HeroId",primarykey=true)
	private  Integer heroId;
	/**名称*/
	@PropertyMap(column="Name")
	private  String name;
	/**类型（1近战2远程）*/
	@PropertyMap(column="Type")
	private  Integer type;
	/**对应职业(1机枪手，1.弓箭手，3弹幕手)*/
	@PropertyMap(column="Career")
	private  Integer career;
	/**初始等级*/
	@PropertyMap(column="Level")
	private  Integer level;
	/**伤害半径*/
	@PropertyMap(column="DamageRadius")
	private  Integer damageRadius;
	/**默认武器*/
	@PropertyMap(column="WeaponId")
	private  String weaponId;
	/**模板ID*/
	@PropertyMap(column="TemplateId")
	private  Integer templateId;
	
    
    public void setHeroId(Integer heroId){
        this.heroId = heroId;
    }

    public  Integer getHeroId(){
        return heroId;
    }
    public void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return name;
    }
    public void setType(Integer type){
        this.type = type;
    }

    public  Integer getType(){
        return type;
    }
    public void setCareer(Integer career){
        this.career = career;
    }

    public  Integer getCareer(){
        return career;
    }
    public void setLevel(Integer level){
        this.level = level;
    }

    public  Integer getLevel(){
        return level;
    }
    public void setDamageRadius(Integer damageRadius){
        this.damageRadius = damageRadius;
    }

    public  Integer getDamageRadius(){
        return damageRadius;
    }
    public void setWeaponId(String weaponId){
        this.weaponId = weaponId;
    }

    public  String getWeaponId(){
        return weaponId;
    }
    public void setTemplateId(Integer templateId){
        this.templateId = templateId;
    }

    public  Integer getTemplateId(){
        return templateId;
    }
}