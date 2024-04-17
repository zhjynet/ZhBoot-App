package com.ruoyi.lean.service.impl;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.List;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.system.service.ISysDictDataService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.lean.mapper.LeanSignMapper;
import com.ruoyi.lean.domain.LeanSign;
import com.ruoyi.lean.service.ILeanSignService;

import javax.net.ssl.SSLContext;

/**
 * 用户签到Service业务层处理
 *
 * @author zhangjy
 * @date 2024-04-16
 */
@Service
public class LeanSignServiceImpl implements ILeanSignService {
    @Autowired
    private LeanSignMapper leanSignMapper;
    @Autowired
    private ISysDictDataService iSysDictDataService;

    /**
     * 查询用户签到
     *
     * @param id 用户签到主键
     * @return 用户签到
     */
    @Override
    public LeanSign selectLeanSignById(Long id) {
        return leanSignMapper.selectLeanSignById(id);
    }

    /**
     * 查询用户签到列表
     *
     * @param leanSign 用户签到
     * @return 用户签到
     */
    @Override
    public List<LeanSign> selectLeanSignList(LeanSign leanSign) {
        return leanSignMapper.selectLeanSignList(leanSign);
    }

    /**
     * 新增用户签到
     *
     * @param leanSign 用户签到
     * @return 结果
     */
    @Override
    public int insertLeanSign(LeanSign leanSign) {
        return leanSignMapper.insertLeanSign(leanSign);
    }

    /**
     * 修改用户签到
     *
     * @param leanSign 用户签到
     * @return 结果
     */
    @Override
    public int updateLeanSign(LeanSign leanSign) {
        return leanSignMapper.updateLeanSign(leanSign);
    }

    /**
     * 批量删除用户签到
     *
     * @param ids 需要删除的用户签到主键
     * @return 结果
     */
    @Override
    public int deleteLeanSignByIds(Long[] ids) {
        return leanSignMapper.deleteLeanSignByIds(ids);
    }

    /**
     * 删除用户签到信息
     *
     * @param id 用户签到主键
     * @return 结果
     */
    @Override
    public int deleteLeanSignById(Long id) {
        return leanSignMapper.deleteLeanSignById(id);
    }

    @Override
    public String getSignUrl(String userLoginNo, String signType) {
        LeanSign leanSign = leanSignMapper.selectLeanSignByUserLoginNo(userLoginNo);
        String sessionId = "";

        if (leanSign != null) {
            sessionId = leanSign.getUserSessionId();
        }
        String leanSignUrl = getUserSignUrl(sessionId, signType);
        if ("401".equals(leanSignUrl)) {
            sessionId = getUserLoginSessionId(userLoginNo);
            leanSignUrl = getUserSignUrl(sessionId, signType);
        }
        LeanSign leanSign1 = new LeanSign();
        leanSign1.setUserSignUrl(leanSignUrl);
        leanSign1.setUserLoginNo(userLoginNo);
        leanSign1.setUserSessionId(sessionId);
        leanSignMapper.insertLeanSign(leanSign1);
        return leanSignUrl;
    }

    public String getUserLoginSessionId(String userLoginNo) {
        String userPass = iSysDictDataService.selectDictLabel("lean", userLoginNo);
        String leanLoginUrl = iSysDictDataService.selectDictLabel("lean", "loginUrl");
        String data = "{\"data\":{\"loginFlag\":\"1\",\"loginNo\":\"" + userLoginNo + "\",\"password\":\"" + userPass + "\",\"verifiedSeq\":\"\",\"verifiedCd\":\"\"}}";
        String resultString = HttpUtil.post(leanLoginUrl, data);
        JSONObject result = JSONObject.parseObject(resultString);
        return result.getJSONObject("data").getString("sessionId");
    }

    public String getUserSignUrl(String sessionId, String signType) {
        String leanSignUrl = iSysDictDataService.selectDictLabel("lean", "signUrl");
        // 创建一个包含所需数据的JSONObject
        JSONObject dataObject = new JSONObject();
        dataObject.put("signType", signType);
        dataObject.put("isMock", false);

        // 将dataObject包装在另一个JSONObject中
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", dataObject);
        // 将最终的JSONObject转换为JSON字符串
        String jsonStr = jsonObject.toJSONString();
        String resulrUrl = "";
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (X509Certificate[] chain, String authType) -> true).build();
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

            // 创建POST请求
            HttpPost post = new HttpPost(leanSignUrl);

            // 设置请求头
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Cookie", "lean_t_s=" + sessionId);

            // 设置请求体
            StringEntity entity = new StringEntity(jsonStr, "UTF-8");
            post.setEntity(entity);

            // 执行请求
            HttpResponse response = httpClient.execute(post);
            String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject result = JSONObject.parseObject(responseBody);
            if (result.getInteger("status") == 401) {
                return "401";
            }
            if (result.getJSONObject("data").getString("result") != null) {
                resulrUrl = result.getJSONObject("data").getString("result");
            }
            // 处理响应
            // ...（根据需要处理响应内容）
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resulrUrl;
    }


    public static void main(String[] args) {
        String data1 = "{\"data\":{\"signType\":1,\"isMock\":true}}";

        // 创建一个包含所需数据的JSONObject
        JSONObject dataObject = new JSONObject();
        dataObject.put("signType", 1);
        dataObject.put("isMock", true);

        // 将dataObject包装在另一个JSONObject中
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", dataObject);

        // 将最终的JSONObject转换为JSON字符串
        String jsonStr = jsonObject.toJSONString();
        // 创建HttpClient实例

        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (X509Certificate[] chain, String authType) -> true).build();
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

            // 创建POST请求
            HttpPost post = new HttpPost("https://lean.zj96596.com.cn:5443/ms/portal/api/it/signIn");

            // 设置请求头
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Cookie", "lean_t_s=ABB23A88A77D04EBE3EDCA4C5F31BCE7FDE178D3E714F0C8193077D9793731151221B7DACAF87EC5FCA31A037413CC843BCAD5DBB21896CBDFE3BED4FF7DAD87");

            // 设置请求体
            StringEntity entity = new StringEntity(jsonStr, "UTF-8");
            post.setEntity(entity);

            // 执行请求
            HttpResponse response = httpClient.execute(post);

            String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("Response Body: " + responseBody);
            // 处理响应

            // ...（根据需要处理响应内容）

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
