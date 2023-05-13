package cn.iocoder.yudao.module.mp.controller.admin.employee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 员工信息 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class EmployeeExcelVO {

    @ExcelProperty("员工姓名")
    private String name;

    @ExcelProperty("身份证号")
    private String idCard;

    @ExcelProperty("联系方式")
    private String mobile;

    @ExcelProperty(value = "性别", converter = DictConvert.class)
    @DictFormat("system_user_sex") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer sex;

    @ExcelProperty("住址")
    private String address;

    @ExcelProperty("生日")
    private LocalDate birthDay;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
