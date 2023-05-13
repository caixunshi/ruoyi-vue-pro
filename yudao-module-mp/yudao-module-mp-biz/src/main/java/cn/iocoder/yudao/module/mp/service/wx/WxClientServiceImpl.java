package cn.iocoder.yudao.module.mp.service.wx;

import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import cn.iocoder.yudao.module.mp.framework.mp.core.MpServiceFactory;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.service.WxOAuth2Service;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author 20520
 * @Date 2023/5/7
 */
@Slf4j
@Service
public class WxClientServiceImpl implements WxClientService {

    @Resource
    @Lazy // 延迟加载，为了解决延迟加载
    private MpServiceFactory mpServiceFactory;

    @Override
    public String getOpenId(String code) {
        // 先判断code是否已存在openId
        Long tenantId = TenantContextHolder.getTenantId();

        // 通过code获取accessToken
        WxOAuth2Service wxOAuth2Service = mpServiceFactory.getRequiredMpService(tenantId).getOAuth2Service();
        try {
            WxOAuth2AccessToken wxOAuth2AccessToken = wxOAuth2Service.getAccessToken(code);
            System.out.println(wxOAuth2AccessToken);
            return wxOAuth2AccessToken.getOpenId();
        } catch (WxErrorException e) {
            log.error("获取openId失败", e);
        }

        return "";
    }

    @Override
    public WxJsapiSignature getJsSdkInfo(String url) {
        // 先判断code是否已存在openId
        Long tenantId = TenantContextHolder.getTenantId();

        // 通过code获取accessToken
        try {
            WxJsapiSignature wxJsapiSignature = mpServiceFactory.getRequiredMpService(tenantId).createJsapiSignature(url);
            System.out.println(wxJsapiSignature);
            return wxJsapiSignature;
        } catch (WxErrorException e) {
            log.error("获取openId失败", e);
        }

        throw new RuntimeException("获取js前面失败");
    }
}
