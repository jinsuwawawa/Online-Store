package cn.store.controller.store.seat;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.im.service.SeatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 坐席登录接口
 */
@Slf4j
@RestController
@Api(tags = "坐席端")
@RequestMapping("/seat/login")
public class SeatLogin {

    @Autowired
    private SeatService seatService;

    @ApiOperation(value = "登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query")
    })
    @PostMapping("/userLogin")
    public ResultMessage<Object> userLogin(String username, String password) {
        return ResultUtil.data(this.seatService.usernameLogin(username, password));
    }

    @ApiOperation(value = "商家快捷登录客服")
    @PostMapping("/quicklogin")
    public ResultMessage<Object> quickLogin(String code) {
        return ResultUtil.data(this.seatService.quickLogin(code));
    }


    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public ResultMessage<Object> logout() {
        //todo
//        UserContext.getCurrentUser().getId()
//        verificationServiceClient.check(uuid);
        return ResultUtil.success();
    }


}
