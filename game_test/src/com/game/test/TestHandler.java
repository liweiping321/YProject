package com.game.test;

import com.game.core.net.Request;
import com.game.protobuf.MsgCode;
import com.game.protobuf.fighting.FightingProto;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.Date;

public class TestHandler extends IoHandlerAdapter {

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		Request request = (Request) message;
		System.out.println("收到消息：" + request.getCode());
		if (TestClient.clientMap.containsKey(session)) {
			switch (request.getCode()) {
			case MsgCode.PingResp:
				TestClient.clientMap.get(session).receivePingResp(request);
				break;
			case MsgCode.LoginResp:
				TestClient.clientMap.get(session).receiveLoginResp(request);
				break;
			case MsgCode.StartMatchResp:
				TestClient.clientMap.get(session).receiveMatchResp(request);
				break;
			case MsgCode.GameLoadingResp:
				FightingProto.PBLoadingProcessReq.Builder builder = FightingProto.PBLoadingProcessReq.newBuilder();
				builder.setProcess(100);
				session.write(Request.valueOf((short) MsgCode.LoadingProcessReq, builder.build()));
				break;
			case MsgCode.LoadingProcessResp:
				System.out.println("success");
				break;
			case MsgCode.GameStartResp:
				System.out.println(new Date().toString());
				break;
			case MsgCode.MoveStopResp:
			case MsgCode.BuffUpdateResp:
			case MsgCode.BuffRemoveResp:
				// FightingProto.PBSkillUseReq.Builder skillUseBuilder = FightingProto.PBSkillUseReq.newBuilder();
				// skillUseBuilder.setIndex(0);
				// skillUseBuilder.setSkillAngle(-1);
				// skillUseBuilder.setStartX(510);
				// skillUseBuilder.setStartY(-460);
				// skillUseBuilder.setTargetX(0);
				// skillUseBuilder.setTargetY(0);
				// session.write(Request.valueOf((short) MsgCode.SkillUseReq, skillUseBuilder.build()));
				break;
			default:
				System.exit(0);
				// System.out.println(request.getCode());
				break;
			}
		}
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// client.session1 = session;
		TestClient.clientMap.put(session, null);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("连接异常");
		cause.printStackTrace();
	}
}
