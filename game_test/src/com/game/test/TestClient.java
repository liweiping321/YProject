package com.game.test;

import com.game.core.net.ClientConnector;
import com.game.core.net.Request;
import com.game.core.net.StrictCodecFactory;
import com.game.executor.GameThreadFactory;
import com.game.executor.pool.GameScheduledThreadPoolExecutor;
import com.game.protobuf.MsgCode;
import com.game.protobuf.common.CommonProto.PBPingReq;
import com.game.protobuf.logic.LogicProto;
import com.game.protobuf.match.MatchProto;
import com.game.test.config.TestConfig;
import com.game.utils.PropertyConfigUtil;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IoSession;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class TestClient
{

	// public GamePlayer gamePlayer;
	final GameScheduledThreadPoolExecutor executor =
			new GameScheduledThreadPoolExecutor(1, new GameThreadFactory("testClient"));

	public IoSession session;

	public static void main(String[] args)throws Exception{
		PropertyConfigUtil.init("com.game.test.config");
		
		testMatch();
	}

	public static TestClient connectToServer(final String playerName, final String account)
	{
		
		final TestClient client = new TestClient();
		new ClientConnector(TestConfig.ip, TestConfig.socketPort, new TestHandler(), new StrictCodecFactory()).connectTo()
				.addListener(new IoFutureListener()
				{
					@Override
					public void operationComplete(IoFuture future)
					{
						client.session = future.getSession();
						clientMap.put(future.getSession(), client);
						client.playerName = playerName;
						client.account = account;
						new Thread(new Runnable()
						{
							@Override
							public void run()
							{
								client.sendLoginReq();
							}
						}).start();

						new Thread(new Runnable()
						{
							@Override
							public void run()
							{
								try
								{
									sleep(2000);
									System.out.println("发送匹配");
									client.sendMatchReq();
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						}).start();
					}
				});
		client.executor.scheduleWithFixedDelay(new Runnable()
		{

			public void run()
			{
				client.sendPingReq();
			}
		}, TestConfig.interval, TestConfig.interval, TimeUnit.MILLISECONDS);
		return client;
	}

	public void sendPingReq()
	{
		PBPingReq.Builder pingReq = PBPingReq.newBuilder();
		pingReq.setClientTick(System.currentTimeMillis());
		pingReq.setDelay(1000);
		Request request = Request.valueOf((short) MsgCode.PingReq, pingReq.build());
		session.write(request);
	}

	public void receivePingResp(Request request) throws Exception
	{
		// PBPingResp pingResp = request.parseParams(PBPingResp.newBuilder());
		// System.err.println(pingResp.toString());
	}

	public void sendLoginReq()
	{

		LogicProto.PBLoginReq.Builder loginReq = LogicProto.PBLoginReq.newBuilder();
		loginReq.setPlayerName(playerName);
		loginReq.setAccount(account);

		Request request = Request.valueOf((short) MsgCode.LoginReq, loginReq.build());
		session.write(request);
	}

	public void receiveLoginResp(Request request) throws Exception
	{
		LogicProto.PBLoginResp loginResp = request.parseParams(LogicProto.PBLoginResp.newBuilder());
		System.err.println(loginResp.toString());
		playerId = loginResp.getPlayerId();
	}


	public void sendMatchReq() throws Exception
	{
		MatchProto.PBStartMatchReq.Builder matchReq = MatchProto.PBStartMatchReq.newBuilder();
		matchReq.setFightingType(4);
		matchReq.setHeroId(1);
		session.write(Request.valueOf((short) MsgCode.StartMatchReq, matchReq.build()));
	}

	public void receiveMatchResp(Request request) throws Exception
	{
		MatchProto.PBStartMatchResp matchResp = request.parseParams(MatchProto.PBStartMatchResp.newBuilder());
		System.out.println("match time = " + matchResp.getMatchTime());
	}

	public static HashMap<IoSession, TestClient> clientMap = new HashMap<>();
	private long playerId;
	private String playerName;
	private String account;

	private static void testMatch()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				connectToServer("player1", "p1");
			}
		}).start();

		// new Thread(new Runnable()
		// {
		// 	@Override
		// 	public void run()
		// 	{
		// 		connectToServer("player2", "p2");
		// 	}
		// }).start();

	}

}
