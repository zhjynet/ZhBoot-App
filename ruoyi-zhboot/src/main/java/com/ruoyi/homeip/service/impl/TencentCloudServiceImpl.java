package com.ruoyi.homeip.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.homeip.service.ITencentCloudService;
import com.ruoyi.system.service.ISysConfigService;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.dnspod.v20210323.models.ModifyRecordResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.dnspod.v20210323.DnspodClient;
import com.tencentcloudapi.dnspod.v20210323.models.ModifyRecordRequest;

/**
 * @version: V1.0
 * @author: zhangjy
 * @className: TencentCloudService
 * @packageName: com.ruoyi.homeip.service.impl
 * @description: 腾讯云API接口实现类
 * @data: 2023-12-15 14:03
 **/
@Service
public class TencentCloudServiceImpl implements ITencentCloudService {

    @Autowired
    private ISysConfigService configService;

    private static final Logger log = LoggerFactory.getLogger(TencentCloudServiceImpl.class);


    private DnspodClient createClient() {
        // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey，此处还需注意密钥对的保密
        // 代码泄露可能会导致 SecretId 和 SecretKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议采用更安全的方式来使用密钥，请参见：https://cloud.tencent.com/document/product/1278/85305
        // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
        Credential cred = new Credential(configService.selectConfigByKey("tencent.cloud.secretId"), configService.selectConfigByKey("tencent.cloud.SecretKey"));
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(configService.selectConfigByKey("tencent.cloud.ApiUrl"));
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        return new DnspodClient(cred, "", clientProfile);
    }

    @Override
    public ModifyRecordResponse modifyRecord(ModifyRecordRequest req) {
        DnspodClient client = createClient();
        // 返回的resp是一个ModifyRecordResponse的实例，与请求对象对应
        ModifyRecordResponse resp = null;
        try {
            resp = client.ModifyRecord(req);
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }
        log.info("modifyRecord:resq{}", JSONObject.toJSONString(resp));
        // 输出json格式的字符串回包
        return resp;
    }
}
