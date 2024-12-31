package cn.store.modules.statistics.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 退款统计VO
 **/
@Data
public class RefundOrderStatisticsDataVO {

    @ApiModelProperty(value = "售后SN")
    private String refundSn;

    @ApiModelProperty(value = "商家名称 ")
    private String storeName;

    @ApiModelProperty(value = "会员名称")
    private String memberName;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "规格内容")
    private String specs;

    @ApiModelProperty(value = "实际退款金额")
    private Double finalPrice;
}
