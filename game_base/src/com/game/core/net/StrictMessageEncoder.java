package com.game.core.net;

import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrictMessageEncoder extends ProtocolEncoderAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(StrictMessageEncoder.class);

	public StrictMessageEncoder() {

	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		try {

			if (message instanceof CommonMessage) {
				encodeResponse(session, (CommonMessage)message, out);
				return;
			}

			@SuppressWarnings("unchecked")
			List<Response> list = (List<Response>) message;
			if (list == null) {
				return;
			}
			
			// 获取长度
			int totalLength = getLength(list);
			IoBuffer cipherBuffer = IoBuffer.allocate(totalLength);
			
			for (Response response : list) {
				// 若存在不同线程给同一玩家发送数据的情况，因此加密过程需要同步处理
				CommonMessage msg = (CommonMessage) response;
				int lastCipherByte = 0;
				int[] encryptKey = getContext(session);
				byte[] plainText = msg.toByteBuffer(true).array();
				int length = plainText.length;
				
				// 加密首字节
				lastCipherByte = (byte) ((plainText[0] ^ encryptKey[0]) & 0xff);
				cipherBuffer.put((byte) lastCipherByte);

				// 循环加密
				int keyIndex = 0;
				for (int i = 1; i < length; i++) {
					keyIndex = i & 0x7;
					encryptKey[keyIndex] = ((encryptKey[keyIndex] + lastCipherByte) ^ i) & 0xff;
					lastCipherByte = (((plainText[i] ^ encryptKey[keyIndex]) & 0xff) + lastCipherByte) & 0xff;
					cipherBuffer.put((byte) lastCipherByte);
				}
			}

			out.write(cipherBuffer.flip());
			out.flush();
		} catch (Exception ex) {
			LOGGER.error("catch error for encoding packet:", ex);
			throw ex;
		}
	}

	/**
	 * 获取长度
	 * @param list
	 * @return
	 */
	private int getLength(List<Response> list) {
		// 若存在不同线程给同一玩家发送数据的情况，因此加密过程需要同步处理
		int length = 0;
		for (Response response : list) {
			CommonMessage msg = (CommonMessage) response;
			byte[] plainText = msg.toByteBuffer(true).array();
			length += plainText.length;
		}
		return length;
	}

	public int encodeResponse(IoSession session, CommonMessage msg, ProtocolEncoderOutput out) throws Exception {
		try {
			// 若存在不同线程给同一玩家发送数据的情况，因此加密过程需要同步处理
			int lastCipherByte = 0;
			int[] encryptKey = getContext(session);
			byte[] plainText = msg.toByteBuffer(true).array();
			int length = plainText.length;
			IoBuffer cipherBuffer = IoBuffer.allocate(length);
			// 加密首字节
			lastCipherByte = (byte) ((plainText[0] ^ encryptKey[0]) & 0xff);
			cipherBuffer.put((byte) lastCipherByte);

			// 循环加密
			int keyIndex = 0;
			for (int i = 1; i < length; i++) {
				keyIndex = i & 0x7;
				encryptKey[keyIndex] = ((encryptKey[keyIndex] + lastCipherByte) ^ i) & 0xff;
				lastCipherByte = (((plainText[i] ^ encryptKey[keyIndex]) & 0xff) + lastCipherByte) & 0xff;
				cipherBuffer.put((byte) lastCipherByte);
			}

			out.write(cipherBuffer.flip());
			out.flush();

			// // 统计流量
			// statisticFlow(session, length);
			return length;
		} catch (Exception ex) {
			LOGGER.error("catch error for encoding packet:", ex);
			throw ex;
		}
	}

	/**
	 * 统计流量
	 * 
	 * @param session
	 * @param packetLength
	 */
	public void statisticFlow(IoSession session, int packetLength) {
		int lastNum = 0;
		if (session.containsAttribute(SessionKey.SEND_BYTE_NUM)) {
			lastNum = (int) session.getAttribute(SessionKey.SEND_BYTE_NUM);
		}
		lastNum += packetLength;
		session.setAttribute(SessionKey.SEND_BYTE_NUM, lastNum);
	}

	// 获取当前加密密钥
	private int[] getContext(IoSession session) {
		int[] keys = (int[]) session.getAttribute(SessionKey.DECRYPTION_KEYS);
		if (keys == null) {
			keys = new int[] { 0xae, 0xbf, 0x56, 0x78, 0xab, 0xcd, 0xef, 0xf1 };
			// LOGGER.error("getContext keys is null, set default keys");
		}
		return keys;
	}

	public static void setKey(IoSession session, int[] key) {
		session.setAttribute(SessionKey.DECRYPTION_KEYS, key);
	}

	public static String toHexDump(String description, int[] dump, int start, int count) {
		String hexDump = "";
		if (description != null) {
			hexDump += description;
			hexDump += "\n";
		}
		int end = start + count;
		for (int i = start; i < end; i += 16) {
			String text = "";
			String hex = "";

			for (int j = 0; j < 16; j++) {
				if (j + i < end) {
					int val = dump[j + i];
					if (val < 0)
						val = (val + 256) & 0xFF;
					if (val < 16) {
						hex += "0" + Integer.toHexString(val) + " ";
					} else {
						hex += Integer.toHexString(val) + " ";
					}

					if (val >= 32 && val <= 127) {
						text += (char) val;
					} else {
						text += ".";
					}
				} else {
					hex += "   ";
					text += " ";
				}
			}
			hex += "  ";
			hex += text;
			hex += '\n';
			hexDump += hex;
		}
		return hexDump;
	}

	public static String toHexDump(String description, byte[] dump, int start, int count) {
		int[] temps = new int[dump.length];
		System.arraycopy(dump, 0, temps, 0, dump.length);
		return toHexDump(description, temps, start, count);
	}
}
