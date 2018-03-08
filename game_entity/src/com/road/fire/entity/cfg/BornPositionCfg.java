package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-05-04 10:03:57 
 */
@EntityMap(table = "t_s_born_position")
public class BornPositionCfg extends  AbstractEntityBean<Integer,BornPositionCfg>{
	
	/**主键ID*/
	@PropertyMap(column="Id",primarykey=true)
	private  Integer id;
	/**场景ID*/
	@PropertyMap(column="SceneId")
	private  Integer sceneId;
	/**阵营类型(0通用，1红队，2蓝队)*/
	@PropertyMap(column="CampType")
	private  Integer campType;
	/**出生坐标x轴*/
	@PropertyMap(column="X")
	private  Integer x;
	/**出生点y轴*/
	@PropertyMap(column="Y")
	private  Integer y;
	
    
    public void setId(Integer id){
        this.id = id;
    }

    public  Integer getId(){
        return id;
    }
    public void setSceneId(Integer sceneId){
        this.sceneId = sceneId;
    }

    public  Integer getSceneId(){
        return sceneId;
    }
    public void setCampType(Integer campType){
        this.campType = campType;
    }

    public  Integer getCampType(){
        return campType;
    }
    public void setX(Integer x){
        this.x = x;
    }

    public  Integer getX(){
        return x;
    }
    public void setY(Integer y){
        this.y = y;
    }

    public  Integer getY(){
        return y;
    }
    
    public int getRealX(){
    	return x;
    }

	public int getRealY() {
		 
		return y;
	}
}