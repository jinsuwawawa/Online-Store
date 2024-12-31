package cn.store.controller.goods;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.goods.entity.vos.CategoryVO;
import cn.store.modules.goods.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 买家端,商品分类接口
 */
@RestController
@Api(tags = "买家端,商品分类接口")
@RequestMapping("/buyer/goods/category")
public class CategoryBuyerController {
    /**
     * 商品分类
     */
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取商品分类列表")
    @ApiImplicitParam(name = "parentId", value = "上级分类ID，全部分类为：0", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/get/{parentId}")
    public ResultMessage<List<CategoryVO>> list(@NotNull(message = "分类ID不能为空") @PathVariable String parentId) {

        return ResultUtil.data(categoryService.listAllChildren(parentId));
    }
}
