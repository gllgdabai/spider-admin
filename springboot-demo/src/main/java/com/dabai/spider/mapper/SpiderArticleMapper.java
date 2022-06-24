package com.dabai.spider.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author
 * @create 2022-04-03
 */
@Mapper
public interface SpiderArticleMapper {
    /**
     * 将爬取的文章加入数据库表 cms_article
     * @param map
     * @return
     */
    public int insertArticle(Map map);

    /**
     * 将爬取的文章正文内容加入数据库表 cms_article_content
     * @param map
     */
//    public void insertArticleContent(Map map);
}
