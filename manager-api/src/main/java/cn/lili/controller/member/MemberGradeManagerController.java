package cn.store.controller.member;

import cn.store.common.enums.ResultCode;
import cn.store.common.enums.ResultUtil;
import cn.store.common.vo.PageVO;
import cn.store.common.vo.ResultMessage;
import cn.store.modules.member.entity.dos.MemberGrade;
import cn.store.modules.member.service.MemberGradeService;
import cn.store.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端,会员等级接口
 */
@RestController
@Api(tags = "管理端,会员等级接口")
@RequestMapping("/manager/member/memberGrade")
public class MemberGradeManagerController {

    @Autowired
    private MemberGradeService memberGradeService;

    @ApiOperation(value = "通过id获取会员等级")
    @ApiImplicitParam(name = "id", value = "会员等级ID", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/get/{id}")
    public ResultMessage<MemberGrade> get(@PathVariable String id) {

        return ResultUtil.data(memberGradeService.getById(id));
    }

    @ApiOperation(value = "获取会员等级分页")
    @GetMapping(value = "/getByPage")
    public ResultMessage<IPage<MemberGrade>> getByPage(PageVO page) {

        return ResultUtil.data(memberGradeService.page(PageUtil.initPage(page)));
    }

    @ApiOperation(value = "添加会员等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员等级ID", required = true, paramType = "path")
    })
    @PostMapping(value = "/add")
    public ResultMessage<Object> daa(@Validated MemberGrade memberGrade) {
        if (memberGradeService.save(memberGrade)) {
            return ResultUtil.success(ResultCode.SUCCESS);
        }
        return ResultUtil.error(ResultCode.ERROR);
    }

    @ApiOperation(value = "修改会员等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员等级ID", required = true, paramType = "path")
    })
    @PutMapping(value = "/update/{id}")
    public ResultMessage<Object> update(@PathVariable String id, MemberGrade memberGrade) {
        if (memberGradeService.updateById(memberGrade)) {
            return ResultUtil.success(ResultCode.SUCCESS);
        }
        return ResultUtil.error(ResultCode.ERROR);
    }


    @ApiOperation(value = "删除会员等级")
    @ApiImplicitParam(name = "id", value = "会员等级ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping(value = "/delete/{id}")
    public ResultMessage<IPage<Object>> delete(@PathVariable String id) {
        if (memberGradeService.removeById(id)) {
            return ResultUtil.success(ResultCode.SUCCESS);
        }
        return ResultUtil.error(ResultCode.ERROR);
    }
}
