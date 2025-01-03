package cn.store.modules.statistics.service;

import cn.store.common.vo.PageVO;
import cn.store.modules.order.aftersale.entity.dos.AfterSale;
import cn.store.modules.statistics.entity.dto.StatisticsQueryParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 售后统计业务层
 **/
public interface AfterSaleStatisticsService extends IService<AfterSale> {

    /**
     * 获取待处理售后数量
     *
     * @param serviceType 售后类型
     * @return 待处理售后数量
     */
    long applyNum(String serviceType);

    /**
     * 获取统计的售后
     *
     * @param statisticsQueryParam 统计搜索参数
     * @param pageVO               分页
     * @return 售后分页列表
     */
    IPage<AfterSale> getStatistics(StatisticsQueryParam statisticsQueryParam, PageVO pageVO);
}