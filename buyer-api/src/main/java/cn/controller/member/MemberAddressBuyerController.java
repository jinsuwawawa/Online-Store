package cn.store.controller.member;

import cn.store.common.enums.ResultUtil;
import cn.store.common.security.OperationalJudgment;
import cn.store.common.security.context.UserContext;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.member.entity.dos.MemberAddress;
import cn.store.modules.member.service.MemberAddressService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;


/**
 * 买家端,会员地址接口
 */
@RestController
@Api(tags = "买家端,会员地址接口")
@RequestMapping("/buyer/member/address")
public class MemberAddressBuyerController {

    /**
     * 会员收件地址
     */
    @Autowired
    private MemberAddressService memberAddressService;

    @ApiOperation(value = "获取会员收件地址分页列表")
    @GetMapping
    public ResultMessage<IPage<MemberAddress>> page(PageVO page) {
        return ResultUtil.data(memberAddressService.getAddressByMember(page, UserContext.getCurrentUser().getId()));
    }

    @ApiOperation(value = "根据ID获取会员收件地址")
    @ApiImplicitParam(name = "id", value = "会员地址ID", dataType = "String", paramType = "path")
    @GetMapping(value = "/get/{id}")
    public ResultMessage<MemberAddress> getShippingAddress(@PathVariable String id) {
        return ResultUtil.data(memberAddressService.getMemberAddress(id));
    }

    @ApiOperation(value = "获取当前会员默认收件地址")
    @GetMapping(value = "/get/default")
    public ResultMessage<MemberAddress> getDefaultShippingAddress() {
        return ResultUtil.data(memberAddressService.getDefaultMemberAddress());
    }

    @ApiOperation(value = "新增会员收件地址")
    @PostMapping
    public ResultMessage<MemberAddress> addShippingAddress(@Valid MemberAddress shippingAddress) {
        //添加会员地址
        shippingAddress.setMemberId(Objects.requireNonNull(UserContext.getCurrentUser()).getId());
        if(shippingAddress.getIsDefault()==null){
            shippingAddress.setIsDefault(false);
        }
        return ResultUtil.data(memberAddressService.saveMemberAddress(shippingAddress));
    }

    @ApiOperation(value = "修改会员收件地址")
    @PutMapping
    public ResultMessage<MemberAddress> editShippingAddress(@Valid MemberAddress shippingAddress) {
        OperationalJudgment.judgment(memberAddressService.getById(shippingAddress.getId()));
        shippingAddress.setMemberId(Objects.requireNonNull(UserContext.getCurrentUser()).getId());
        return ResultUtil.data(memberAddressService.updateMemberAddress(shippingAddress));
    }

    @ApiOperation(value = "删除会员收件地址")
    @ApiImplicitParam(name = "id", value = "会员地址ID", dataType = "String", paramType = "path")
    @DeleteMapping(value = "/delById/{id}")
    public ResultMessage<Object> delShippingAddressById(@PathVariable String id) {
        OperationalJudgment.judgment(memberAddressService.getById(id));
        memberAddressService.removeMemberAddress(id);
        return ResultUtil.success();
    }

}
