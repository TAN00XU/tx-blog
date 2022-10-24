package com.tan00xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tan00xu.dto.LabelOptionDTO;
import com.tan00xu.dto.ResourceDTO;
import com.tan00xu.entity.Resource;
import com.tan00xu.vo.ConditionVO;
import com.tan00xu.vo.ResourceVO;

import java.util.List;


/**
 * 资源服务类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/21 19:15:07
 */
public interface ResourceService extends IService<Resource> {


    /**
     * 添加或修改资源 后台
     *
     * @param resourceVO 资源对象
     */
    void saveOrUpdateResource(ResourceVO resourceVO);

    /**
     * 删除资源 后台
     *
     * @param resourceId 资源id
     */
    void deleteResource(Integer resourceId);

    /**
     * 获取资源列表 后台
     *
     * @param conditionVO 条件
     * @return 资源列表
     */
    List<ResourceDTO> listResources(ConditionVO conditionVO);


    /**
     * 查看角色的资源选项列表 后台
     *
     * @return 角色资源选项列表 {@link List}<{@link LabelOptionDTO}>
     */
    List<LabelOptionDTO> listRoleResourceOption();

}
