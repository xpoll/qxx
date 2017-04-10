package cn.blmdz.hunt.engine.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.yaml.snakeyaml.Yaml;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.blmdz.hunt.engine.config.model.BackConfig;
import cn.blmdz.hunt.engine.config.model.BaseConfig;
import cn.blmdz.hunt.engine.config.model.Component;
import cn.blmdz.hunt.engine.config.model.FrontConfig;
import cn.blmdz.hunt.engine.config.model.ProtectedAuth;
import cn.blmdz.hunt.engine.config.model.Render.Layout;
import cn.blmdz.hunt.engine.config.model.Service;
import cn.blmdz.hunt.engine.config.model.WhiteAuth;
import cn.blmdz.hunt.engine.model.App;
import cn.blmdz.hunt.engine.utils.FileLoader;
import cn.blmdz.hunt.engine.utils.FileLoaderHelper;
import cn.blmdz.hunt.engine.utils.Yamls;

@SuppressWarnings("unchecked")
@org.springframework.stereotype.Component
public class ConfigParser {
   private static final Logger log = LoggerFactory.getLogger(ConfigParser.class);
   private static final Yaml YAML = Yamls.getInstance();
   private static final Pattern IMPORT_PATTERN = Pattern.compile("^#\\s*import\\s+(.+)$");
   @Autowired
   private FileLoaderHelper fileLoaderHelper;
   private final Map<String, BaseConfig> atomConfigCache = Maps.newHashMap();

   public <T extends BaseConfig> T parseConfig(App app, Class<T> configClass) throws IOException, InstantiationException, IllegalAccessException {
      String configFilePath;
      if(configClass == FrontConfig.class) {
         if(Strings.isNullOrEmpty(app.getAssetsHome())) {
            log.warn("app [{}] have no assetsHome", app.getKey());
            return null;
         }

         configFilePath = app.getAssetsHome() + "front_config.yaml";
      } else {
         if(Strings.isNullOrEmpty(app.getConfigPath())) {
            log.warn("app [{}] have no configPath", app.getKey());
            return null;
         }

         configFilePath = app.getConfigPath();
      }

      if(this.checkNeedReload(configFilePath)) {
    	  BaseConfig config = this.parseConfig0(app, configFilePath, configClass);
         if(config instanceof FrontConfig) {
            this.postFrontConfig(app, (FrontConfig)config);
         } else {
            this.postBackConfig(app, (BackConfig)config);
         }

         return (T) config;
      } else {
         return null;
      }
   }

   private boolean checkNeedReload(String configFilePath) {
      BaseConfig cachedConfig = (BaseConfig)this.atomConfigCache.get(configFilePath);
      if(cachedConfig == null) {
         return true;
      } else if(!this.fileLoaderHelper.load(configFilePath, cachedConfig.getSign()).isNotModified()) {
         return true;
      } else {
         for(String importFilePath : cachedConfig.getImported()) {
            if(this.checkNeedReload(importFilePath)) {
               return true;
            }
         }

         return false;
      }
   }

   
private <T extends BaseConfig> T parseConfig0(App app, String path, Class<T> configClass) throws IOException, IllegalAccessException, InstantiationException {
      BaseConfig config = (BaseConfig)this.atomConfigCache.get(path);
      FileLoader.Resp resp;
      if(config == null) {
         resp = this.fileLoaderHelper.load(path);
      } else {
         resp = this.fileLoaderHelper.load(path, config.getSign());
      }

      if(resp.isNotFound()) {
         log.warn("config not found for app [{}] and path [{}]", app.getKey(), path);
         return (T) config;
      } else {
         if(!resp.isNotModified()) {
            String configStr = resp.asString();
            config = (BaseConfig)configClass.newInstance();
            config.setLocation(path);
            config.setSign(resp.getSign());
            config.setLoadedAt(DateTime.now().toDate());
            this.atomConfigCache.put(path, config);
            BufferedReader bufferedReader = new BufferedReader(new StringReader(configStr));

            String line;
            while((line = bufferedReader.readLine()) != null) {
               if(!line.trim().equals("")) {
                  Matcher matcher = IMPORT_PATTERN.matcher(line);
                  if(!matcher.matches()) {
                     break;
                  }

                  String importFileName = matcher.group(1);
                  String importFilePath = this.resolvePath(path, importFileName + ".yaml");
                  config.getImported().add(importFilePath);
                  BaseConfig importConfig = this.parseConfig0(app, importFilePath, configClass);
                  if(importConfig != null) {
                     config.merge(importConfig);
                  }
               }
            }

            config.merge(YAML.loadAs(configStr, configClass));
            log.info("load app [{}] config success, config: {}", app, config);
         } else if(config != null) {
            for(String importFilePath : config.getImported()) {
            	BaseConfig importConfig = this.parseConfig0(app, importFilePath, configClass);
               config.merge(importConfig);
            }
         }

         return (T) config;
      }
   }

   private String resolvePath(String originPath, String relativePath) {
      String originFolderPath = originPath.substring(0, originPath.lastIndexOf("/") + 1);
      return originFolderPath + relativePath;
   }

   private void postBackConfig(App app, BackConfig backConfig) {
      backConfig.setApp(app.getKey());
      if(backConfig.getServices() != null) {
         for(Service service : backConfig.getServices().values()) {
            if(Strings.isNullOrEmpty(service.getApp())) {
               service.setApp(app.getKey());
            }
         }
      }

   }

   private void postFrontConfig(App app, FrontConfig frontConfig) {
      frontConfig.setApp(app.getKey());
      if(frontConfig.getComponents() != null) {
         frontConfig.getComponentCategoryListMap().clear();

         for(String path : frontConfig.getComponents().keySet()) {
            Component component = (Component)frontConfig.getComponents().get(path);
            component.setPath(path);
            List<Component> componentList = frontConfig.getComponentCategoryListMap().get(component.getCategory());
            if(componentList == null) {
               componentList = Lists.newArrayList();
               frontConfig.getComponentCategoryListMap().put(component.getCategory(), componentList);
            }

            componentList.add(component);
         }
      }

      if(frontConfig.getAuths() != null && frontConfig.getAuths().getWhiteList() != null) {
         for(WhiteAuth whiteAuth : frontConfig.getAuths().getWhiteList()) {
            whiteAuth.setRegexPattern(Pattern.compile("^" + whiteAuth.getPattern() + "$"));
         }
      }

      if(frontConfig.getAuths() != null && frontConfig.getAuths().getProtectedList() != null) {
         for(ProtectedAuth protectedAuth : frontConfig.getAuths().getProtectedList()) {
            protectedAuth.setRegexPattern(Pattern.compile("^" + protectedAuth.getPattern() + "$"));
         }
      }

      if(frontConfig.getRender() != null && frontConfig.getRender().getLayouts() != null) {
         for(String key : frontConfig.getRender().getLayouts().keySet()) {
            Layout layout = (Layout)frontConfig.getRender().getLayouts().get(key);
            layout.setKey(key);
            layout.setApp(app.getKey());
         }
      }

   }
}
