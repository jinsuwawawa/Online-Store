package cn.store.modules.statistics.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分类统计VO
 **/
@Data
public class CategoryStatisticsDataVO {


    @ApiModelProperty("一级分类ID")
    private String categoryId;

    @ApiModelProperty("一级分类名称")
    private String categoryName;

    @ApiModelProperty(value = "销售数量")
    private String num;

    @ApiModelProperty(value = "销售金额")
    private Double price;
}
