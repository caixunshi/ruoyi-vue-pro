package cn.iocoder.yudao.module.mp.controller.admin.storeinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 门店信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreInfoUpdateReqVO extends StoreInfoBaseVO {

    @Schema(description = "主键ID", required = true, example = "4599")
    @NotNull(message = "主键ID不能为空")
    private Long id;

}
