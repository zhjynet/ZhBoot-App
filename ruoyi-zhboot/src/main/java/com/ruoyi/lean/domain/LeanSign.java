package com.ruoyi.lean.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户签到对象 lean_sign
 * 
 * @author zhangjy
 * @date 2024-04-16
 */
public class LeanSign extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 无业务意义自增ID */
    private Long id;

    /** 登录账号 */
    @Excel(name = "登录账号")
    private String userLoginNo;

    /** 用户sessionId */
    @Excel(name = "用户sessionId")
    private String userSessionId;

    /** 用户URL */
    @Excel(name = "用户URL")
    private String userSignUrl;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtCreat;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtModified;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserLoginNo(String userLoginNo) 
    {
        this.userLoginNo = userLoginNo;
    }

    public String getUserLoginNo() 
    {
        return userLoginNo;
    }
    public void setUserSessionId(String userSessionId) 
    {
        this.userSessionId = userSessionId;
    }

    public String getUserSessionId() 
    {
        return userSessionId;
    }
    public void setUserSignUrl(String userSignUrl) 
    {
        this.userSignUrl = userSignUrl;
    }

    public String getUserSignUrl() 
    {
        return userSignUrl;
    }
    public void setGmtCreat(Date gmtCreat) 
    {
        this.gmtCreat = gmtCreat;
    }

    public Date getGmtCreat() 
    {
        return gmtCreat;
    }
    public void setGmtModified(Date gmtModified) 
    {
        this.gmtModified = gmtModified;
    }

    public Date getGmtModified() 
    {
        return gmtModified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userLoginNo", getUserLoginNo())
            .append("userSessionId", getUserSessionId())
            .append("userSignUrl", getUserSignUrl())
            .append("gmtCreat", getGmtCreat())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
