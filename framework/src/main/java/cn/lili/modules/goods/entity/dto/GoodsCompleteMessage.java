package cn.store.modules.goods.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品购买完成信息
 **/
@Data
public class GoodsCompleteMessage {


    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "商品skuId")
    private String skuId;

    @ApiModelProperty(value = "购买会员sn")
    private String memberId;

    @ApiModelProperty(value = "购买数量")
    private Integer buyNum;

}
