package cn.store.controller.order;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.common.vo.SearchVO;
import cn.store.modules.order.order.entity.dos.Order;
import cn.store.modules.order.order.entity.vo.PaymentLog;
import cn.store.modules.order.order.service.OrderService;
import cn.store.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 管理端,收款日志接口
 */
@RestController
@Api(tags = "管理端,收款日志接口")
@RequestMapping("/manager/order/paymentLog")
public class PaymentLogManagerController {

    @Autowired
    private OrderService orderService;


    @GetMapping
    @ApiOperation(value = "分页获取支付日志")
    public ResultMessage<IPage<PaymentLog>> getByPage(Order order,
                                                      SearchVO searchVo,
                                                      PageVO page) {
        return ResultUtil.data(orderService.queryPaymentLogs(PageUtil.initPage(page), PageUtil.initWrapper(order, searchVo)));
    }
}
