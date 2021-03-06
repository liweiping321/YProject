package ${package};

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.core.handler.OnlinePlayerRequestHandler;
import com.road.fire.player.PlayerInfo;
import com.game.protobuf.MsgCode;

<#list imports as import>
import ${import};
</#list>

/**
 *  ${comment}
 *  @since  ${date}
 */
@Handler(code=MsgCode.${handlerName}Req)
public class ${handlerName}Handler extends OnlinePlayerRequestHandler {

    @Override
    public void doHandle(PlayerInfo playerInfo, Request request) throws Exception {
        ${sentence}
    }
}