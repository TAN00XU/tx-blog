package com.tan00xu.strategy.context;

import com.tan00xu.dto.ArticleSearchDTO;
import com.tan00xu.strategy.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.tan00xu.enums.SearchModeEnum.getStrategy;


/**
 * 搜索策略上下文类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/29 17:36:46
 */
@Service
public class SearchStrategyContext {
    /**
     * 搜索模式
     */
    @Value("${search.mode}")
    private String searchMode;

    @Autowired
    private Map<String, SearchStrategy> searchStrategyMap;


    /**
     * 执行搜索策略
     *
     * @param keywords 关键字
     * @return {@link List}<{@link ArticleSearchDTO}>
     */
    public List<ArticleSearchDTO> executeSearchStrategy(String keywords) {
        return searchStrategyMap.get(getStrategy(searchMode)).searchArticle(keywords);
    }

}
