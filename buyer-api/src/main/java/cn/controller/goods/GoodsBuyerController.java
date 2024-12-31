package cn.store.controller.goods;

import cn.store.common.enums.ResultCode;
import cn.store.common.enums.ResultUtil;
import cn.store.common.exception.ServiceException;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.goods.entity.dos.Goods;
import cn.store.modules.goods.entity.dos.GoodsSku;
import cn.store.modules.goods.entity.dto.GoodsSearchParams;
import cn.store.modules.goods.entity.vos.GoodsVO;
import cn.store.modules.goods.service.GoodsService;
import cn.store.modules.goods.service.GoodsSkuService;
import cn.store.modules.search.entity.dos.EsGoodsIndex;
import cn.store.modules.search.entity.dos.EsGoodsRelatedInfo;
import cn.store.modules.search.entity.dto.EsGoodsSearchDTO;
import cn.store.modules.search.service.EsGoodsSearchService;
import cn.store.modules.search.service.HotWordsService;
import cn.store.modules.statistics.aop.PageViewPoint;
import cn.store.modules.statistics.aop.enums.PageViewEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 买家端,商品接口
 */
@Slf4j
@Api(tags = "买家端,商品接口")
@RestController
@RequestMapping("/buyer/goods/goods")
public class GoodsBuyerController {

    /**
     * 商品
     */
    @Autowired
    private GoodsService goodsService;
    /**
     * 商品SKU
     */
    @Autowired
    private GoodsSkuService goodsSkuService;
    /**
     * ES商品搜索
     */
    @Autowired
    private EsGoodsSearchService goodsSearchService;

    @Autowired
    private HotWordsService hotWordsService;

    @ApiOperation(value = "通过id获取商品信息")
    @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping(value = "/get/{goodsId}")
    public ResultMessage<GoodsVO> get(@NotNull(message = "商品ID不能为空") @PathVariable("goodsId") String id) {
        return ResultUtil.data(goodsService.getGoodsVO(id));
    }

    @ApiOperation(value = "通过id获取商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "skuId", value = "skuId", required = true, paramType = "path")
    })
    @GetMapping(value = "/sku/{goodsId}/{skuId}")
    @PageViewPoint(type = PageViewEnum.SKU, id = "#id")
    public ResultMessage<Map<String, Object>> getSku(@NotNull(message = "商品ID不能为空") @PathVariable("goodsId") String goodsId,
                                                     @NotNull(message = "SKU ID不能为空") @PathVariable("skuId") String skuId) {
        try {
            // 读取选中的列表
            Map<String, Object> map = goodsSkuService.getGoodsSkuDetail(goodsId, skuId);
            return ResultUtil.data(map);
        } catch (ServiceException se) {
            log.info(se.getMsg(), se);
            throw se;
        } catch (Exception e) {
            log.error(ResultCode.GOODS_ERROR.message(), e);
            return ResultUtil.error(ResultCode.GOODS_ERROR);
        }

    }

    @ApiOperation(value = "获取商品分页列表")
    @GetMapping
    public ResultMessage<IPage<Goods>> getByPage(GoodsSearchParams goodsSearchParams) {
        return ResultUtil.data(goodsService.queryByParams(goodsSearchParams));
    }

    @ApiOperation(value = "获取商品sku列表")
    @GetMapping("/sku")
    public ResultMessage<List<GoodsSku>> getSkuByPage(GoodsSearchParams goodsSearchParams) {
        return ResultUtil.data(goodsSkuService.getGoodsSkuByList(goodsSearchParams));
    }

    @ApiOperation(value = "从ES中获取商品信息")
    @GetMapping("/es")
    public ResultMessage<Page<EsGoodsIndex>> getGoodsByPageFromEs(EsGoodsSearchDTO goodsSearchParams, PageVO pageVO) {
        pageVO.setNotConvert(true);
        return ResultUtil.data(goodsSearchService.searchGoodsByPage(goodsSearchParams, pageVO));
    }

    @ApiOperation(value = "从ES中获取相关商品品牌名称，分类名称及属性")
    @GetMapping("/es/related")
    public ResultMessage<EsGoodsRelatedInfo> getGoodsRelatedByPageFromEs(EsGoodsSearchDTO goodsSearchParams, PageVO pageVO) {
        pageVO.setNotConvert(true);
        EsGoodsRelatedInfo selector = goodsSearchService.getSelector(goodsSearchParams, pageVO);
        return ResultUtil.data(selector);
    }

    @ApiOperation(value = "获取搜索热词")
    @GetMapping("/hot-words")
    public ResultMessage<List<String>> getGoodsHotWords(Integer count) {
        List<String> hotWords = hotWordsService.getHotWords(count);
        return ResultUtil.data(hotWords);
    }

}
