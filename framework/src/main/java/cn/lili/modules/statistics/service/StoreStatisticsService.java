package cn.store.modules.statistics.service;

import cn.store.modules.store.entity.dos.Store;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 店铺统计业务层
 **/
public interface StoreStatisticsService extends IService<Store> {

    /**
     * 获取待审核店铺数量
     *
     * @return 待审核店铺数量
     */
    long auditNum();

    /**
     * 获取所有店铺数量
     *
     * @return 店铺总数
     */
    long storeNum();

    /**
     * 获取今天的店铺数量
     *
     * @return 今天的店铺数量
     */
    long todayStoreNum();
}