package cn.store.controller.statistics;

import cn.store.common.enums.ResultUtil;
import cn.store.common.security.context.UserContext;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.statistics.entity.dto.GoodsStatisticsQueryParam;
import cn.store.modules.statistics.entity.vo.GoodsStatisticsDataVO;
import cn.store.modules.statistics.service.StoreFlowStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 店铺端,商品统计接口
 **/
@Api(tags = "店铺端,商品统计接口")
@RestController
@RequestMapping("/store/statistics/goods")
public class GoodsStatisticsStoreController {

    /**
     * 商品统计
     */
    @Autowired
    private StoreFlowStatisticsService storeFlowStatisticsService;

    @ApiOperation(value = "获取统计列表,排行前一百的数据")
    @GetMapping
    public ResultMessage<List<GoodsStatisticsDataVO>> getByPage(GoodsStatisticsQueryParam statisticsQueryParam) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        statisticsQueryParam.setStoreId(storeId);
        return ResultUtil.data(storeFlowStatisticsService.getGoodsStatisticsData(statisticsQueryParam, 100));
    }
}
