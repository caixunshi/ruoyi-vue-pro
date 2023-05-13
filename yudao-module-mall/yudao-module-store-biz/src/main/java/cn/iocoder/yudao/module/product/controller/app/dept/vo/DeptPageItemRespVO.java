package cn.iocoder.yudao.module.product.controller.app.dept.vo;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import lombok.Data;

@Data
public class DeptPageItemRespVO {
    /**
     * 部门名称
     */
    private String name;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 部门状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

    private Integer openStartTime;

    private Integer openEndTime;

    private String labels;

    private String address;

}
