package cn.store.modules.goods.serviceimpl;

import cn.store.modules.goods.entity.dos.GoodsWords;
import cn.store.modules.goods.mapper.GoodsWordsMapper;
import cn.store.modules.goods.service.GoodsWordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 商品关键字业务层实现
 **/
@Service
public class GoodsWordsServiceImpl extends ServiceImpl<GoodsWordsMapper, GoodsWords> implements GoodsWordsService {
}
