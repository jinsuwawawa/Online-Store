package cn.store.controller.statistics;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.member.entity.vo.MemberDistributionVO;
import cn.store.modules.statistics.entity.dto.StatisticsQueryParam;
import cn.store.modules.statistics.entity.vo.OnlineMemberVO;
import cn.store.modules.statistics.entity.vo.PlatformViewVO;
import cn.store.modules.statistics.service.PlatformViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理端,流量统计接口
 */
@Api(tags = "管理端,流量统计接口")
@RestController
@RequestMapping("/manager/statistics/view")
public class ViewStatisticsManagerController {
    @Autowired
    private PlatformViewService platformViewService;

    @ApiOperation(value = "流量数据 表单获取")
    @GetMapping("/list")
    public ResultMessage<List<PlatformViewVO>> getByPage(StatisticsQueryParam queryParam) {
        return ResultUtil.data(platformViewService.list(queryParam));
    }

    @ApiOperation(value = "当前在线人数")
    @GetMapping("/online/current")
    public ResultMessage<Long> currentNumberPeopleOnline() {
        return ResultUtil.data(platformViewService.online());
    }


    @ApiOperation(value = "会员分布")
    @GetMapping("/online/distribution")
    public ResultMessage<List<MemberDistributionVO>> memberDistribution() {
        return ResultUtil.data(platformViewService.memberDistribution());
    }

    @ApiOperation(value = "在线人数历史（默认48小时）")
    @GetMapping("/online/history")
    public ResultMessage<List<OnlineMemberVO>> history() {
        return ResultUtil.data(platformViewService.statisticsOnline());
    }

}
