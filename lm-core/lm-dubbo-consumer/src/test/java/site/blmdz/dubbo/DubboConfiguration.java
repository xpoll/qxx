

package site.blmdz.dubbo;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import io.terminus.boot.zookeeper.autoconfigure.ZookeeperAutoConfiguration;

@Configuration
@EnableAutoConfiguration
@AutoConfigureAfter(ZookeeperAutoConfiguration.class)
public class DubboConfiguration {
}
