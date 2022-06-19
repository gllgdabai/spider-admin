package com.dabai.cms.controller;

import com.dabai.cms.entity.BigEvent;
import com.dabai.cms.entity.SearchResult;
import com.dabai.cms.es.entity.BigEventEntity;
import com.dabai.cms.es.service.BigEventService;
import com.dabai.cms.service.IBigEventService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/** 大事件管理Controller
 * @author
 * @create 2022-06-18 13:19
 */
@Controller
@RequestMapping("/cms/bigEvent")
public class BigEventController extends BaseController {

    @Autowired
    private IBigEventService bigEventService;

    @Autowired
    @Qualifier("cmsBigEventService")
    private BigEventService bigEventServiceES;


    @GetMapping
    @ResponseBody
    public AjaxResult findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "") String year) {
        PageInfo<BigEvent> pageInfo = null;
        SearchResult<BigEventEntity> pageInfoES = null;
        if("".equals(search)) {
            pageInfo = bigEventService.findBigEventByPage(pageNum, pageSize, search, year);
        } else {
            try {
                pageInfoES = bigEventServiceES.findPageByES(pageNum, pageSize, search, year);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(pageInfo != null) {
            return AjaxResult.success(pageInfo);
        }
        return AjaxResult.success(pageInfoES);
    }


    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody BigEvent bigEvent)
    {
        int i = bigEventService.insertBigEvent(bigEvent);
        if(i > 0) {
            // 成功插入数据表cms_bigEvent,需要同步插入es
            BigEventEntity entity = new BigEventEntity();
            entity.setId(bigEvent.getId());
            entity.setYear(bigEvent.getYear());
            entity.setDate(bigEvent.getDate());
            entity.setEvent(bigEvent.getEvent());
            bigEventServiceES.save(entity);
        }
        return toAjax(i);
    }

    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody BigEvent bigEvent)
    {
        int i = bigEventService.updateBigEvent(bigEvent);
        if(i > 0) {
            // 成功更新数据表cms_bigEvent,需要同步更新es
            BigEventEntity entity = new BigEventEntity();
            entity.setId(bigEvent.getId());
            entity.setYear(bigEvent.getYear());
            entity.setDate(bigEvent.getDate());
            entity.setEvent(bigEvent.getEvent());
            bigEventServiceES.save(entity); // 覆盖
        }
        return toAjax(i);
    }

    @PostMapping( "/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable Long id)
    {
        int i = bigEventService.deleteBigEventById(id);
        if (i > 0) { // 数据库删除成功，把es中的内容也同步删除掉
            bigEventServiceES.deleteBigEventEntityById(id);
        }
        return toAjax(i);
    }


}
