package com.dabai.cms.es.service;

import com.alibaba.fastjson.JSONObject;
import com.dabai.cms.entity.SearchResult;
import com.dabai.cms.es.dao.BigEventRepository;
import com.dabai.cms.es.entity.BigEventEntity;
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
 * @create 2022-06-19 12:38
 */
@Service("cmsBigEventService")
public class BigEventService {
    @Autowired
    @Qualifier("cmsBigEventRepository")
    private BigEventRepository bigEventRepository;

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public BigEventService(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    public int deleteBigEventEntityById(Long id) {
        return bigEventRepository.deleteBigEventEntityById(id);
    }

    public List<BigEventEntity> findByEventLike(String event) {
        return bigEventRepository.findByEventLike(event);
    }

    public void save(BigEventEntity bigEventEntity) {
        bigEventRepository.save(bigEventEntity);
    }

    /**
     * （分页）查询文章管理列表，通过ElasticSearch全文检索
     * @param pageNum   当前页码
     * @param pageSize  页码大小
     * @param search    用于es搜索
     * @return 自定义实体SearchResult 来接收 查询的结果
     */
    public SearchResult<BigEventEntity> findPageByES(Integer pageNum, Integer pageSize, String search, String year) throws IOException {
        SearchRequest searchRequest = new SearchRequest("bigevent_index");
        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if ("".equals(year)) {
            searchSourceBuilder.query(QueryBuilders.termQuery("event", search))
                    .from((pageNum-1)*pageSize) // 指定从哪条开始查询 start = (page-1)*size
                    .size(pageSize);
        } else {
            searchSourceBuilder.query(QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("year", year))
                            .must(QueryBuilders.termQuery("event", search)))
                    .from((pageNum-1)*pageSize) // 指定从哪条开始查询 start = (page-1)*size
                    .size(pageSize);
        }

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<BigEventEntity> events = new ArrayList<>();
        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits().value;
        for (SearchHit hit : hits) {
            BigEventEntity eventEntity = JSONObject.parseObject(hit.getSourceAsString(), BigEventEntity.class);
            events.add(eventEntity);
        }
        return new SearchResult<>(events, total);
    }

}
