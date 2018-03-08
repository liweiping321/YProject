 
package  com.game.core.net.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.core.net.Request;
import com.game.core.net.session.NetSession;
import com.google.protobuf.GeneratedMessage;
import com.googlecode.protobuf.format.JsonFormat;
 
/**
 * 
 * @author lip.li
 *
 */
public abstract class RequestHandler {
	
	protected static final Logger LOG =LogManager.getLogger(RequestHandler.class);
	/** 是否能启用 **/
	private boolean enable = true;
	/**是否需要验证权限**/
	private boolean needAuth = false;
	/**是否单线程执行**/
	private boolean singleThread = true;
	/**消息间隔*/
	private int msgIntervalTime;
	
	private int msgCode;
	 
	/**
	 * true 处理器需要授权验证检查，false不需要进行授权验证,
	 */
	public boolean needAuth() {
		return needAuth;
	}
	
	public int getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(int msgCode) {
		this.msgCode = msgCode;
	}

	/**
	 * true 处理器使用游戏循环线程进行处理(顺序执行) false 处理器使用线程池中的线程进行处理
	 */
	public boolean singleThread() {
		return singleThread;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isNeedAuth() {
		return needAuth;
	}

	public void setNeedAuth(boolean needAuth) {
		this.needAuth = needAuth;
	}

	public boolean isSingleThread() {
		return singleThread;
	}

	public void setSingleThread(boolean singleThread) {
		this.singleThread = singleThread;
	}

	public int getMsgIntervalTime() {
		return msgIntervalTime;
	}

	public void setMsgIntervalTime(int msgIntervalTime) {
		this.msgIntervalTime = msgIntervalTime;
	}
	
 

	public abstract void handle(NetSession session, Request request)throws Exception;
	
 
	public void print(Request request){
		LOG.info("收到消息 code:{}",request.getCode());
	}
	
	public void print(Request request ,GeneratedMessage protoMsg){
		LOG.info("收到消息 code:{},content:{}",request.getCode(),JsonFormat.printToString(protoMsg));
	}
	
	
	 @Override
	public String toString() {
		return "RequestMsgHandler [needAuth=" + needAuth + ", singleThread="
				+ singleThread + ",HandlerName="+this.getClass().getName()+"]";
	}
}
