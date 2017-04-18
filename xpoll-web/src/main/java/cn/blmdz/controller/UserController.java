package cn.blmdz.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.eventbus.EventBus;

import cn.blmdz.common.exception.JsonResponseException;
import cn.blmdz.entity.QxxUser;
import cn.blmdz.exception.GlobalException;
import cn.blmdz.service.UserService;
import cn.blmdz.service.event.LoginEvent;
import cn.blmdz.util.UserMaker;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EventBus eventBus;
	
	@RequestMapping(value="/login", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> login(
			@RequestParam("owner") String owner,
			@RequestParam("pwd") String pwd,
			@RequestParam("vcode") String vcode,
            @RequestParam(value = "target", required = false) String target,
            HttpServletRequest request, HttpServletResponse response) {
		
		//校验
		QxxUser user = null;
		try {
			user = userService.login(owner, pwd);
		} catch (GlobalException e) {
			throw new JsonResponseException(500, e.getMessage());
		} catch (Exception e) {
			throw new JsonResponseException(500, "系统异常");
		}

        request.getSession().setAttribute("user_id", user.getId());

        eventBus.post(new LoginEvent(UserMaker.from(user)));
        
        target = !StringUtils.hasText(target)?"/":target;
        return Maps.<String, Object>newHashMap("redirect", target);
	}
}
