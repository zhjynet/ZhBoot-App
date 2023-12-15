package com.ruoyi.homeip.service;

import com.tencentcloudapi.dnspod.v20210323.models.ModifyRecordRequest;
import com.tencentcloudapi.dnspod.v20210323.models.ModifyRecordResponse;

/**
 * @version: V1.0
 * @author: zhangjy
 * @className: ITencentCloudService
 * @packageName: com.ruoyi.homeip.service
 * @description: 腾讯云相关API
 * @data: 2023-12-15 13:51
 **/
public interface ITencentCloudService {
    public ModifyRecordResponse modifyRecord(ModifyRecordRequest req);
}
