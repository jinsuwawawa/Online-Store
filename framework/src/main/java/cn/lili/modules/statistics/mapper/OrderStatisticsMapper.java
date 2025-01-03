package cn.store.modules.statistics.mapper;

import cn.store.modules.order.order.entity.dos.Order;
import cn.store.modules.order.order.entity.vo.OrderSimpleVO;
import cn.store.modules.statistics.entity.vo.OrderStatisticsDataVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单统计数据处理层
 **/
public interface OrderStatisticsMapper extends BaseMapper<Order> {

    /**
     * 获取订单统计数据
     *
     * @param queryWrapper 查询条件
     * @return 订单统计列表
     */
    @Select("SELECT DATE_FORMAT(create_time,'%Y-%m-%d') AS create_time,sum(flow_price) AS price FROM li_order " +
            " ${ew.customSqlSegment}")
    List<OrderStatisticsDataVO> getOrderStatisticsData(@Param(Constants.WRAPPER) Wrapper queryWrapper);

    /**
     * 订单数量
     *
     * @param queryWrapper 查询条件
     * @return 订单数量
     */
    @Select("SELECT count(0) FROM li_order ${ew.customSqlSegment}")
    Integer count(@Param(Constants.WRAPPER) Wrapper queryWrapper);

    /**
     * 查询订单简短信息分页
     *
     * @param page         分页
     * @param queryWrapper 查询条件
     * @return 简短订单分页
     */
    @Select("select o.sn,o.flow_price,o.create_time,o.order_status,o.pay_status,o.payment_method,o.payment_time,o.member_name,o.store_name as store_name,o.store_id as store_id,o.client_type,o.order_type,o.deliver_status " +
            ",GROUP_CONCAT(oi.goods_id) as group_goods_id," +
            " GROUP_CONCAT(oi.sku_id) as group_sku_id," +
            " GROUP_CONCAT(oi.num) as group_num" +
            ",GROUP_CONCAT(oi.image) as group_images" +
            ",GROUP_CONCAT(oi.goods_name) as group_name " +
            ",GROUP_CONCAT(oi.after_sale_status) as group_after_sale_status" +
            ",GROUP_CONCAT(oi.complain_status) as group_complain_status" +
            ",GROUP_CONCAT(oi.comment_status) as group_comment_status" +
            ",GROUP_CONCAT(oi.sn) as group_order_items_sn " +
            ",GROUP_CONCAT(oi.goods_price) as group_goods_price " +
            " FROM li_order o INNER JOIN li_order_item AS oi on o.sn = oi.order_sn ${ew.customSqlSegment} ")
    IPage<OrderSimpleVO> queryByParams(IPage<OrderSimpleVO> page, @Param(Constants.WRAPPER) Wrapper<OrderSimpleVO> queryWrapper);

}