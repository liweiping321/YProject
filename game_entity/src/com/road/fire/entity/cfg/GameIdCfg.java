package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;
import java.util.Date;

/**
 *@author 工具生成
 *@date 2017-04-25 02:45:24 
 */
@EntityMap(table = "t_s_game_id")
public class GameIdCfg extends  AbstractEntityBean<Integer,GameIdCfg>{
	
	/**游戏区ID*/
	@PropertyMap(column="GameZoneId",primarykey=true)
	private  Integer gameZoneId;
	/**游戏区标识*/
	@PropertyMap(column="SiteId")
	private  String siteId;
	/**游戏区名称*/
	@PropertyMap(column="GameName")
	private  String gameName;
	/**记录插入时间*/
	@PropertyMap(column="InsertTime")
	private  Date insertTime;
	/**合区时间*/
	@PropertyMap(column="CombineTime")
	private  Date combineTime;
	/**游戏区状态（1：正常，0：合区）*/
	@PropertyMap(column="GameStatus")
	private  Short gameStatus;
	/**服务器语言：0-中文*/
	@PropertyMap(column="LanguageSet")
	private  Short languageSet;
	
    
    public void setGameZoneId(Integer gameZoneId){
        this.gameZoneId = gameZoneId;
    }

    public  Integer getGameZoneId(){
        return gameZoneId;
    }
    public void setSiteId(String siteId){
        this.siteId = siteId;
    }

    public  String getSiteId(){
        return siteId;
    }
    public void setGameName(String gameName){
        this.gameName = gameName;
    }

    public  String getGameName(){
        return gameName;
    }
    public void setInsertTime(Date insertTime){
        this.insertTime = insertTime;
    }

    public  Date getInsertTime(){
        return insertTime;
    }
    public void setCombineTime(Date combineTime){
        this.combineTime = combineTime;
    }

    public  Date getCombineTime(){
        return combineTime;
    }
    public void setGameStatus(Short gameStatus){
        this.gameStatus = gameStatus;
    }

    public  Short getGameStatus(){
        return gameStatus;
    }
    public void setLanguageSet(Short languageSet){
        this.languageSet = languageSet;
    }

    public  Short getLanguageSet(){
        return languageSet;
    }
}