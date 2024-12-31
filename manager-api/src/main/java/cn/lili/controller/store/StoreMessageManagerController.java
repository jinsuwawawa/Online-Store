package cn.store.controller.store;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.message.entity.dos.StoreMessage;
import cn.store.modules.message.entity.vos.StoreMessageQueryVO;
import cn.store.modules.message.service.StoreMessageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 管理端,店铺消息消息管理接口
 */
@RestController
@Api(tags = "管理端,店铺消息消息管理接口")
@RequestMapping("/manager/other/storeMessage")
public class StoreMessageManagerController {

    @Autowired
    private StoreMessageService storeMessageService;

    @GetMapping
    @ApiOperation(value = "多条件分页获取")
    public ResultMessage<IPage<StoreMessage>> getByCondition(StoreMessageQueryVO storeMessageQueryVO,
                                                             PageVO pageVo) {
        IPage<StoreMessage> page = storeMessageService.getPage(storeMessageQueryVO, pageVo);
        return ResultUtil.data(page);
    }

}
