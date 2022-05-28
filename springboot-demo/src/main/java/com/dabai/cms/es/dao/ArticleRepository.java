package com.dabai.cms.es.dao;

import com.dabai.cms.es.entity.ArticleEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @create 2022-05-21 10:16
 */
@Repository("cmsArticleRepository")
public interface ArticleRepository extends ElasticsearchRepository<ArticleEntity, String> {
    ArticleEntity findArticleEntityById(String id);

    int deleteArticleEntityById(String id);

    List<ArticleEntity> findByTitleLike(String title);

    List<ArticleEntity> findByContentLike(String content);

    List<ArticleEntity> findByTitleLikeOrContentLike(String title, String content);

}
