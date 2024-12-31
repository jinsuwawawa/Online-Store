package cn.store.controller.setting;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.system.entity.dto.payment.dto.PaymentSupportForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端,系统设置扩展接口
 * 对一些系统设置的支持，例如动态表单等
 */
@RestController
@Api(tags = "管理端,系统设置扩展接口")
@RequestMapping("/manager/setting/settingx")
public class SettingXManagerController {

    @ApiOperation(value = "支持支付方式表单")
    @GetMapping("/paymentSupport")
    public ResultMessage<PaymentSupportForm> paymentForm() {
        return ResultUtil.data(new PaymentSupportForm());
    }

}
