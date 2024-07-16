package com.ruoyi.homeip.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.List;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.ip.AddressUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.homeip.service.ITencentCloudService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysDictDataService;
import com.tencentcloudapi.dnspod.v20210323.models.ModifyRecordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.homeip.mapper.HomeIpMapper;
import com.ruoyi.homeip.domain.HomeIp;
import com.ruoyi.homeip.service.IHomeIpService;


/**
 * IP地址Service业务层处理
 *
 * @author zhangjy
 * @date 2023-12-13
 */
@Service
public class HomeIpServiceImpl implements IHomeIpService {
    @Autowired
    private HomeIpMapper homeIpMapper;
    @Autowired
    private ITencentCloudService tencentCloudService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private ISysDictDataService iSysDictDataService;

    /**
     * 查询IP地址
     *
     * @param id IP地址主键
     * @return IP地址
     */
    @Override
    public HomeIp selectHomeIpById(Long id) {
        return homeIpMapper.selectHomeIpById(id);
    }

    /**
     * 查询IP地址列表
     *
     * @param homeIp IP地址
     * @return IP地址
     */
    @Override
    public List<HomeIp> selectHomeIpList(HomeIp homeIp) {
        return homeIpMapper.selectHomeIpList(homeIp);
    }

    /**
     * 新增IP地址
     *
     * @param homeIp IP地址
     * @return 结果
     */
    @Override
    public int insertHomeIp(HomeIp homeIp) {
        return homeIpMapper.insertHomeIp(homeIp);
    }

    /**
     * 新增IP地址-通过Servlet
     *
     * @return 结果
     */
    @Override
    public boolean insertHomeIpServlet(String homeName) {
        String homeUserAgent = iSysDictDataService.selectDictLabel("homeip", "homeUserAgent");;
        HomeIp homeIp = getLastOne();
        String nowIp = IpUtils.getIpAddr();
        String userAgent = ServletUtils.getRequest().getHeader("User-Agent");
        String ip = IpUtils.getIpAddr();
        if (homeIp != null) {
            String lastIp = homeIp.getHomeIp();
            if (!lastIp.equals(nowIp)) {
                HomeIp newHomeIp = new HomeIp();
                newHomeIp.setHomeIp(nowIp);
                newHomeIp.setHomeUa(userAgent);
                newHomeIp.setHomeLocation(AddressUtils.getRealAddressByIP(ip));
                newHomeIp.setHomeName(homeName);
                insertHomeIp(newHomeIp);
                if (homeUserAgent.equals(userAgent)) {
                    ModifyRecordRequest req = new ModifyRecordRequest();
                    req.setDomain(configService.selectConfigByKey("tencent.cloud.domain.zh"));
                    req.setSubDomain("home");
                    req.setRecordType("A");
                    req.setRecordLine("默认");
                    req.setValue(nowIp);
                    req.setRecordId(281551793L);
                    tencentCloudService.modifyRecord(req);
                }
            } else {
                homeIp.setGmtModified(new Date(System.currentTimeMillis()));
                homeIp.setHomeName(homeName);
                updateHomeIp(homeIp);
            }
        } else {
            HomeIp newHomeIp = new HomeIp();
            newHomeIp.setHomeIp(nowIp);
            newHomeIp.setHomeUa(userAgent);
            newHomeIp.setHomeLocation(AddressUtils.getRealAddressByIP(ip));
            newHomeIp.setHomeName(homeName);
            insertHomeIp(newHomeIp);
        }
        return true;
    }

    public HomeIp getLastOne() {
        return homeIpMapper.selectLastOne();
    }

    /**
     * 修改IP地址
     *
     * @param homeIp IP地址
     * @return 结果
     */
    @Override
    public int updateHomeIp(HomeIp homeIp) {
        return homeIpMapper.updateHomeIp(homeIp);
    }

    /**
     * 批量删除IP地址
     *
     * @param ids 需要删除的IP地址主键
     * @return 结果
     */
    @Override
    public int deleteHomeIpByIds(Long[] ids) {
        return homeIpMapper.deleteHomeIpByIds(ids);
    }

    /**
     * 删除IP地址信息
     *
     * @param id IP地址主键
     * @return 结果
     */
    @Override
    public int deleteHomeIpById(Long id) {
        return homeIpMapper.deleteHomeIpById(id);
    }

    private String tencentDdns(String ip) {
        String result = "";
        try {
            String shpath = "tencent-ddns " + ip;
            Process ps = Runtime.getRuntime().exec(shpath);
            ps.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
