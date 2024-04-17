package com.ruoyi.homeip.service.impl;

import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.homeip.service.IRonghwService;

/**
 * @version: V1.0
 * @author: zhangjy
 * @className: RonghwServiceImpl
 * @packageName: com.ruoyi.homeip.service.impl
 * @description:
 * @data: 2023-12-25 14:44
 **/
public class RonghwServiceImpl implements IRonghwService {

    @Override
    public void find() {
        HttpUtils.sendSSLPost("https://www.ronghw.cn/api/visitor/websiteCommon/list?v=1703486567511","{\"page\":1,\"pageSize\":10,\"sorter\":\"createTime=desc\",\"filter\":\"耕文路\"}");
    }

    public static void main(String[] args) {
        HttpUtils.sendSSLPost("https://www.ronghw.cn/api/visitor/websiteCommon/list?v=1703486567511","{\"page\":1,\"pageSize\":10,\"sorter\":\"createTime=desc\",\"filter\":\"耕文路\"}");

    }
}
