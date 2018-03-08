package com.game.generate.proto;

import com.game.type.Configuration;

/**
 * @author jianpeng.zhang
 * @since 2017/3/7.
 */
@Configuration(config = "config/handler_config.properties")
public class HandlerConfig {

	public static String outPutPath;

	public static String protoFilePath;

	public static String handlerOutputPath;

	public static String msgCodePkgPath;

	public static String getOutPutPath() {
		return outPutPath;
	}

	public static void setOutPutPath(String outPutPath) {
		HandlerConfig.outPutPath = outPutPath;
	}

	public static String getProtoFilePath() {
		return protoFilePath;
	}

	public static void setProtoFilePath(String protoFilePath) {
		HandlerConfig.protoFilePath = protoFilePath;
	}

	public static String getHandlerOutputPath() {
		return handlerOutputPath;
	}

	public static void setHandlerOutputPath(String handlerOutputPath) {
		HandlerConfig.handlerOutputPath = handlerOutputPath;
	}

	public static String getMsgCodePkgPath() {
		return msgCodePkgPath;
	}

	public static void setMsgCodePkgPath(String msgCodePkgPath) {
		HandlerConfig.msgCodePkgPath = msgCodePkgPath;
	}
}
