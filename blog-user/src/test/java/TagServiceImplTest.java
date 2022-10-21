import com.tan00xu.BlogUserApplication;
import com.tan00xu.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/21 16:26
 */
@SpringBootTest(classes = BlogUserApplication.class)
@RunWith(SpringRunner.class)
public class TagServiceImplTest {

    @Autowired
    private TagService tagService;


    @Test
    public void testDeleteTag() {

    }
}
