package cn.iocoder.yudao.module.trade.service.order;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.enums.TerminalEnum;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.string.StrUtils;
import cn.iocoder.yudao.module.member.api.address.AddressApi;
import cn.iocoder.yudao.module.member.api.address.dto.AddressRespDTO;
import cn.iocoder.yudao.module.pay.api.order.PayOrderApi;
import cn.iocoder.yudao.module.pay.api.order.PayOrderInfoCreateReqDTO;
import cn.iocoder.yudao.module.product.api.sku.ProductSkuApi;
import cn.iocoder.yudao.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.iocoder.yudao.module.product.api.spu.ProductSpuApi;
import cn.iocoder.yudao.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.iocoder.yudao.module.product.enums.spu.ProductSpuStatusEnum;
import cn.iocoder.yudao.module.promotion.api.price.PriceApi;
import cn.iocoder.yudao.module.promotion.api.price.dto.PriceCalculateReqDTO;
import cn.iocoder.yudao.module.promotion.api.price.dto.PriceCalculateRespDTO;
import cn.iocoder.yudao.module.trade.controller.app.order.vo.AppTradeOrderCreateReqVO;
import cn.iocoder.yudao.module.trade.controller.app.order.vo.AppTradeOrderCreateReqVO.Item;
import cn.iocoder.yudao.module.trade.convert.order.TradeOrderConvert;
import cn.iocoder.yudao.module.trade.convert.price.PriceConvert;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.iocoder.yudao.module.trade.dal.mysql.order.TradeOrderMapper;
import cn.iocoder.yudao.module.trade.dal.mysql.orderitem.TradeOrderItemMapper;
import cn.iocoder.yudao.module.trade.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.trade.enums.order.TradeOrderItemRefundStatusEnum;
import cn.iocoder.yudao.module.trade.enums.order.TradeOrderRefundStatusEnum;
import cn.iocoder.yudao.module.trade.enums.order.TradeOrderStatusEnum;
import cn.iocoder.yudao.module.trade.enums.order.TradeOrderTypeEnum;
import cn.iocoder.yudao.module.trade.framework.order.config.TradeOrderProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.*;
import static cn.iocoder.yudao.module.trade.enums.ErrorCodeConstants.ORDER_CREATE_SKU_NOT_SALE;
import static cn.iocoder.yudao.module.trade.enums.ErrorCodeConstants.ORDER_CREATE_SPU_NOT_FOUND;

/**
 * 交易订单 Service 实现类
 *
 * @author LeeYan9
 * @since 2022-08-26
 */
@Service
public class TradeOrderServiceImpl implements TradeOrderService {

    // TODO LeeYan9: 静态变量, 需要在最前面哈; 另外, 静态变量的注释最好写下;
    private static final String BLANK_PLACEHOLDER = " ";
    private static final String MULTIPLIER_PLACEHOLDER = "x";

    @Resource
    private TradeOrderMapper tradeOrderMapper;
    @Resource
    private TradeOrderItemMapper tradeOrderItemMapper;

    @Resource
    private PriceApi priceApi;
    @Resource
    private ProductSkuApi productSkuApi;
    @Resource
    private ProductSpuApi productSpuApi;
    @Resource
    private PayOrderApi payOrderApi;
    @Resource
    private AddressApi addressApi;

    @Resource
    private TradeOrderProperties tradeOrderProperties;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTradeOrder(Long userId, String clientIp, AppTradeOrderCreateReqVO createReqVO) {
        // 商品 SKU 检查：可售状态、库存
        List<ProductSkuRespDTO> skus = validateSkuSaleable(createReqVO.getItems());
        // 商品 SPU 检查：可售状态
        List<ProductSpuRespDTO> spus = validateSpuSaleable(convertSet(skus, ProductSkuRespDTO::getSpuId));
        // 用户收件地址的校验
        AddressRespDTO address = validateAddress(userId, createReqVO.getAddressId());

        // 价格计算
        PriceCalculateReqDTO priceCalculateReqDTO = PriceConvert.INSTANCE.convert(createReqVO, userId);
        PriceCalculateRespDTO priceResp = priceApi.calculatePrice(priceCalculateReqDTO);

        // 插入 TradeOrderDO 订单
        TradeOrderDO tradeOrderDO = createTradeOrder(userId, clientIp, createReqVO, priceResp.getOrder(), address);
        // 插入 TradeOrderItemDO 订单项
        createTradeOrderItems(tradeOrderDO, priceResp.getOrder().getItems(), skus);

        // 下单时扣减商品库存 TODO
//        List<SkuDecrementStockBatchReqDTO.Item> skuDecrementStockItems = ProductSkuConvert.INSTANCE.convert(tradeOrderItems);
//        productSkuApi.decrementStockBatch(SkuDecrementStockBatchReqDTO.of(skuDecrementStockItems));

        // 删除购物车商品 TODO 芋艿：待实现

        // 扣减积分，抵扣金额 TODO 芋艿：待实现

        // 有使用优惠券时更新

        // 增加订单日志 TODO 芋艿：待实现

        // 构建预支付请求参数
        // TODO @LeeYan9: 需要更新到订单上
//        PayOrderInfoCreateReqDTO payOrderCreateReqDTO = PayOrderConvert.INSTANCE.convert(tradeOrderDO);
//        fillPayOrderInfoFromItems(payOrderCreateReqDTO, tradeOrderItems);
        // 生成预支付

        // TODO @LeeYan9: 是可以思考下, 订单的营销优惠记录, 应该记录在哪里, 微信讨论起来!
        return tradeOrderDO.getId();
    }

    private void fillPayOrderInfoFromItems(PayOrderInfoCreateReqDTO payOrderInfoCreateReqDTO,
                                           List<TradeOrderItemDO> tradeOrderItems) {
        // 填写 商品&应用信息
        payOrderInfoCreateReqDTO.setMerchantOrderId(tradeOrderProperties.getMerchantOrderId());
        payOrderInfoCreateReqDTO.setAppId(tradeOrderProperties.getAppId());

        // 填写商品信息
        StrBuilder subject = new StrBuilder();
        StrBuilder body = new StrBuilder();
        for (TradeOrderItemDO tradeOrderItem : tradeOrderItems) {
            // append subject
            subject.append(BLANK_PLACEHOLDER);
            subject.append(tradeOrderItem.getName());
            // append body
            body.append(BLANK_PLACEHOLDER);
            body.append(tradeOrderItem.getName());
            body.append(MULTIPLIER_PLACEHOLDER);
            body.append(tradeOrderItem.getCount());
        }
        // 设置 subject & body
        // TODO @LeeYan9: 可以抽象一个 StrUtils 方法; 或者看看 hutool 有没自带的哈
        payOrderInfoCreateReqDTO.setSubject(StrUtils.maxLength(subject.subString(1), 32));
        payOrderInfoCreateReqDTO.setBody(StrUtils.maxLength(body.subString(1), 128));
    }

    private void xfillItemsInfoFromSkuAndOrder(TradeOrderDO tradeOrderDO, List<TradeOrderItemDO> tradeOrderItems,
                                              Map<Long, ProductSkuRespDTO> spuInfos) {
        for (TradeOrderItemDO tradeOrderItem : tradeOrderItems) {
            // 填充订单信息
            tradeOrderItem.setOrderId(tradeOrderDO.getId());
            tradeOrderItem.setUserId(tradeOrderDO.getUserId());
            // 填充SKU信息
            ProductSkuRespDTO skuInfoRespDTO = spuInfos.get(tradeOrderItem.getSkuId());
            tradeOrderItem.setSpuId(skuInfoRespDTO.getSpuId());
            tradeOrderItem.setPicUrl(skuInfoRespDTO.getPicUrl());
            tradeOrderItem.setName(skuInfoRespDTO.getName());
            tradeOrderItem.setRefundStatus(TradeOrderItemRefundStatusEnum.NONE.getStatus());
            // todo
            List<TradeOrderItemDO.Property> property =
                    BeanUtil.copyToList(skuInfoRespDTO.getProperties(), TradeOrderItemDO.Property.class);
            tradeOrderItem.setProperties(property);
        }
    }

    /**
     * 校验商品 SKU 是否可出售
     *
     * @param items 商品 SKU
     * @return 商品 SKU 数组
     */
    private List<ProductSkuRespDTO> validateSkuSaleable(List<Item> items) {
        List<ProductSkuRespDTO> skus = productSkuApi.getSkuList(convertSet(items, Item::getSkuId));
        // SKU 不存在
        if (items.size() != skus.size()) {
            throw exception(ErrorCodeConstants.ORDER_CREATE_SKU_NOT_FOUND);
        }
        // 校验是否禁用 or 库存不足
        Map<Long, ProductSkuRespDTO> skuMap = convertMap(skus, ProductSkuRespDTO::getId);
        items.forEach(item -> {
            ProductSkuRespDTO sku = skuMap.get(item.getSkuId());
            // SKU 禁用
            if (ObjectUtil.notEqual(CommonStatusEnum.ENABLE.getStatus(), sku.getStatus())) {
                throw exception(ORDER_CREATE_SKU_NOT_SALE);
            }
            // SKU 库存不足
            if (item.getCount() > sku.getStock()) {
                throw exception(ErrorCodeConstants.ORDER_CREATE_SKU_STOCK_NOT_ENOUGH);
            }
        });
        return skus;
    }

    /**
     * 校验商品 SPU 是否可出售
     *
     * @param spuIds 商品 SPU 编号数组
     * @return 商品 SPU 数组
     */
    private List<ProductSpuRespDTO> validateSpuSaleable(Set<Long> spuIds) {
        List<ProductSpuRespDTO> spus = productSpuApi.getSpuList(spuIds);
        // SPU 不存在
        if (spus.size() != spuIds.size()) {
            throw exception(ORDER_CREATE_SPU_NOT_FOUND);
        }
        // 校验是否存在禁用的 SPU
        ProductSpuRespDTO spu = CollectionUtils.findFirst(spus,
                spuDTO -> ObjectUtil.notEqual(ProductSpuStatusEnum.ENABLE.getStatus(), spuDTO.getStatus()));
        if (Objects.isNull(spu)) {
            throw exception(ErrorCodeConstants.ORDER_CREATE_SPU_NOT_SALE);
        }
        return spus;
    }

    /**
     * 校验收件地址是否存在
     *
     * @param userId 用户编号
     * @param addressId 收件地址编号
     * @return 收件地址
     */
    private AddressRespDTO validateAddress(Long userId, Long addressId) {
        AddressRespDTO address = addressApi.getAddress(userId, addressId);
        if (Objects.isNull(address)) {
            throw exception(ErrorCodeConstants.ORDER_CREATE_ADDRESS_NOT_FOUND);
        }
        return address;
    }

    private TradeOrderDO createTradeOrder(Long userId, String clientIp, AppTradeOrderCreateReqVO createReqVO,
                                          PriceCalculateRespDTO.Order order, AddressRespDTO address) {
        TradeOrderDO tradeOrderDO = TradeOrderConvert.INSTANCE.convert(userId, clientIp, createReqVO, order, address);
        tradeOrderDO.setNo(IdUtil.getSnowflakeNextId() + ""); // TODO @LeeYan9: 思考下, 怎么生成好点哈; 这个是会展示给用户的;
        tradeOrderDO.setStatus(TradeOrderStatusEnum.WAITING_PAYMENT.getStatus());
        tradeOrderDO.setType(TradeOrderTypeEnum.NORMAL.getType());
        tradeOrderDO.setRefundStatus(TradeOrderRefundStatusEnum.NONE.getStatus());
        tradeOrderDO.setProductCount(getSumValue(order.getItems(),  PriceCalculateRespDTO.OrderItem::getCount, Integer::sum));
        tradeOrderDO.setTerminal(TerminalEnum.H5.getTerminal()); // todo 数据来源?
        tradeOrderMapper.insert(tradeOrderDO);
        return tradeOrderDO;
    }

    private void createTradeOrderItems(TradeOrderDO tradeOrderDO,
                                       List<PriceCalculateRespDTO.OrderItem> orderItems, List<ProductSkuRespDTO> skus) {
        List<TradeOrderItemDO> tradeOrderItemDOs = TradeOrderConvert.INSTANCE.convertList(tradeOrderDO, orderItems, skus);
        tradeOrderItemMapper.insertBatch(tradeOrderItemDOs);
    }

}