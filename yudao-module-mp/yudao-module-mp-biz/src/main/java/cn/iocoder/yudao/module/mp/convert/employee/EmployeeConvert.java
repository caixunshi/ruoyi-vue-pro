package cn.iocoder.yudao.module.mp.convert.employee;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.mp.controller.admin.employee.vo.*;
import cn.iocoder.yudao.module.mp.dal.dataobject.employee.EmployeeDO;

/**
 * 员工信息 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface EmployeeConvert {

    EmployeeConvert INSTANCE = Mappers.getMapper(EmployeeConvert.class);

    EmployeeDO convert(EmployeeCreateReqVO bean);

    EmployeeDO convert(EmployeeUpdateReqVO bean);

    EmployeeRespVO convert(EmployeeDO bean);

    List<EmployeeRespVO> convertList(List<EmployeeDO> list);

    PageResult<EmployeeRespVO> convertPage(PageResult<EmployeeDO> page);

    List<EmployeeExcelVO> convertList02(List<EmployeeDO> list);

}
