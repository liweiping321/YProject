package com.road.fire.player;

import com.game.player.obj.BaseOnlinePlayer;
import com.game.protobuf.match.PlayerMatchInfo;

public class OnlinePlayer extends BaseOnlinePlayer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7169423641785907006L;

	private PlayerMatchInfo matchInfo;

	@Override
	public void initModules() {

	}

	public PlayerMatchInfo getMatchInfo()
	{
		return matchInfo;
	}

	public void setMatchInfo(PlayerMatchInfo matchInfo)
	{
		this.matchInfo = matchInfo;
	}
}
