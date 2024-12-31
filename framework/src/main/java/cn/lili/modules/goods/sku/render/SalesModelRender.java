package cn.store.modules.goods.sku.render;

import cn.store.modules.goods.entity.dos.GoodsSku;
import cn.store.modules.goods.entity.dto.GoodsOperationDTO;

import java.util.List;

/**
 * 根据商品销售模型渲染商品sku
 **/
public interface SalesModelRender {


    String getSalesMode();

    void renderSingle(GoodsSku goodsSku, GoodsOperationDTO goodsOperationDTO);

    void renderBatch(List<GoodsSku> goodsSkus, GoodsOperationDTO goodsOperationDTO);

}
