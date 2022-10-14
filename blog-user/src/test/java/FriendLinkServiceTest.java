import com.tan00xu.BlogUserApplication;
import com.tan00xu.dto.FriendlyLinkDTO;
import com.tan00xu.service.FriendlyLinkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 14:11
 */
@SpringBootTest(classes = BlogUserApplication.class)
@RunWith(SpringRunner.class)
public class FriendLinkServiceTest {

    @Autowired
    FriendlyLinkService friendlyLinkService;

    @Test
    public void testListFriendLinks() {
        List<FriendlyLinkDTO> friendlyLinkDTOS = friendlyLinkService.listFriendLinks();
        friendlyLinkDTOS.forEach(System.out::println);
    }


}
