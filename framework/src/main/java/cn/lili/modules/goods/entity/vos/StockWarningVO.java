package cn.store.modules.goods.entity.vos;

import cn.store.modules.goods.entity.dos.GoodsSku;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存警告封装类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockWarningVO {

    @ApiModelProperty(value = "库存警告数量")
    private Integer stockWarningNum;

    @ApiModelProperty(value = "商品SKU列表")
    private IPage<GoodsSku> goodsSkuPage;

}
