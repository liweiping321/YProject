package ${package};

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.player.OnlinePlayer;
import com.road.fire.core.handler.OnlinePlayerRequestHandler;
import com.MsgCode;
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
    public void doHandle(OnlinePlayer player, Request request) throws Exception {
        ${sentence}
    }
}