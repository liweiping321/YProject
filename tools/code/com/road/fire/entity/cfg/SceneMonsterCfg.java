package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
@EntityMap(table = "t_s_scene_monster")
public class SceneMonsterCfg extends  AbstractEntityBean<Integer,SceneMonsterCfg>{
	
	/**场景怪物ID*/
	@PropertyMap(column="SceneMonsterId",primarykey=true)
	private  Integer sceneMonsterId;
	/**名称（场景中的怪物，掉落载体）*/
	@PropertyMap(column="Name")
	private  String name;
	/**怪物Id*/
	@PropertyMap(column="MonsterId")
	private  Integer monsterId;
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
	/**怪物产生障碍点(x_y|x_y)0坐标系统格子*/
	@PropertyMap(column="ImpassePoints")
	private  String impassePoints;
	/**掉落偏移点(x_y|x_y)*/
	@PropertyMap(column="DropOffsetPoints")
	private  String dropOffsetPoints;
	/**怪物的移动路径关键点(x_y|x_y|x_y)*/
	@PropertyMap(column="MovePath")
	private  String movePath;
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
	
    
    public void setSceneMonsterId(Integer sceneMonsterId){
        this.sceneMonsterId = sceneMonsterId;
    }

    public  Integer getSceneMonsterId(){
        return sceneMonsterId;
    }
    public void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return name;
    }
    public void setMonsterId(Integer monsterId){
        this.monsterId = monsterId;
    }

    public  Integer getMonsterId(){
        return monsterId;
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
    public void setImpassePoints(String impassePoints){
        this.impassePoints = impassePoints;
    }

    public  String getImpassePoints(){
        return impassePoints;
    }
    public void setDropOffsetPoints(String dropOffsetPoints){
        this.dropOffsetPoints = dropOffsetPoints;
    }

    public  String getDropOffsetPoints(){
        return dropOffsetPoints;
    }
    public void setMovePath(String movePath){
        this.movePath = movePath;
    }

    public  String getMovePath(){
        return movePath;
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
}