package com.dabai.cms.mapper;

import com.dabai.cms.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 文章管理Mapper接口
 * @author
 * @create 2022-05-15
 */
@Mapper
public interface ArticleMapper
{
    /**
     * 查询文章
     * 
     * @param id 文章ID
     * @return 文章
     */
    public Article selectArticleById(String id);

    public List<Article> selectArticleByIds(List<String> ids);

    /**
     * 查询文章内容
     * @param article_id
     * @return
     */
    public Map<String,Object> getArticleContent(String article_id);

    /**
     * 查询文章列表
     * 
     * @param article 文章
     * @return 文章集合
     */
    public List<Article> selectArticleList(Article article);

    /**
     * 查询文章列表
     * @param searchByTitle 根据文章标题模糊查询
     * @return
     */
    List<Article> selectArticleListByTitile(String searchByTitle);

    /**
     * 新增文章管理
     *
     * @param article 文章
     * @return 结果
     */
    public int insertArticle(Article article);

    /**
     * 插入文章内容
     * @param article
     */
    public int insertArticleContent(Article article);
    /**
     * 修改文章
     * 
     * @param article 文章
     * @return 结果
     */
    public int updateArticle(Article article);

    /**
     *  更新文章内容
     * @param article
     * @return
     */
    public int updateArticleContent(Article article);
    /**
     * 删除文章
     * 
     * @param id 文章ID
     * @return 结果
     */
    public int deleteArticleById(String id);
    /**
     * 删除文章内容
     * @param id 文章ID
     * @return 结果
     */
    public int deleteArticleContentById(String id);
    /**
     * 批量删除文章
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteArticleByIds(String[] ids);

    /**
     * 批量删除文章内容
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteArticleContentByIds(String[] ids);


//    public int checkExistsByTitleAndLink(@Param("title") String title, @Param("link") String link);

}
