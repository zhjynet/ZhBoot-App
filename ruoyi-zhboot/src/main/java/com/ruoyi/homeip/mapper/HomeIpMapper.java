package com.ruoyi.homeip.mapper;

import java.util.List;
import com.ruoyi.homeip.domain.HomeIp;
import org.apache.ibatis.annotations.Mapper;

/**
 * IP地址Mapper接口
 * 
 * @author zhangjy
 * @date 2023-12-13
 */
@Mapper
public interface HomeIpMapper 
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
     * 删除IP地址
     * 
     * @param id IP地址主键
     * @return 结果
     */
    public int deleteHomeIpById(Long id);

    /**
     * 批量删除IP地址
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHomeIpByIds(Long[] ids);
}
