package cn.store.controller.settings;

import cn.store.common.enums.ResultCode;
import cn.store.common.enums.ResultUtil;
import cn.store.common.exception.ServiceException;
import cn.store.common.security.context.UserContext;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.page.entity.dos.PageData;
import cn.store.modules.page.entity.dto.PageDataDTO;
import cn.store.modules.page.entity.enums.PageEnum;
import cn.store.modules.page.entity.vos.PageDataListVO;
import cn.store.modules.page.service.PageDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 店铺端,页面设置管理接口
 */
@RestController
@Api(tags = "店铺端,页面设置管理接口")
@RequestMapping("/store/settings/pageData")
public class PageDataStoreController {

    @Autowired
    private PageDataService pageDataService;

    @ApiOperation(value = "页面列表")
    @ApiImplicitParam(name = "pageClientType", value = "客户端类型", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{pageClientType}/pageDataList")
    public ResultMessage<IPage<PageDataListVO>> pageDataList(@PathVariable String pageClientType, PageVO pageVO) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        PageDataDTO pageDataDTO = new PageDataDTO();
        pageDataDTO.setPageType(PageEnum.STORE.name());
        pageDataDTO.setPageClientType(pageClientType);
        pageDataDTO.setNum(storeId);
        return ResultUtil.data(pageDataService.getPageDataList(pageVO, pageDataDTO));
    }

    @ApiOperation(value = "获取页面信息")
    @ApiImplicitParam(name = "id", value = "页面ID", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/{id}")
    public ResultMessage<PageData> getPageData(@PathVariable String id) {
        //查询当前店铺下的页面数据
        PageData pageData = pageDataService.getOne(
                new LambdaQueryWrapper<PageData>()
                        .eq(PageData::getPageType, PageEnum.STORE.name())
                        .eq(PageData::getNum, UserContext.getCurrentUser().getStoreId())
                        .eq(PageData::getId, id));
        return ResultUtil.data(pageData);
    }

    @ApiOperation(value = "添加页面")
    @PostMapping("/save")
    public ResultMessage<PageData> addPageData(@Valid PageData pageData) {
        //添加店铺类型，填写店铺ID
        pageData.setPageType(PageEnum.STORE.name());
        pageData.setNum(UserContext.getCurrentUser().getStoreId());
        return ResultUtil.data(pageDataService.addPageData(pageData));
    }

    @ApiOperation(value = "修改页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "页面ID", required = true, dataType = "String", paramType = "path")
    })
    @PutMapping("/update/{id}")
    public ResultMessage<PageData> updatePageData(@Valid PageData pageData, @NotNull @PathVariable String id) {

        this.checkAuthority(id);
        pageData.setId(id);
        //添加店铺类型，填写店铺ID
        pageData.setPageType(PageEnum.STORE.name());
        pageData.setNum(UserContext.getCurrentUser().getStoreId());
        return ResultUtil.data(pageDataService.updatePageData(pageData));
    }


    @ApiOperation(value = "发布页面")
    @ApiImplicitParam(name = "id", value = "页面ID", required = true, dataType = "String", paramType = "path")
    @PutMapping("/release/{id}")
    public ResultMessage<PageData> release(@PathVariable String id) {
        this.checkAuthority(id);
        return ResultUtil.data(pageDataService.releasePageData(id));
    }

    @ApiOperation(value = "删除页面")
    @ApiImplicitParam(name = "id", value = "页面ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/removePageData/{id}")
    public ResultMessage<Object> remove(@PathVariable String id) {
        this.checkAuthority(id);
        return ResultUtil.data(pageDataService.removePageData(id));
    }


    /**
     * 店铺权限判定
     *
     * @param id
     */
    private void checkAuthority(String id) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        LambdaQueryWrapper<PageData> queryWrapper = new LambdaQueryWrapper<PageData>().eq(PageData::getId, id).eq(PageData::getPageType, PageEnum.STORE.name()).eq(PageData::getNum, storeId);
        PageData data = pageDataService.getOne(queryWrapper);
        if (data == null) {
            throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
        }
    }
}
