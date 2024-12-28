package cn.lili.modules.goods.entity.vos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分类品牌VO
 */
@Data
public class CategoryBrandVO {
    /**
     * 品牌id
     */
    @ApiModelProperty(value = "品牌id", required = true)
    private String id;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称", required = true)
    private String name;
}
