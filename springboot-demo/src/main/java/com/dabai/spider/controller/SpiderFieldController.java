package com.dabai.spider.controller;

import com.dabai.spider.entity.SpiderField;
import com.dabai.spider.service.ISpiderFieldService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 爬虫字段Controller
 * @author
 * @create 2022-04-24 10:16
 */
@Controller
@RequestMapping("/spider/spiderField")
public class SpiderFieldController extends BaseController {
    @Autowired
    private ISpiderFieldService spiderFieldService;

    /**
     * 查询爬虫字段列表
     */
    @GetMapping("/{configId}")
    @ResponseBody
    public AjaxResult getSpiderField(@PathVariable("configId") Long configId) {
        List<SpiderField> spiderFields = spiderFieldService.selectSpiderFieldListByConfigId(configId);
        return AjaxResult.success(spiderFields);
    }

    /**
     * 新增保存爬虫字段
     */
    @Log(title = "爬虫字段", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody SpiderField spiderField)
    {
        return toAjax(spiderFieldService.insertSpiderField(spiderField));
    }


    /**
     * 修改保存爬虫字段
     */
    @Log(title = "爬虫字段", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody SpiderField spiderField)
    {
        return toAjax(spiderFieldService.updateSpiderField(spiderField));
    }

    /**
     * 删除爬虫字段
     */
    @Log(title = "爬虫字段", businessType = BusinessType.DELETE)
    @PostMapping( "/remove/{fieldId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable Long fieldId)
    {
        return toAjax(spiderFieldService.deleteSpiderFieldById(fieldId));
    }

}

