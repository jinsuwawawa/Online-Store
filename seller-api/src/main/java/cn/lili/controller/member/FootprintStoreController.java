package cn.store.controller.member;

import cn.store.common.enums.ResultUtil;
import cn.store.common.security.context.UserContext;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.member.entity.dto.FootPrintQueryParams;
import cn.store.modules.member.service.FootprintService;
import cn.store.modules.search.entity.dos.EsGoodsIndex;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商家端,浏览历史接口
 */
@RestController
@Api(tags = "商家端,浏览历史接口")
@RequestMapping("/store/member/footprint")
public class FootprintStoreController {
    @Autowired
    private FootprintService footprintService;

    @ApiOperation(value = "分页获取")
    @GetMapping
    public ResultMessage<IPage<EsGoodsIndex>> getByPage(FootPrintQueryParams params) {
        return ResultUtil.data(footprintService.footPrintPage(params));
    }
}
