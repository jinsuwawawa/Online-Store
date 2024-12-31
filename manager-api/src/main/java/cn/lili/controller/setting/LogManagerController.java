package cn.store.controller.setting;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.common.vo.SearchVO;
import cn.store.modules.permission.service.SystemLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 管理端,日志管理接口
 */
@Slf4j
@RestController
@Api(tags = "日志管理接口")
@RequestMapping("/manager/setting/log")
public class LogManagerController {
    @Autowired
    private SystemLogService systemLogService;

    @GetMapping(value = "/getAllByPage")
    @ApiOperation(value = "分页获取全部")
    public ResultMessage<Object> getAllByPage(@RequestParam(required = false) Integer type,
                                              @RequestParam String searchKey,
                                              String operatorName,
                                              SearchVO searchVo,
                                              PageVO pageVo) {
        try {
            return ResultUtil.data(systemLogService.queryLog(null, operatorName, searchKey, searchVo, pageVo));
        } catch (Exception e) {
            log.error("日志获取错误",e);
        }
        return null;
    }


    @ApiOperation(value = "批量删除")
    @DeleteMapping(value = "/{ids}")
    public ResultMessage<Object> delByIds(@PathVariable List<String> ids) {
        systemLogService.deleteLog(ids);
        return ResultUtil.success();
    }

    @DeleteMapping
    @ApiOperation(value = "全部删除")
    public ResultMessage<Object> delAll() {
        systemLogService.flushAll();
        return ResultUtil.success();
    }
}
