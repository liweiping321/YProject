/**
 * 
 */
package com.game.db.base.handle;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 属性映射处理器管理类
 * 
 * @author lip.li
 * @date 2017年1月5日
 */
public class HandlerManger {
	
	private static final Logger logger =LogManager.getLogger(HandlerManger.class);
 
	private final Map<Class<?>, Class<?>> handlerMap = new HashMap<Class<?>, Class<?>>();

	private final Map<Class<?>, IPropertyMapHandle<?>> simpleHandlerMap = new ConcurrentHashMap<Class<?>, IPropertyMapHandle<?>>();

	private HandlerManger() {
		init();
	}

	/**
	 * 
	 */
	private void init() {
		handlerMap.put(boolean.class, BooleanHandler.class);
		handlerMap.put(Boolean.class, BooleanHandler.class);

		handlerMap.put(byte.class, ByteHandler.class);
		handlerMap.put(Byte.class, ByteHandler.class);

		handlerMap.put(short.class, ShortHandler.class);
		handlerMap.put(Short.class, ShortHandler.class);

		handlerMap.put(int.class, IntegerHandler.class);
		handlerMap.put(Integer.class, IntegerHandler.class);

		handlerMap.put(long.class, LongHandler.class);
		handlerMap.put(Long.class, LongHandler.class);

		handlerMap.put(float.class, FloatHandler.class);
		handlerMap.put(Float.class, FloatHandler.class);

		handlerMap.put(double.class, DoubleHandler.class);
		handlerMap.put(Double.class, DoubleHandler.class);

		handlerMap.put(String.class, StringHandler.class);

		handlerMap.put(Date.class, DateHandler.class);

		handlerMap.put(java.sql.Date.class, SqlDateHandler.class);

		handlerMap.put(Time.class, TimeHandler.class);

		handlerMap.put(Timestamp.class, TimeStampHandler.class);

		handlerMap.put(List.class, ListHandler.class);
		handlerMap.put(Map.class, MapHandler.class);
		handlerMap.put(Set.class, SetHandler.class);

	}

	public IPropertyMapHandle<?> getHander(Class<?> clazz) {

		IPropertyMapHandle<?> handle = simpleHandlerMap.get(clazz);
		if (handle == null) {
			Class<?> handlerClazz = handlerMap.get(clazz);
			if (null != handlerClazz) {
				handle = createHandler(handlerClazz);
			} else {
				handlerClazz = BeanJsonHandler.class;
				handle = createHandler(handlerClazz, clazz);
			}

			if (handle != null) {
				simpleHandlerMap.put(clazz, handle);
			}
		}
		return handle;
	}

	public IPropertyMapHandle<?> createHandler(Class<?> handlerClazz, Object... vargs) {
		try {
			Class<?>[] parameterTypes = new Class[vargs.length];
			for (int i = 0; i < vargs.length; i++) {
				parameterTypes[i] = vargs[i].getClass();
			}
			return (IPropertyMapHandle<?>) handlerClazz.getConstructor(parameterTypes).newInstance(vargs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public IPropertyMapHandle<?> createHandler(Class<?> handlerClazz) {
		try {
			return (IPropertyMapHandle<?>) handlerClazz.newInstance();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private static final HandlerManger instance = new HandlerManger();

	public static HandlerManger getInstance() {
		return instance;
	}

}
