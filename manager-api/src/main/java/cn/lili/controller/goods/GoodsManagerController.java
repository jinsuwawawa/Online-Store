package cn.store.controller.goods;

import cn.store.common.aop.annotation.DemoSite;
import cn.store.common.aop.annotation.PreventDuplicateSubmissions;
import cn.store.common.enums.ResultCode;
import cn.store.common.enums.ResultUtil;
import cn.store.common.exception.ServiceException;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.goods.entity.dos.Goods;
import cn.store.modules.goods.entity.dos.GoodsSku;
import cn.store.modules.goods.entity.dto.GoodsSearchParams;
import cn.store.modules.goods.entity.enums.GoodsAuthEnum;
import cn.store.modules.goods.entity.enums.GoodsStatusEnum;
import cn.store.modules.goods.entity.vos.GoodsVO;
import cn.store.modules.goods.service.GoodsService;
import cn.store.modules.goods.service.GoodsSkuService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

/**
 * 管理端,商品管理接口
 */
@RestController
@Api(tags = "管理端,商品管理接口")
@RequestMapping("/manager/goods/goods")
public class GoodsManagerController {
    /**
     * 商品
     */
    @Autowired
    private GoodsService goodsService;
    /**
     * 规格商品
     */
    @Autowired
    private GoodsSkuService goodsSkuService;

    @ApiOperation(value = "分页获取")
    @GetMapping(value = "/list")
    public ResultMessage<IPage<Goods>> getByPage(GoodsSearchParams goodsSearchParams) {
        return ResultUtil.data(goodsService.queryByParams(goodsSearchParams));
    }

    @ApiOperation(value = "分页获取商品列表")
    @GetMapping(value = "/sku/list")
    public ResultMessage<IPage<GoodsSku>> getSkuByPage(GoodsSearchParams goodsSearchParams) {
        return ResultUtil.data(goodsSkuService.getGoodsSkuByPage(goodsSearchParams));
    }

    @ApiOperation(value = "分页获取待审核商品")
    @GetMapping(value = "/auth/list")
    public ResultMessage<IPage<Goods>> getAuthPage(GoodsSearchParams goodsSearchParams) {
        goodsSearchParams.setAuthFlag(GoodsAuthEnum.TOBEAUDITED.name());
        return ResultUtil.data(goodsService.queryByParams(goodsSearchParams));
    }

    @PreventDuplicateSubmissions
    @ApiOperation(value = "管理员下架商品", notes = "管理员下架商品时使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, paramType = "query", allowMultiple = true),
            @ApiImplicitParam(name = "reason", value = "下架理由", required = true, paramType = "query")
    })
    @DemoSite
    @PutMapping(value = "/{goodsId}/under")
    public ResultMessage<Object> underGoods(@PathVariable String goodsId, @NotEmpty(message = "下架原因不能为空") @RequestParam String reason) {
        List<String> goodsIds = Arrays.asList(goodsId.split(","));
        if (Boolean.TRUE.equals(goodsService.managerUpdateGoodsMarketAble(goodsIds, GoodsStatusEnum.DOWN, reason))) {
            return ResultUtil.success();
        }
        throw new ServiceException(ResultCode.GOODS_UNDER_ERROR);
    }

    @PreventDuplicateSubmissions
    @ApiOperation(value = "管理员审核商品", notes = "管理员审核商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsIds", value = "商品ID", required = true, paramType = "path", allowMultiple = true, dataType = "int"),
            @ApiImplicitParam(name = "authFlag", value = "审核结果", required = true, paramType = "query", dataType = "string")
    })
    @PutMapping(value = "{goodsIds}/auth")
    public ResultMessage<Object> auth(@PathVariable List<String> goodsIds, @RequestParam String authFlag) {
        //校验商品是否存在
        if (goodsService.auditGoods(goodsIds, GoodsAuthEnum.valueOf(authFlag))) {
            return ResultUtil.success();
        }
        throw new ServiceException(ResultCode.GOODS_AUTH_ERROR);
    }


    @PreventDuplicateSubmissions
    @ApiOperation(value = "管理员上架商品", notes = "管理员上架商品时使用")
    @PutMapping(value = "/{goodsId}/up")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, allowMultiple = true)
    })
    public ResultMessage<Object> unpGoods(@PathVariable List<String> goodsId) {
        if (Boolean.TRUE.equals(goodsService.updateGoodsMarketAble(goodsId, GoodsStatusEnum.UPPER, ""))) {
            return ResultUtil.success();
        }
        throw new ServiceException(ResultCode.GOODS_UPPER_ERROR);
    }


    @ApiOperation(value = "通过id获取商品详情")
    @GetMapping(value = "/get/{id}")
    public ResultMessage<GoodsVO> get(@PathVariable String id) {
        GoodsVO goods = goodsService.getGoodsVO(id);
        return ResultUtil.data(goods);
    }

}
