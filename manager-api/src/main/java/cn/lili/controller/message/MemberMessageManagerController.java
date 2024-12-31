package cn.store.controller.message;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.message.entity.dos.MemberMessage;
import cn.store.modules.message.entity.vos.MemberMessageQueryVO;
import cn.store.modules.message.service.MemberMessageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 管理端,会员消息消息管理接口
 */
@RestController
@Api(tags = "管理端,会员消息消息管理接口")
@RequestMapping("/manager/other/memberMessage")
public class MemberMessageManagerController {
    @Autowired
    private MemberMessageService memberMessageService;


    @GetMapping
    @ApiOperation(value = "多条件分页获取")
    public ResultMessage<IPage<MemberMessage>> getByCondition(MemberMessageQueryVO memberMessageQueryVO,
                                                              PageVO pageVo) {
        IPage<MemberMessage> page = memberMessageService.getPage(memberMessageQueryVO, pageVo);
        return ResultUtil.data(page);
    }

}
