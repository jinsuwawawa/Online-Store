package cn.store.modules.goods.service;


import cn.store.modules.goods.entity.dos.Specification;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 规格业务层
 */
public interface SpecificationService extends IService<Specification> {

    /**
     * 删除规格
     *
     * @param ids 规格ID
     * @return 是否删除成功
     */
    boolean deleteSpecification(List<String> ids);

}