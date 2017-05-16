package cn.blmdz.hbs.hbs;

import java.io.FileNotFoundException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.blmdz.hbs.config.Components;
import cn.blmdz.hbs.config.ConfigYaml;
import cn.blmdz.hbs.config.HbsProperties;
import cn.blmdz.hbs.config.RenderConstants;
import cn.blmdz.hbs.file.FileLoaderHelper;
import cn.blmdz.hbs.services.SpringExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yongzongyang on 2017/5/11.
 */
@Slf4j
@Component
public class HandlebarsEngine {

    private Handlebars handlebars;
    
    @Autowired private SpringExecutor springExecutor;
    @Autowired private ConfigYaml configYaml;
	@Autowired private HbsProperties properties;
    @Autowired private FileLoaderHelper fileLoaderHelper;
    
    @PostConstruct
    public void init() {
        TemplateLoader templateLoader = new GreatTemplateLoader(fileLoaderHelper, properties.getRoot() + "/" + properties.getHome());
        this.handlebars = new Handlebars(templateLoader);
    }

    public void registerHelper(String name, Helper<?> helper) {
        this.handlebars.registerHelper(name, helper);
    }

    public void registerHelpers(Map<String, Helper<?>> helpers) {
        for (String key : helpers.keySet()) {
            this.handlebars.registerHelper(key, helpers.get(key));
        }
    }
    
    public String execPath(String path, Map<String, Object> context, boolean isComponent) throws FileNotFoundException {

        if (context == null) context = Maps.newHashMap();

        try {
            Template template;
            // 是否是组件
            if (isComponent) {
                String componentViewPath = "component:" + path + "/view";
                template = this.handlebars.compile(componentViewPath);
                if (template == null) {
                    log.error("failed to exec handlebars template:path={}", path);
                    return "";
                }
                
                context.put(RenderConstants.COMPONENT_PATH, path);
            } else {
                template = this.handlebars.compile(path);
            }
            return template.apply(context);
        } catch (FileNotFoundException e) {
			log.error("failed to execute handlebars\' template(path={}),cause:{} ", path, e.getMessage());
			if (!isComponent) {
                throw new FileNotFoundException("view not found: " + path);
			}
    	} catch (Exception e) {
			log.error("failed to execute handlebars\' template(path={}),cause:{} ", path, e);
        }
        return "";
    }
    
    public String execComponent(String path, Map<String, Object> context) throws FileNotFoundException {
    	Components c = configYaml.loadComponent(path);
    	
    	if (c !=null && !Strings.isNullOrEmpty(c.getUri()) && springExecutor.detectType(c.getUri())) {
    		Object obj = springExecutor.exec(c.getUri(), context);
    		context.put(RenderConstants.DATA, obj);
    	}
    	return execPath(path, context, true);
    }
}
