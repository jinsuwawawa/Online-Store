package cn.store.modules.statistics.serviceimpl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.store.modules.statistics.mapper.StoreStatisticsMapper;
import cn.store.modules.statistics.service.StoreStatisticsService;
import cn.store.modules.store.entity.dos.Store;
import cn.store.modules.store.entity.enums.StoreStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 商品统计业务层实现
 **/
@Service
public class StoreStatisticsServiceImpl extends ServiceImpl<StoreStatisticsMapper, Store> implements StoreStatisticsService {


    @Override
    public long auditNum() {
        LambdaQueryWrapper<Store> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Store::getStoreDisable, StoreStatusEnum.APPLYING.name());
        return this.count(queryWrapper);
    }

    @Override
    public long storeNum() {
        LambdaQueryWrapper<Store> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Store::getStoreDisable, StoreStatusEnum.OPEN.name());
        return this.count(queryWrapper);
    }

    @Override
    public long todayStoreNum() {
        LambdaQueryWrapper<Store> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Store::getStoreDisable, StoreStatusEnum.OPEN.name());
        queryWrapper.ge(Store::getCreateTime, DateUtil.beginOfDay(new DateTime()));
        return this.count(queryWrapper);
    }

}
