package cn.iocoder.yudao.module.mp.service.storeinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.mp.controller.admin.storeinfo.vo.StoreInfoCreateReqVO;
import cn.iocoder.yudao.module.mp.controller.admin.storeinfo.vo.StoreInfoExportReqVO;
import cn.iocoder.yudao.module.mp.controller.admin.storeinfo.vo.StoreInfoPageReqVO;
import cn.iocoder.yudao.module.mp.controller.admin.storeinfo.vo.StoreInfoUpdateReqVO;
import cn.iocoder.yudao.module.mp.dal.dataobject.storeinfo.StoreInfoDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 门店信息 Service 接口
 *
 * @author 芋道源码
 */
public interface StoreInfoService {

    /**
     * 创建门店信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStoreInfo(@Valid StoreInfoCreateReqVO createReqVO);

    /**
     * 更新门店信息
     *
     * @param updateReqVO 更新信息
     */
    void updateStoreInfo(@Valid StoreInfoUpdateReqVO updateReqVO);

    /**
     * 删除门店信息
     *
     * @param id 编号
     */
    void deleteStoreInfo(Long id);

    /**
     * 获得门店信息
     *
     * @param id 编号
     * @return 门店信息
     */
    StoreInfoDO getStoreInfo(Long id);

    /**
     * 获得门店信息列表
     *
     * @param ids 编号
     * @return 门店信息列表
     */
    List<StoreInfoDO> getStoreInfoList(Collection<Long> ids);

    /**
     * 获得门店信息分页
     *
     * @param pageReqVO 分页查询
     * @return 门店信息分页
     */
    PageResult<StoreInfoDO> getStoreInfoPage(StoreInfoPageReqVO pageReqVO);

    /**
     * 获得门店信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 门店信息列表
     */
    List<StoreInfoDO> getStoreInfoList(StoreInfoExportReqVO exportReqVO);

}
