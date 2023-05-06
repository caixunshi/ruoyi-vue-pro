package cn.iocoder.yudao.module.system.service.storeinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo.StoreInfoCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo.StoreInfoExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo.StoreInfoPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo.StoreInfoUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.storeinfo.StoreInfoConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.storeinfo.StoreInfoDO;
import cn.iocoder.yudao.module.system.dal.mysql.storeinfo.StoreInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.STORE_INFO_NOT_EXISTS;

/**
 * 门店信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class StoreInfoServiceImpl implements StoreInfoService {

    @Resource
    private StoreInfoMapper storeInfoMapper;

    @Override
    public Long createStoreInfo(StoreInfoCreateReqVO createReqVO) {
        // 插入
        StoreInfoDO storeInfo = StoreInfoConvert.INSTANCE.convert(createReqVO);
        storeInfoMapper.insert(storeInfo);
        // 返回
        return storeInfo.getId();
    }

    @Override
    public void updateStoreInfo(StoreInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateStoreInfoExists(updateReqVO.getId());
        // 更新
        StoreInfoDO updateObj = StoreInfoConvert.INSTANCE.convert(updateReqVO);
        storeInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteStoreInfo(Long id) {
        // 校验存在
        validateStoreInfoExists(id);
        // 删除
        storeInfoMapper.deleteById(id);
    }

    private void validateStoreInfoExists(Long id) {
        if (storeInfoMapper.selectById(id) == null) {
            throw exception(STORE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public StoreInfoDO getStoreInfo(Long id) {
        return storeInfoMapper.selectById(id);
    }

    @Override
    public List<StoreInfoDO> getStoreInfoList(Collection<Long> ids) {
        return storeInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<StoreInfoDO> getStoreInfoPage(StoreInfoPageReqVO pageReqVO) {
        return storeInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<StoreInfoDO> getStoreInfoList(StoreInfoExportReqVO exportReqVO) {
        return storeInfoMapper.selectList(exportReqVO);
    }

}
