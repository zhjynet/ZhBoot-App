package com.ruoyi.homeip.service;

import java.util.List;
import com.ruoyi.homeip.domain.HomeIp;

/**
 * IP地址Service接口
 * 
 * @author zhangjy
 * @date 2023-12-13
 */
public interface IHomeIpService 
{
    /**
     * 查询IP地址
     * 
     * @param id IP地址主键
     * @return IP地址
     */
    public HomeIp selectHomeIpById(Long id);

    /**
     * 查询IP地址列表
     * 
     * @param homeIp IP地址
     * @return IP地址集合
     */
    public List<HomeIp> selectHomeIpList(HomeIp homeIp);

    /**
     * 新增IP地址
     * 
     * @param homeIp IP地址
     * @return 结果
     */
    public int insertHomeIp(HomeIp homeIp);

    /**
     * 修改IP地址
     * 
     * @param homeIp IP地址
     * @return 结果
     */
    public int updateHomeIp(HomeIp homeIp);

    /**
     * 批量删除IP地址
     * 
     * @param ids 需要删除的IP地址主键集合
     * @return 结果
     */
    public int deleteHomeIpByIds(Long[] ids);

    /**
     * 删除IP地址信息
     * 
     * @param id IP地址主键
     * @return 结果
     */
    public int deleteHomeIpById(Long id);
}
