package com.road.fire.match;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.game.core.net.ClientConnector;
import com.game.core.net.server.ServerCodecFactory;
import com.game.utils.XMLUtils;
import com.road.fire.core.net.Logic2MatchMinaHandler;
import com.road.fire.entity.cfg.MatchServerCfg;
import com.road.fire.entity.cfg.MatchServerListCfg;

/**
 * 
 * @author lip.li
 *
 */
public class Logic2MatchClientMgr {
	
	private List<Logic2MatchClient> clients=new CopyOnWriteArrayList<Logic2MatchClient>();
 
	private static final Logic2MatchClientMgr instance=new Logic2MatchClientMgr();
	
	
	public void initClients(String fileName)throws Exception{
		
		MatchServerListCfg serverListCfg=XMLUtils.readXml(fileName, MatchServerListCfg.class);
		if(serverListCfg!=null){
			List<MatchServerCfg> matchServerCfgs=serverListCfg.getServers();
			for(MatchServerCfg matchServerCfg:matchServerCfgs){
				init(matchServerCfg);
			}
		}
		
		
	}
	
	private void init(MatchServerCfg matchServer) {
		Logic2MatchClient matchClient=	new Logic2MatchClient();
		
		ClientConnector clientConnector = new ClientConnector(
				matchServer.getIp(), matchServer.getPort(),
				new Logic2MatchMinaHandler(matchClient),
				new ServerCodecFactory());
		
		clientConnector.connectTo();
		
		matchClient.setConnector(clientConnector);
		
		clients.add(matchClient);

	}
	
	
	
	public static Logic2MatchClientMgr getInstance() {
		return instance;
	}

	public static void main(String[] args) throws Exception{
		Logic2MatchClientMgr.getInstance().initClients("E:\\YProject\\server\\trunk\\workspace\\game_logic\\config\\logic_math_list.xml");
	}
	
}
