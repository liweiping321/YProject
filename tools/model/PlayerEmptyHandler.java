package com.road.fire.handler.friend;

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.core.handler.OnlinePlayerRequestHandler;
import com.MsgCode;
import com.road.fire.player.OnlinePlayer;

/**
 *  ${comment}
 *  @since  ${date}
 */
@Handler(code=MsgCode.${handlerName}Req)
public class ${handlerName}Handler  extends OnlinePlayerRequestHandler {

    @Override
    public void doHandle(OnlinePlayer role, Request request) throws Exception {
    	  print(request);

    }

}