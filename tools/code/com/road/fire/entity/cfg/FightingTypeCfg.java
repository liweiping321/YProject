package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-06-06 03:52:23 
 */
@EntityMap(table = "t_s_fighting_type")
public class FightingTypeCfg extends  AbstractEntityBean<Integer,FightingTypeCfg>{
	
	/**类型ID*/
	@PropertyMap(column="Id",primarykey=true)
	private  Integer id;
	/**名字*/
	@PropertyMap(column="Name")
	private  String name;
	/**开放等级*/
	@PropertyMap(column="OpenLevel")
	private  Integer openLevel;
	/**队伍人数*/
	@PropertyMap(column="TeamNum")
	private  Integer teamNum;
	/**参战总人数*/
	@PropertyMap(column="TotalNum")
	private  Integer totalNum;
	/**战斗时长（毫秒）*/
	@PropertyMap(column="FightingTime")
	private  Integer fightingTime;
	/**游戏加载时间(毫秒)*/
	@PropertyMap(column="LoadingTime")
	private  Integer loadingTime;
	/**玩家准备时间*/
	@PropertyMap(column="PlayerReadyTime")
	private  Integer playerReadyTime;
	/**消耗金币*/
	@PropertyMap(column="Gold")
	private  Integer gold;
	/**消耗钻石*/
	@PropertyMap(column="Diamond")
	private  Integer diamond;
	/**场景ID(以后可能需要扩展)*/
	@PropertyMap(column="SceneId")
	private  Integer sceneId;
	/**两队elo允许的最大平均差*/
	@PropertyMap(column="Md")
	private  Integer md;
	/**胜利所需击杀人数*/
	@PropertyMap(column="VictoryNum")
	private  Integer victoryNum;
	/**英雄最大等级*/
	@PropertyMap(column="HeroMaxLevel")
	private  Integer heroMaxLevel;
	
    
    public void setId(Integer id){
        this.id = id;
    }

    public  Integer getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return name;
    }
    public void setOpenLevel(Integer openLevel){
        this.openLevel = openLevel;
    }

    public  Integer getOpenLevel(){
        return openLevel;
    }
    public void setTeamNum(Integer teamNum){
        this.teamNum = teamNum;
    }

    public  Integer getTeamNum(){
        return teamNum;
    }
    public void setTotalNum(Integer totalNum){
        this.totalNum = totalNum;
    }

    public  Integer getTotalNum(){
        return totalNum;
    }
    public void setFightingTime(Integer fightingTime){
        this.fightingTime = fightingTime;
    }

    public  Integer getFightingTime(){
        return fightingTime;
    }
    public void setLoadingTime(Integer loadingTime){
        this.loadingTime = loadingTime;
    }

    public  Integer getLoadingTime(){
        return loadingTime;
    }
    public void setPlayerReadyTime(Integer playerReadyTime){
        this.playerReadyTime = playerReadyTime;
    }

    public  Integer getPlayerReadyTime(){
        return playerReadyTime;
    }
    public void setGold(Integer gold){
        this.gold = gold;
    }

    public  Integer getGold(){
        return gold;
    }
    public void setDiamond(Integer diamond){
        this.diamond = diamond;
    }

    public  Integer getDiamond(){
        return diamond;
    }
    public void setSceneId(Integer sceneId){
        this.sceneId = sceneId;
    }

    public  Integer getSceneId(){
        return sceneId;
    }
    public void setMd(Integer md){
        this.md = md;
    }

    public  Integer getMd(){
        return md;
    }
    public void setVictoryNum(Integer victoryNum){
        this.victoryNum = victoryNum;
    }

    public  Integer getVictoryNum(){
        return victoryNum;
    }
    public void setHeroMaxLevel(Integer heroMaxLevel){
        this.heroMaxLevel = heroMaxLevel;
    }

    public  Integer getHeroMaxLevel(){
        return heroMaxLevel;
    }
}