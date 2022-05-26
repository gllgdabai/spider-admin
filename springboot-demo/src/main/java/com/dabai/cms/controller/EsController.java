package com.dabai.cms.controller;

import com.dabai.cms.es.entity.ArticleEntity;
import com.dabai.cms.es.service.ArticleService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @create 2022-05-21 11:26
 */
@RestController
@RequestMapping("/cms/es")
public class EsController {
    @Autowired
    @Qualifier("cmsArticleService")
    private ArticleService articleService;

//    @RequestMapping("/save")
//    @ResponseBody
//    public String save(String id,String title,String content,String link){
//        ArticleEntity articleEntity=new ArticleEntity();
//        articleEntity.setId(id);
//        articleEntity.setTitle(title);
//        articleEntity.setContent(content);
//        articleEntity.setLink(link);
//        articleService.save(articleEntity);
//        return "OK";
//    }

    @GetMapping("/findByKeywordByPage")
    @ResponseBody
    public AjaxResult findPageByEs(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String searchByEs) {
        PageInfo<ArticleEntity> pageInfo = articleService.findByKeywordByPage(pageNum, pageSize, searchByEs);
        return AjaxResult.success(pageInfo);
    }

    @RequestMapping("/findById")
    @ResponseBody
    public Object findArticleEntityById(String id){
        ArticleEntity articleEntity=articleService.findArticleEntityById(id);
        return articleEntity;
    }

    @RequestMapping("/findByKeyword")
    @ResponseBody
    public Object findByKeyword(String keyword){
        List<ArticleEntity> list=articleService.findByKeyword(keyword);
        return list;
    }

    @RequestMapping("/deleteById")
    @ResponseBody
    public String deleteArticleEntityById(String id){
        int i=articleService.deleteArticleEntityById(id);
        return i+"";
    }
}
