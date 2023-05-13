package cn.iocoder.yudao.module.mp.controller.admin.storeinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* 门店信息 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class StoreInfoBaseVO {

    @Schema(description = "门店名称", required = true, example = "张三")
    @NotNull(message = "门店名称不能为空")
    private String storeName;

    @Schema(description = "状态", example = "2")
    private Integer status;

    @Schema(description = "营业时间", required = true)
    @NotNull(message = "营业时间不能为空")
    private String openTime;

    @Schema(description = "营业时间", required = true)
    @NotNull(message = "营业时间不能为空")
    private String closeTime;

    @Schema(description = "联系电话", required = true)
    @NotNull(message = "联系电话不能为空")
    private String mobile;

    @Schema(description = "城市名称", required = true, example = "张三")
    @NotNull(message = "城市名称不能为空")
    private String cityName;

    @Schema(description = "区名称", required = true, example = "赵六")
    @NotNull(message = "区名称不能为空")
    private String areaName;

    @Schema(description = "经度", required = true)
    @NotNull(message = "经度不能为空")
    private String longitude;

    @Schema(description = "标签", required = true)
    private String labels;

    @Schema(description = "纬度", required = true)
    @NotNull(message = "纬度不能为空")
    private String latitude;

    @Schema(description = "联系地址", required = true)
    @NotNull(message = "联系地址不能为空")
    private String address;
}
