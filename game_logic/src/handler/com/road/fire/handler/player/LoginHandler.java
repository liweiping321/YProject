package com.road.fire.handler.player;

import java.util.Random;

import com.MsgCode;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.game.GameProto.PBLoginReq;
import com.game.protobuf.game.GameProto.PBLoginResp;
import com.road.fire.GameServer;
import com.road.fire.core.handler.GamePlayerRequestHandler;
import com.road.fire.core.session.GamePlayer;
import com.road.fire.player.OnlinePlayer;

/**
 *  用户登录请求 501
 *  @since  2017-03-09 01:10:03 
 */
@Handler(code=MsgCode.LoginReq,needAuth=false,singleThread=false)
public class LoginHandler extends GamePlayerRequestHandler {

    @Override
    public void doHandle(GamePlayer player, Request request) throws Exception {
        PBLoginReq req = request.parseParams(PBLoginReq.newBuilder());
        
        long playerId=Math.abs(new Random().nextLong());
        OnlinePlayer onlinePlayer=new OnlinePlayer();
        onlinePlayer.setGamePlayer(player);
        onlinePlayer.setPlayerId(playerId);
        onlinePlayer.setPlayerName(req.getPlayerName());


//        onlinePlayer.setMatchInfo(new PlayerMatchInfo());
//        onlinePlayer.getMatchInfo().setPlayerId(playerId);
//        onlinePlayer.getMatchInfo().setPlayerName(req.getPlayerName());

        player.setCurrentPlayer(onlinePlayer);
        player.setValidate(true);
        
        GameServer.getInstance().getLineManager().addPlayer(onlinePlayer);
       
        
        PBLoginResp.Builder loginResp= PBLoginResp.newBuilder();
        loginResp.setPlayerId(playerId);
        
        player.sendMsg(MsgCode.LoginResp, loginResp.build());
    }
}