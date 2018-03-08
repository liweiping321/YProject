package ${package};

import com.game.core.net.Request;
import com.game.core.net.handler.anno.Handler;
import com.road.fire.core.handler.HeroRequestHandler;
import com.road.fire.fighting.phy.living.Hero;
import com.game.protobuf.MsgCode;

<#list imports as import>
import ${import};
</#list>

/**
 *  ${comment}
 *  @since  ${date}
 */
@Handler(code=MsgCode.${handlerName}Req)
public class ${handlerName}Handler extends HeroRequestHandler {

    @Override
    public void doHandle(Hero hero, Request request) throws Exception {
        ${sentence}
    }
}