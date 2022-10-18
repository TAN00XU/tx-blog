import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tan00xu.BlogUserApplication;
import com.tan00xu.dao.CommentDao;
import com.tan00xu.dao.UserAuthDao;
import com.tan00xu.dto.FriendlyLinkDTO;
import com.tan00xu.entity.Comment;
import com.tan00xu.service.FriendlyLinkService;
import com.tan00xu.util.CmdOutputInformationUtils;
import com.tan00xu.vo.CommentVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

import static com.tan00xu.constant.CommonConst.TRUE;

/**
 * @author 饮梦 TAN00XU
 * @date 2022/10/13 14:11
 */
@SpringBootTest(classes = BlogUserApplication.class)
@RunWith(SpringRunner.class)
public class FriendLinkServiceTest {

    @Autowired
    FriendlyLinkService friendlyLinkService;
    @Autowired
    CommentDao commentDao;
    @Autowired
    private UserAuthDao userAuthDao;

    public static void main(String[] args) {

    }

    @Test
    public void Test() {
        CommentVO commentVO = CommentVO.builder()
                .topicId(54)
                .type(1)
                .build();
        LambdaQueryWrapper<Comment> eq = new LambdaQueryWrapper<Comment>()
                .eq(Objects.nonNull(commentVO.getTopicId()), Comment::getTopicId, commentVO.getTopicId())
                .eq(Comment::getType, commentVO.getType())
                .isNull(Comment::getParentId)
                .eq(Comment::getIsReview, TRUE);
        Long commentCount = commentDao.selectCount(eq);
        CmdOutputInformationUtils.error(commentCount);
    }

    @Test
    public void testListFriendLinks() {
        List<FriendlyLinkDTO> friendlyLinkDTOS = friendlyLinkService.listFriendLinks();
        friendlyLinkDTOS.forEach(System.out::println);
    }

}
