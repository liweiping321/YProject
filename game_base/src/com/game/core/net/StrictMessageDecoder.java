package com.game.core.net;

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrictMessageDecoder extends CumulativeProtocolDecoder {
	private static final Logger LOGGER = LoggerFactory.getLogger(StrictMessageDecoder.class);

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		if (in.remaining() < 4) {
			// 剩余不足4字节，不足以解析数据包头，暂不处理
			return false;
		}

		int header = 0;
		int packetLength = 0;
		int[] decryptKey = getContext(session);
		
		byte[] validateData = new byte[4];
		in.get(validateData);
		int[] copyDecryptKeys = new int[decryptKey.length];
		System.arraycopy(decryptKey, 0, copyDecryptKeys, 0, copyDecryptKeys.length);
		validateData = decrypt(validateData, copyDecryptKeys);
		ByteBuffer byteBuffer = ByteBuffer.wrap(validateData);
		// 解密完毕，按照明文读取
		header = byteBuffer.getShort();
		packetLength = byteBuffer.getShort();
		
		// 预解密长度信息成功，回溯位置
		in.position(in.position() - 4);

		if (header != CommonMessage.HEADER) {
			session.closeNow();
			session.setAttribute(SessionKey.ERROR, "后端强制关闭");
			session.closeNow();
			LOGGER.info("数据包头不匹配 hearder : " + header);
			return false;
		}

		int headSize = CommonMessage.getHeadSize(true);
		if (packetLength < headSize) {
			// 数据包长度错误，断开连接
			LOGGER.error(String.format("error packet length: packetlength=%d Packet.HDR_SIZE=%d", packetLength, CommonMessage.HDR_SIZE));
			LOGGER.error(String.format("Disconnect the client:%s", session.getRemoteAddress()));
			session.closeNow();
			session.setAttribute(SessionKey.ERROR, "后端强制关闭");
			return false;
		}
		if (in.remaining() < packetLength) {
			// 数据长度不足，等待下次接收
			return false;
		}
		// 读取数据并解密数据
		byte[] data = new byte[packetLength];
		in.get(data, 0, packetLength);
		data = decrypt(data, decryptKey);
		Request packet = CommonMessage.build(data, true);
		if (packet != null) {
			out.write(packet);
			
//			// 统计流量
//			statisticFlow(session, packetLength);
		}
		return true;
	}

//	/**
//	 * 统计流量
//	 * @param session
//	 * @param packetLength
//	 */
//	private void statisticFlow(IoSession session, int packetLength) {
//		int lastNum = 0;
//		if(session.containsAttribute(SessionKey.RECIEVED_BYTE_NUM)){
//			lastNum = (int)session.getAttribute(SessionKey.RECIEVED_BYTE_NUM);
//		}
//		lastNum += packetLength;
//		session.setAttribute(SessionKey.RECIEVED_BYTE_NUM, lastNum);
//	}

	// 获取密钥上下文
	private int[] getContext(IoSession session) {
		int[] keys = (int[]) session.getAttribute(SessionKey.DECRYPTION_KEYS);
		if (keys == null) {
			keys = new int[] { 0xae, 0xbf, 0x56, 0x78, 0xab, 0xcd, 0xef, 0xf1 };
			// LOGGER.info("getContext keys is null, set default keys");
		}
		return keys;
	}

	public static void setKey(IoSession session, int[] keys) {
		session.setAttribute(SessionKey.DECRYPTION_KEYS, keys);
	}

	// 解密整段数据
	private byte[] decrypt(byte[] data, int[] decryptKey) throws Exception {
		if (data.length == 0)
			return data;

		if (decryptKey.length < 8)
			throw new Exception("The decryptKey must be 64bits length!");

		int length = data.length;
		int lastCipherByte;
		int plainText;
		int key;

		// 解密首字节
		lastCipherByte = data[0] & 0xff;
		data[0] ^= decryptKey[0];

		for (int index = 1; index < length; index++) {
			// 解密当前字节
			key = ((decryptKey[index & 0x7] + lastCipherByte) ^ index);
			plainText = (((data[index] & 0xff) - lastCipherByte) ^ key) & 0xff;

			// 更新变量值
			lastCipherByte = data[index] & 0xff;
			data[index] = (byte) plainText;
			decryptKey[index & 0x7] = (byte) (key & 0xff);
		}

		return data;
	}
}
