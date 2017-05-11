package cn.blmdz.hbs.hbs;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class HelperRegister {
   @Autowired
   private HandlebarsEngine handlebarsEngine;
   @Autowired
   private ApplicationContext applicationContext;

   @PostConstruct
   private void registerBuildInHelpers() {
      Map<String, AbstractHelpers> helpersMap = this.applicationContext.getBeansOfType(AbstractHelpers.class);

      for(AbstractHelpers helpers : helpersMap.values()) {
         this.handlebarsEngine.registerHelpers(helpers.getHelpers());
      }

   }
}
