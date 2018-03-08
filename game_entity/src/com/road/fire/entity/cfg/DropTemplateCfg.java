package com.road.fire.entity.cfg;
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;

/**
 *@author 工具生成
 *@date 2017-05-19 04:40:44 
 */
@EntityMap(table = "t_s_drop_template")
public class DropTemplateCfg extends  AbstractEntityBean<Integer,DropTemplateCfg>{
	/**增长ID*/
	@PropertyMap(column="GoodsId",primarykey=true)
	private  Integer goodsId;
	/**名称*/
	@PropertyMap(column="Name")
	private  String name;
	/**(1001 经验,2001 BUFF，2002 技能[道具] )*/
	@PropertyMap(column="Type")
	private  Integer type;
	/**出生朝向*/
	@PropertyMap(column="BornFaceDir")
	private  Integer bornFaceDir;
	/**拾取半径*/
	@PropertyMap(column="PickRadius")
	private  Integer pickRadius;
	/**场景掉落模型*/
	@PropertyMap(column="TemplateId")
	private  Integer templateId;
 
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
    public void setGoodsId(Integer goodsId){
        this.goodsId = goodsId;
    }

    public  Integer getGoodsId(){
        return goodsId;
    }
    public void setBornFaceDir(Integer bornFaceDir){
        this.bornFaceDir = bornFaceDir;
    }

    public  Integer getBornFaceDir(){
        return bornFaceDir;
    }
    public void setPickRadius(Integer pickRadius){
        this.pickRadius = pickRadius;
    }

    public  Integer getPickRadius(){
        return pickRadius;
    }
    public void setTemplateId(Integer templateId){
        this.templateId = templateId;
    }

    public  Integer getTemplateId(){
        return templateId;
    }
}