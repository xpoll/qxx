package cn.blmdz.test.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.eventbus.EventBus;

import cn.blmdz.model.User;
import cn.blmdz.service.event.LoginEvent;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserEventTest {

	@Autowired
	private EventBus eventBus;
	
	@Test
	public void loginEvent() {
		eventBus.post(new LoginEvent(new User()));
	}
}
