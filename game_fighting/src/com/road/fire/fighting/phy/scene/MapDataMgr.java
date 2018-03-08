package com.road.fire.fighting.phy.scene;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class MapDataMgr {
	
	private static final Logger LOG=LogManager.getLogger(MapDataMgr.class);
	
	private Map<Integer,MapData> mapDatas=new HashMap<Integer,MapData>();
	
	private MapDataMgr(){	
	}
	
	public void init(String mapPath)throws Exception{
	 
		Collection<File> files=FileUtils.listFiles(new File(mapPath),new String[]{"map"}, true);
		for(File file:files){
		
		  List<String> lines=FileUtils.readLines(file);
		
		  MapData mapData=  JSON.parseObject(lines.get(0), MapData.class);
		  byte[][] blockTiles=new byte[mapData.getHeight()][mapData.getWidth()];
		  
		  int index=0;
		  for(int i=lines.size()-1;i>=1;i--){
			  String line=lines.get(i);
			  String[] tiles=line.split(",");
			  for(int j=0;j<tiles.length;j++){
				  blockTiles[index][j]=Byte.parseByte(tiles[j]);
			  }
			  index++;
		  }
		  
		  mapData.setBlockTiles(blockTiles);
		  
		  mapData.initAllPoint();
		  
		  int mapId=getMapId(file);
		  mapDatas.put(mapId, mapData);
 
		}
	}
	
	
	
	public int getMapId(File file){
		String mapId=file.getName().split("\\.")[0];
		
		return Integer.parseInt(mapId);
	}
	
	public MapData getMapData(int mapId){
		
		try {
			return (MapData)mapDatas.get(mapId).clone();
		} catch (CloneNotSupportedException e) {
			LOG.error(e.getMessage(),e);
		}
		return null;
	}
	
    private final static MapDataMgr instance=new MapDataMgr();


	public static MapDataMgr getInstance() {
		return instance;
	}
    
}
