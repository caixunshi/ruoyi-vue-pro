package cn.iocoder.yudao.module.mp.controller.admin.employee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 员工信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeeUpdateReqVO extends EmployeeBaseVO {

    @Schema(description = "主键Id", required = true, example = "10229")
    @NotNull(message = "主键Id不能为空")
    private Long id;

}
