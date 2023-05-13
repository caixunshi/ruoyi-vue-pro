package cn.iocoder.yudao.module.mp.controller.app.store.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 App - 订单交易的分页项 Response VO")
@Data
public class AppStoreInfoPageItemRespVO {
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 营业时间
     */
    private String openTime;
    /**
     * 营业时间
     */
    private String closeTime;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 标签
     */
    private String labels;
    /**
     * 纬度
     */
    private String latitude;

    /**
     * 联系地址
     */
    private String address;
}
