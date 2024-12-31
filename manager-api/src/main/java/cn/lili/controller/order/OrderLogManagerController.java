package cn.store.controller.order;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.common.vo.SearchVO;
import cn.store.modules.order.trade.entity.dos.OrderLog;
import cn.store.modules.order.trade.service.OrderLogService;
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
 * 管理端,订单日志管理接口
 */
@RestController
@Api(tags = "管理端,订单日志管理接口")
@RequestMapping("/manager/order/orderLog")
public class OrderLogManagerController {
    @Autowired
    private OrderLogService orderLogService;

    @GetMapping(value = "/get/{id}")
    @ApiOperation(value = "通过id获取")
    public ResultMessage<OrderLog> get(@PathVariable String id) {

        return ResultUtil.data(orderLogService.getById(id));
    }

    @GetMapping(value = "/getByPage")
    @ApiOperation(value = "分页获取")
    public ResultMessage<IPage<OrderLog>> getByPage(OrderLog entity,
                                                    SearchVO searchVo,
                                                    PageVO page) {
        return ResultUtil.data(orderLogService.page(PageUtil.initPage(page), PageUtil.initWrapper(entity, searchVo)));
    }

}
