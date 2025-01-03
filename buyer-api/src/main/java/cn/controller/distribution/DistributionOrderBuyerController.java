package cn.store.controller.distribution;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.distribution.entity.dos.DistributionOrder;
import cn.store.modules.distribution.entity.vos.DistributionOrderSearchParams;
import cn.store.modules.distribution.service.DistributionOrderService;
import cn.store.modules.distribution.service.DistributionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 买家端,分销商品佣金提现接口
 */
@RestController
@Api(tags = "买家端,分销订单接口")
@RequestMapping("/buyer/distribution/order")
public class DistributionOrderBuyerController {

    /**
     * 分销订单
     */
    @Autowired
    private DistributionOrderService distributionOrderService;
    /**
     * 分销员
     */
    @Autowired
    private DistributionService distributionService;


    @ApiOperation(value = "分销员订单")
    @GetMapping
    public ResultMessage<IPage<DistributionOrder>> casHistory(DistributionOrderSearchParams distributionOrderSearchParams) {
        //获取当前登录的分销员
        distributionOrderSearchParams.setDistributionId(distributionService.getDistribution().getId());
        return ResultUtil.data(distributionOrderService.getDistributionOrderPage(distributionOrderSearchParams));
    }


}
