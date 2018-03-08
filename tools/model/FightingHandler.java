package ${package};

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.core.session.FightingPlayer;
import com.road.fire.core.handler.FightingPlayerRequestHandler;
import com.game.protobuf.MsgCode;
<#list imports as import>
import ${import};
</#list>

/**
 *  ${comment}
 *  @since  ${date}
 */
@Handler(code=MsgCode.${handlerName}Req)
public class ${handlerName}Handler extends FightingPlayerRequestHandler {

    @Override
    public void doHandle(FightingPlayer player, Request request) throws Exception {
        ${sentence}
      
    }
}