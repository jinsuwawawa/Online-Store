package cn.store.controller.common;

import cn.store.cache.limit.annotation.LimitPoint;
import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.verification.entity.enums.VerificationEnums;
import cn.store.modules.verification.service.VerificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 滑块验证码接口
 */
@Slf4j
@RestController
@RequestMapping("/common/common/slider")
@Api(tags = "滑块验证码接口")
public class SliderImageController {

    @Autowired
    private VerificationService verificationService;

    @LimitPoint(name = "slider_image", key = "verification")
    @GetMapping("/{verificationEnums}")
    @ApiOperation(value = "获取校验接口,一分钟同一个ip请求10次")
    public ResultMessage getSliderImage(@RequestHeader String uuid, @PathVariable VerificationEnums verificationEnums) {
        return ResultUtil.data(verificationService.createVerification(verificationEnums, uuid));

    }

    @LimitPoint(name = "slider_image", key = "verification_pre_check", limit = 600)
    @PostMapping("/{verificationEnums}")
    @ApiOperation(value = "验证码预校验")
    public ResultMessage verificationImage(Integer xPos, @RequestHeader String uuid, @PathVariable VerificationEnums verificationEnums) {
        return ResultUtil.data(verificationService.preCheck(xPos, uuid, verificationEnums));
    }
}
