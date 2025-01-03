package cn.store.controller.common;

import cn.store.cache.Cache;
import cn.store.common.context.ThreadContextHolder;
import cn.store.common.enums.ResultCode;
import cn.store.common.enums.ResultUtil;
import cn.store.common.exception.ServiceException;
import cn.store.common.security.AuthUser;
import cn.store.common.security.context.UserContext;
import cn.store.common.security.enums.UserEnums;
import cn.store.common.utils.ResponseUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.file.entity.File;
import cn.store.modules.file.entity.dto.FileOwnerDTO;
import cn.store.modules.file.service.FileService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 文件管理管理接口
 */
@RestController
@Api(tags = "文件管理接口")
@RequestMapping("/common/common/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private Cache cache;

    @ApiOperation(value = "获取自己的图片资源")
    @GetMapping
    @ApiImplicitParam(name = "title", value = "名称模糊匹配")
    public ResultMessage<IPage<File>> getFileList(@RequestHeader String accessToken, FileOwnerDTO fileOwnerDTO) {

        AuthUser authUser = UserContext.getAuthUser(cache, accessToken);
        if (authUser == null) {
            ResponseUtil.output(ThreadContextHolder.getHttpResponse(), 403, ResponseUtil.resultMap(false,
                    403, "登录已失效，请重新登录"));
            return null;
        }
        //只有买家才写入自己id
        if (authUser.getRole().equals(UserEnums.MEMBER)) {
            fileOwnerDTO.setOwnerId(authUser.getId());
        }//如果是店铺，则写入店铺id
        else if (authUser.getRole().equals(UserEnums.STORE)) {
            fileOwnerDTO.setOwnerId(authUser.getStoreId());
        }
        fileOwnerDTO.setUserEnums(authUser.getRole().name());
        return ResultUtil.data(fileService.customerPageOwner(fileOwnerDTO));
    }

    @ApiOperation(value = "文件重命名")
    @PostMapping(value = "/rename")
    public ResultMessage<File> upload(@RequestHeader String accessToken, String id, String newName) {

        AuthUser authUser = UserContext.getAuthUser(cache, accessToken);
        File file = fileService.getById(id);
        file.setName(newName);
        //操作图片属性判定
        switch (authUser.getRole()) {
            case MEMBER:
                if (file.getOwnerId().equals(authUser.getId()) && file.getUserEnums().equals(authUser.getRole().name())) {
                    break;
                }
                throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
            case STORE:
                if (file.getOwnerId().equals(authUser.getStoreId()) && file.getUserEnums().equals(authUser.getRole().name())) {
                    break;
                }
                throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
            case MANAGER:
                break;
            default:
                throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
        }
        fileService.updateById(file);
        return ResultUtil.data(file);
    }

    @ApiOperation(value = "文件删除")
    @DeleteMapping(value = "/delete/{ids}")
    public ResultMessage delete(@RequestHeader String accessToken, @PathVariable List<String> ids) {

        AuthUser authUser = UserContext.getAuthUser(cache, accessToken);
        fileService.batchDelete(ids, authUser);
        return ResultUtil.success();
    }

}
