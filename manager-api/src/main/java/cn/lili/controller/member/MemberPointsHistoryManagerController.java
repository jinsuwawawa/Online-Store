package cn.store.controller.member;
 
import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.member.entity.dos.MemberPointsHistory;
import cn.store.modules.member.entity.vo.MemberPointsHistoryVO;
import cn.store.modules.member.service.MemberPointsHistoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端,会员积分历史接口
 */
@RestController
@Api(tags = "管理端,会员积分历史接口")
@RequestMapping("/manager/member/memberPointsHistory")
public class MemberPointsHistoryManagerController {
    @Autowired
    private MemberPointsHistoryService memberPointsHistoryService;

    @ApiOperation(value = "分页获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "会员ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "memberName", value = "会员名称", required = true, paramType = "query")
    })
    @GetMapping(value = "/getByPage")
    public ResultMessage<IPage<MemberPointsHistory>> getByPage(PageVO page, String memberId, String memberName) {
        return ResultUtil.data(memberPointsHistoryService.MemberPointsHistoryList(page, memberId, memberName));
    }

    @ApiOperation(value = "获取会员积分VO")
    @ApiImplicitParam(name = "memberId", value = "会员ID", paramType = "query")
    @GetMapping(value = "/getMemberPointsHistoryVO")
    public ResultMessage<MemberPointsHistoryVO> getMemberPointsHistoryVO(String memberId) {
        return ResultUtil.data(memberPointsHistoryService.getMemberPointsHistoryVO(memberId));
    }


}
