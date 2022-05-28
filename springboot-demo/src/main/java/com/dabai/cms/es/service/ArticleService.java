package com.dabai.cms.es.service;

import com.alibaba.fastjson.JSONObject;
import com.dabai.cms.entity.SearchResult;
import com.dabai.cms.es.dao.ArticleRepository;
import com.dabai.cms.es.entity.ArticleEntity;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public ArticleService(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

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
     * @param searchByTitle    用于es搜索
     * @return 自定义实体SearchResult 来接收 查询的结果
     */
    public SearchResult<ArticleEntity> findPageByTitle(Integer pageNum, Integer pageSize, String searchByTitle) throws IOException {
        SearchRequest searchRequest = new SearchRequest("article_index");
        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(searchByTitle, "title"))
                .from((pageNum-1)*pageSize) // 指定从哪条开始查询 start = (page-1)*size
                .size(pageSize);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<ArticleEntity> articles = new ArrayList<>();
        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits().value;
        for (SearchHit hit : hits) {
            ArticleEntity articleEntity = JSONObject.parseObject(hit.getSourceAsString(), ArticleEntity.class);
            articles.add(articleEntity);
        }
        return new SearchResult<>(articles, total);
    }

    /**
     * （分页）查询文章管理列表，通过ElasticSearch全文检索
     * @param pageNum   当前页码
     * @param pageSize  页码大小
     * @param search    用于es搜索
     * @return 自定义实体SearchResult 来接收 查询的结果
     */
    public SearchResult<ArticleEntity> findPageByKeyword(Integer pageNum, Integer pageSize, String search) throws IOException {
        SearchRequest searchRequest = new SearchRequest("article_index");
        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(search, "title", "content"))
                .from((pageNum-1)*pageSize) // 指定从哪条开始查询 start = (page-1)*size
                .size(pageSize);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<ArticleEntity> articles = new ArrayList<>();
        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits().value;
        for (SearchHit hit : hits) {
            ArticleEntity articleEntity = JSONObject.parseObject(hit.getSourceAsString(), ArticleEntity.class);
            articles.add(articleEntity);
        }
        return new SearchResult<>(articles, total);
    }

}
