package cn.iocoder.yudao.module.product.controller.app.dept;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.product.controller.app.dept.vo.DeptPageItemRespVO;
import cn.iocoder.yudao.module.product.controller.app.dept.vo.DeptPageReqVO;
import cn.iocoder.yudao.module.product.convert.dept.DeptCovert;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;


@Tag(name = "用户 APP - 商品 SPU")
@RestController
@RequestMapping("/dept")
@Validated
public class AppDeptController {

    @Resource
    private DeptService deptService;

    @GetMapping("/page")
    @Operation(summary = "获得部门列表")
    public CommonResult<PageResult<DeptPageItemRespVO>> getSpuPage(@Valid DeptPageReqVO pageVO) {
        List<DeptDO> deptList = deptService.getDeptListByParentIdFromCache(0L, false);
        // 过滤数据
        int skip = (pageVO.getPageNo() - 1) * pageVO.getPageSize();
        deptList = deptList.stream().filter(item -> {
            if (StringUtils.hasText(pageVO.getName())) {
                return StringUtils.split(item.getName(), pageVO.getName()).length > 1;
            }
            return true;
        }).filter(item -> {
            if (StringUtils.hasText(pageVO.getAddress())) {
                return pageVO.getAddress().equals(item.getAddress());
            }
            return true;
        }).skip(skip).limit(pageVO.getPageSize()).collect(Collectors.toList());;

        List<DeptPageItemRespVO> resultList = deptList.stream().map(item -> {
            DeptPageItemRespVO deptPageItemRespVO = DeptCovert.INSTANCE.convert(item);
            // 判断当前状态
            if (deptPageItemRespVO.getStatus().equals(CommonStatusEnum.ENABLE.getStatus())) {
                int nowTime = Integer.parseInt(DateFormatUtils.format(new Date(), "hhmm"));
                if (nowTime < deptPageItemRespVO.getOpenStartTime() || nowTime > deptPageItemRespVO.getOpenEndTime()) {
                    deptPageItemRespVO.setStatus(CommonStatusEnum.DISABLE.getStatus());
                }
            }
            return deptPageItemRespVO;
        }).collect(Collectors.toList());
        PageResult pageResult = new PageResult();
        pageResult.setList(resultList);
        return success(pageResult);
    }
}
