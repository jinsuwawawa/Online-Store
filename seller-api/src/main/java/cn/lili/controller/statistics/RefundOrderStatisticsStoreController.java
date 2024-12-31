package cn.store.controller.statistics;

import cn.store.common.enums.ResultUtil;
import cn.store.common.security.context.UserContext;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.statistics.entity.dto.StatisticsQueryParam;
import cn.store.modules.statistics.entity.vo.RefundOrderStatisticsDataVO;
import cn.store.modules.statistics.service.RefundOrderStatisticsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 店铺端,退款统计接口
 */
@Api(tags = "店铺端,退款统计接口")
@RestController
@RequestMapping("/store/statistics/refundOrder")
public class RefundOrderStatisticsStoreController {

    @Autowired
    private RefundOrderStatisticsService refundOrderStatisticsService;

    @ApiOperation(value = "获取退款统计列表")
    @GetMapping("/getByPage")
    public ResultMessage<IPage<RefundOrderStatisticsDataVO>> getByPage(PageVO pageVO, StatisticsQueryParam statisticsQueryParam) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        statisticsQueryParam.setStoreId(storeId);
        return ResultUtil.data(refundOrderStatisticsService.getRefundOrderStatisticsData(pageVO, statisticsQueryParam));
    }

    @ApiOperation(value = "获取退款统计金额")
    @GetMapping("/getPrice")
    public ResultMessage<Object> getPrice(StatisticsQueryParam statisticsQueryParam) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        statisticsQueryParam.setStoreId(storeId);
        Double price = refundOrderStatisticsService.getRefundOrderStatisticsPrice(statisticsQueryParam);
        return ResultUtil.data(price);
    }
}
