import com.tan00xu.BlogUserApplication;
import com.tan00xu.strategy.context.UploadStrategyContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/26 16:58
 */
@SpringBootTest(classes = BlogUserApplication.class)
@RunWith(SpringRunner.class)
public class UploadStrategyContextTest {

    @Autowired
    UploadStrategyContext uploadStrategyContext;


    @Test
    public void test() {
    }
}
