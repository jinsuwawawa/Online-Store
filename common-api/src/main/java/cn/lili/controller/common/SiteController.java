package cn.store.controller.common;

import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.system.entity.enums.SettingEnum;
import cn.store.modules.system.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 站点基础配置获取
 */
@Slf4j
@RestController
@RequestMapping("/common/common/site")
@Api(tags = "站点基础接口")
public class SiteController {

    @Autowired
    private SettingService settingService;

    @ApiOperation(value = "获取站点基础信息")
    @GetMapping
    public ResultMessage<Object> baseSetting() {
        return ResultUtil.data(settingService.get(SettingEnum.BASE_SETTING.name()));
    }
}
