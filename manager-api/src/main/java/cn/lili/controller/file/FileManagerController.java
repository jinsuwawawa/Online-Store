package cn.store.controller.file;

import cn.store.common.enums.ResultUtil;
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
 * 管理端,文件管理管理接口
 */
@RestController
@Api(tags = "管理端,文件管理接口")
@RequestMapping("/manager/common/file")
public class FileManagerController {

    @Autowired
    private FileService fileService;


    @ApiOperation(value = "管理端管理所有图片")
    @GetMapping
    @ApiImplicitParam(name = "title", value = "名称模糊匹配")
    public ResultMessage<IPage<File>> adminFiles(FileOwnerDTO fileOwnerDTO) {

        return ResultUtil.data(fileService.customerPage(fileOwnerDTO));
    }


    @ApiOperation(value = "文件重命名")
    @PostMapping(value = "/rename")
    public ResultMessage<File> upload(String id, String newName) {
        File file = fileService.getById(id);
        file.setName(newName);
        fileService.updateById(file);
        return ResultUtil.data(file);
    }

    @ApiOperation(value = "文件删除")
    @DeleteMapping(value = "/delete/{ids}")
    public ResultMessage delete(@PathVariable List<String> ids) {
        fileService.batchDelete(ids);
        return ResultUtil.success();
    }

}
