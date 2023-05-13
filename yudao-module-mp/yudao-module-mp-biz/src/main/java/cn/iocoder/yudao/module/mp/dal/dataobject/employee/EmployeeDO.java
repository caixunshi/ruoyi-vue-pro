package cn.iocoder.yudao.module.mp.dal.dataobject.employee;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;

/**
 * 员工信息 DO
 *
 * @author 芋道源码
 */
@TableName("t_employee")
@KeySequence("t_employee_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDO extends BaseDO {

    /**
     * 主键Id
     */
    @TableId
    private Long id;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 联系方式
     */
    private String mobile;
    /**
     * 性别
     *
     * 枚举 {@link TODO system_user_sex 对应的类}
     */
    private Integer sex;
    /**
     * 住址
     */
    private String address;
    /**
     * 生日
     */
    private LocalDate birthDay;

}
