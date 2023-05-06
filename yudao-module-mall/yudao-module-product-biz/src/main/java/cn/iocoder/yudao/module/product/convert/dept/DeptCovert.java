package cn.iocoder.yudao.module.product.convert.dept;

import cn.iocoder.yudao.module.product.controller.app.dept.vo.DeptPageItemRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author shi.cai
 * @date 2023/5/6
 */
@Mapper
public interface DeptCovert {
    DeptCovert INSTANCE = Mappers.getMapper(DeptCovert.class);

    DeptPageItemRespVO convert(DeptDO deptDO);
}
