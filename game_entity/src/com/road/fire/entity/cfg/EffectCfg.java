package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-05-04 10:03:58 
 */
@EntityMap(table = "t_s_effect")
public class EffectCfg extends  AbstractEntityBean<Integer,EffectCfg>{
	
	/**主键ID*/
	@PropertyMap(column="EffectId",primarykey=true)
	private  Integer effectId;
	/**effect类型*/
	@PropertyMap(column="EffectType")
	private  Integer effectType;
	/**标题*/
	@PropertyMap(column="Title")
	private  String title;
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
	
    
    public void setEffectId(Integer effectId){
        this.effectId = effectId;
    }

    public  Integer getEffectId(){
        return effectId;
    }
    public void setEffectType(Integer effectType){
        this.effectType = effectType;
    }

    public  Integer getEffectType(){
        return effectType;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public  String getTitle(){
        return title;
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
}