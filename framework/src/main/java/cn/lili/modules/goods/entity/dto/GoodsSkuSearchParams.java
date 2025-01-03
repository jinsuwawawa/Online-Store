package cn.store.modules.goods.entity.dto;

import cn.store.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 规格商品查询条件
 **/
@Data
public class GoodsSkuSearchParams extends GoodsSearchParams {

    private static final long serialVersionUID = -6235885068610635045L;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @Override
    public <T> QueryWrapper<T> queryWrapper() {
        QueryWrapper<T> queryWrapper = super.queryWrapper();
        queryWrapper.eq(StringUtils.isNotEmpty(goodsId), "goods_id", goodsId);
        return queryWrapper;
    }


}
