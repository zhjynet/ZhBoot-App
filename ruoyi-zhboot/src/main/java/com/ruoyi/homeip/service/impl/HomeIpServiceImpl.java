package com.ruoyi.homeip.service.impl;

import java.util.List;
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
public class HomeIpServiceImpl implements IHomeIpService 
{
    @Autowired
    private HomeIpMapper homeIpMapper;

    /**
     * 查询IP地址
     * 
     * @param id IP地址主键
     * @return IP地址
     */
    @Override
    public HomeIp selectHomeIpById(Long id)
    {
        return homeIpMapper.selectHomeIpById(id);
    }

    /**
     * 查询IP地址列表
     * 
     * @param homeIp IP地址
     * @return IP地址
     */
    @Override
    public List<HomeIp> selectHomeIpList(HomeIp homeIp)
    {
        return homeIpMapper.selectHomeIpList(homeIp);
    }

    /**
     * 新增IP地址
     * 
     * @param homeIp IP地址
     * @return 结果
     */
    @Override
    public int insertHomeIp(HomeIp homeIp)
    {
        return homeIpMapper.insertHomeIp(homeIp);
    }

    /**
     * 修改IP地址
     * 
     * @param homeIp IP地址
     * @return 结果
     */
    @Override
    public int updateHomeIp(HomeIp homeIp)
    {
        return homeIpMapper.updateHomeIp(homeIp);
    }

    /**
     * 批量删除IP地址
     * 
     * @param ids 需要删除的IP地址主键
     * @return 结果
     */
    @Override
    public int deleteHomeIpByIds(Long[] ids)
    {
        return homeIpMapper.deleteHomeIpByIds(ids);
    }

    /**
     * 删除IP地址信息
     * 
     * @param id IP地址主键
     * @return 结果
     */
    @Override
    public int deleteHomeIpById(Long id)
    {
        return homeIpMapper.deleteHomeIpById(id);
    }
}
