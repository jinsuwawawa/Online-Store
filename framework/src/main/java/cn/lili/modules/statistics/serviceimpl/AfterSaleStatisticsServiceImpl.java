package cn.store.modules.statistics.serviceimpl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.store.common.security.AuthUser;
import cn.store.common.security.context.UserContext;
import cn.store.common.security.enums.UserEnums;
import cn.store.common.vo.PageVO;
import cn.store.modules.order.aftersale.entity.dos.AfterSale;
import cn.store.modules.order.trade.entity.enums.AfterSaleStatusEnum;
import cn.store.modules.statistics.entity.dto.StatisticsQueryParam;
import cn.store.modules.statistics.mapper.AfterSaleStatisticsMapper;
import cn.store.modules.statistics.service.AfterSaleStatisticsService;
import cn.store.modules.statistics.util.StatisticsDateUtil;
import cn.store.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 售后统计业务层实现
 **/
@Service
public class AfterSaleStatisticsServiceImpl extends ServiceImpl<AfterSaleStatisticsMapper, AfterSale> implements AfterSaleStatisticsService {


    @Override
    public long applyNum(String serviceType) {
        AuthUser authUser = Objects.requireNonNull(UserContext.getCurrentUser());
        LambdaQueryWrapper<AfterSale> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AfterSale::getServiceStatus, AfterSaleStatusEnum.APPLY.name());
        queryWrapper.eq(CharSequenceUtil.isNotEmpty(serviceType), AfterSale::getServiceType, serviceType);
        queryWrapper.eq(CharSequenceUtil.equals(authUser.getRole().name(), UserEnums.STORE.name()),
                AfterSale::getStoreId, authUser.getStoreId());
        return this.count(queryWrapper);
    }


    @Override
    public IPage<AfterSale> getStatistics(StatisticsQueryParam statisticsQueryParam, PageVO pageVO) {

        LambdaQueryWrapper<AfterSale> queryWrapper = new LambdaQueryWrapper<>();
        Date[] dates = StatisticsDateUtil.getDateArray(statisticsQueryParam);
        queryWrapper.between(AfterSale::getCreateTime, dates[0], dates[1]);
        queryWrapper.eq(CharSequenceUtil.isNotEmpty(statisticsQueryParam.getStoreId()), AfterSale::getStoreId, statisticsQueryParam.getStoreId());

        return this.page(PageUtil.initPage(pageVO), queryWrapper);
    }

}
