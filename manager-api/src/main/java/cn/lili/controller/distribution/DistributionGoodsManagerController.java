package cn.store.controller.distribution;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.distribution.entity.dto.DistributionGoodsSearchParams;
import cn.store.modules.distribution.entity.vos.DistributionGoodsVO;
import cn.store.modules.distribution.service.DistributionGoodsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端,分销商品管理接口
 */
@RestController
@Api(tags = "管理端,分销商品管理接口")
@RequestMapping("/manager/distribution/goods")
public class DistributionGoodsManagerController {

    @Autowired
    private DistributionGoodsService distributionGoodsService;

    @GetMapping(value = "/getByPage")
    @ApiOperation(value = "分页获取")
    public ResultMessage<IPage<DistributionGoodsVO>> getByPage(DistributionGoodsSearchParams distributionGoodsSearchParams) {
        return ResultUtil.data(distributionGoodsService.goodsPage(distributionGoodsSearchParams));
    }


    @DeleteMapping(value = "/delByIds/{ids}")
    @ApiOperation(value = "批量删除")
    public ResultMessage<Object> delAllByIds(@PathVariable List ids) {

        distributionGoodsService.removeByIds(ids);
        return ResultUtil.success();
    }
}
