package com.ruoyi.lean.service;

import java.util.List;
import com.ruoyi.lean.domain.LeanSign;

/**
 * 用户签到Service接口
 * 
 * @author zhangjy
 * @date 2024-04-16
 */
public interface ILeanSignService 
{
    /**
     * 查询用户签到
     * 
     * @param id 用户签到主键
     * @return 用户签到
     */
    public LeanSign selectLeanSignById(Long id);

    /**
     * 查询用户签到列表
     * 
     * @param leanSign 用户签到
     * @return 用户签到集合
     */
    public List<LeanSign> selectLeanSignList(LeanSign leanSign);

    /**
     * 新增用户签到
     * 
     * @param leanSign 用户签到
     * @return 结果
     */
    public int insertLeanSign(LeanSign leanSign);

    /**
     * 修改用户签到
     * 
     * @param leanSign 用户签到
     * @return 结果
     */
    public int updateLeanSign(LeanSign leanSign);

    /**
     * 批量删除用户签到
     * 
     * @param ids 需要删除的用户签到主键集合
     * @return 结果
     */
    public int deleteLeanSignByIds(Long[] ids);

    /**
     * 删除用户签到信息
     * 
     * @param id 用户签到主键
     * @return 结果
     */
    public int deleteLeanSignById(Long id);


    public String getSignUrl(String userLoginNo,String signType);
}
