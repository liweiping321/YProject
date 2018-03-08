package com.road.fire.handler.match;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.protobuf.MsgCode;
import com.game.protobuf.match.MatchProto.PBStartMatchReq;
import com.road.fire.cfg.provider.FightingTypeCfgProvider;
import com.road.fire.cfg.provider.HeroCfgProvider;
import com.road.fire.cfg.provider.WeaponCfgProvider;
import com.road.fire.consts.PlayerStatus;
import com.road.fire.core.handler.OnlinePlayerRequestHandler;
import com.road.fire.entity.cfg.FightingTypeCfg;
import com.road.fire.entity.cfg.HeroCfg;
import com.road.fire.entity.cfg.WeaponCfg;
import com.road.fire.match.MatchPoolFactory;
import com.road.fire.match.PlayerMatchInfo;
import com.road.fire.player.PlayerInfo;

/**
 *  请求加入游戏战斗 601
 *  @since  2017-05-04 05:40:11 
 */
@Handler(code=MsgCode.StartMatchReq,singleThread=false)
public class StartMatchHandler extends OnlinePlayerRequestHandler {

    @Override
    public void doHandle(PlayerInfo playerInfo, Request request) throws Exception {
        PBStartMatchReq req = request.parseParams(PBStartMatchReq.newBuilder());
        
        if(playerInfo.getStatus()!=PlayerStatus.idle){
        	LOG.error("can't match ,Player is in {} status",playerInfo.getStatus());
        	return ;
        }
        
        HeroCfg heroCfg=HeroCfgProvider.getInstance().getConfigVoByKey(req.getHeroId());
        if(heroCfg==null){
        	LOG.error("can't find HeroCfg ,heroId:{}",req.getHeroId());
        	return;
        }
        
		FightingTypeCfg fightingTypeCfg = FightingTypeCfgProvider.getInstance()
				.getConfigVoByKey(req.getFightingType());
		if(fightingTypeCfg==null){
			LOG.error("can't find FightingTypeCfg ,fightingType:{}",req.getFightingType());
			return ;
		}

		WeaponCfg weaponCfg = WeaponCfgProvider.getInstance().getConfigVoByKey(Integer.parseInt(heroCfg.getWeaponId()));
		if (weaponCfg == null)
		{
			LOG.error("can't find WeaponCfg ,weaponId:{}",heroCfg.getWeaponId());
			return ;
		}


		PlayerMatchInfo playerMatchInfo=new PlayerMatchInfo(playerInfo, fightingTypeCfg, 500, heroCfg, weaponCfg);
		
		MatchPoolFactory.getInstance().addMatcher(playerMatchInfo);
		
        
    }
}