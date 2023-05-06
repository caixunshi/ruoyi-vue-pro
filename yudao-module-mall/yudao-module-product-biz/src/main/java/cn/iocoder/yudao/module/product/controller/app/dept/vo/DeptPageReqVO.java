package cn.iocoder.yudao.module.product.controller.app.dept.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class DeptPageReqVO extends PageParam {
    private String name;
    private String address;
}
