package com.dabai.spider.es.service;

import com.dabai.spider.es.dao.ArticleRepository;
import com.dabai.spider.es.entity.ArticleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author
 * @create 2022-05-21 10:21
 */
@Service("spiderArticleService")
public class ArticleService {
    @Autowired
    @Qualifier("spiderArticleRepository")
    private ArticleRepository articleRepository;

    public void save(ArticleEntity articleEntity){
        articleRepository.save(articleEntity);
    }

}
