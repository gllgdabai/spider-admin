package com.dabai.spider.controller;

import com.dabai.spider.entity.SpiderConfig;
import com.dabai.spider.service.ISpiderConfigService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 爬虫配置Controller
 * 
 * @author
 * @date 2022-04-03
 */
@Controller
@RequestMapping("/spider/spiderConfig")
public class SpiderConfigController extends BaseController {

    @Autowired
    private ISpiderConfigService spiderConfigService;

    @GetMapping
    @ResponseBody
    public AjaxResult findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String searchByName,
                               @RequestParam(defaultValue = "") String searchByCode) {
        PageInfo<SpiderConfig> pageInfo = spiderConfigService.selectSpiderConfigListByPage(pageNum, pageSize, searchByName, searchByCode);
        return AjaxResult.success(pageInfo);
    }

    @GetMapping("/searchByName")
    @ResponseBody
    public AjaxResult getList(@RequestParam(defaultValue = "") String name) {
        List<SpiderConfig> spiderConfigs = spiderConfigService.selectSpiderConfigListByName(name);
        return AjaxResult.success(spiderConfigs);
    }

    /**
     * 新增保存爬虫配置
     */
    @Log(title = "爬虫配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody SpiderConfig spiderConfig)
    {
        SpiderConfig check=spiderConfigService.selectSpiderConfigByCode(spiderConfig.getSpiderCode());
        if(check!=null){
            return AjaxResult.error("爬虫编码["+spiderConfig.getSpiderCode()+"]已经存在!");
        }
        return toAjax(spiderConfigService.insertSpiderConfig(spiderConfig));
    }


    /**
     * 修改保存爬虫配置
     */
    @Log(title = "爬虫配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody SpiderConfig spiderConfig)
    {
        return toAjax(spiderConfigService.updateSpiderConfig(spiderConfig));
    }

    /**
     * 删除爬虫配置
     */
    @Log(title = "爬虫配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(spiderConfigService.deleteSpiderConfigById(id));
    }

}
