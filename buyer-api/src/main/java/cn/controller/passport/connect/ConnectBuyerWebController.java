package cn.store.controller.passport.connect;


import cn.store.common.enums.ResultCode;
import cn.store.common.enums.ResultUtil;
import cn.store.common.exception.ServiceException;
import cn.store.common.security.token.Token;
import cn.store.common.utils.UuidUtils;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.connect.entity.dto.AuthCallback;
import cn.store.modules.connect.entity.dto.ConnectAuthUser;
import cn.store.modules.connect.request.AuthRequest;
import cn.store.modules.connect.service.ConnectService;
import cn.store.modules.connect.util.ConnectUtil;
import cn.store.modules.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 买家端,web联合登录
 */
@Slf4j
@RestController
@Api(tags = "买家端,web联合登录")
@RequestMapping("/buyer/passport/connect/connect")
public class ConnectBuyerWebController {

    @Autowired
    private ConnectService connectService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ConnectUtil connectUtil;


    @GetMapping("/login/web/{type}")
    @ApiOperation(value = "WEB信任登录授权,包含PC、WAP")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "登录方式:QQ,微信,微信_PC",
                    allowableValues = "QQ,WECHAT,WECHAT_PC", paramType = "path")
    })
    public ResultMessage<String> webAuthorize(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = connectUtil.getAuthRequest(type);
        String authorizeUrl = authRequest.authorize(UuidUtils.getUUID());
        response.sendRedirect(authorizeUrl);
        return ResultUtil.data(authorizeUrl);
    }


    @ApiOperation(value = "信任登录统一回调地址", hidden = true)
    @GetMapping("/callback/{type}")
    public void callBack(@PathVariable String type, AuthCallback callback, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        connectUtil.callback(type, callback, httpServletRequest, httpServletResponse);
    }

    @ApiOperation(value = "信任登录响应结果获取")
    @GetMapping("/result")
    public ResultMessage<Object> callBackResult(String state) {
        if (state == null) {
            throw new ServiceException(ResultCode.USER_CONNECT_LOGIN_ERROR);
        }
        return connectUtil.getResult(state);
    }

    @ApiOperation(value = "APP-unionID登录")
    @PostMapping("/app/login")
    public ResultMessage<Token> unionLogin(@RequestBody ConnectAuthUser authUser, @RequestHeader("uuid") String uuid) {
        try {
            return ResultUtil.data(connectService.unionLoginCallback(authUser, uuid));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("unionID登录错误", e);
        }
        return null;
    }

}
