package com.ruoyi.homeip.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * IP地址对象 home_ip
 * 
 * @author zhangjy
 * @date 2023-12-13
 */
public class HomeIp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 无业务意义自增ID */
    private Long id;

    /** 家庭名称 */
    @Excel(name = "家庭名称")
    private String homeName;

    /** IP地址 */
    @Excel(name = "IP地址")
    private String homeIp;

    /** 访问UA */
    @Excel(name = "访问UA")
    private String homeUa;

    /** 所属地址 */
    @Excel(name = "所属地址")
    private String homeLocation;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreat;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHomeName(String homeName) 
    {
        this.homeName = homeName;
    }

    public String getHomeName() 
    {
        return homeName;
    }
    public void setHomeIp(String homeIp) 
    {
        this.homeIp = homeIp;
    }

    public String getHomeIp() 
    {
        return homeIp;
    }
    public void setHomeUa(String homeUa) 
    {
        this.homeUa = homeUa;
    }

    public String getHomeUa() 
    {
        return homeUa;
    }
    public void setHomeLocation(String homeLocation) 
    {
        this.homeLocation = homeLocation;
    }

    public String getHomeLocation() 
    {
        return homeLocation;
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
            .append("homeName", getHomeName())
            .append("homeIp", getHomeIp())
            .append("homeUa", getHomeUa())
            .append("homeLocation", getHomeLocation())
            .append("gmtCreat", getGmtCreat())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
