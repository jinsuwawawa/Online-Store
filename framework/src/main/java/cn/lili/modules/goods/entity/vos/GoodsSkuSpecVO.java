package cn.lili.modules.goods.entity.vos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品规格VO
 */
@Data
public class GoodsSkuSpecVO {


    @ApiModelProperty(value = "商品skuId")
    private String skuId;

    @ApiModelProperty(value = "商品sku所包含规格")
    private List<SpecValueVO> specValues;

    @ApiModelProperty(value = "库存")
    private Integer quantity;

}
