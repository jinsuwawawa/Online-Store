package cn.store.controller.common;

import cn.store.common.utils.IpHelper;
import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理端,IP接口
 */
@RestController
@Api(tags = "获取IP信息以及天气")
@RequestMapping("/common/common/ip")
public class IpInfoManagerController {
    @Autowired
    private IpHelper ipHelper;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "IP及天气相关信息")
    public ResultMessage<Object> upload(HttpServletRequest request) {

        String result = ipHelper.getIpCity(request);
        return ResultUtil.data(result);
    }
}