package cn.store.controller.goods;

import cn.store.common.enums.ResultCode;
import cn.store.common.enums.ResultUtil;
import cn.store.common.exception.ServiceException;
import cn.store.common.security.OperationalJudgment;
import cn.store.common.security.context.UserContext;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.goods.entity.dos.DraftGoods;
import cn.store.modules.goods.entity.dto.DraftGoodsDTO;
import cn.store.modules.goods.entity.dto.DraftGoodsSearchParams;
import cn.store.modules.goods.entity.vos.DraftGoodsVO;
import cn.store.modules.goods.service.DraftGoodsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 店铺端,草稿商品接口
 */
@RestController
@Api(tags = "店铺端,草稿商品接口")
@RequestMapping("/store/goods/draftGoods")
public class DraftGoodsStoreController {
    @Autowired
    private DraftGoodsService draftGoodsService;


    @ApiOperation(value = "分页获取草稿商品列表")
    @GetMapping(value = "/page")
    public ResultMessage<IPage<DraftGoods>> getDraftGoodsByPage(DraftGoodsSearchParams searchParams) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        searchParams.setStoreId(storeId);
        return ResultUtil.data(draftGoodsService.getDraftGoods(searchParams));
    }

    @ApiOperation(value = "获取草稿商品")
    @GetMapping(value = "/{id}")
    public ResultMessage<DraftGoodsVO> getDraftGoods(@PathVariable String id) {
        DraftGoodsVO draftGoods = OperationalJudgment.judgment(draftGoodsService.getDraftGoods(id));
        return ResultUtil.data(draftGoods);
    }

    @ApiOperation(value = "保存草稿商品")
    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public ResultMessage<String> saveDraftGoods(@RequestBody DraftGoodsDTO draftGoodsVO) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        if (draftGoodsVO.getStoreId() == null) {
            draftGoodsVO.setStoreId(storeId);
        } else if (draftGoodsVO.getStoreId() != null && !storeId.equals(draftGoodsVO.getStoreId())) {
            throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
        }
        draftGoodsService.saveGoodsDraft(draftGoodsVO);
        return ResultUtil.success();
    }

    @ApiOperation(value = "删除草稿商品")
    @DeleteMapping(value = "/{id}")
    public ResultMessage<String> deleteDraftGoods(@PathVariable String id) {
        OperationalJudgment.judgment(draftGoodsService.getDraftGoods(id));
        draftGoodsService.deleteGoodsDraft(id);
        return ResultUtil.success();
    }

}
