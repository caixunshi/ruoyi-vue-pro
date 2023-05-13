package cn.iocoder.yudao.module.mp.controller.admin.employee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 员工信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeeRespVO extends EmployeeBaseVO {

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
