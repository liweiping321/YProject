package com.road.fire.core.http;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.game.http.entity.HttpRequestMsg;
import com.game.http.entity.HttpResponseMsg;
import com.game.http.entity.ResponseStatus;
import com.game.http.entity.UrlBean;
import com.game.utils.LogUtils;
import com.game.utils.StringUtil;

public class HttpDispatchHandler extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String target = request.getPathInfo();
		try {
			if (target.endsWith("/")) {
				target = target.substring(0, target.length() - 1);
			}
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			response.addHeader("Access-Control-Allow-Origin", "*");
			LogUtils.debug("请求路径：" + target);

			UrlBean urlBean = WebServer.requestMap.get(target);
			if (urlBean == null) {
				LogUtils.error("请求路径不存在：" + target);
				response.getWriter()
						.write(new HttpResponseMsg<>(ResponseStatus.FAIL, "请求路径不存在：" + target).toJsonString());
			} else {
				String param = request.getParameter("param");

				if (!StringUtil.isEmpty(param)) {

					HttpRequestMsg<?> requestMsg = JSON.parseObject(param, HttpRequestMsg.class);

					Object object = urlBean.getMethod().invoke(urlBean.getWebHandler(), requestMsg);
					if (object instanceof HttpResponseMsg) {

						response.getWriter().write(((HttpResponseMsg<?>) object).toJsonString());
					} else if (object != null) {
						response.getWriter().write(object.toString());
					}

				} else {
					response.getWriter().write(new HttpResponseMsg<>(ResponseStatus.FAIL, "请求参数为空!").toJsonString());
				}

			}
		} catch (Exception e) {
			StringBuffer buffer = new StringBuffer();
			Map<String, String[]> map = request.getParameterMap();
			for (String paraName : map.keySet()) {
				buffer.append(paraName).append("=").append(map.get(paraName)[0]).append(",");
			}
			LogUtils.error("Http处理异常，URL：" + target + ",参数：" + buffer.toString(), e);
			target = e.getCause().getMessage();
			response.getWriter().write(new HttpResponseMsg<>(ResponseStatus.FAIL, target).toJsonString());
		}
		response.getWriter().flush();
	}
}
