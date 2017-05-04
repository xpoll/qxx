package cn.blmdz.reflection;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyTest {

	// 路由[Router]
	private static ProxyAction<?> router(RegistryCenter registry, String bean) {
		ProxyAction<?> value = registry.getBean(bean);
		System.out.println(value.getClass());
		ProxyAction<?> proxy = (ProxyAction<?>) Proxy.newProxyInstance(
				value.getClass().getClassLoader(),
				value.getClass().getInterfaces(),
				new ProxyDemo(value));
		return proxy;
	}

	// Main
	public static void main(String[] args) {
		RegistryCenter registry = new RegistryCenter();
		router(registry, "actiondone1").execute(null);
		router(registry, "actiondone2").execute(null);
	}
}

/** 代理 **/
class ProxyDemo implements InvocationHandler {

	private ProxyAction<?> target;
	
	public ProxyDemo(ProxyAction<?> action) {
		this.target = action;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("------代理之前");
		System.out.println(method);
		for (Object obj : args) {
			System.out.println(obj);
		}
		Object obj = method.invoke(target, args);
		System.out.println("------代理之后");
		return obj;
	}
	
}
/** 注册中心 **/
class RegistryCenter {
	private Map<String, ProxyAction<?>> map;
	public RegistryCenter() {
		map = new HashMap<>();
		map.put(ActionDone1.class.getSimpleName().toLowerCase(), new ActionDone1());
		map.put(ActionDone2.class.getSimpleName().toLowerCase(), new ActionDone2());
	}
	public ProxyAction<?> getBean(String name) {
		return map.get(name);
	}
}
/** 实现 **/
class ActionDone1 implements ProxyAction<Boolean> {
	@Override
	public Boolean execute (Map<String, Serializable> paramMap) {
		System.out.println("ActionDone1 do something");
		return true;
	}
}
class ActionDone2 implements ProxyAction<Boolean> {
	@Override
	public Boolean execute (Map<String, Serializable> paramMap) {
		System.out.println("ActionDone2 do something");
		return true;
	}
}
/** 接口-被代理的对象 **/
interface ProxyAction<T> {
	T execute(Map<String, Serializable> paramMap);
}