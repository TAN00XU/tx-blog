import com.tan00xu.BlogUserApplication;
import com.tan00xu.dto.ResourceDTO;
import com.tan00xu.service.ResourceService;
import com.tan00xu.util.CmdOutputInformationUtils;
import com.tan00xu.vo.ConditionVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/21 22:10
 */
@SpringBootTest(classes = BlogUserApplication.class)
@RunWith(SpringRunner.class)
public class ResourceServiceImplTest {
    @Autowired
    private ResourceService resourceService;


    @Test
    public void testListResources() {
        List<ResourceDTO> resourceDTOS = resourceService.listResources(new ConditionVO());
        List<Integer> list = new ArrayList<>();

//        resourceDTOS.forEach(i -> {
//            list.add(i.getId());
//        });
//        List<Integer> collect = resourceDTOS.stream().map(ResourceDTO::getId).collect(Collectors.toList());
        List<Map<Integer, String>> collect = resourceDTOS.stream().map(i -> {
            Map<Integer, String> map = new HashMap<>();
            map.put(i.getId(), String.valueOf(i.getCreateTime()));
            return map;
        }).collect(Collectors.toList());


        collect.forEach(CmdOutputInformationUtils::info);
    }

}
