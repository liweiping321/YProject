/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.game.core.net;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.game.utils.LogUtils;

/**
 * 游戏协议包类，描述具体游戏数据包结构。<br>
 * <br>
 * 封包规则：一个包分为包头和包体两部分，结构如下：<br>
 * 【分隔符|包长|校验和|code】【包体】。<br>
 * 其中，包头各部分长度为2字节。检验和计算范围从code开始，直到整个包结束。
 * 
 **/
public class CommonMessage {
	/** 包头长度 */
	public static final short HDR_SIZE = 12;
	/** 包分隔符 */
	public static final short HEADER = 0x71ab;
	/** 协议号 */
	private short code;
	/** 包体数据 */
	private byte[] body;

	/** 包体数据最大长度 */
	public static final short MAX_BODY_SIZE = Short.MAX_VALUE - HDR_SIZE; 
	
	private int playerId; // 玩家ID

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int accountId) {
		this.playerId = accountId;
	}
	
	/**
	 * 是否加密
	 * @param strict
	 * @return
	 */
	public static int getHeadSize(boolean strict){
		if(strict){
			return HDR_SIZE - 4;
		}
		return HDR_SIZE;
	}
	
	/**
	 * 构建实例。<br>
	 * 注意：所构建的实例的校验和是从输入参数msgData中读取的，并非通过消息数据计算所得。
	 * 
	 * @param msgData
	 *            消息数据，至少包括包头，允许不带包体数据。
	 * @return
	 */
	public static Request build(byte[] msgData, boolean strict) {
		int headSize = getHeadSize(strict);
		if (msgData == null || msgData.length < headSize) {
			return null;
		}

		ByteBuffer buff = ByteBuffer.wrap(msgData);
		// 跳过分隔符和包长，包长由输入参数长度确定。
		buff.position(4);
		short checksum = buff.getShort();
		short code = buff.getShort();
		int playerId = 0;
		if(!strict){
			playerId = buff.getInt();
		}
		int bodyLen = msgData.length - headSize;
		if (bodyLen > 0) {
			byte[] body = new byte[bodyLen];
			buff.get(body, 0, bodyLen);
			// 检查校验和是否正确
			short getCS = calcChecksum(msgData);
			if (checksum != getCS) {
				LogUtils.error(String.format("数据包校验失败，数据包将被丢弃。code: 0x{}。校验和应为{}，实际接收校验和为{}", code, getCS, checksum));
				return null;
			}
			return Request.valueOf(code, playerId, body);
		} else {
			return Request.valueOf(code, playerId);
		}
	}
	
	/**
	 * 计算校验和
	 * 
	 * @param data
	 *            完整的消息数据，包括包头和包体，计算将从第7个字节开始。
	 * @return
	 */
	private static short calcChecksum(byte[] data) {
		int val = 0x77;
		int i = 6;
		int size = data.length;
		while (i < size) {
			val += (data[i++] & 0xFF);
		}
		return (short) (val & 0x7F7F);
	}

	/**
	 * 数据包转换为ByteBuffer，包括包头和包体。
	 * 
	 * @return
	 */
	public ByteBuffer toByteBuffer(boolean strict) {
		short len = getLen(strict);
		ByteBuffer buff = ByteBuffer.allocate(len);
		buff.putShort(HEADER);
		// 插入长度
		buff.putShort(len);
		// 先跳过校验和
		buff.position(6);
		buff.putShort(code);
		if(!strict){
			buff.putInt(playerId);
		}
		if (body != null) {
			buff.put(body);
		}
		int pos = buff.position();

		// 插入校验和
		buff.position(4);
		buff.putShort(calcChecksum(buff.array()));

		buff.position(pos);

		buff.flip();
		return buff;
	}

	/**
	 * 获取数据包的长度，包括包头和包体。
	 * 
	 * @return
	 */
	public short getLen(boolean strict) {
		int headSize = getHeadSize(strict);
		int bodyLen = body == null ? 0 : body.length;
		return (short)(headSize + bodyLen);
	}

	/**
	 * 获取协议号
	 * 
	 * @return
	 */
	public short getCode() {
		return code;
	}

	/**
	 * 设置协议号
	 * 
	 * @param code
	 */
	public void setCode(short code) {
		this.code = code;
	}

	/**
	 * 获取包体，包体允许为null。
	 * 
	 * @return
	 */
	public byte[] getBody() {
		return body;
	}

	/**
	 * 设置包体，包体允许为null。
	 * 
	 * @param bytes
	 */
	public void setBody(byte[] bytes) {
		this.body = bytes;
	}

	/**
	 * 包头的字符串表示形式。
	 * 
	 * @return
	 */
	public String headerToStr(boolean strict) {
		StringBuilder sb = new StringBuilder();
		sb.append("len: ").append(getLen(strict));
		sb.append(", code: 0x").append(Integer.toHexString(code));
		return sb.toString();
	}

	/**
	 * 数据包的字符串表示形式。
	 * 
	 * @return
	 */
	public String detailToStr(boolean strict) {
		String str = "";
		if (body != null) {
			try {
				str = new String(body, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				str = "(UnsupportedEncodingException)";
			}
		}
		return String.format("%s. content:%s.", headerToStr(strict), str);
	}

	@Override
	public String toString() {
		return "CommonMessage [code=" + code + "]";
	}
}
