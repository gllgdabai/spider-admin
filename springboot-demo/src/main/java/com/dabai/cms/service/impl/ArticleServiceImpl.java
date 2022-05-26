package com.dabai.cms.service.impl;

import com.dabai.cms.entity.Article;
import com.dabai.cms.es.service.ArticleService;
import com.dabai.cms.mapper.ArticleMapper;
import com.dabai.cms.service.IArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.Guid;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 文章管理Service业务层处理
 * @author
 * @create 2022-05-15
 */
@Service
public class ArticleServiceImpl implements IArticleService
{
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    @Qualifier("cmsArticleService")
    private ArticleService articleServiceEs;    //es

    /**
     * 查询文章管理
     * 
     * @param id 文章管理ID
     * @return 文章管理
     */
    @Override
    public Article selectArticleById(String id)
    {
        Article article=articleMapper.selectArticleById(id);
        Map<String, Object> m = articleMapper.getArticleContent(id);
        if(m!=null){
            article.setContent(String.valueOf(m.get("content")));
            article.setContent_markdown_source(String.valueOf(m.get("content_markdown_source")));
        }
        return article;
    }

    @Override
    public List<Article> selectArticleByIds(List<String> ids) {
        return articleMapper.selectArticleByIds(ids);
    }

    /**
     * 查询文章管理列表
     * 
     * @param article 文章管理
     * @return 文章管理
     */
    @Override
    public List<Article> selectArticleList(Article article)
    {
        return articleMapper.selectArticleList(article);
    }

    /**
     * （分页）查询文章管理列表
     * @param pageNum   当前页码
     * @param pageSize  页码大小
     * @param searchByTitle    用于文章标题模糊搜索
     * @return 文章管理
     */
    @Override
    public PageInfo<Article> selectArticleListByPage(Integer pageNum, Integer pageSize, String searchByTitle) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.selectArticleListByTitile(searchByTitle);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        return pageInfo;
    }

    /**
     * 新增文章管理
     * 
     * @param article 文章管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertArticle(Article article)
    {
        article.setId(Guid.get());
        article.setCreateTime(DateUtils.getNowDate());
        article.setUpdateTime(DateUtils.getNowDate());
        article.setDeleted(0);
        String tags=article.getTags();
        if(StringUtils.isNotEmpty(tags)){
            if(!tags.endsWith(",")){
                tags+=",";
                article.setTags(tags);
            }
        }

        if(article.getCommentFlag()==null){
            article.setCommentFlag("0");
        }
        if("on".equals(article.getCommentFlag())){
            article.setCommentFlag("1");
        }
        if("off".equals(article.getCommentFlag())){
            article.setCommentFlag("0");
        }

        return articleMapper.insertArticle(article);
    }

    /**
     * 修改文章管理
     * 
     * @param article 文章管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateArticle(Article article)
    {
        article.setUpdateTime(DateUtils.getNowDate());
        String tags=article.getTags();
        if(StringUtils.isNotEmpty(tags)){
            if(!tags.endsWith(",")){
                tags+=",";
                article.setTags(tags);
            }
        }
        if(article.getCommentFlag()==null){
            article.setCommentFlag("0");
        }
        if("on".equals(article.getCommentFlag())){
            article.setCommentFlag("1");
        }
        if("off".equals(article.getCommentFlag())){
            article.setCommentFlag("0");
        }
        return articleMapper.updateArticle(article);
    }

    /**
     * 批量删除文章管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteArticleByIds(String ids)
    {
//        articleMapper.deleteArticleContentByIds(Convert.toStrArray(ids));
        return articleMapper.deleteArticleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除文章管理信息
     * 
     * @param id 文章管理ID
     * @return 结果
     */
    @Override
    public int deleteArticleById(String id)
    {
//        articleMapper.deleteArticleContentById(id);
        return articleMapper.deleteArticleById(id);
    }

}
