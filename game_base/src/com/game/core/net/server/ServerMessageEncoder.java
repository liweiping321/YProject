package com.game.core.net.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.core.net.CommonMessage;

public class ServerMessageEncoder extends ProtocolEncoderAdapter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerMessageEncoder.class);

	public ServerMessageEncoder() {

	}

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		try {
			CommonMessage msg = (CommonMessage) message;
			byte[] plainText = msg.toByteBuffer(false).array();
			int length = plainText.length;
			IoBuffer cipherBuffer = IoBuffer.allocate(length);
			cipherBuffer.put(plainText);
			out.write(cipherBuffer.flip());
			out.flush();
		} catch (Exception ex) {
			LOGGER.error("catch error for encoding packet:", ex);
			throw ex;
		}
	}

}
