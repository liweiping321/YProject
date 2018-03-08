package com.road.fire.handler.game;

import com.MsgCode;
import com.game.consts.GameConsts;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.game.GameProto;
import com.game.protobuf.game.GameProto.PBStartGameReq;
import com.game.utils.LogUtils;
import com.road.fire.core.handler.OnlinePlayerRequestHandler;
import com.road.fire.player.OnlinePlayer;

/**
 * 请求加入游戏战斗 601
 * 
 * @since 2017-03-09 02:26:39
 */
@Handler(code = MsgCode.StartGameReq)
public class StartGameHandler extends OnlinePlayerRequestHandler {

	@Override
	public void doHandle(OnlinePlayer player, Request request) throws Exception {
		PBStartGameReq req = request.parseParams(PBStartGameReq.newBuilder());

		int heroId = req.getHeroId();
		int mapId = req.getMapId();

		if (heroId == 0) {
			LogUtils.error("not selected hero!" + player.getPlayerId());
			heroId = 1;
		}
		LogUtils.error(player.getPlayerId() + " selected heroId:" + heroId);

//		if (player.getMatchInfo().getStatue() != PlayerMatchInfo.Match.NO_MATCH ) {
//			LogUtils.error("in game!");
//			return;
//		}
//		player.getMatchInfo().setHeroId(heroId);
//		player.getMatchInfo().setMapId(mapId);
//
//		// 添加到等待队列
//		RoomInfoBean room = player.getMatchInfo().getRoomInfo();
//		if (room == null) {
//			room = MatchFactory.createRoom(player.getMatchInfo());
//		}
//		MatchFactory.addToWait(room);


		GameProto.PBReadyMatchResp.Builder matchResp = GameProto.PBReadyMatchResp.newBuilder();
		matchResp.setMatchTime(GameConsts.MATCH_TIME);
		player.sendMsg(MsgCode.StartGameResp, matchResp.build());

		LogUtils.error(player.getPlayerName() + " join game! mapId:" + mapId);

	}
}