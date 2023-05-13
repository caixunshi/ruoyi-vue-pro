package cn.iocoder.yudao.module.mp.controller.admin.employee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 员工信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class EmployeeBaseVO {

    @Schema(description = "员工姓名", required = true, example = "芋艿")
    @NotNull(message = "员工姓名不能为空")
    private String name;

    @Schema(description = "身份证号", required = true)
    @NotNull(message = "身份证号不能为空")
    private String idCard;

    @Schema(description = "联系方式", required = true)
    @NotNull(message = "联系方式不能为空")
    private String mobile;

    @Schema(description = "性别")
    private Integer sex;

    @Schema(description = "住址")
    private String address;

    @Schema(description = "生日")
    private LocalDate birthDay;

}
