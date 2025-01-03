package cn.store.modules.statistics.serviceimpl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.store.common.security.AuthUser;
import cn.store.common.security.context.UserContext;
import cn.store.common.security.enums.UserEnums;
import cn.store.modules.goods.entity.dos.Goods;
import cn.store.modules.goods.entity.enums.GoodsAuthEnum;
import cn.store.modules.goods.entity.enums.GoodsStatusEnum;
import cn.store.modules.goods.service.GoodsSkuService;
import cn.store.modules.statistics.mapper.GoodsStatisticsMapper;
import cn.store.modules.statistics.service.GoodsStatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 商品统计业务层实现
 **/
@Service
public class GoodsStatisticsServiceImpl extends ServiceImpl<GoodsStatisticsMapper, Goods> implements GoodsStatisticsService {

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Override
    public long goodsNum(GoodsStatusEnum goodsStatusEnum, GoodsAuthEnum goodsAuthEnum) {
        LambdaQueryWrapper<Goods> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Goods::getDeleteFlag, false);

        if (goodsStatusEnum != null) {
            queryWrapper.eq(Goods::getMarketEnable, goodsStatusEnum.name());
        }
        if (goodsAuthEnum != null) {
            queryWrapper.eq(Goods::getAuthFlag, goodsAuthEnum.name());
        }
        AuthUser currentUser = Objects.requireNonNull(UserContext.getCurrentUser());
        queryWrapper.eq(CharSequenceUtil.equals(currentUser.getRole().name(), UserEnums.STORE.name()),
                Goods::getStoreId, currentUser.getStoreId());

        return this.count(queryWrapper);
    }

    @Override
    public long todayUpperNum() {
        LambdaQueryWrapper<Goods> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Goods::getMarketEnable, GoodsStatusEnum.UPPER.name());
        queryWrapper.ge(Goods::getCreateTime, DateUtil.beginOfDay(new DateTime()));
        return this.count(queryWrapper);
    }

    @Override
    public long alertQuantityNum() {
        QueryWrapper queryWrapper = new QueryWrapper();
        AuthUser currentUser = Objects.requireNonNull(UserContext.getCurrentUser());
        queryWrapper.eq(CharSequenceUtil.equals(currentUser.getRole().name(), UserEnums.STORE.name()),
                "store_id", currentUser.getStoreId());
        queryWrapper.eq("market_enable",GoodsStatusEnum.UPPER.name());
        queryWrapper.apply("quantity < alert_quantity");
        queryWrapper.gt("alert_quantity",0);
        return goodsSkuService.count(queryWrapper);
    }
}
