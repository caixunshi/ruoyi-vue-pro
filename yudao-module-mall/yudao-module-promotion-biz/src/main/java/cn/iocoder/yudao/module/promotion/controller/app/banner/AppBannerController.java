package cn.iocoder.yudao.module.promotion.controller.app.banner;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.promotion.controller.admin.banner.vo.BannerRespVO;
import cn.iocoder.yudao.module.promotion.convert.banner.BannerConvert;
import cn.iocoder.yudao.module.promotion.dal.dataobject.banner.BannerDO;
import cn.iocoder.yudao.module.promotion.service.banner.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * @author: XIA
 */
@RestController
@RequestMapping("/market/banner")
@Tag(name = "用户APP- 首页Banner")
@Validated
public class AppBannerController {

    @Resource
    private BannerService bannerService;

    // TODO @xia：新建一个 AppBannerRespVO，只返回必要的字段。status 要过滤下。然后 sort 下结果
    @GetMapping("/list")
    @Operation(summary = "获得banner列表")
    public CommonResult<List<BannerRespVO>> getBannerList() {
        List<BannerDO> list = bannerService.getBannerList();
        return success(BannerConvert.INSTANCE.convertList(list));
    }

}
