package cn.iocoder.yudao.module.mp.service.wx;

import me.chanjar.weixin.common.bean.WxJsapiSignature;

/**
 * @Author 20520
 * @Date 2023/5/7
 */
public interface WxClientService {
    /**
     * 通过code获取openId
     * @param code
     * @return
     */
    String getOpenId(String code);

    WxJsapiSignature getJsSdkInfo(String url);
}
