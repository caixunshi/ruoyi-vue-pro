package cn.iocoder.yudao.module.system.convert.storeinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo.StoreInfoCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo.StoreInfoExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo.StoreInfoRespVO;
import cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo.StoreInfoUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.storeinfo.StoreInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 门店信息 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface StoreInfoConvert {

    StoreInfoConvert INSTANCE = Mappers.getMapper(StoreInfoConvert.class);

    StoreInfoDO convert(StoreInfoCreateReqVO bean);

    StoreInfoDO convert(StoreInfoUpdateReqVO bean);

    StoreInfoRespVO convert(StoreInfoDO bean);

    List<StoreInfoRespVO> convertList(List<StoreInfoDO> list);

    PageResult<StoreInfoRespVO> convertPage(PageResult<StoreInfoDO> page);

    List<StoreInfoExcelVO> convertList02(List<StoreInfoDO> list);

}
