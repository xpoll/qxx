package cn.blmdz.hbs.hbs;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.collect.Maps;

import cn.blmdz.hbs.file.FileLoaderHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by yongzongyang on 2017/5/11.
 */
@Slf4j
@Component
public class HandlebarsEngine implements ApplicationContextAware {

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
//                if (this.caches == null) {
//                } else {
//                    template = this.caches.getUnchecked(this.getAppPathKey(componentViewPath)).orNull();
//                }

                if (template == null) {
                    log.error("failed to exec handlebars template:path={}", path);
                    return "";
                }

                context.put("_COMP_PATH_", path);
            } else {
                    template = this.handlebars.compile(path);
//                if (this.caches == null) {
//                } else {
//                    template = this.caches.getUnchecked(this.getAppPathKey(path)).orNull();
//                }
                if (template == null) {
                    throw new FileNotFoundException("view not found: " + path);
                }
            }
            return template.apply(context);
        } catch (IOException e) {
            log.error("failed to exec handlebars template:path={}", path);
        }
        return "";
    }
}
