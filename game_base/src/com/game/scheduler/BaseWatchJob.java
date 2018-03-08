package com.game.scheduler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import com.game.utils.LogUtils;

/**
 * 观望者任务基础类
 * @author jason.lin
 *
 */
public abstract class BaseWatchJob extends Thread{
	private WatchService watchService;
	private String configPath = null;
	
	public BaseWatchJob(String loadPath){
		try {
			this.configPath = loadPath;
			Path path = Paths.get(configPath);
			watchService = path.getFileSystem().newWatchService();
			path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (Exception e) {
			LogUtils.error("BaseReloadJob", e);
		}
	}
	
	/**
	 * 开始监听文件改动
	 */
	public void run(){
		while(true){
			try {
				WatchKey watchKey = watchService.take();  
				Thread.sleep(1000);
				if(watchKey != null) {
				    List<WatchEvent<?>> list = watchKey.pollEvents();
				    watchKey.reset(); 
				    for(WatchEvent<?> watch : list){
				    	if (watch.kind() == StandardWatchEventKinds.OVERFLOW) {
				           continue;
			            }
				    	
				    	// 变化的文件
				    	Path path = (Path) watch.context();
				    	
				    	// 拼接变化的文件路径
				    	StringBuilder filePath = new StringBuilder(configPath);
				    	if(!configPath.endsWith("\\") && !configPath.endsWith("/")){
				    		filePath.append("/");
				    	}
				    	filePath.append(path.toString());
				    	
				    	// 执行文件变化
				    	excute(filePath.toString());
						LogUtils.error("reload success! " + path.toString());
				    }
				}
			} catch (Exception e) {
				LogUtils.error("csv reload start errro!", e);
			} 
		}
	}

	/**
	 * 文件变化，执行相应操作
	 * @param path 绝对路径
	 */
	public abstract void excute(String path);
	
}
