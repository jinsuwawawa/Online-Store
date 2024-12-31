package cn.store.controller.order;

import cn.store.common.aop.annotation.PreventDuplicateSubmissions;
import cn.store.common.enums.ResultUtil;
import cn.store.common.security.AuthUser;
import cn.store.common.security.OperationalJudgment;
import cn.store.common.security.context.UserContext;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.order.order.entity.dos.OrderComplaint;
import cn.store.modules.order.order.entity.enums.CommunicationOwnerEnum;
import cn.store.modules.order.order.entity.vo.*;
import cn.store.modules.order.order.service.OrderComplaintCommunicationService;
import cn.store.modules.order.order.service.OrderComplaintService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 店铺端,交易投诉接口
 **/
@RestController
@Api(tags = "店铺端,交易投诉接口")
@RequestMapping("/store/order/complain")
public class OrderComplaintStoreController {

    /**
     * 交易投诉
     */
    @Autowired
    private OrderComplaintService orderComplaintService;

    /**
     * 投诉沟通
     */
    @Autowired
    private OrderComplaintCommunicationService orderComplaintCommunicationService;

    @ApiOperation(value = "通过id获取")
    @ApiImplicitParam(name = "id", value = "投诉单ID", required = true, paramType = "path")
    @GetMapping(value = "/{id}")
    public ResultMessage<OrderComplaintVO> get(@PathVariable String id) {
        return ResultUtil.data(OperationalJudgment.judgment(orderComplaintService.getOrderComplainById(id)));
    }

    @ApiOperation(value = "分页获取")
    @GetMapping
    public ResultMessage<IPage<OrderComplaint>> get(OrderComplaintSearchParams searchParams, PageVO pageVO) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        searchParams.setStoreId(storeId);
        return ResultUtil.data(orderComplaintService.getOrderComplainByPage(searchParams, pageVO));
    }

    @ApiOperation(value = "添加交易投诉对话")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "complainId", value = "投诉单ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, paramType = "query")
    })
    @PostMapping("/communication")
    public ResultMessage<OrderComplaintCommunicationVO> addCommunication(@RequestParam String complainId, @RequestParam String content) {
        AuthUser currentUser = Objects.requireNonNull(UserContext.getCurrentUser());
        OrderComplaintCommunicationVO communicationVO = new OrderComplaintCommunicationVO(complainId, content, CommunicationOwnerEnum.STORE.name(), currentUser.getUsername(), currentUser.getStoreId());
        orderComplaintCommunicationService.addCommunication(communicationVO);
        return ResultUtil.success();
    }

    @ApiOperation(value = "修改申诉信息")
    @PutMapping
    public ResultMessage<OrderComplaintVO> update(OrderComplaintVO orderComplainVO) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        orderComplainVO.setStoreId(storeId);
        orderComplaintService.updateOrderComplain(orderComplainVO);
        return ResultUtil.data(orderComplainVO);
    }

    @PreventDuplicateSubmissions
    @ApiOperation(value = "申诉")
    @PutMapping("/appeal")
    public ResultMessage<OrderComplaintVO> appeal(StoreAppealVO storeAppealVO) {
        orderComplaintService.appeal(storeAppealVO);
        return ResultUtil.data(orderComplaintService.getOrderComplainById(storeAppealVO.getOrderComplaintId()));
    }

    @PreventDuplicateSubmissions
    @ApiOperation(value = "修改状态")
    @PutMapping(value = "/status")
    public ResultMessage<Object> updateStatus(OrderComplaintOperationParams orderComplainVO) {
        orderComplaintService.updateOrderComplainByStatus(orderComplainVO);
        return ResultUtil.success();
    }

}
