package cn.store.modules.goods.entity.dto;

import cn.store.common.vo.PageVO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品品牌dto
 */
@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "商品品牌dto")
public class BrandPageDTO extends PageVO {

    private static final long serialVersionUID = 8906820486037326039L;

    @ApiModelProperty(value = "品牌名称")
    private String name;
}
