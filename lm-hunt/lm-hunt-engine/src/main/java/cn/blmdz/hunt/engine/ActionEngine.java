package cn.blmdz.hunt.engine;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import cn.blmdz.hunt.protocol.Action;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ActionEngine {

	private final Map<Class<? extends Action>, ActionInvoker<? extends Action>> actions = Maps.newHashMap();

	public <T extends Action> ActionEngine register(Class<T> clazz, ActionInvoker<T> action) {
		Preconditions.checkNotNull(clazz, "action name can not be null");
		Preconditions.checkNotNull(action, "action can not be null");

		ActionInvoker<?> oldAction = this.actions.put(clazz, action);
		if (oldAction != null) {
			log.warn("action {} has been replaced by {}", clazz.getName(), oldAction);
		}
		return this;
	}

	public <T extends Action> ActionEngine registerOnce(Class<T> clazz, ActionInvoker<T> action) {
		Preconditions.checkNotNull(clazz, "action name can not be null");
		Preconditions.checkNotNull(action, "action can not be null");

		if (this.actions.get(clazz) != null) {
			log.warn("action {} can only be registered for one time ", clazz.getName());
			return this;
		}

		return register(clazz, action);
	}

	public <T extends Action> boolean handler(T action, HttpServletRequest request, HttpServletResponse response) {
		if (action == null) {
			log.warn("action is null, ignore this");
			return false;
		}

		@SuppressWarnings("unchecked")
		ActionInvoker<T> invoker = (ActionInvoker<T>) this.actions.get(action.getClass());
		if (invoker == null) {
			log.error("action invoker for class {} is null", action.getClass().getName());
			throw new NullPointerException("action invoker for class " + action.getClass().getName() + " is null");
		}
		try {
			return invoker.invoke(action, request, response);
		} catch (Exception e) {
			log.error("error when invoke ActionInvoker {} for action {}", invoker.getClass().getName(), action);
			invoker.onException(action, request, response, e);
		}
		return false;
	}
}