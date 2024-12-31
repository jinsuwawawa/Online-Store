package cn.store.modules.goods.entity.vos;

import cn.store.modules.goods.entity.dos.Goods;
import cn.store.modules.goods.entity.dos.Wholesale;
import cn.store.modules.goods.entity.dto.GoodsParamsDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 商品VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsVO extends Goods {

    private static final long serialVersionUID = 6377623919990713567L;

    @ApiModelProperty(value = "分类名称")
    private List<String> categoryName;

    @ApiModelProperty(value = "商品参数")
    private List<GoodsParamsDTO> goodsParamsDTOList;

    @ApiModelProperty(value = "商品图片")
    private List<String> goodsGalleryList;

    @ApiModelProperty(value = "sku列表")
    private List<GoodsSkuVO> skuList;

    @ApiModelProperty(value = "批发商品消费规则列表")
    private List<Wholesale> wholesaleList;
}
