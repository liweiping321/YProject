package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:22 
 */
@EntityMap(table = "t_s_level_exp")
public class LevelExpCfg extends  AbstractEntityBean<Integer,LevelExpCfg>{
	
	/**唯一ID(1-10000 英雄)*/
	@PropertyMap(column="Id",primarykey=true)
	private  Integer id;
	/**类型(0.英雄，1.怪物)*/
	@PropertyMap(column="Type")
	private  Integer type;
	/**等级*/
	@PropertyMap(column="Level")
	private  Integer level;
	/**下一等级经验*/
	@PropertyMap(column="NextLeveExp")
	private  Integer nextLeveExp;
	/**天赋点*/
	@PropertyMap(column="Talent")
	private  Integer talent;
	
    
    public void setId(Integer id){
        this.id = id;
    }

    public  Integer getId(){
        return id;
    }
    public void setType(Integer type){
        this.type = type;
    }

    public  Integer getType(){
        return type;
    }
    public void setLevel(Integer level){
        this.level = level;
    }

    public  Integer getLevel(){
        return level;
    }
    public void setNextLeveExp(Integer nextLeveExp){
        this.nextLeveExp = nextLeveExp;
    }

    public  Integer getNextLeveExp(){
        return nextLeveExp;
    }
    public void setTalent(Integer talent){
        this.talent = talent;
    }

    public  Integer getTalent(){
        return talent;
    }
}