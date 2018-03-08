package com.road.fire.web;


import com.game.http.AbstractWebHandler;
import com.game.http.entity.HttpRequestMsg;
import com.game.http.entity.HttpResponseMsg;
import com.game.http.entity.RequestMapping;
import com.road.fire.cfg.CfgEntityProviders;

@RequestMapping("/api")
public class WebHandler extends AbstractWebHandler
{
	/**
	 * 刷新
	 */
	@RequestMapping("/reload")
	public HttpResponseMsg<?> reload(HttpRequestMsg<String[]> reqMsg) {
 

		CfgEntityProviders.getInstance().loadData(reqMsg.getBody());
		return new HttpResponseMsg<>();
	}

	/**
	 * 刷新
	 */
	@RequestMapping("/reloadAll")
	public HttpResponseMsg<?> reloadAll(HttpRequestMsg<?> reqMsg) {
 

		CfgEntityProviders.getInstance().loadData();
		return new HttpResponseMsg<>();
	}

	 
}
