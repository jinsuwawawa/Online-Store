package cn.store.controller.common;

import cn.store.common.enums.ResultCode;
import cn.store.common.enums.ResultUtil;
import cn.store.common.security.context.UserContext;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.file.entity.FileDirectory;
import cn.store.modules.file.entity.dto.FileDirectoryDTO;
import cn.store.modules.file.service.FileDirectoryService;
import cn.store.modules.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 文件目录管理接口
 */
@RestController
@Api(tags = "文件目录管理接口")
@RequestMapping("/common/resource/fileDirectory")
@RequiredArgsConstructor
public class FileDirectoryController {

    private final FileDirectoryService fileDirectoryService;
    private final FileService fileService;

    @ApiOperation(value = "获取文件目录列表")
    @GetMapping
    public ResultMessage<List<FileDirectoryDTO>> getSceneFileList() {
        return ResultUtil.data(fileDirectoryService.getFileDirectoryList(UserContext.getCurrentUser().getId()));
    }

    @ApiOperation(value = "添加文件目录")
    @PostMapping
    public ResultMessage<FileDirectory> addSceneFileList(@RequestBody @Valid FileDirectory fileDirectory) {
        fileDirectory.setDirectoryType(UserContext.getCurrentUser().getRole().name());
        fileDirectory.setOwnerId(UserContext.getCurrentUser().getId());
        fileDirectoryService.save(fileDirectory);
        return ResultUtil.data(fileDirectory);
    }

    @ApiOperation(value = "修改文件目录")
    @PutMapping
    public ResultMessage<FileDirectory> editSceneFileList(@RequestBody @Valid FileDirectory fileDirectory) {
        fileDirectory.setDirectoryType(UserContext.getCurrentUser().getRole().name());
        fileDirectory.setOwnerId(UserContext.getCurrentUser().getId());
        fileDirectoryService.updateById(fileDirectory);
        return ResultUtil.data(fileDirectory);
    }

    @ApiOperation(value = "删除文件目录")
    @DeleteMapping("/{id}")
    public ResultMessage<Object> deleteSceneFileList(@PathVariable String id) {
        //检测文件夹下是否包含图片
        if(fileService.countByDirectory(id)){
            return ResultUtil.error(ResultCode.FILE_DIRECTORY_NOT_EMPTY);
        }
        //删除目录
        fileDirectoryService.removeById(id);
        return ResultUtil.success();
    }

}
