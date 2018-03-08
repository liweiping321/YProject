package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
@EntityMap(table = "t_s_scene")
public class SceneCfg extends  AbstractEntityBean<Integer,SceneCfg>{
	
	/**场景Id*/
	@PropertyMap(column="SceneId",primarykey=true)
	private  Integer sceneId;
	/**地图id*/
	@PropertyMap(column="MapId")
	private  Integer mapId;
	/**战斗类型*/
	@PropertyMap(column="FightingType")
	private  Integer fightingType;
	/**出生点类型(0顺序，1随机)*/
	@PropertyMap(column="BornType")
	private  Integer bornType;
	/**关卡名*/
	@PropertyMap(column="SceneName")
	private  String sceneName;
	/**开启等级*/
	@PropertyMap(column="OpenLevel")
	private  Integer openLevel;
	/**场景处理脚本*/
	@PropertyMap(column="ActionScript")
	private  String actionScript;
	/**掉落列表(id1|id2|id3)*/
	@PropertyMap(column="DropIds")
	private  String dropIds;
	/**场景音乐*/
	@PropertyMap(column="Music")
	private  String music;
	/**描述*/
	@PropertyMap(column="Description")
	private  String description;
	
    
    public void setSceneId(Integer sceneId){
        this.sceneId = sceneId;
    }

    public  Integer getSceneId(){
        return sceneId;
    }
    public void setMapId(Integer mapId){
        this.mapId = mapId;
    }

    public  Integer getMapId(){
        return mapId;
    }
    public void setFightingType(Integer fightingType){
        this.fightingType = fightingType;
    }

    public  Integer getFightingType(){
        return fightingType;
    }
    public void setBornType(Integer bornType){
        this.bornType = bornType;
    }

    public  Integer getBornType(){
        return bornType;
    }
    public void setSceneName(String sceneName){
        this.sceneName = sceneName;
    }

    public  String getSceneName(){
        return sceneName;
    }
    public void setOpenLevel(Integer openLevel){
        this.openLevel = openLevel;
    }

    public  Integer getOpenLevel(){
        return openLevel;
    }
    public void setActionScript(String actionScript){
        this.actionScript = actionScript;
    }

    public  String getActionScript(){
        return actionScript;
    }
    public void setDropIds(String dropIds){
        this.dropIds = dropIds;
    }

    public  String getDropIds(){
        return dropIds;
    }
    public void setMusic(String music){
        this.music = music;
    }

    public  String getMusic(){
        return music;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public  String getDescription(){
        return description;
    }
}