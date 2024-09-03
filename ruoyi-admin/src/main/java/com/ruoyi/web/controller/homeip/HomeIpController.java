package com.ruoyi.web.controller.homeip;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.homeip.domain.HomeIp;
import com.ruoyi.homeip.service.IHomeIpService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * IP地址Controller
 * 
 * @author zhangjy
 * @date 2023-12-13
 */
@Api("用户信息管理")
@RestController
@RequestMapping("/homeip/ip")
public class HomeIpController extends BaseController
{
    @Autowired
    private IHomeIpService homeIpService;

    /**
     * 查询IP地址列表
     */
    @ApiOperation("查询IP地址列表")
    @PreAuthorize("@ss.hasPermi('homeip:ip:list')")
    @GetMapping("/list")
    public TableDataInfo list(HomeIp homeIp)
    {
        startPage();
        List<HomeIp> list = homeIpService.selectHomeIpList(homeIp);
        return getDataTable(list);
    }

    /**
     * 导出IP地址列表
     */
    @ApiOperation("导出IP地址列表")
    @PreAuthorize("@ss.hasPermi('homeip:ip:export')")
    @Log(title = "IP地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomeIp homeIp)
    {
        List<HomeIp> list = homeIpService.selectHomeIpList(homeIp);
        ExcelUtil<HomeIp> util = new ExcelUtil<HomeIp>(HomeIp.class);
        util.exportExcel(response, list, "IP地址数据");
    }

    /**
     * 获取IP地址详细信息
     */
    @ApiOperation("获取IP地址详细信息")
    @PreAuthorize("@ss.hasPermi('homeip:ip:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(homeIpService.selectHomeIpById(id));
    }

    /**
     * 新增IP地址
     */
    @ApiOperation("新增IP地址")
    @PreAuthorize("@ss.hasPermi('homeip:ip:add')")
    @Log(title = "IP地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomeIp homeIp)
    {
        return toAjax(homeIpService.insertHomeIp(homeIp));
    }

    @Anonymous
    @RequestMapping("cG9zdG15aG9tZWlwCg/post/home/getip")
    //@PostMapping("cG9zdG15aG9tZWlwCg/post/home/ip")//
    public AjaxResult addIp(String homeName) {
        return toAjax(homeIpService.insertHomeIpServlet(homeName));
    }
    /**
     * 修改IP地址
     */
    @ApiOperation("修改IP地址")
    @PreAuthorize("@ss.hasPermi('homeip:ip:edit')")
    @Log(title = "IP地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomeIp homeIp)
    {
        return toAjax(homeIpService.updateHomeIp(homeIp));
    }

    /**
     * 删除IP地址
     */
    @ApiOperation("删除IP地址")
    @PreAuthorize("@ss.hasPermi('homeip:ip:remove')")
    @Log(title = "IP地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(homeIpService.deleteHomeIpByIds(ids));
    }
}
