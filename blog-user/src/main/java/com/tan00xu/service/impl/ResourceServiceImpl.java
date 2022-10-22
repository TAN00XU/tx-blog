package com.tan00xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan00xu.dao.ResourceDao;
import com.tan00xu.dao.RoleResourceDao;
import com.tan00xu.dto.ResourceDTO;
import com.tan00xu.entity.Resource;
import com.tan00xu.entity.RoleResource;
import com.tan00xu.exception.BizException;
import com.tan00xu.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.tan00xu.service.ResourceService;
import com.tan00xu.util.BeanCopyUtils;
import com.tan00xu.util.CmdOutputInformationUtils;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 资源服务实现类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/21 19:15:20
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, Resource> implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @Autowired
    private RoleResourceDao roleResourceDao;

    @Override
    public void saveOrUpdateResource(ResourceVO resourceVO) {
        CmdOutputInformationUtils.error(resourceVO);
        // 更新资源信息
        Resource resource = BeanCopyUtils.copyObject(resourceVO, Resource.class);
        this.saveOrUpdate(resource);
        // 重新加载角色资源信息
        filterInvocationSecurityMetadataSource.clearDataSource();
    }

    @Override
    public void deleteResource(Integer resourceId) {
        // 查询是否有角色关联
        Long count = roleResourceDao.selectCount(
                new LambdaQueryWrapper<RoleResource>()
                        .eq(RoleResource::getResourceId, resourceId));
        if (count > 0) {
            throw new BizException("该资源下存在角色");
        }
        // 删除子资源
        List<Integer> resourceIdList = resourceDao.selectList(
                        new LambdaQueryWrapper<Resource>()
                                .select(Resource::getId).
                                eq(Resource::getParentId, resourceId))
                .stream()
                .map(Resource::getId)
                .collect(Collectors.toList());
        resourceIdList.add(resourceId);
        resourceDao.deleteBatchIds(resourceIdList);
    }


    @Override
    public List<ResourceDTO> listResources(ConditionVO conditionVO) {
        // 查询资源列表
        List<Resource> resourceList = resourceDao.selectList(
                new LambdaQueryWrapper<Resource>()
                        .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Resource::getResourceName, conditionVO.getKeywords())
        );
        // 获取所有模块
        List<Resource> parentList = listResourceModule(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resourceList);
        // 绑定模块下的所有接口
        List<ResourceDTO> resourceDTOList = parentList.stream()
                .map(item -> {
                    ResourceDTO resourceDTO = BeanCopyUtils.copyObject(item, ResourceDTO.class);
                    List<ResourceDTO> childrenList = BeanCopyUtils.copyList(childrenMap.get(item.getId()), ResourceDTO.class);
                    resourceDTO.setChildren(childrenList);
                    // 移除
                    childrenMap.remove(item.getId());
                    return resourceDTO;
                }).collect(Collectors.toList());
        // 若还有资源未取出则拼接
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Resource> childrenList = new ArrayList<>();
            Collection<List<Resource>> values = childrenMap.values();
            values.forEach(childrenList::addAll);
            // List<Resource>
//            childrenMap.values().forEach(childrenList::addAll);

            List<ResourceDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtils.copyObject(item, ResourceDTO.class))
                    .collect(Collectors.toList());
            resourceDTOList.addAll(childrenDTOList);
        }
        return resourceDTOList;
    }


    /**
     * 获取所有资源模块
     * 过滤掉有父级id的
     *
     * @param resourceList 资源列表
     * @return 资源模块列表 {@link List}<{@link Resource}>
     */
    private List<Resource> listResourceModule(List<Resource> resourceList) {
        return resourceList.stream()
                // 过滤掉有父级id的
                .filter(item -> Objects.isNull(item.getParentId()))
                .collect(Collectors.toList());
    }


    /**
     * 获取模块下的所有资源
     *
     * @param resourceList 资源列表
     * @return 模块资源 {@link Map}<{@link Integer}, {@link List}<{@link Resource}>>
     */
    private Map<Integer, List<Resource>> listResourceChildren(List<Resource> resourceList) {
        return resourceList.stream()
                // 过滤掉没有父级id的
                .filter(item -> Objects.nonNull(item.getParentId()))
                // 按父级id分组
                .collect(Collectors.groupingBy(Resource::getParentId));
    }
}
