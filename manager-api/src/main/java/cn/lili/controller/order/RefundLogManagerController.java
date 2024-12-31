package cn.store.controller.order;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.common.vo.SearchVO;
import cn.store.modules.payment.entity.RefundLog;
import cn.store.modules.payment.service.RefundLogService;
import cn.store.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端,退款日志接口
 */
@RestController
@Api(tags = "管理端,退款日志接口")
@RequestMapping("/manager/order/refundLog")
public class RefundLogManagerController {
    @Autowired
    private RefundLogService refundLogService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查看退款日志详情")
    public ResultMessage<RefundLog> get(@PathVariable String id) {
        return ResultUtil.data(refundLogService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "分页获取退款日志")
    public ResultMessage<IPage<RefundLog>> getByPage(RefundLog entity, SearchVO searchVo, PageVO page) {
        return ResultUtil.data(refundLogService.page(PageUtil.initPage(page), PageUtil.initWrapper(entity, searchVo)));
    }

}
