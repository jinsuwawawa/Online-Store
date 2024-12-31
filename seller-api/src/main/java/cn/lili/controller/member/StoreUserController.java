package cn.store.controller.member;

import cn.store.common.enums.ResultCode;
import cn.store.common.exception.ServiceException;
import cn.store.common.security.AuthUser;
import cn.store.common.security.context.UserContext;
import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.member.entity.dos.Member;
import cn.store.modules.member.service.MemberService;
import cn.store.modules.store.entity.dos.Store;
import cn.store.modules.store.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 店铺端,管理员接口
 */
@RestController
@Api(tags = "店铺端,管理员接口")
@RequestMapping("/store/member/user")
public class StoreUserController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private StoreService storeService;


    @GetMapping(value = "/info")
    @ApiOperation(value = "获取当前登录用户接口")
    public ResultMessage<Member> getUserInfo() {
        AuthUser tokenUser = UserContext.getCurrentUser();
        if (tokenUser != null) {
            Member member = memberService.findByUsername(tokenUser.getUsername());
            member.setPassword(null);
            return ResultUtil.data(member);
        }
        throw new ServiceException(ResultCode.USER_NOT_LOGIN);
    }

    @GetMapping(value = "")
    @ApiOperation(value = "获取当前登录店铺接口")
    public ResultMessage<Store> getStoreInfo() {
        AuthUser tokenUser = UserContext.getCurrentUser();
        if (tokenUser != null) {
            Store store = storeService.getById(tokenUser.getStoreId());
            return ResultUtil.data(store);
        }
        throw new ServiceException(ResultCode.USER_NOT_LOGIN);
    }


}
