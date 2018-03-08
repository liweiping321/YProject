package com.road.fire.handler.logic;

import com.game.consts.GameConsts;
import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.MsgCode;
import com.game.protobuf.logic.LogicProto.PBLoginReq;
import com.game.protobuf.logic.LogicProto.PBLoginResp;
import com.game.utils.ProbabilityUtil;
import com.road.fire.cfg.provider.NameCfgProvider;
import com.road.fire.consts.PlayerStatus;
import com.road.fire.core.handler.FightingPlayerRequestHandler;
import com.road.fire.core.session.FightingPlayer;
import com.road.fire.entity.business.PlayerData;
import com.road.fire.fighting.util.PhyIncrId;
import com.road.fire.player.PlayerInfo;

/**
 *  用户登录请求 501
 *  @since  2017-05-04 05:42:19 
 */
@Handler(code=MsgCode.LoginReq, singleThread = false)
public class LoginHandler extends FightingPlayerRequestHandler {

    @Override
    public void doHandle(FightingPlayer player, Request request) throws Exception {
        PBLoginReq req = request.parseParams(PBLoginReq.newBuilder());
       
        long playerId=PhyIncrId.incrAndGetPhyId();
        
        PlayerData playerData=new PlayerData();
        playerData.setUserID(playerId);
        
        int sex=ProbabilityUtil.randomGetInt(GameConsts.SexMale, GameConsts.SexFemale);
        String roleName=NameCfgProvider.getInstance().createRoleName(sex);
        playerData.setNickname(roleName);
        
        PlayerInfo playerInfo=new PlayerInfo();
        playerInfo.setPlayer(player);
        playerInfo.setStatus(PlayerStatus.idle);
        playerInfo.setPlayerData(playerData);
        
        player.setCurrentPlayer(playerInfo);
        player.setValidate(true);
    
        PBLoginResp.Builder loginResp= PBLoginResp.newBuilder();
        loginResp.setPlayerId(playerId);
        
        
        player.sendMsg(MsgCode.LoginResp, loginResp.build());
    }
}