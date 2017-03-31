package com.blmdz.spring.boot.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.google.common.collect.Lists;

@ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
@Getter
@Setter
public class MybatisProperties {
   public static final String MYBATIS_PREFIX = "mybatis";
   private String config;
   private String[] mapperLocations;
   private String typeAliasesPackage;
   private String typeHandlersPackage;
   private boolean checkConfigLocation = false;
   private ExecutorType executorType = ExecutorType.SIMPLE;

   public Resource[] resolveMapperLocations() {
      List<Resource> resources = Lists.newArrayList();
      if(this.mapperLocations != null) {
         for(String mapperLocation : mapperLocations) {
            try {
               Resource[] mappers = (new PathMatchingResourcePatternResolver()).getResources(mapperLocation);
               resources.addAll(Arrays.asList(mappers));
            } catch (IOException var8) {
               ;
            }
         }
      }

      Resource[] mapperLocations = new Resource[resources.size()];
      mapperLocations = (Resource[])resources.toArray(mapperLocations);
      return mapperLocations;
   }
}
