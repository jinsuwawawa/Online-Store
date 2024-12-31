package cn.store.controller.statistics;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.statistics.entity.dos.MemberStatisticsData;
import cn.store.modules.statistics.entity.dto.StatisticsQueryParam;
import cn.store.modules.statistics.service.MemberStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理端,会员统计接口
 */
@Api(tags = "管理端,会员统计接口")
@RestController
@RequestMapping("/manager/statistics/member")
public class MemberStatisticsManagerController {
    @Autowired
    private MemberStatisticsService memberStatisticsService;

    @ApiOperation(value = "获取会员统计")
    @GetMapping
    public ResultMessage<List<MemberStatisticsData>> getByList(StatisticsQueryParam statisticsQueryParam) {
        return ResultUtil.data(memberStatisticsService.statistics(statisticsQueryParam));
    }
}
