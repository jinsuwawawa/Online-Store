package cn.store.modules.goods.serviceimpl;

import cn.store.modules.goods.entity.dos.GoodsUnit;
import cn.store.modules.goods.mapper.GoodsUnitMapper;
import cn.store.modules.goods.service.GoodsUnitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 计量单位业务层实现
 */
@Service
public class GoodsUnitServiceImpl extends ServiceImpl<GoodsUnitMapper, GoodsUnit> implements GoodsUnitService {

}