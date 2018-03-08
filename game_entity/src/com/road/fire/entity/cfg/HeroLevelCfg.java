package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-05-04 10:03:58 
 */
@EntityMap(table = "t_s_hero_level")
public class HeroLevelCfg extends  AbstractEntityBean<Integer,HeroLevelCfg>{
	
	/**唯一主键*/
	@PropertyMap(column="Id",primarykey=true)
	private  Integer id;
	/**角色Id*/
	@PropertyMap(column="HeroId")
	private  Integer heroId;
	/**等级*/
	@PropertyMap(column="Level")
	private  Integer level;
	/**生命上限*/
	@PropertyMap(column="MaxHp")
	private  Integer maxHp;
	/**攻击*/
	@PropertyMap(column="Attack")
	private  Integer attack;
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
	/**移动衰减值(万分比)*/
	@PropertyMap(column="MoveReduce")
	private  Integer moveReduce;
	
    
    public void setId(Integer id){
        this.id = id;
    }

    public  Integer getId(){
        return id;
    }
    public void setHeroId(Integer heroId){
        this.heroId = heroId;
    }

    public  Integer getHeroId(){
        return heroId;
    }
    public void setLevel(Integer level){
        this.level = level;
    }

    public  Integer getLevel(){
        return level;
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
    public void setMoveReduce(Integer moveReduce){
        this.moveReduce = moveReduce;
    }

    public  Integer getMoveReduce(){
        return moveReduce;
    }
}