import com.tan00xu.BlogUserApplication;
import com.tan00xu.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/20 23:17
 */
@SpringBootTest(classes = BlogUserApplication.class)
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    RedisService redisService;

    @Test
    public void testSet() {
        redisService.set("test", "test");
    }
}
