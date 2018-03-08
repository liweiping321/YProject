package com.road.fire.entity.cfg;
 
import com.game.consts.GameConsts;
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-05-17 06:18:58 
 */
@EntityMap(table = "t_s_scene_drop")
public class SceneDropCfg extends  AbstractEntityBean<Integer,SceneDropCfg>{
	
	/**场景掉落ID*/
	@PropertyMap(column="SceneDropId",primarykey=true)
	private  Integer sceneDropId;
	/**名称*/
	@PropertyMap(column="Name")
	private  String name;
	/**掉落宝箱ID*/
	@PropertyMap(column="DropBoxId")
	private  Integer dropBoxId;
	/**场景ID*/
	@PropertyMap(column="SceneId")
	private  Integer sceneId;
	/**出生点X轴*/
	@PropertyMap(column="X")
	private  Integer x;
	/**出生点Y轴*/
	@PropertyMap(column="Y")
	private  Integer y;
	/**出生朝向*/
	@PropertyMap(column="BornFaceDir")
	private  Integer bornFaceDir;
	/**出生类型(0.资源load完后延迟出生，1.场景初始化出生,2.外部驱动出生)*/
	@PropertyMap(column="BornType")
	private  Integer bornType;
	/**出生延迟时间(单位毫秒)*/
	@PropertyMap(column="BornDelayTime")
	private  Integer bornDelayTime;
	/**是否可以复活*/
	@PropertyMap(column="Relive")
	private  Boolean relive;
	/**复活延迟时间(单位毫秒)*/
	@PropertyMap(column="ReliveDelayTime")
	private  Integer reliveDelayTime;
	/**生命时长(-1表示永久,单位毫秒)*/
	@PropertyMap(column="LifeTime")
	private  Integer lifeTime;
	/**拾取半径*/
	@PropertyMap(column="PickRadius")
	private  Integer pickRadius;
	/**场景掉落模型*/
	@PropertyMap(column="TemplateId")
	private  Integer templateId;
	
    
    public void setSceneDropId(Integer sceneDropId){
        this.sceneDropId = sceneDropId;
    }

    public  Integer getSceneDropId(){
        return sceneDropId;
    }
    public void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return name;
    }
    public void setDropBoxId(Integer dropBoxId){
        this.dropBoxId = dropBoxId;
    }

    public  Integer getDropBoxId(){
        return dropBoxId;
    }
    public void setSceneId(Integer sceneId){
        this.sceneId = sceneId;
    }

    public  Integer getSceneId(){
        return sceneId;
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
    public void setBornFaceDir(Integer bornFaceDir){
        this.bornFaceDir = bornFaceDir;
    }

    public  Integer getBornFaceDir(){
        return bornFaceDir;
    }
    public void setBornType(Integer bornType){
        this.bornType = bornType;
    }

    public  Integer getBornType(){
        return bornType;
    }
    public void setBornDelayTime(Integer bornDelayTime){
        this.bornDelayTime = bornDelayTime;
    }

    public  Integer getBornDelayTime(){
        return bornDelayTime;
    }
    public void setRelive(Boolean relive){
        this.relive = relive;
    }

    public  Boolean getRelive(){
        return relive;
    }
    public void setReliveDelayTime(Integer reliveDelayTime){
        this.reliveDelayTime = reliveDelayTime;
    }

    public  Integer getReliveDelayTime(){
        return reliveDelayTime;
    }
    public void setLifeTime(Integer lifeTime){
        this.lifeTime = lifeTime;
    }

    public  Integer getLifeTime(){
        return lifeTime;
    }
    
	public Integer getPickRadius() {
		return pickRadius;
	}

	public void setPickRadius(Integer pickRadius) {
		this.pickRadius = pickRadius;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	 
	public int getRealX() {
		return x ;
	}

	public int getRealY() {
		return y;
	}
    
}