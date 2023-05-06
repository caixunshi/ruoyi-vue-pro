package cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 门店信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreInfoPageReqVO extends PageParam {

    @Schema(description = "门店名称", example = "张三")
    private String storeName;

    @Schema(description = "营业时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] openTime;

    @Schema(description = "营业时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] closeTime;

    @Schema(description = "联系电话")
    private String mobile;

    @Schema(description = "城市名称", example = "张三")
    private String cityName;

    @Schema(description = "区名称", example = "赵六")
    private String areaName;

    @Schema(description = "经度")
    private String longitude;

    @Schema(description = "标签")
    private String labels;

    @Schema(description = "纬度")
    private String latitude;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
