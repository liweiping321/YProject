package com.game.http.entity;

/**
 * 错误码
 * @author jason.lin
 *
 */
public class ErrorCode {
	/***成功***/
	public static final short SUCCESS = 0;
	
	/***重名***/
	public static final short DOUBLE_NAME = 1;
	
	/***敏感字***/
	public static final short BAD_WORD = 2;
	
	/***昵称过长***/
	public static final short TOO_LONG = 3;
	
}
