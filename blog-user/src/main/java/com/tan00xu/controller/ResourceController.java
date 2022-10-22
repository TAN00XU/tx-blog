package com.tan00xu.controller;

import com.tan00xu.dto.ResourceDTO;
import com.tan00xu.service.ResourceService;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.ResourceVO;
import com.tan00xu.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源控制器类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/21 19:07:41
 */
@Tag(name = "资源模块")
@RestController
public class ResourceController {
    @Autowired
    private ResourceService resourceService;


    /**
     * 获取资源列表 后台
     *
     * @param conditionVO 条件VO
     * @return {@link Result}<{@link List}<{@link ResourceDTO}>>
     */
    @Operation(summary = "查看资源列表")
    @GetMapping("/admin/resources")
    public Result<List<ResourceDTO>> listResources(ConditionVO conditionVO) {
        return Result.ok(resourceService.listResources(conditionVO));
    }

    /**
     * 新增或修改资源 后台
     *
     * @param resourceVO 资源信息
     * @return {@link Result<>}
     */
    @Operation(summary = "新增或修改资源")
    @PostMapping("/admin/resources")
    public Result<?> saveOrUpdateResource(@RequestBody @Validated ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.ok();
    }

    /**
     * 删除api资源 后台
     *
     * @param resourceId 资源id
     * @return {@link Result<>}
     */
    @Operation(summary = "删除资源")
    @DeleteMapping("/admin/resources/{resourceId}")
    public Result<?> deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.ok();
    }
}
