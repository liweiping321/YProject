package com.road.fire.entity.cfg;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root(name="servers")
public class MatchServerListCfg {
	@ElementList(name="server",inline=true,type=MatchServerCfg.class)
	private List<MatchServerCfg>  servers;
	
	private MatchServerListCfg(){
		servers=new ArrayList<MatchServerCfg>();
	}

	public List<MatchServerCfg> getServers() {
		return servers;
	}

	public void setServers(List<MatchServerCfg> servers) {
		this.servers = servers;
	}

	 

	 
	 
	
	
}
