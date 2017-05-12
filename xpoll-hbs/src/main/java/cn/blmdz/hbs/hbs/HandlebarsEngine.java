package cn.blmdz.hbs.hbs;

import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.collect.Maps;

import cn.blmdz.hbs.file.FileLoaderHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yongzongyang on 2017/5/11.
 */
@Slf4j
@Component
public class HandlebarsEngine implements ApplicationContextAware {

	@Getter
	@Setter
    private ApplicationContext context;

    private Handlebars handlebars;

    @Autowired
    public HandlebarsEngine(FileLoaderHelper fileLoaderHelper) {
        TemplateLoader templateLoader = new GreatTemplateLoader(fileLoaderHelper);
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
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public String execPath(String path, Map<String, Object> context, boolean isComponent) throws FileNotFoundException {

        if (context == null) context = Maps.newHashMap();

        try {
            Template template;
            // 是否是组件
            if (isComponent) {
                String componentViewPath = "component:" + path; // + "/view";
                template = this.handlebars.compile(componentViewPath);
                if (template == null) {
                    log.error("failed to exec handlebars template:path={}", path);
                    return "";
                }
                context.put("_COMP_PATH_", path);
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
}
