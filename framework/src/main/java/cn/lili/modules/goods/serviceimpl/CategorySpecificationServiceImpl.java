package cn.store.modules.goods.serviceimpl;

import cn.store.modules.goods.entity.dos.CategorySpecification;
import cn.store.modules.goods.entity.dos.Specification;
import cn.store.modules.goods.mapper.CategorySpecificationMapper;
import cn.store.modules.goods.service.CategorySpecificationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类规格业务层实现
 */
@Service
public class CategorySpecificationServiceImpl extends ServiceImpl<CategorySpecificationMapper, CategorySpecification> implements CategorySpecificationService {

    @Override
    public List<Specification> getCategorySpecList(String categoryId) {
        return this.baseMapper.getCategorySpecList(categoryId);
    }

    @Override
    public void deleteByCategoryId(String categoryId) {
        this.baseMapper.delete(new LambdaQueryWrapper<CategorySpecification>().eq(CategorySpecification::getCategoryId, categoryId));
    }
}