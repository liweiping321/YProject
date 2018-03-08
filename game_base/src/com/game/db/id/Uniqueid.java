package com.game.db.id;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-03-13 11:30:18 
 */
@EntityMap(table = "t_u_uniqueid")
public class Uniqueid extends  AbstractEntityBean<String,Uniqueid>{
	
	/**表名*/
	@PropertyMap(column="TableName",primarykey=true)
	private  String tableName;
	/**当前最大Id*/
	@PropertyMap(column="CurrentId")
	private  Long currentId;
	
    
    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public  String getTableName(){
        return tableName;
    }
    public void setCurrentId(Long currentId){
        this.currentId = currentId;
    }

    public  Long getCurrentId(){
        return currentId;
    }
}