package cn.store.controller.common;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.store.cache.Cache;
import cn.store.common.enums.ResultCode;
import cn.store.common.enums.ResultUtil;
import cn.store.common.exception.ServiceException;
import cn.store.common.security.AuthUser;
import cn.store.common.security.context.UserContext;
import cn.store.common.security.enums.UserEnums;
import cn.store.common.utils.Base64DecodeMultipartFile;
import cn.store.common.utils.CommonUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.file.entity.File;
import cn.store.modules.file.plugin.FilePluginFactory;
import cn.store.modules.file.service.FileService;
import cn.store.modules.system.entity.dos.Setting;
import cn.store.modules.system.entity.enums.SettingEnum;
import cn.store.modules.system.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

/**
 * 文件上传接口
 */
@Slf4j
@RestController
@Api(tags = "文件上传接口")
@RequestMapping("/common/common/upload")
public class UploadController {

    @Autowired
    private FileService fileService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private FilePluginFactory filePluginFactory;
    @Autowired
    private Cache cache;

    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/file")
    public ResultMessage<Object> upload(MultipartFile file,
                                        String base64,
                                        @RequestHeader String accessToken, @RequestParam String directoryPath) {

        if(StrUtil.isBlank(directoryPath)){
            directoryPath = "default";
        }

        AuthUser authUser = UserContext.getAuthUser(cache, accessToken);
        //如果用户未登录，则无法上传图片
        if (authUser == null) {
            throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
        }
        if (file == null) {
            throw new ServiceException(ResultCode.FILE_NOT_EXIST_ERROR);
        }
        Setting setting = settingService.get(SettingEnum.OSS_SETTING.name());
        if (setting == null || CharSequenceUtil.isBlank(setting.getSettingValue())) {
            throw new ServiceException(ResultCode.OSS_NOT_EXIST);
        }
        if (CharSequenceUtil.isEmpty(file.getContentType())) {
            throw new ServiceException(ResultCode.IMAGE_FILE_EXT_ERROR);
        }


        if (!CharSequenceUtil.containsAny(Objects.requireNonNull(file.getContentType()).toLowerCase(), "image", "video")) {
            throw new ServiceException(ResultCode.FILE_TYPE_NOT_SUPPORT);
        }

        if (CharSequenceUtil.isNotBlank(base64)) {
            //base64上传
            file = Base64DecodeMultipartFile.base64Convert(base64);
        }
        String result;
        String fileKey = CommonUtil.rename(Objects.requireNonNull(file.getOriginalFilename()));
        File newFile = new File();
        try {
            InputStream inputStream = file.getInputStream();
            //上传至第三方云服务或服务器
            String scene = UserContext.getCurrentUser().getRole().name();
            if (StrUtil.equalsAny(UserContext.getCurrentUser().getRole().name(), UserEnums.MEMBER.name(), UserEnums.STORE.name(), UserEnums.SEAT.name())) {
                scene = scene + "/" + authUser.getId();
            }
            fileKey = scene + "/" + directoryPath + "/" + fileKey;
            //上传至第三方云服务或服务器
            result = filePluginFactory.filePlugin().inputStreamUpload(inputStream, fileKey);
            //保存数据信息至数据库
            newFile.setName(file.getOriginalFilename());
            newFile.setFileSize(file.getSize());
            newFile.setFileType(file.getContentType());
            newFile.setFileKey(fileKey);
            newFile.setUrl(result);
            newFile.setCreateBy(authUser.getUsername());
            newFile.setUserEnums(authUser.getRole().name());
            //如果是店铺，则记录店铺id
            if (authUser.getRole().equals(UserEnums.STORE)) {
                newFile.setOwnerId(authUser.getStoreId());
                newFile.setOwnerName(authUser.getStoreName());
            } else {
                newFile.setOwnerId(authUser.getId());
                newFile.setOwnerName(authUser.getNickName());
            }

            //存储文件目录
            if (StrUtil.isNotEmpty(directoryPath)) {
                if (directoryPath.indexOf("/") > 0) {
                    newFile.setFileDirectoryId(directoryPath.substring(directoryPath.lastIndexOf("/") + 1));
                } else {
                    newFile.setFileDirectoryId(directoryPath);
                }
            }
            fileService.save(newFile);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new ServiceException(ResultCode.OSS_EXCEPTION_ERROR);
        }
        return ResultUtil.data(result);
    }
}
