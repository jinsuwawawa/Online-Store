package cn.store.controller.common;


import cn.hutool.json.JSONUtil;
import cn.store.common.enums.ResultCode;
import cn.store.common.enums.ResultUtil;
import cn.store.common.exception.ServiceException;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.system.entity.dos.Setting;
import cn.store.modules.system.entity.dto.ImSetting;
import cn.store.modules.system.entity.enums.SettingEnum;
import cn.store.modules.system.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IM控制器
 */
@RestController
@RequestMapping("/common/common/IM")
@Api(tags = "IM 中心")
public class IMController {

    @Autowired
    private SettingService settingService;

    @ApiOperation(value = "获取IM接口前缀")
    @GetMapping
    public ResultMessage<String> getUrl() {
        String imUrl;
        try {
            Setting imSettingVal = settingService.get(SettingEnum.IM_SETTING.name());
            ImSetting imSetting = JSONUtil.toBean(imSettingVal.getSettingValue(), ImSetting.class);
            imUrl = imSetting.getHttpUrl();
        } catch (Exception e) {
            throw new ServiceException(ResultCode.PLATFORM_NOT_SUPPORTED_IM);
        }
        return ResultUtil.data(imUrl);
    }

}
