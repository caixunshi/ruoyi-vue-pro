package cn.iocoder.yudao.module.mp.controller.admin.employee.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 员工信息 Excel 导出 Request VO，参数和 EmployeePageReqVO 是一致的")
@Data
public class EmployeeExportReqVO {

    @Schema(description = "员工姓名", example = "芋艿")
    private String name;

    @Schema(description = "联系方式")
    private String mobile;

}
