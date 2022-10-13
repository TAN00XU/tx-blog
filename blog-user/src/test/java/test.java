import com.tan00xu.BlogUserApplication;
import com.tan00xu.service.RedisService;
import com.tan00xu.service.impl.ArticleServiceImpl;
import com.tan00xu.util.IpUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/11 11:08
 */
@SpringBootTest(classes = BlogUserApplication.class)
@RunWith(SpringRunner.class)
public class test {

    @Autowired
    RedisService redisService;

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

    public static void main(String[] args) {
//        String regex = "^[省|区|市]$[\u4e00-\u9fa5]*";
//
        String name = "西藏自治区日喀则市 电信";
        String name1 = "四川省广安市岳池县 电信";
        String name2 = "北京市 电信";
//
//        String s = name1.replaceAll(regex, "");
//        System.out.println(s);
//        Pattern compile = Pattern.compile(regex);
//        Matcher matcher = compile.matcher(name);
//        int i = name1.indexOf("市");
//        String substring = name1.substring(0, i + 1);
//        System.out.println(substring);
        System.out.println(getProvince(name));
        System.out.println(getProvince(name1));
        System.out.println(getProvince(name2));
        System.out.println(getProvince("测试"));

    }

    public static String getProvince(String name) {
        String provinceName = "";
        if (StringUtils.isNotBlank(name)) {
            int index = 0;
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

    @Autowired
    ArticleServiceImpl articleService;

    @Test
    public void testArticleService() {
        articleService.listArticles();
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testBCryptPasswordEncoder() {
        String encode = passwordEncoder.encode("123123");
        System.out.println(encode);
    }
}
