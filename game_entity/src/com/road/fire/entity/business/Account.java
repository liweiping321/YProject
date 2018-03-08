package com.road.fire.entity.business;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;
import java.util.Date;

/**
 *@author 工具生成
 *@date 2017-03-13 11:30:18 
 */
@EntityMap(table = "t_u_account")
public class Account extends  AbstractEntityBean<Long,Account>{
	
	/**用户ID*/
	@PropertyMap(column="UserID",primarykey=true)
	private  Long userID;
	/**用户名*/
	@PropertyMap(column="Account")
	private  String account;
	/**站点*/
	@PropertyMap(column="Site")
	private  String site;
	/**是否在线*/
	@PropertyMap(column="IsOnline")
	private  Boolean isOnline;
	/**创建时间*/
	@PropertyMap(column="CreateDate")
	private  Date createDate;
	/**封号时间，小于当前时间表示未被封号或已解封*/
	@PropertyMap(column="ForbidDate")
	private  Date forbidDate;
	/**最后充值时间，NULL为未充值*/
	@PropertyMap(column="LastPayDate")
	private  Date lastPayDate;
	/**最后登录IP*/
	@PropertyMap(column="LastLoginIP")
	private  String lastLoginIP;
	/**最后登录时间*/
	@PropertyMap(column="LastLoginDate")
	private  Date lastLoginDate;
	/**上一天最后登录时间*/
	@PropertyMap(column="LastLogin2Date")
	private  Date lastLogin2Date;
	/**上上一天最后登录时间*/
	@PropertyMap(column="LastLogin3Date")
	private  Date lastLogin3Date;
	/**最后登出时间*/
	@PropertyMap(column="LastLogoutDate")
	private  Date lastLogoutDate;
	/**0-不存在，1-存在*/
	@PropertyMap(column="IsExist")
	private  Boolean isExist;
	
    
    public void setUserID(Long userID){
        this.userID = userID;
    }

    public  Long getUserID(){
        return userID;
    }
    public void setAccount(String account){
        this.account = account;
    }

    public  String getAccount(){
        return account;
    }
    public void setSite(String site){
        this.site = site;
    }

    public  String getSite(){
        return site;
    }
    public void setIsOnline(Boolean isOnline){
        this.isOnline = isOnline;
    }

    public  Boolean getIsOnline(){
        return isOnline;
    }
    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }

    public  Date getCreateDate(){
        return createDate;
    }
    public void setForbidDate(Date forbidDate){
        this.forbidDate = forbidDate;
    }

    public  Date getForbidDate(){
        return forbidDate;
    }
    public void setLastPayDate(Date lastPayDate){
        this.lastPayDate = lastPayDate;
    }

    public  Date getLastPayDate(){
        return lastPayDate;
    }
    public void setLastLoginIP(String lastLoginIP){
        this.lastLoginIP = lastLoginIP;
    }

    public  String getLastLoginIP(){
        return lastLoginIP;
    }
    public void setLastLoginDate(Date lastLoginDate){
        this.lastLoginDate = lastLoginDate;
    }

    public  Date getLastLoginDate(){
        return lastLoginDate;
    }
    public void setLastLogin2Date(Date lastLogin2Date){
        this.lastLogin2Date = lastLogin2Date;
    }

    public  Date getLastLogin2Date(){
        return lastLogin2Date;
    }
    public void setLastLogin3Date(Date lastLogin3Date){
        this.lastLogin3Date = lastLogin3Date;
    }

    public  Date getLastLogin3Date(){
        return lastLogin3Date;
    }
    public void setLastLogoutDate(Date lastLogoutDate){
        this.lastLogoutDate = lastLogoutDate;
    }

    public  Date getLastLogoutDate(){
        return lastLogoutDate;
    }
    public void setIsExist(Boolean isExist){
        this.isExist = isExist;
    }

    public  Boolean getIsExist(){
        return isExist;
    }
}