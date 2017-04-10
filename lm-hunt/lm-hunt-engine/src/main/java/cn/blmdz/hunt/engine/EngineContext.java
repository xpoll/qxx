package cn.blmdz.hunt.engine;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(excludeFilters = {@Filter({Configuration.class})})
public class EngineContext {
}