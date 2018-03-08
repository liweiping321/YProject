package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-04-25 03:05:37 
 */
@EntityMap(table = "t_s_name")
public class NameCfg extends  AbstractEntityBean<Integer,NameCfg>{
	
	/**主键*/
	@PropertyMap(column="Id",primarykey=true)
	private  Integer id;
	/**姓或名*/
	@PropertyMap(column="Value")
	private  String value;
	/**1为姓，2为男性名字，3位女性名字*/
	@PropertyMap(column="Type")
	private  Integer type;
	
    
    public void setId(Integer id){
        this.id = id;
    }

    public  Integer getId(){
        return id;
    }
    public void setValue(String value){
        this.value = value;
    }

    public  String getValue(){
        return value;
    }
    public void setType(Integer type){
        this.type = type;
    }

    public  Integer getType(){
        return type;
    }
}