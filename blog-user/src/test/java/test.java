import com.tan00xu.BlogUserApplication;
import com.tan00xu.dao.RoleDao;
import com.tan00xu.dto.RoleDTO;
import com.tan00xu.service.RedisService;
import com.tan00xu.service.impl.ArticleServiceImpl;
import com.tan00xu.util.CmdOutputInformationUtils;
import com.tan00xu.util.IpUtils;
import com.tan00xu.vo.ConditionVO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>(); //定义Map集合对象
        map.put("apple", "新鲜的苹果"); //向集合中添加对象
        map.put("computer", "配置优良的计算机");
        map.put("test", "测试");

        Collection<String> values = map.values();
        CmdOutputInformationUtils.info(values);
        values.forEach(System.out::println);

        List<String> list = new ArrayList<>();
        list.addAll(values);
        values.forEach(list::add);

        System.out.println(list);

    }

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
        // 统计游客地域分布
        String ipSource = IpUtils.getIpSource("220.182.8.7");
        System.out.println(ipSource);

//        if (StringUtils.isNotBlank(ipSource)) {
//            String regex = "[省|区|市][\u4e00-\u9fa5]*";
//            ipSource.replaceAll("")
//            ipSource = ipSource.substring(0, 2)
//                    .replaceAll(PROVINCE, "")
//                    .replaceAll(CITY, "");
//            System.out.println(ipSource);
//            redisService.hIncr(VISITOR_AREA, ipSource, 1L);
//        }
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
