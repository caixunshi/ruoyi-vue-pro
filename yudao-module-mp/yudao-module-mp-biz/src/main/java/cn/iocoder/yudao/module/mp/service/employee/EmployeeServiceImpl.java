package cn.iocoder.yudao.module.mp.service.employee;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.mp.controller.admin.employee.vo.*;
import cn.iocoder.yudao.module.mp.dal.dataobject.employee.EmployeeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.mp.convert.employee.EmployeeConvert;
import cn.iocoder.yudao.module.mp.dal.mysql.employee.EmployeeMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.mp.enums.ErrorCodeConstants.*;

/**
 * 员工信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Long createEmployee(EmployeeCreateReqVO createReqVO) {
        // 插入
        EmployeeDO employee = EmployeeConvert.INSTANCE.convert(createReqVO);
        employeeMapper.insert(employee);
        // 返回
        return employee.getId();
    }

    @Override
    public void updateEmployee(EmployeeUpdateReqVO updateReqVO) {
        // 校验存在
        validateEmployeeExists(updateReqVO.getId());
        // 更新
        EmployeeDO updateObj = EmployeeConvert.INSTANCE.convert(updateReqVO);
        employeeMapper.updateById(updateObj);
    }

    @Override
    public void deleteEmployee(Long id) {
        // 校验存在
        validateEmployeeExists(id);
        // 删除
        employeeMapper.deleteById(id);
    }

    private void validateEmployeeExists(Long id) {
        if (employeeMapper.selectById(id) == null) {
            throw exception(EMPLOYEE_NOT_EXISTS);
        }
    }

    @Override
    public EmployeeDO getEmployee(Long id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public List<EmployeeDO> getEmployeeList(Collection<Long> ids) {
        return employeeMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<EmployeeDO> getEmployeePage(EmployeePageReqVO pageReqVO) {
        return employeeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<EmployeeDO> getEmployeeList(EmployeeExportReqVO exportReqVO) {
        return employeeMapper.selectList(exportReqVO);
    }

}
