package cn.blmdz.service.event.listener;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cn.blmdz.service.event.LoginEvent;
import cn.blmdz.service.event.LoginoutEvent;
import cn.blmdz.service.event.UserEvent;

@Component
public class UserEventListener {
	
	@Autowired
    private final EventBus eventBus;

    public UserEventListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @PostConstruct
    public void init() {
        eventBus.register(this);
    }

    @Subscribe
    public void onUserEvent(UserEvent event) {
    	System.out.println("UserEvent");
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event) {
    	System.out.println("LoginEvent");
    }
    
    @Subscribe
    public void onLoginoutEvent(LoginoutEvent event) {
    	System.out.println("LoginoutEvent");
    }
}