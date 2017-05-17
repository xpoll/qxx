package cn.blmdz.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.eventbus.EventBus;

import cn.blmdz.common.exception.JsonResponseException;
import cn.blmdz.common.model.BaseUser;
import cn.blmdz.common.model.Response;
import cn.blmdz.common.util.UserUtil;
import cn.blmdz.hbs.exception.GlobalException;
import cn.blmdz.web.entity.QxxUser;
import cn.blmdz.web.service.UserService;
import cn.blmdz.web.service.event.LoginEvent;
import cn.blmdz.web.util.UserMaker;

@RestController
@RequestMapping("/api/user")
public class Users {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EventBus eventBus;
	

    @RequestMapping("")
    public Response<BaseUser> getLoginUser() {
        return Response.ok(UserUtil.getCurrentUser());
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<String> login(
			@RequestParam("owner") String owner,
			@RequestParam("pwd") String pwd,
			@RequestParam("vcode") String vcode,
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
        
        return Response.ok();
	}
}
