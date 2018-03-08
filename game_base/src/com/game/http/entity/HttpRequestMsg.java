package com.game.http.entity;

import org.apache.commons.beanutils.ConvertUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpRequestMsg<V> {

	private V body;

	public HttpRequestMsg() {

	}

	public HttpRequestMsg(String sessionId, V body) {
		super();
		this.body = body;
	}

	public V getBody() {
		return body;
	}

	public void setBody(V body) {
		this.body = body;
	}

	@SuppressWarnings("unchecked")
	public V convertBody(Class<V> clazz) {
		if (body == null) {
			return null;
		}
		if (body instanceof JSONObject) {
			return ((JSONObject) body).toJavaObject(clazz);
		}
		return (V) ConvertUtils.convert(body, clazz);

	}
}
