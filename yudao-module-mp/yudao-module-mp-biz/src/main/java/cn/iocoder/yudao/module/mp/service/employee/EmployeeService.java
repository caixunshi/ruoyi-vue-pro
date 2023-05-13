package cn.iocoder.yudao.module.mp.service.employee;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.mp.controller.admin.employee.vo.*;
import cn.iocoder.yudao.module.mp.dal.dataobject.employee.EmployeeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 员工信息 Service 接口
 *
 * @author 芋道源码
 */
public interface EmployeeService {

    /**
     * 创建员工信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEmployee(@Valid EmployeeCreateReqVO createReqVO);

    /**
     * 更新员工信息
     *
     * @param updateReqVO 更新信息
     */
    void updateEmployee(@Valid EmployeeUpdateReqVO updateReqVO);

    /**
     * 删除员工信息
     *
     * @param id 编号
     */
    void deleteEmployee(Long id);

    /**
     * 获得员工信息
     *
     * @param id 编号
     * @return 员工信息
     */
    EmployeeDO getEmployee(Long id);

    /**
     * 获得员工信息列表
     *
     * @param ids 编号
     * @return 员工信息列表
     */
    List<EmployeeDO> getEmployeeList(Collection<Long> ids);

    /**
     * 获得员工信息分页
     *
     * @param pageReqVO 分页查询
     * @return 员工信息分页
     */
    PageResult<EmployeeDO> getEmployeePage(EmployeePageReqVO pageReqVO);

    /**
     * 获得员工信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 员工信息列表
     */
    List<EmployeeDO> getEmployeeList(EmployeeExportReqVO exportReqVO);

}
