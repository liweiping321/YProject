package com.game.http.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * HTTP响应结果
 * 
 * @author kevin
 *
 */
public class HttpResponseMsg<V> {

	private int status;

	private int errorCode;

	private V body;

	public HttpResponseMsg() {
	}

	public HttpResponseMsg(V body) {
		this.body = body;
	}

	public HttpResponseMsg(int status, V body) {
		this.status = status;
		this.body = body;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public V getBody() {
		return body;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setBody(V body) {
		this.body = body;
	}

	public String toJsonString() {
		return JSON.toJSONString(this);
	}

	public V convertBody(Class<V> clazz) {
		if (body == null) {

			return null;
		}
		if (body instanceof JSONObject) {
			return ((JSONObject) body).toJavaObject(clazz);
		}
		return clazz.cast(body);
	}
}
