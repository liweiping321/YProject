package com.road.fire.entity.business;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-04-25 02:31:07 
 */
@EntityMap(table = "t_u_player")
public class PlayerData extends  AbstractEntityBean<Long,PlayerData>{
	
	/**用户ID*/
	@PropertyMap(column="UserID",primarykey=true)
	private  Long userID;
	/**昵称*/
	@PropertyMap(column="Nickname")
	private  String nickname;
	/**1为男，0为女，其它性别*/
	@PropertyMap(column="Sex")
	private  Short sex;
	/**职业*/
	@PropertyMap(column="Career")
	private  Short career;
	/**等级*/
	@PropertyMap(column="Level")
	private  Short level;
	/**金币*/
	@PropertyMap(column="Gold")
	private  Long gold;
	/**经验*/
	@PropertyMap(column="Exp")
	private  Integer exp;
	/**金钱(使用真实货币充值兑换的币种)*/
	@PropertyMap(column="Money")
	private  Integer money;
	/**公会ID，未加入公会则为0*/
	@PropertyMap(column="GuildID")
	private  Long guildID;
	/**玩家状态*/
	@PropertyMap(column="State")
	private  Short state;
	/**0-不存在，1-存在*/
	@PropertyMap(column="IsExist")
	private  Boolean isExist;
	
    
    public void setUserID(Long userID){
        this.userID = userID;
    }

    public  Long getUserID(){
        return userID;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public  String getNickname(){
        return nickname;
    }
    public void setSex(Short sex){
        this.sex = sex;
    }

    public  Short getSex(){
        return sex;
    }
    public void setCareer(Short career){
        this.career = career;
    }

    public  Short getCareer(){
        return career;
    }
    public void setLevel(Short level){
        this.level = level;
    }

    public  Short getLevel(){
        return level;
    }
    public void setGold(Long gold){
        this.gold = gold;
    }

    public  Long getGold(){
        return gold;
    }
    public void setExp(Integer exp){
        this.exp = exp;
    }

    public  Integer getExp(){
        return exp;
    }
    public void setMoney(Integer money){
        this.money = money;
    }

    public  Integer getMoney(){
        return money;
    }
    public void setGuildID(Long guildID){
        this.guildID = guildID;
    }

    public  Long getGuildID(){
        return guildID;
    }
    public void setState(Short state){
        this.state = state;
    }

    public  Short getState(){
        return state;
    }
    public void setIsExist(Boolean isExist){
        this.isExist = isExist;
    }

    public  Boolean getIsExist(){
        return isExist;
    }
}