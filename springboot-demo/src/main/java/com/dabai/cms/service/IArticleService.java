package com.dabai.cms.service;


import com.dabai.cms.entity.Article;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 文章管理Service接口
 *
 * @author
 * @create 2022-05-15 22:34
 */
public interface IArticleService 
{
    /**
     * 查询文章管理
     * 
     * @param id 文章管理ID
     * @return 文章管理
     */
    public Article selectArticleById(String id);

    public List<Article> selectArticleByIds(List<String> ids);
    /**
     * 查询文章管理列表
     * 
     * @param article 文章管理
     * @return 文章管理集合
     */
    public List<Article> selectArticleList(Article article);

    /**
     * （分页）查询文章管理列表
     * @param pageNum   当前页码
     * @param pageSize  页码大小
     * @param searchByTitle    用于文章标题模糊搜索
     * @return 爬虫任务集合
     */
    public PageInfo<Article> selectArticleListByPage(Integer pageNum, Integer pageSize, String searchByTitle);

    /**
     * 新增文章管理
     * 
     * @param article 文章管理
     * @return 结果
     */
    public int insertArticle(Article article);

    /**
     * 修改文章管理
     * 
     * @param article 文章管理
     * @return 结果
     */
    public int updateArticle(Article article);

    /**
     * 批量删除文章管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteArticleByIds(String ids);

    /**
     * 删除文章管理信息
     * 
     * @param id 文章管理ID
     * @return 结果
     */
    public int deleteArticleById(String id);


}
