package cn.store.controller.statistics;

import cn.store.common.enums.ResultUtil;
import cn.store.common.security.context.UserContext;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.order.aftersale.entity.dos.AfterSale;
import cn.store.modules.order.order.entity.vo.OrderSimpleVO;
import cn.store.modules.statistics.entity.dto.StatisticsQueryParam;
import cn.store.modules.statistics.entity.vo.OrderOverviewVO;
import cn.store.modules.statistics.entity.vo.OrderStatisticsDataVO;
import cn.store.modules.statistics.service.AfterSaleStatisticsService;
import cn.store.modules.statistics.service.OrderStatisticsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 店铺端,订单统计接口
 */
@Api(tags = "店铺端,订单统计接口")
@RestController
@RequestMapping("/store/statistics/order")
public class OrderStatisticsStoreController {

    /**
     * 售后
     */
    @Autowired
    private AfterSaleStatisticsService afterSaleStatisticsService;
    /**
     * 订单统计
     */
    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @ApiOperation(value = "订单概览统计")
    @GetMapping("/overview")
    public ResultMessage<OrderOverviewVO> overview(StatisticsQueryParam statisticsQueryParam) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        try {
            statisticsQueryParam.setStoreId(storeId);
            return ResultUtil.data(orderStatisticsService.overview(statisticsQueryParam));
        } catch (Exception e) {
            log.error("订单概览统计错误", e);
        }
        return null;
    }

    @ApiOperation(value = "订单图表统计")
    @GetMapping
    public ResultMessage<List<OrderStatisticsDataVO>> statisticsChart(StatisticsQueryParam statisticsQueryParam) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        try {
            statisticsQueryParam.setStoreId(storeId);
            return ResultUtil.data(orderStatisticsService.statisticsChart(statisticsQueryParam));
        } catch (Exception e) {
            log.error("订单图表统计错误", e);
        }
        return null;
    }


    @ApiOperation(value = "订单统计")
    @GetMapping("/order")
    public ResultMessage<IPage<OrderSimpleVO>> order(StatisticsQueryParam statisticsQueryParam, PageVO pageVO) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        try {
            statisticsQueryParam.setStoreId(storeId);
            return ResultUtil.data(orderStatisticsService.getStatistics(statisticsQueryParam, pageVO));
        } catch (Exception e) {
            log.error("订单统计错误", e);
        }
        return null;
    }


    @ApiOperation(value = "退单统计")
    @GetMapping("/refund")
    public ResultMessage<IPage<AfterSale>> refund(StatisticsQueryParam statisticsQueryParam, PageVO pageVO) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        statisticsQueryParam.setStoreId(storeId);
        return ResultUtil.data(afterSaleStatisticsService.getStatistics(statisticsQueryParam, pageVO));
    }
}
