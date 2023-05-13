package cn.iocoder.yudao.module.mp.controller.admin.employee;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.mp.controller.admin.employee.vo.*;
import cn.iocoder.yudao.module.mp.dal.dataobject.employee.EmployeeDO;
import cn.iocoder.yudao.module.mp.convert.employee.EmployeeConvert;
import cn.iocoder.yudao.module.mp.service.employee.EmployeeService;

@Tag(name = "管理后台 - 员工信息")
@RestController
@RequestMapping("/mp/employee")
@Validated
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @PostMapping("/create")
    @Operation(summary = "创建员工信息")
    @PreAuthorize("@ss.hasPermission('mp:employee:create')")
    public CommonResult<Long> createEmployee(@Valid @RequestBody EmployeeCreateReqVO createReqVO) {
        return success(employeeService.createEmployee(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新员工信息")
    @PreAuthorize("@ss.hasPermission('mp:employee:update')")
    public CommonResult<Boolean> updateEmployee(@Valid @RequestBody EmployeeUpdateReqVO updateReqVO) {
        employeeService.updateEmployee(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除员工信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mp:employee:delete')")
    public CommonResult<Boolean> deleteEmployee(@RequestParam("id") Long id) {
        employeeService.deleteEmployee(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得员工信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mp:employee:query')")
    public CommonResult<EmployeeRespVO> getEmployee(@RequestParam("id") Long id) {
        EmployeeDO employee = employeeService.getEmployee(id);
        return success(EmployeeConvert.INSTANCE.convert(employee));
    }

    @GetMapping("/list")
    @Operation(summary = "获得员工信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('mp:employee:query')")
    public CommonResult<List<EmployeeRespVO>> getEmployeeList(@RequestParam("ids") Collection<Long> ids) {
        List<EmployeeDO> list = employeeService.getEmployeeList(ids);
        return success(EmployeeConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得员工信息分页")
    @PreAuthorize("@ss.hasPermission('mp:employee:query')")
    public CommonResult<PageResult<EmployeeRespVO>> getEmployeePage(@Valid EmployeePageReqVO pageVO) {
        PageResult<EmployeeDO> pageResult = employeeService.getEmployeePage(pageVO);
        return success(EmployeeConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出员工信息 Excel")
    @PreAuthorize("@ss.hasPermission('mp:employee:export')")
    @OperateLog(type = EXPORT)
    public void exportEmployeeExcel(@Valid EmployeeExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<EmployeeDO> list = employeeService.getEmployeeList(exportReqVO);
        // 导出 Excel
        List<EmployeeExcelVO> datas = EmployeeConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "员工信息.xls", "数据", EmployeeExcelVO.class, datas);
    }

}
