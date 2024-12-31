package cn.store.controller.store.seat;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.im.entity.vo.SeatVO;
import cn.store.modules.im.service.SeatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SeatController
 */
@RestController
@Api(tags = "店铺端,坐席管理")
@RequestMapping("/manager/seat/setting")
@Transactional(rollbackFor = Exception.class)
public class SeatStoreManagerController {

    @Autowired
    private SeatService seatService;

    @ApiOperation(value = "查看店铺坐席列表")
    @GetMapping("/list")
    public ResultMessage<List<SeatVO>> getSeats(String storeId) {
        return ResultUtil.data(seatService.seatVoList(storeId));
    }

}
