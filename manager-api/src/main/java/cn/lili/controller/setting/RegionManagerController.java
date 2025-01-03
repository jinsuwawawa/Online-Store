package cn.store.controller.setting;

import cn.store.common.aop.annotation.DemoSite;
import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.system.entity.dos.Region;
import cn.store.modules.system.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 管理端,行政地区管理接口
 */
@RestController
@Api(tags = "管理端,行政地区管理接口")
@RequestMapping("/manager/setting/region")
public class RegionManagerController {
    @Autowired
    private RegionService regionService;

    @DemoSite
    @PostMapping(value = "/sync")
    @ApiOperation(value = "同步高德行政地区数据")
    public void synchronizationData(String url) {
        regionService.synchronizationData(url);
    }

    @GetMapping(value = "/{id}")
    @ApiImplicitParam(name = "id", value = "地区ID", required = true, dataType = "String", paramType = "path")
    @ApiOperation(value = "通过id获取地区详情")
    public ResultMessage<Region> get(@PathVariable String id) {
        return ResultUtil.data(regionService.getById(id));
    }

    @GetMapping(value = "/item/{id}")
    @ApiImplicitParam(name = "id", value = "地区ID", required = true, dataType = "String", paramType = "path")
    @ApiOperation(value = "通过id获取子地区")
    public ResultMessage<List<Region>> getItem(@PathVariable String id) {
        return ResultUtil.data(regionService.getItem(id));
    }

    @DemoSite
    @PutMapping(value = "/{id}")
    @ApiImplicitParam(name = "id", value = "地区ID", required = true, dataType = "String", paramType = "path")
    @ApiOperation(value = "更新地区")
    public ResultMessage<Region> update(@PathVariable String id, @Valid Region region) {
        region.setId(id);
        regionService.updateById(region);
        return ResultUtil.data(region);
    }


    @DemoSite
    @PostMapping
    @ApiOperation(value = "增加地区")
    public ResultMessage<Region> save(@Valid Region region) {
        regionService.save(region);
        return ResultUtil.data(region);
    }

    @DemoSite
    @DeleteMapping(value = "{ids}")
    @ApiImplicitParam(name = "id", value = "地区ID", required = true, dataType = "String", allowMultiple = true, paramType = "path")
    @ApiOperation(value = "批量通过id删除")
    public ResultMessage<Object> delAllByIds(@PathVariable List<String> ids) {
        regionService.removeByIds(ids);
        return ResultUtil.success();
    }
}
