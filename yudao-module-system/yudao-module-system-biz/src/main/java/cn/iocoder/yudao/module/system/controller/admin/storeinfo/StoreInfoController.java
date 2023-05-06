package cn.iocoder.yudao.module.system.controller.admin.storeinfo;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.storeinfo.vo.*;
import cn.iocoder.yudao.module.system.convert.storeinfo.StoreInfoConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.storeinfo.StoreInfoDO;
import cn.iocoder.yudao.module.system.service.storeinfo.StoreInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 门店信息")
@RestController
@RequestMapping("/t/store-info")
@Validated
public class StoreInfoController {

    @Resource
    private StoreInfoService storeInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建门店信息")
    @PreAuthorize("@ss.hasPermission('t:store-info:create')")
    public CommonResult<Long> createStoreInfo(@Valid @RequestBody StoreInfoCreateReqVO createReqVO) {
        return success(storeInfoService.createStoreInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新门店信息")
    @PreAuthorize("@ss.hasPermission('t:store-info:update')")
    public CommonResult<Boolean> updateStoreInfo(@Valid @RequestBody StoreInfoUpdateReqVO updateReqVO) {
        storeInfoService.updateStoreInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除门店信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('t:store-info:delete')")
    public CommonResult<Boolean> deleteStoreInfo(@RequestParam("id") Long id) {
        storeInfoService.deleteStoreInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得门店信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('t:store-info:query')")
    public CommonResult<StoreInfoRespVO> getStoreInfo(@RequestParam("id") Long id) {
        StoreInfoDO storeInfo = storeInfoService.getStoreInfo(id);
        return success(StoreInfoConvert.INSTANCE.convert(storeInfo));
    }

    @GetMapping("/list")
    @Operation(summary = "获得门店信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('t:store-info:query')")
    public CommonResult<List<StoreInfoRespVO>> getStoreInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<StoreInfoDO> list = storeInfoService.getStoreInfoList(ids);
        return success(StoreInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得门店信息分页")
    @PreAuthorize("@ss.hasPermission('t:store-info:query')")
    public CommonResult<PageResult<StoreInfoRespVO>> getStoreInfoPage(@Valid StoreInfoPageReqVO pageVO) {
        PageResult<StoreInfoDO> pageResult = storeInfoService.getStoreInfoPage(pageVO);
        return success(StoreInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出门店信息 Excel")
    @PreAuthorize("@ss.hasPermission('t:store-info:export')")
    @OperateLog(type = EXPORT)
    public void exportStoreInfoExcel(@Valid StoreInfoExportReqVO exportReqVO,
                                     HttpServletResponse response) throws IOException {
        List<StoreInfoDO> list = storeInfoService.getStoreInfoList(exportReqVO);
        // 导出 Excel
        List<StoreInfoExcelVO> datas = StoreInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "门店信息.xls", "数据", StoreInfoExcelVO.class, datas);
    }

}
