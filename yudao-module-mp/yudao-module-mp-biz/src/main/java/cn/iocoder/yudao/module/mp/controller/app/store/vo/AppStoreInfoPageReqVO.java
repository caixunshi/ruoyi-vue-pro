package cn.iocoder.yudao.module.mp.controller.app.store.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// TODO 芋艿：字段优化
@Schema(description = "交易订单分页 Request VO")
@Data
public class AppStoreInfoPageReqVO extends PageParam {

    private String storeName;

    private String cityName;

    private String areaName;
}
