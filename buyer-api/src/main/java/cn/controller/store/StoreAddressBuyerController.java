package cn.store.controller.store;

import cn.store.common.enums.ResultUtil;
import cn.store.common.security.OperationalJudgment;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.store.entity.dos.StoreAddress;
import cn.store.modules.store.service.StoreAddressService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 买家端,商家地址（自提点）接口
 */
@RestController
@Api(tags = "买家端,商家地址（自提点）接口")
@RequestMapping("/buyer/store/address")
public class StoreAddressBuyerController {

    /**
     * 店铺自提点
     */
    @Autowired
    private StoreAddressService storeAddressService;

    @ApiOperation(value = "获取商家自提点分页")
    @ApiImplicitParam(name = "storeId", value = "店铺Id", required = true, dataType = "String", paramType = "path")
    @GetMapping("/page/{storeId}")
    public ResultMessage<IPage<StoreAddress>> get(PageVO pageVo,@PathVariable String storeId) {
        return ResultUtil.data(storeAddressService.getStoreAddress(storeId, pageVo));
    }

    @ApiOperation(value = "获取商家自提点信息")
    @ApiImplicitParam(name = "id", value = "自提点ID", required = true, paramType = "path")
    @GetMapping("/{id}")
    public ResultMessage<StoreAddress> get(@PathVariable String id) {
        StoreAddress address = OperationalJudgment.judgment(storeAddressService.getById(id));
        return ResultUtil.data(address);
    }
}
