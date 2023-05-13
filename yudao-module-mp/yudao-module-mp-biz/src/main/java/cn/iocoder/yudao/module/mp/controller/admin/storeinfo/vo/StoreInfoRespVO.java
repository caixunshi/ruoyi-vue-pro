package cn.iocoder.yudao.module.mp.controller.admin.storeinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 门店信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreInfoRespVO extends StoreInfoBaseVO {

    @Schema(description = "主键ID", required = true, example = "4599")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
