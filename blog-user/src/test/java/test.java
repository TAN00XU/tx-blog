import com.tan00xu.BlogUserApplication;
import com.tan00xu.dao.RoleDao;
import com.tan00xu.dto.RoleDTO;
import com.tan00xu.service.RedisService;
import com.tan00xu.service.impl.ArticleServiceImpl;
import com.tan00xu.util.CmdOutputInformationUtils;
import com.tan00xu.vo.ConditionVO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/11 11:08
 */
@SpringBootTest(classes = BlogUserApplication.class)
@RunWith(SpringRunner.class)
public class test {

    @Autowired
    RedisService redisService;
    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleDao roleDao;


    public static String getProvince(String name) {
        String provinceName = "";
        if (StringUtils.isNotBlank(name)) {
            int index;
            if ((index = name.indexOf("省")) > 0 ||
                    (index = name.indexOf("区")) > 0 ||
                    (index = name.indexOf("市")) > 0) {
                provinceName = name.substring(0, index + 1);
            }
            if ((index = name.indexOf("省")) > 0 ||
                    (index = name.indexOf("区")) > 0 ||
                    (index = name.indexOf("市")) > 0) {
                provinceName = name.substring(0, index + 1);
            }

        }
        return provinceName;
    }


    @Test
    public void test() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        String path = "无";
        //项目部署的目录
        if (jarFile != null) {
            path = jarFile.getParentFile().getPath();
        }
        CmdOutputInformationUtils.error(path);
    }

    @Test
    public void testArticleService() {
        articleService.listArticles();
    }

    @Test
    public void testBCryptPasswordEncoder() {
        String encode = passwordEncoder.encode("123123");
        System.out.println(encode);
    }

    @Test
    public void testRoleDao() {
        List<RoleDTO> roleDTOS = roleDao.listRoles(0L, 5L, new ConditionVO());
        roleDTOS.forEach(CmdOutputInformationUtils::debug);
    }


}
