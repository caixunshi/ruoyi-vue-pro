package cn.iocoder.yudao.module.system.dal.mysql.storeinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo.StoreInfoExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo.StoreInfoPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.storeinfo.StoreInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 门店信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface StoreInfoMapper extends BaseMapperX<StoreInfoDO> {

    default PageResult<StoreInfoDO> selectPage(StoreInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StoreInfoDO>()
                .likeIfPresent(StoreInfoDO::getStoreName, reqVO.getStoreName())
                .betweenIfPresent(StoreInfoDO::getOpenTime, reqVO.getOpenTime())
                .betweenIfPresent(StoreInfoDO::getCloseTime, reqVO.getCloseTime())
                .eqIfPresent(StoreInfoDO::getMobile, reqVO.getMobile())
                .likeIfPresent(StoreInfoDO::getCityName, reqVO.getCityName())
                .likeIfPresent(StoreInfoDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(StoreInfoDO::getLongitude, reqVO.getLongitude())
                .eqIfPresent(StoreInfoDO::getLabels, reqVO.getLabels())
                .eqIfPresent(StoreInfoDO::getLatitude, reqVO.getLatitude())
                .betweenIfPresent(StoreInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(StoreInfoDO::getId));
    }

    default List<StoreInfoDO> selectList(StoreInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<StoreInfoDO>()
                .likeIfPresent(StoreInfoDO::getStoreName, reqVO.getStoreName())
                .eqIfPresent(StoreInfoDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(StoreInfoDO::getOpenTime, reqVO.getOpenTime())
                .betweenIfPresent(StoreInfoDO::getCloseTime, reqVO.getCloseTime())
                .eqIfPresent(StoreInfoDO::getMobile, reqVO.getMobile())
                .likeIfPresent(StoreInfoDO::getCityName, reqVO.getCityName())
                .likeIfPresent(StoreInfoDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(StoreInfoDO::getLongitude, reqVO.getLongitude())
                .eqIfPresent(StoreInfoDO::getLabels, reqVO.getLabels())
                .eqIfPresent(StoreInfoDO::getLatitude, reqVO.getLatitude())
                .betweenIfPresent(StoreInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(StoreInfoDO::getId));
    }

}
