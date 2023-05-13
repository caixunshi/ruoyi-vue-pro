package cn.iocoder.yudao.module.mp.controller.app.wx;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.mp.controller.app.wx.vo.AccessTokenReqVO;
import cn.iocoder.yudao.module.mp.service.wx.WxClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "微信鉴权")
@RestController
@RequestMapping("/wx")
@Validated
@Slf4j
public class SignController {

    @Resource
    private WxClientService wxClientService;

    @GetMapping("/getOpenId")
    @Operation(summary = "获取用户openId")
    public CommonResult<String> getOpenId(@RequestParam String code) {
        String openId = wxClientService.getOpenId(code);
        // 最终组合
        return success(openId);
    }

    @PostMapping("/getJsSdkInfo")
    @Operation(summary = "获取js-sdk签名")
    public CommonResult<WxJsapiSignature> getJsSdkInfo(@RequestBody AccessTokenReqVO reqVO) {
        // 最终组合
        return success(wxClientService.getJsSdkInfo(reqVO.getUrl()));
    }
}
