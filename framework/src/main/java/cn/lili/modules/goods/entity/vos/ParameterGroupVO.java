package cn.store.modules.goods.entity.vos;

import cn.store.modules.goods.entity.dos.Parameters;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 参数组vo
 */
@ApiModel
@Data
public class ParameterGroupVO implements Serializable {

    private static final long serialVersionUID = 724427321881170297L;
    @ApiModelProperty("参数组关联的参数集合")
    private List<Parameters> params;
    @ApiModelProperty(value = "参数组名称")
    private String groupName;
    @ApiModelProperty(value = "参数组id")
    private String groupId;


}
