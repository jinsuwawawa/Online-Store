package cn.lili.modules.goods.entity.vos;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 兑换VO
 */
@Data
public class ExchangeVO {

    /** 是否允许兑换 */
    @ApiModelProperty(value="是否允许积分兑换")
    private Integer enableExchange;

    /** 兑换所需金额 */
    @ApiModelProperty(value="兑换所需金额 ")
    private Double exchangeMoney;

    /** 商品所属积分分类 */
    @ApiModelProperty(value="积分兑换所属分类 ")
    private Integer categoryId;

    /** 兑换所需积分 */
    @ApiModelProperty(value="积分兑换使用的积分 ")
    private Integer exchangePoint;
}
