package cn.store.controller.statistics;

import cn.store.common.enums.ResultUtil;
import cn.store.common.security.context.UserContext;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.statistics.entity.dto.StatisticsQueryParam;
import cn.store.modules.statistics.entity.vo.PlatformViewVO;
import cn.store.modules.statistics.service.PlatformViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 店铺端,流量统计接口
 */
@Api(tags = "店铺端,流量统计接口")
@RestController
@RequestMapping("/store/statistics/view")
public class ViewStatisticsStoreController {
    @Autowired
    private PlatformViewService platformViewService;

    @ApiOperation(value = "流量数据 表单获取")
    @GetMapping("/list")
    public ResultMessage<List<PlatformViewVO>> getByPage(StatisticsQueryParam queryParam) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        queryParam.setStoreId(storeId);
        return ResultUtil.data(platformViewService.list(queryParam));
    }
}
