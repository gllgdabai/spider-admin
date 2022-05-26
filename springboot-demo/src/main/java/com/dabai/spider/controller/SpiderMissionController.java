package com.dabai.spider.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.dabai.spider.entity.SpiderMission;
import com.dabai.spider.service.ISpiderConfigService;
import com.dabai.spider.service.ISpiderMissionService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @create 2022-04-03
 */
@Controller
@RequestMapping("/spider/spiderMission")
public class SpiderMissionController extends BaseController {
    @Autowired
    private ISpiderMissionService spiderMissionService;

    @Autowired
    private ISpiderConfigService spiderConfigService;

    @GetMapping
    @ResponseBody
    public AjaxResult findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        PageInfo<SpiderMission> pageInfo = spiderMissionService.selectSpiderMissionListByPage(pageNum, pageSize, search);
        return AjaxResult.success(pageInfo);
    }



    /**
     * 新增保存爬虫任务
     */
    @Log(title = "爬虫任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody SpiderMission spiderMission)
    {
        String entryUrls=spiderMission.getEntryUrls();
        String[] arr=null;
        if(entryUrls.contains(",")){
            //前台多条传过来用逗号分隔
            arr=entryUrls.split(",");

        }else{
            //前台多条传过来用换行分隔
            arr=entryUrls.split("\r\n");
        }
        if(arr==null||arr.length==0){
            return AjaxResult.error("入口URL必填!多条用逗号分隔或者换行分隔!");
        }
        boolean isUrlFlag=true;
        String tempUrl="";
        String urlAll="";
        for(String url:arr){
            if(!isURL(url)){
                isUrlFlag=false;
                tempUrl=url;
                break;
            }else{
                urlAll+=url+",";
            }
        }
        if(!isUrlFlag){
            return AjaxResult.error("["+tempUrl+"]不是一个有效的url!");
        }
        if(isUrlFlag){
            if(urlAll.endsWith(",")){
                urlAll=urlAll.substring(0,urlAll.length()-1);
                spiderMission.setEntryUrls(urlAll);
            }
        }
        // 把spiderConfigIdName 根据"|"划分，取到配置Id
        String spiderConfigIdName = spiderMission.getSpiderConfigIdName();
        String configId = spiderConfigIdName.split("|")[0];
        spiderMission.setSpiderConfigId(Long.valueOf(configId));
        return toAjax(spiderMissionService.insertSpiderMission(spiderMission));
    }

    private static boolean isURL(String str){
        str = str.toLowerCase();
        String regex = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        return str.matches(regex);
    }

    /**
     * 修改保存爬虫任务
     */
    @Log(title = "爬虫任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody SpiderMission spiderMission)
    {
        String entryUrls=spiderMission.getEntryUrls();
        String[] arr=null;
        if(entryUrls.contains(",")){
            //前台多条传过来用逗号分隔
            arr=entryUrls.split(",");

        }else{
            //前台多条传过来用换行分隔
            arr=entryUrls.split("\r\n");
        }
        if(arr==null||arr.length==0){
            return AjaxResult.error("入口URL必填!多条用逗号分隔或者换行分隔!");
        }
        boolean isUrlFlag=true;
        String tempUrl="";
        String urlAll="";
        for(String url:arr){
            if(!isURL(url)){
                isUrlFlag=false;
                tempUrl=url;
                break;
            }else{
                urlAll+=url+",";
            }
        }
        if(!isUrlFlag){
            return AjaxResult.error("["+tempUrl+"]不是一个有效的url!");
        }
        if(isUrlFlag){
            if(urlAll.endsWith(",")){
                urlAll=urlAll.substring(0,urlAll.length()-1);
                spiderMission.setEntryUrls(urlAll);
            }
        }
        // 把spiderConfigIdName 根据"|"划分，取到配置Id
        String spiderConfigIdName = spiderMission.getSpiderConfigIdName();
        String configId = spiderConfigIdName.split("|")[0];
        spiderMission.setSpiderConfigId(Long.valueOf(configId));
        return toAjax(spiderMissionService.updateSpiderMission(spiderMission));
    }

    /**
     * 删除爬虫任务
     */
    @Log(title = "爬虫任务", businessType = BusinessType.DELETE)
    @PostMapping( "/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(spiderMissionService.deleteSpiderMissionById(id));
    }



    /**
     * 启动爬虫任务
     */
    @PostMapping( "/runSpiderMission/{missionId}")
    @ResponseBody
    public AjaxResult runSpiderMission(@PathVariable("missionId") String missionId)
    {
        return spiderMissionService.runSpiderMission(missionId);
    }

}
