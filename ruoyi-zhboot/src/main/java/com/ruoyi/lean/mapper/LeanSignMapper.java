package com.ruoyi.lean.mapper;

import java.util.List;
import com.ruoyi.lean.domain.LeanSign;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户签到Mapper接口
 * 
 * @author zhangjy
 * @date 2024-04-16
 */
@Mapper
public interface LeanSignMapper 
{
    /**
     * 查询用户签到
     * 
     * @param id 用户签到主键
     * @return 用户签到
     */
    public LeanSign selectLeanSignById(Long id);


    /**
     * 查询用户签到
     *
     * @param userLoginNo 用户签到主键
     * @return 用户签到
     */
    public LeanSign selectLeanSignByUserLoginNo(String userLoginNo);
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
     * 删除用户签到
     * 
     * @param id 用户签到主键
     * @return 结果
     */
    public int deleteLeanSignById(Long id);

    /**
     * 批量删除用户签到
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLeanSignByIds(Long[] ids);
}
