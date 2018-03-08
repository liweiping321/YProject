package com.game.core.net.server;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class ServerCodecFactory implements ProtocolCodecFactory {

	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;

	public ServerCodecFactory() {
		this.encoder = new ServerMessageEncoder();
		this.decoder = new ServerMessageDecoder();
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return this.decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return this.encoder;
	}
}
