package cn.iocoder.yudao.module.mp.dal.dataobject.storeinfo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 门店信息 DO
 *
 * @author 芋道源码
 */
@TableName("t_store_info")
@KeySequence("t_store_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreInfoDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 营业时间
     */
    private String openTime;
    /**
     * 营业时间
     */
    private String closeTime;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 区名称
     */
    private String areaName;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 标签
     */
    private String labels;
    /**
     * 纬度
     */
    private String latitude;

    /**
     * 地址
     */
    private String address;

}
