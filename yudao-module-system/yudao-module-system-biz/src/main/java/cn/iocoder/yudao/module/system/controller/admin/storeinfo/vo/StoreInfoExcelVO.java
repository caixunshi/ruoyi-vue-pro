package cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 门店信息 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class StoreInfoExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("门店名称")
    private String storeName;

    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("营业时间")
    private Integer openTime;

    @ExcelProperty("营业时间")
    private Integer closeTime;

    @ExcelProperty("联系电话")
    private String mobile;

    @ExcelProperty("城市名称")
    private String cityName;

    @ExcelProperty("区名称")
    private String areaName;

    @ExcelProperty("经度")
    private String longitude;

    @ExcelProperty("标签")
    private String labels;

    @ExcelProperty("纬度")
    private String latitude;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
