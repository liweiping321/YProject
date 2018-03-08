package com.game.core.net.server;

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.core.net.CommonMessage;
import com.game.core.net.Request;
import com.game.core.net.SessionKey;

public class ServerMessageDecoder extends CumulativeProtocolDecoder {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerMessageDecoder.class);

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		if (in.remaining() < 4) {
			// 剩余不足4字节，不足以解析数据包头，暂不处理
			return false;
		}

		int header = 0;
		int packetLength = 0;
		
		byte[] validateData = new byte[4];
		in.get(validateData);
		ByteBuffer byteBuffer = ByteBuffer.wrap(validateData);
		// 解密完毕，按照明文读取
		header = byteBuffer.getShort();
		packetLength = byteBuffer.getShort();
		
		// 预解密长度信息成功，回溯位置
		in.position(in.position() - 4);

		if (header != CommonMessage.HEADER) {
			session.closeNow();
			session.setAttribute(SessionKey.ERROR, "后端强制关闭");
			LOGGER.info("数据包头不匹配 hearder : " + header);
			return false;
		}
		int headSize = CommonMessage.getHeadSize(false);
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
		Request packet = CommonMessage.build(data, false);
		if (packet != null) {
			out.write(packet);
		}
		return true;
	}
}
