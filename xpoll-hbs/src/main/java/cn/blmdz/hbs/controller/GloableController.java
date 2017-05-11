package cn.blmdz.hbs.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.blmdz.hbs.request.AssetsHandler;
import cn.blmdz.hbs.request.ViewRender;
import cn.blmdz.hbs.util.Domains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by yongzongyang on 2017/5/11.
 */
@Controller
public class GloableController {
    @Autowired
    private AssetsHandler assetsHandler;
    @Autowired
    private ViewRender viewRender;

    @RequestMapping
    public void controller(HttpServletRequest request,
                           HttpServletResponse response,
                           Map<String, Object> content) {
        String domain = Domains.getDomainFromRequest(request);
        String path = request.getRequestURI().substring(request.getContextPath().length() + 1);
        System.out.println(domain + ": " + path);
        
        if (Strings.isNullOrEmpty(path)) {
        	path = "index";
        }

        String method = request.getMethod().toUpperCase();
        System.out.println(method);

        List<Map<String, String>> list = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("a", "Lily");
        map.put("age", "23");
        list.add(map);
        map = Maps.newHashMap();
        map.put("a", "Lily");
        map.put("age", "23");
        list.add(map);
        content.put("data", list);


        boolean isAssets = assetsHandler.handle(path, response);
        if (!isAssets) {
            viewRender.view(domain, path, request, response, content);
        }
    }
}
