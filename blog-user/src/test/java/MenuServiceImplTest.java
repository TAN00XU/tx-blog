import com.tan00xu.BlogUserApplication;
import com.tan00xu.dto.UserMenuDTO;
import com.tan00xu.service.MenuService;
import com.tan00xu.util.CmdOutputInformationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/20 10:58
 */
@SpringBootTest(classes = BlogUserApplication.class)
@RunWith(SpringRunner.class)
public class MenuServiceImplTest {

    @Autowired
    private MenuService menuService;

    @Test
    public void listUserMenus() {
        List<UserMenuDTO> userMenuDTOS =
                menuService.listUserMenus();
        userMenuDTOS.forEach(CmdOutputInformationUtils::info);

    }
}
