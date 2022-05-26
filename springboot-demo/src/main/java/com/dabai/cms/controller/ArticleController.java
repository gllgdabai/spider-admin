package com.dabai.cms.controller;

import com.dabai.cms.entity.Article;
import com.dabai.cms.es.entity.ArticleEntity;
import com.dabai.cms.es.service.ArticleService;
import com.dabai.cms.service.IArticleService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/** 文章管理Controller
 * @author
 * @create 2022-05-15 22:34
 */
@Controller
@RequestMapping("/cms/article")
public class ArticleController extends BaseController
{
    @Autowired
    private IArticleService articleService;

    @Autowired
    @Qualifier("cmsArticleService")
    private ArticleService articleServiceEs;

    @GetMapping
    @ResponseBody
    public AjaxResult findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String search) {
        PageInfo<Article> pageInfo = articleService.selectArticleListByPage(pageNum, pageSize, search);
        return AjaxResult.success(pageInfo);
    }

    @GetMapping("/findByKeywordByPage")
    @ResponseBody
    public AjaxResult findPageByEs(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(defaultValue = "") String searchByEs) {
        PageInfo<ArticleEntity> pageInfo = articleServiceEs.findByKeywordByPage(pageNum, pageSize, searchByEs);
        return AjaxResult.success(pageInfo);
    }


    /**
     * 新增保存文章管理
     */
    @Log(title = "文章管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody Article article)
    {
        return toAjax(articleService.insertArticle(article));
    }


    /**
     * 修改保存文章管理
     */
    @Log(title = "文章管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody Article article)
    {
        int i = articleService.updateArticle(article);
        if (i > 0) { // 数据库更新成功，把es中的内容也同步更新
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setId(article.getId());
            articleEntity.setTitle(article.getTitle());
            articleEntity.setContent(article.getContent());
            articleEntity.setLink(article.getLink());
            articleServiceEs.save(articleEntity); // 存入es
        }
        return toAjax(articleService.updateArticle(article));
    }

    /**
     * 删除文章管理
     */
    @Log(title = "文章管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable String id)
    {
        int i = articleService.deleteArticleById(id);
        if (i > 0) { // 数据库删除成功，把es中的内容也同步删除掉
            articleServiceEs.deleteArticleEntityById(id);
        }
        return toAjax(i);
    }
}
