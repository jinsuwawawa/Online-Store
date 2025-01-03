package cn.store.modules.statistics.serviceimpl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.store.common.security.context.UserContext;
import cn.store.common.security.enums.UserEnums;
import cn.store.modules.statistics.mapper.BillStatisticsMapper;
import cn.store.modules.statistics.service.BillStatisticsService;
import cn.store.modules.store.entity.dos.Bill;
import cn.store.modules.store.entity.enums.BillStatusEnum;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 结算单统计
 **/
@Service
public class BillStatisticsServiceImpl extends ServiceImpl<BillStatisticsMapper, Bill> implements BillStatisticsService {


    @Override
    public long billNum(BillStatusEnum billStatusEnum) {
        LambdaUpdateWrapper<Bill> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(Bill::getBillStatus, billStatusEnum.name());
        lambdaUpdateWrapper.eq(CharSequenceUtil.equals(Objects.requireNonNull(UserContext.getCurrentUser()).getRole().name(), UserEnums.STORE.name()),
                Bill::getStoreId, UserContext.getCurrentUser().getStoreId());
        return this.count(lambdaUpdateWrapper);
    }


}
