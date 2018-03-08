package com.game.core.net;

/**
 * sessionKey常量
 * @author jason.lin
 *
 */
public enum SessionKey {

	/**
	 * 玩家id
	 */
	PLAYER_ID,
	
	/**
	 * 密码表key
	 */
	DECRYPTION_KEYS,
	/**
	 * 密钥
	 */
	SECRET_KEY,
	/**
	 * 渠道
	 */
	CHANNEL,
	/**
	 * 区服
	 */
	SERVER,
	/**
	 * Session关闭信息
	 */
	ERROR,
	/**
	 * 时间缀
	 */
	TIMESTAMP,
	/**
	 * 远程客户端地址
	 */
	REMOTE_ADDRESS,
	/**
	 * 标记
	 */
	MARK,
	
	/**
	 * 服务器接收的字节数
	 */
	RECIEVED_BYTE_NUM,
	
	/**
	 * 服务器发送的字节数
	 */
	SEND_BYTE_NUM,
	
	/**
	 * 战斗场数
	 */
	FIGHT_NUM,
	
	/***登录键值**/
	LOGIN_KEY,
	
	/***逻辑服**/
	LOGIC_SERVER,
	
	/***在线玩家信息**/
	ONLINE_PLAYER,
	;
}
