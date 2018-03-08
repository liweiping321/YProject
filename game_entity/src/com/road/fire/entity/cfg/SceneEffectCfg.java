package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-04-25 02:37:38 
 */
@EntityMap(table = "t_s_scene_effect")
public class SceneEffectCfg extends  AbstractEntityBean<Integer,SceneEffectCfg>{
	
	/**主角模板id*/
	@PropertyMap(column="HeroId",primarykey=true)
	private  Integer heroId;
	/**名称*/
	@PropertyMap(column="Name")
	private  String name;
	/**类型（1近战2远程）*/
	@PropertyMap(column="Type")
	private  Integer type;
	/**对应职业(0表示战士，1法师，2枪手)*/
	@PropertyMap(column="Career")
	private  Integer career;
	/**开放等级*/
	@PropertyMap(column="OpenLevel")
	private  Integer openLevel;
	/**基础技能*/
	@PropertyMap(column="AttackSkill")
	private  String attackSkill;
	/**按键功能(0技能1键，1技能2键，2技能3键，3跳跃键，4方向键):哪个键_什么功能_功能条件*/
	@PropertyMap(column="KeyType")
	private  String keyType;
	/**对应属性池ID*/
	@PropertyMap(column="AttributeId")
	private  Integer attributeId;
	/**1男0女*/
	@PropertyMap(column="Sex")
	private  Integer sex;
	/**资源名字*/
	@PropertyMap(column="ModelName")
	private  String modelName;
	/**伤害矩形列表(如果有多个，用|分割，格式为x|y|w|h；其中x，y为相对于锚点的坐标)*/
	@PropertyMap(column="DamageRect")
	private  String damageRect;
	/**碰撞矩形*/
	@PropertyMap(column="CollideRect")
	private  String collideRect;
	/**碰撞矩形*/
	@PropertyMap(column="BoxCollider")
	private  String boxCollider;
	/**头像图片*/
	@PropertyMap(column="HeadIcon")
	private  String headIcon;
	/**缩放比例*/
	@PropertyMap(column="ZoomProportion")
	private  Double zoomProportion;
	/**默认武器*/
	@PropertyMap(column="DefaultWeapon")
	private  String defaultWeapon;
	/**默认时装*/
	@PropertyMap(column="DefaultFashion")
	private  String defaultFashion;
	/**选择英雄时默认动作*/
	@PropertyMap(column="DefaultAim")
	private  String defaultAim;
	/**reload时间*/
	@PropertyMap(column="ReloadTime")
	private  Double reloadTime;
	/**动画控制器*/
	@PropertyMap(column="AimCtrlResPath")
	private  String aimCtrlResPath;
	/**行走音效*/
	@PropertyMap(column="RunMusic")
	private  String runMusic;
	/**起跳音效*/
	@PropertyMap(column="JumpMusic")
	private  String jumpMusic;
	/**死亡音效*/
	@PropertyMap(column="DieMusic")
	private  String dieMusic;
	/**死亡光效*/
	@PropertyMap(column="DieAnimation")
	private  String dieAnimation;
	/**是否默认激活*/
	@PropertyMap(column="IsActive")
	private  Integer isActive;
	/**血条偏移量*/
	@PropertyMap(column="HpOffset")
	private  Double hpOffset;
	
    
    public void setHeroId(Integer heroId){
        this.heroId = heroId;
    }

    public  Integer getHeroId(){
        return heroId;
    }
    public void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return name;
    }
    public void setType(Integer type){
        this.type = type;
    }

    public  Integer getType(){
        return type;
    }
    public void setCareer(Integer career){
        this.career = career;
    }

    public  Integer getCareer(){
        return career;
    }
    public void setOpenLevel(Integer openLevel){
        this.openLevel = openLevel;
    }

    public  Integer getOpenLevel(){
        return openLevel;
    }
    public void setAttackSkill(String attackSkill){
        this.attackSkill = attackSkill;
    }

    public  String getAttackSkill(){
        return attackSkill;
    }
    public void setKeyType(String keyType){
        this.keyType = keyType;
    }

    public  String getKeyType(){
        return keyType;
    }
    public void setAttributeId(Integer attributeId){
        this.attributeId = attributeId;
    }

    public  Integer getAttributeId(){
        return attributeId;
    }
    public void setSex(Integer sex){
        this.sex = sex;
    }

    public  Integer getSex(){
        return sex;
    }
    public void setModelName(String modelName){
        this.modelName = modelName;
    }

    public  String getModelName(){
        return modelName;
    }
    public void setDamageRect(String damageRect){
        this.damageRect = damageRect;
    }

    public  String getDamageRect(){
        return damageRect;
    }
    public void setCollideRect(String collideRect){
        this.collideRect = collideRect;
    }

    public  String getCollideRect(){
        return collideRect;
    }
    public void setBoxCollider(String boxCollider){
        this.boxCollider = boxCollider;
    }

    public  String getBoxCollider(){
        return boxCollider;
    }
    public void setHeadIcon(String headIcon){
        this.headIcon = headIcon;
    }

    public  String getHeadIcon(){
        return headIcon;
    }
    public void setZoomProportion(Double zoomProportion){
        this.zoomProportion = zoomProportion;
    }

    public  Double getZoomProportion(){
        return zoomProportion;
    }
    public void setDefaultWeapon(String defaultWeapon){
        this.defaultWeapon = defaultWeapon;
    }

    public  String getDefaultWeapon(){
        return defaultWeapon;
    }
    public void setDefaultFashion(String defaultFashion){
        this.defaultFashion = defaultFashion;
    }

    public  String getDefaultFashion(){
        return defaultFashion;
    }
    public void setDefaultAim(String defaultAim){
        this.defaultAim = defaultAim;
    }

    public  String getDefaultAim(){
        return defaultAim;
    }
    public void setReloadTime(Double reloadTime){
        this.reloadTime = reloadTime;
    }

    public  Double getReloadTime(){
        return reloadTime;
    }
    public void setAimCtrlResPath(String aimCtrlResPath){
        this.aimCtrlResPath = aimCtrlResPath;
    }

    public  String getAimCtrlResPath(){
        return aimCtrlResPath;
    }
    public void setRunMusic(String runMusic){
        this.runMusic = runMusic;
    }

    public  String getRunMusic(){
        return runMusic;
    }
    public void setJumpMusic(String jumpMusic){
        this.jumpMusic = jumpMusic;
    }

    public  String getJumpMusic(){
        return jumpMusic;
    }
    public void setDieMusic(String dieMusic){
        this.dieMusic = dieMusic;
    }

    public  String getDieMusic(){
        return dieMusic;
    }
    public void setDieAnimation(String dieAnimation){
        this.dieAnimation = dieAnimation;
    }

    public  String getDieAnimation(){
        return dieAnimation;
    }
    public void setIsActive(Integer isActive){
        this.isActive = isActive;
    }

    public  Integer getIsActive(){
        return isActive;
    }
    public void setHpOffset(Double hpOffset){
        this.hpOffset = hpOffset;
    }

    public  Double getHpOffset(){
        return hpOffset;
    }
}