package cn.store.modules.goods.entity.dto;

import cn.store.modules.goods.entity.dos.Wholesale;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**

 **/
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class WholesaleDTO extends Wholesale {

    private static final long serialVersionUID = 853297561151783335L;

    public WholesaleDTO(Wholesale wholesale) {
        BeanUtils.copyProperties(wholesale, this);
    }
}
