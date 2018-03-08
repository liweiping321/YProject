package com.game.core.net;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class StrictCodecFactory implements ProtocolCodecFactory {

	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;

	public StrictCodecFactory(){
		this.encoder = new StrictMessageEncoder();
		this.decoder = new StrictMessageDecoder();
	}
	
	public StrictCodecFactory(ProtocolEncoder encoder, ProtocolDecoder decoder) {
		this.encoder = encoder;
		this.decoder = decoder;
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
