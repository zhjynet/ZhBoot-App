package com.ruoyi.web.controller.lean;

import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.lean.domain.LeanSign;
import com.ruoyi.lean.service.ILeanSignService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户签到Controller
 *
 * @author zhangjy
 * @date 2024-04-16
 */
@RestController
@RequestMapping("/lean/sign")
public class LeanSignController extends BaseController {
    @Autowired
    private ILeanSignService leanSignService;

    /**
     * 查询用户签到列表
     */
    @PreAuthorize("@ss.hasPermi('lean:sign:list')")
    @GetMapping("/list")
    public TableDataInfo list(LeanSign leanSign) {
        startPage();
        List<LeanSign> list = leanSignService.selectLeanSignList(leanSign);
        return getDataTable(list);
    }

    /**
     * 导出用户签到列表
     */
    @PreAuthorize("@ss.hasPermi('lean:sign:export')")
    @Log(title = "用户签到", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LeanSign leanSign) {
        List<LeanSign> list = leanSignService.selectLeanSignList(leanSign);
        ExcelUtil<LeanSign> util = new ExcelUtil<LeanSign>(LeanSign.class);
        util.exportExcel(response, list, "用户签到数据");
    }

    /**
     * 获取用户签到详细信息
     */
    @PreAuthorize("@ss.hasPermi('lean:sign:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(leanSignService.selectLeanSignById(id));
    }

    /**
     * 新增用户签到
     */
    @PreAuthorize("@ss.hasPermi('lean:sign:add')")
    @Log(title = "用户签到", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LeanSign leanSign) {
        return toAjax(leanSignService.insertLeanSign(leanSign));
    }

    /**
     * 修改用户签到
     */
    @PreAuthorize("@ss.hasPermi('lean:sign:edit')")
    @Log(title = "用户签到", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LeanSign leanSign) {
        return toAjax(leanSignService.updateLeanSign(leanSign));
    }

    /**
     * 删除用户签到
     */
    @PreAuthorize("@ss.hasPermi('lean:sign:remove')")
    @Log(title = "用户签到", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(leanSignService.deleteLeanSignByIds(ids));
    }


    @ApiOperation("新增IP地址-接口调用")
    @Anonymous
    @GetMapping("/lean/getUrl")
    public ResponseEntity<Object> getUrl(String userLoginNo, String signType) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(leanSignService.getSignUrl(userLoginNo, signType)))
                .build();
        //return AjaxResult.success(leanSignService.getSignUrl(userLoginNo,signType));
    }
}
