package com.web;

import com.game.http.AbstractWebHandler;
import com.game.http.entity.HttpRequestMsg;
import com.game.http.entity.HttpResponseMsg;
import com.game.http.entity.RequestMapping;
import com.game.utils.LogUtils;
import com.road.fire.cfg.FightingCfgProviders;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/handler")
public class WebHandler extends AbstractWebHandler
{
    
	/**
	 * 上行开关设置
	 */
	@RequestMapping("/reload")
	public HttpResponseMsg<?> reload(HttpRequestMsg<List<String>> reqMsg){

		List<String> reloadList = reqMsg.getBody();
		if (reloadList == null || reloadList.isEmpty())
		{
			LogUtils.warn("刷新列表为空");
			return new HttpResponseMsg<>("刷新列表为空");
		}

		FightingCfgProviders.getInstance().loadData(reloadList.toArray(new String[reloadList.size()]));
		return new HttpResponseMsg<>("刷新完成。列表为：\n" + Arrays.toString(reloadList.toArray(new String[reloadList.size()])));
	}
}
