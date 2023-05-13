package cn.iocoder.yudao.module.mp.controller.admin.employee.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 员工信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeePageReqVO extends PageParam {

    @Schema(description = "员工姓名", example = "芋艿")
    private String name;

    @Schema(description = "联系方式")
    private String mobile;

}
