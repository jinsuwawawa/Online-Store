package cn.store.modules.statistics.serviceimpl;

import cn.store.common.security.context.UserContext;
import cn.store.common.security.enums.UserEnums;
import cn.store.common.utils.StringUtils;
import cn.store.modules.order.aftersale.entity.enums.ComplaintStatusEnum;
import cn.store.modules.order.order.entity.dos.OrderComplaint;
import cn.store.modules.statistics.mapper.OrderComplaintStatisticsMapper;
import cn.store.modules.statistics.service.OrderComplaintStatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 交易投诉业务层实现
 **/
@Service
public class OrderComplaintStatisticsServiceImpl extends ServiceImpl<OrderComplaintStatisticsMapper, OrderComplaint> implements OrderComplaintStatisticsService {

    @Override
    public long waitComplainNum() {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.ne("complain_status", ComplaintStatusEnum.COMPLETE.name());
        queryWrapper.eq(StringUtils.equals(UserContext.getCurrentUser().getRole().name(), UserEnums.STORE.name()),
                "store_id", UserContext.getCurrentUser().getStoreId());
        return this.count(queryWrapper);
    }


}
