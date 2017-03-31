

package site.blmdz.dubbo;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DubboConfiguration.class, 
locations="classpath:dubbo-consumer.xml")
@ActiveProfiles("dubbo")
public abstract class DubboBaseTest {
}
