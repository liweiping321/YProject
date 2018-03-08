package com.road.fire.fighting.property;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.game.property.PropertyManagerImpl;
import com.game.property.PropertyValue;
import com.game.protobuf.MsgCode;
import com.game.protobuf.fighting.FightingProto.PBPropValueUpdateResp;
import com.road.fire.FtMsgUtil;
import com.road.fire.fighting.phy.living.Living;
/**
 * 战斗服务器属性管理器
 * @author lip.li
 *
 * @param <T>
 */
public class FightingPropertyManger<T extends Living<?>> extends PropertyManagerImpl<T> {

	public FightingPropertyManger(T owner) {
		super(owner);
		 
	}

	@Override
	public void sendPropertyValue(boolean broadcast, int... keys) {
		 if(keys!=null&&keys.length>0){
			 List<PropertyValue> values=getPropertyValues(keys);
			 sendProppertyValues(broadcast, values);
		 }
	}



	@Override
	public void sendUpdatePropertyValue(boolean broadcast) {
		List<PropertyValue> values=getUpdatePropertyValues();
		
		if(!CollectionUtils.isEmpty(values)){
			 sendProppertyValues(broadcast, values);
		}
	}
	
	private void sendProppertyValues(boolean broadcast,
			List<PropertyValue> values) {
		
		PBPropValueUpdateResp updateResp= FtMsgUtil.builderPropValueUpdateResp(owner.getPhyId(), values);
		
		 if(broadcast){
			owner.broadcastMsg(MsgCode.PropValueUpdateResp,updateResp );
		 }else{
			 owner.sendMsg(MsgCode.PropValueUpdateResp, updateResp);
		 }
		 
		 for(PropertyValue value:values){
			 value.setUpdate(false);
		 }
	}

}
