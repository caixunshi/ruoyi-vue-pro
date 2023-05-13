package cn.iocoder.yudao.module.mp.controller.app.store;


import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.mp.controller.admin.storeinfo.vo.StoreInfoPageReqVO;
import cn.iocoder.yudao.module.mp.controller.app.store.vo.AppStoreInfoPageItemRespVO;
import cn.iocoder.yudao.module.mp.controller.app.store.vo.AppStoreInfoPageReqVO;
import cn.iocoder.yudao.module.mp.convert.storeinfo.StoreInfoConvert;
import cn.iocoder.yudao.module.mp.dal.dataobject.storeinfo.StoreInfoDO;
import cn.iocoder.yudao.module.mp.service.storeinfo.StoreInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "门店管理")
@RestController
@RequestMapping("/store")
@Validated
@Slf4j
public class AppStoreController {

    @Resource
    private StoreInfoService storeInfoService;

    @GetMapping("/page")
    @Operation(summary = "获取门店分页数据")
    public CommonResult<PageResult<AppStoreInfoPageItemRespVO>> getOrderPage(AppStoreInfoPageReqVO reqVO) {
        StoreInfoPageReqVO storeInfoPageReqVO = new StoreInfoPageReqVO();
        storeInfoPageReqVO.setAreaName(reqVO.getAreaName());
        storeInfoPageReqVO.setCityName(reqVO.getCityName());
        storeInfoPageReqVO.setStoreName(reqVO.getStoreName());
        storeInfoPageReqVO.setPageNo(reqVO.getPageNo());
        storeInfoPageReqVO.setPageSize(reqVO.getPageSize());
        PageResult<StoreInfoDO> pageResult = storeInfoService.getStoreInfoPage(storeInfoPageReqVO);

        // 最终组合
        return success(StoreInfoConvert.INSTANCE.convertPage02(pageResult));
    }
}
