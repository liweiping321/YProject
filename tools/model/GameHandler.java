package ${package};

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.game.core.net.session.GamePlayer;
import com.road.fire.core.handler.GamePlayerRequestHandler;
import com.MsgCode;
<#list imports as import>
import ${import};
</#list>

/**
 *  ${comment}
 *  @since  ${date}
 */
@Handler(code=MsgCode.${handlerName}Req)
public class ${handlerName}Handler extends GamePlayerRequestHandler {

    @Override
    public void doHandle(GamePlayer player, Request request) throws Exception {
        ${sentence}
      
    }
}