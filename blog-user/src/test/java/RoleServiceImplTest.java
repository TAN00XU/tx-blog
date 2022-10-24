import com.tan00xu.BlogUserApplication;
import com.tan00xu.dto.RoleDTO;
import com.tan00xu.service.RoleService;
import com.tan00xu.util.CmdOutputInformationUtils;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.PageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/24 21:47
 */
@SpringBootTest(classes = BlogUserApplication.class)
@RunWith(SpringRunner.class)
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void listRoles() {
        PageResult<RoleDTO> roleDTOPageResult = roleService.listRoles(new ConditionVO());

        CmdOutputInformationUtils.info(roleDTOPageResult);
    }
}
