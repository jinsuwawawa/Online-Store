package cn.store.controller.member;

import cn.store.common.enums.ResultUtil;
import cn.store.common.security.context.UserContext;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.member.entity.dto.FootPrintQueryParams;
import cn.store.modules.member.service.FootprintService;
import cn.store.modules.search.entity.dos.EsGoodsIndex;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 买家端,浏览历史接口
 */
@RestController
@Api(tags = "买家端,浏览历史接口")
@RequestMapping("/buyer/member/footprint")
public class FootprintController {

    /**
     * 会员足迹
     */
    @Autowired
    private FootprintService footprintService;

    @ApiOperation(value = "分页获取")
    @GetMapping
    public ResultMessage<IPage<EsGoodsIndex>> getByPage(FootPrintQueryParams params) {
        params.setMemberId(UserContext.getCurrentUser().getId());
        return ResultUtil.data(footprintService.footPrintPage(params));
    }

    @ApiOperation(value = "根据id删除")
    @ApiImplicitParam(name = "ids", value = "商品ID", required = true, allowMultiple = true, dataType = "String", paramType = "path")
    @DeleteMapping(value = "/delByIds/{ids}")
    public ResultMessage<Object> delAllByIds(@NotNull(message = "商品ID不能为空") @PathVariable("ids") List ids) {
        footprintService.deleteByIds(ids);
        return ResultUtil.success();

    }

    @ApiOperation(value = "清空足迹")
    @DeleteMapping
    public ResultMessage<Object> deleteAll() {
        footprintService.clean();
        return ResultUtil.success();
    }

    @ApiOperation(value = "获取当前会员足迹数量")
    @GetMapping(value = "/getFootprintNum")
    public ResultMessage<Object> getFootprintNum() {
        return ResultUtil.data(footprintService.getFootprintNum());
    }


    @GetMapping("/history")
    @ApiOperation(value = "获取会员的历史足迹")
    public ResultMessage<IPage<EsGoodsIndex>> getMemberHistory(FootPrintQueryParams params) {
        return ResultUtil.data(footprintService.footPrintPage(params));
    }
}
