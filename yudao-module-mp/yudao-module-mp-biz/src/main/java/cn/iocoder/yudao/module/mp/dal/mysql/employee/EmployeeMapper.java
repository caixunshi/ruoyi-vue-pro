package cn.iocoder.yudao.module.mp.dal.mysql.employee;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.mp.dal.dataobject.employee.EmployeeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.mp.controller.admin.employee.vo.*;

/**
 * 员工信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface EmployeeMapper extends BaseMapperX<EmployeeDO> {

    default PageResult<EmployeeDO> selectPage(EmployeePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EmployeeDO>()
                .likeIfPresent(EmployeeDO::getName, reqVO.getName())
                .eqIfPresent(EmployeeDO::getMobile, reqVO.getMobile())
                .orderByDesc(EmployeeDO::getId));
    }

    default List<EmployeeDO> selectList(EmployeeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EmployeeDO>()
                .likeIfPresent(EmployeeDO::getName, reqVO.getName())
                .eqIfPresent(EmployeeDO::getMobile, reqVO.getMobile())
                .orderByDesc(EmployeeDO::getId));
    }

}
