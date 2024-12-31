package cn.store.controller.common;

import cn.store.cache.limit.annotation.LimitPoint;
import cn.store.common.enums.ResultCode;
import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.sms.SmsUtil;
import cn.store.modules.verification.entity.enums.VerificationEnums;
import cn.store.modules.verification.service.VerificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 短信验证码接口
 */
@RestController
@Api(tags = "短信验证码接口")
@RequestMapping("/common/common/sms")
public class SmsController {

    @Autowired
    private SmsUtil smsUtil;
    @Autowired
    private VerificationService verificationService;

    @LimitPoint(name = "sms_send", key = "sms")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "String", name = "mobile", value = "手机号"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "uuid", value = "uuid"),
    })
    @GetMapping("/{verificationEnums}/{mobile}")
    @ApiOperation(value = "发送短信验证码,一分钟同一个ip请求1次")
    public ResultMessage getSmsCode(
            @RequestHeader String uuid,
            @PathVariable String mobile,
            @PathVariable VerificationEnums verificationEnums) {
        verificationService.check(uuid, verificationEnums);
        smsUtil.sendSmsCode(mobile, verificationEnums, uuid);
        return ResultUtil.success(ResultCode.VERIFICATION_SEND_SUCCESS);
    }
}
