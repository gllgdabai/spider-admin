package com.dabai.cms.es.service;

import com.dabai.cms.es.dao.ArticleRepository;
import com.dabai.cms.es.entity.ArticleEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @create 2022-05-21 10:21
 */
@Service("cmsArticleService")
public class ArticleService {
    @Autowired
    @Qualifier("cmsArticleRepository")
    private ArticleRepository articleRepository;

    public ArticleEntity findArticleEntityById(String id) {
        return articleRepository.findArticleEntityById(id);
    }

    public int deleteArticleEntityById(String id) {
        return articleRepository.deleteArticleEntityById(id);
    }

    public List<ArticleEntity> findByKeyword(String keyword) {
        return articleRepository.findByTitleLikeOrContentLike(keyword, keyword);
    }

    public void save(ArticleEntity articleEntity){
        articleRepository.save(articleEntity);
    }


    /**
     * （分页）查询文章管理列表，通过ElasticSearch全文检索
     * @param pageNum   当前页码
     * @param pageSize  页码大小
     * @param searchByEs    用于es搜索
     * @return 文章管理
     */
    public PageInfo<ArticleEntity> findByKeywordByPage(Integer pageNum, Integer pageSize, String searchByEs) {
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleEntity> articles = articleRepository.findByTitleLikeOrContentLike(searchByEs, searchByEs);
        PageInfo<ArticleEntity> pageInfo = new PageInfo<>(articles);
        return pageInfo;
    }

}
