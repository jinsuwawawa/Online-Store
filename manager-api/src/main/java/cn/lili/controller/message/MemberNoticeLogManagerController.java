package cn.store.controller.message;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.member.entity.dos.MemberNoticeLog;
import cn.store.modules.member.service.MemberNoticeLogService;
import cn.store.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端,会员消息接口
 */
@RestController
@Api(tags = "管理端,会员消息接口")
@RequestMapping("/manager/message/memberNoticeLog")
public class MemberNoticeLogManagerController {
    @Autowired
    private MemberNoticeLogService memberNoticeLogService;

    @ApiOperation(value = "通过id获取")
    @GetMapping(value = "/get/{id}")
    public ResultMessage<MemberNoticeLog> get(@PathVariable String id) {
        MemberNoticeLog memberNoticeLog = memberNoticeLogService.getById(id);
        return ResultUtil.data(memberNoticeLog);
    }

    @ApiOperation(value = "获取全部数据")
    @GetMapping(value = "/getAll")
    public ResultMessage<List<MemberNoticeLog>> getAll() {
        List<MemberNoticeLog> list = memberNoticeLogService.list();
        return ResultUtil.data(list);
    }

    @ApiOperation(value = "分页获取")
    @GetMapping(value = "/getByPage")
    public ResultMessage<IPage<MemberNoticeLog>> getByPage(PageVO page) {
        IPage<MemberNoticeLog> data = memberNoticeLogService.page(PageUtil.initPage(page));
        return ResultUtil.data(data);
    }

    @ApiOperation(value = "编辑或更新数据")
    @PostMapping(value = "/insertOrUpdate")
    public ResultMessage<MemberNoticeLog> saveOrUpdate(MemberNoticeLog memberNoticeLog) {
        memberNoticeLogService.saveOrUpdate(memberNoticeLog);
        return ResultUtil.data(memberNoticeLog);
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping(value = "/delByIds/{ids}")
    public ResultMessage<Object> delAllByIds(@PathVariable List ids) {
        memberNoticeLogService.removeByIds(ids);
        return ResultUtil.success();
    }
}
