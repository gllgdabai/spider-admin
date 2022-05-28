package com.dabai;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author
 * @create 2022-05-28 13:00
 */
public class RestHighLevelClientTests extends SpringbootDemoApplicationTests {

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public RestHighLevelClientTests(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    // 索引、映射操作
    /**
     *  创建索引 创建映射
     */
    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("fruits");
        // 指定映射。 参数1：指定映射 json结构  参数2：指定数据类型 json
        createIndexRequest.mapping("{\n" +
                "    \"properties\": {\n" +
                "      \"title\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"price\": {\n" +
                "        \"type\": \"double\"\n" +
                "      },\n" +
                "      \"create_at\": {\n" +
                "        \"type\": \"date\"\n" +
                "      },\n" +
                "      \"description\": {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"ik_max_word\"\n" +
                "      }\n" +
                "    }\n" +
                "  }", XContentType.JSON);
        // 参数1：创建索引请求对象  参数2：请求配置对象
        CreateIndexResponse createIndexResponse =
                restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.isAcknowledged());
        restHighLevelClient.close();
    }

    /**
     *  删除索引
     */
    @Test
    public void testDeleteIndex() throws IOException {
        // 参数1：删除索引对象  参数2：请求配置对象
        AcknowledgedResponse response = restHighLevelClient.indices().delete(new DeleteIndexRequest("fruits"), RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    // 文档操作
    /**
     *  索引一条文档
     */
    @Test
    public void testCreate() throws IOException {
        IndexRequest indexRequest = new IndexRequest("fruits");
        indexRequest
                // 手动指定文档id
                .id("2")
                .source("{\n" +
                "  \"title\": \"banana\",\n" +
                "  \"price\": 1.0,\n" +
                "  \"created_at\": \"2022-5-28\",\n" +
                "  \"description\": \"香蕉又便宜又好吃\"\n" +
                "}", XContentType.JSON);

        // 参数1：索引请求对象   参数2：请求配置对象
        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 更新文档
     */
    @Test
    public void testUpdate() throws IOException {
        //参数1：去哪个索引中更新  参数2：更新文档id
        UpdateRequest updateRequest = new UpdateRequest("fruits", "1");
        updateRequest.doc("{\n" +
                "  \"doc\": {\n" +
                "    \"price\": 2.0\n" +
                "  }\n" +
                "}", XContentType.JSON);
        // 参数1：更新请求对象   参数2：请求配置对象
        restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
    }

    /**
     *  删除文档
     */
    @Test
    public void testDelete() throws IOException {
        DeleteResponse response = restHighLevelClient.delete(new DeleteRequest("fruits", "2"), RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 基于 id 查询文档
     */
    @Test
    public void testQueryById() throws IOException {
        GetResponse response = restHighLevelClient.get(new GetRequest("fruits", "1"), RequestOptions.DEFAULT);
        System.out.println("id: " + response.getId());
        System.out.println("source: " + response.getSourceAsString());
    }

    /**
     * 查询所有文档
     */
    @Test
    public void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("fruits");
        // 指定条件对象
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery()); // 查询所有
        searchRequest.source(sourceBuilder);    // 指定查询条件
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("总条数：" + response.getHits().getTotalHits());
        System.out.println("最大得分：" + response.getHits().getMaxScore());
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 不同条件查询
     */
    @Test
    public void testQuery() throws IOException {
        // 1. term查询(关键词查询)
//        query(QueryBuilders.termQuery("description", "口感"));

        // 2. range查询(范围查询)
//        query(QueryBuilders.rangeQuery("price").gt(0).lte(1.2));

        // 3. prefix查询(前缀查询)
        query(QueryBuilders.prefixQuery("title","苹"));

        // 4.multi_match 多字段查询
        query(QueryBuilders.multiMatchQuery("便宜好吃", "title", "description"));
    }

    private void query(QueryBuilder queryBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest("fruits");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("符合条件的总数：" + response.getHits().getTotalHits());
        System.out.println("获取文档最大分数：" + response.getHits().getMaxScore());
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     *  分页查询 form 起始位置 size 每页展示记录数
     */
    @Test
    public void testSearchPage() throws IOException {
        SearchRequest searchRequest = new SearchRequest("article_index");
        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery("国务院", "title", "content"))
                .from(0) // 指定从哪条开始查询 start = (page-1)*size
                .size(5);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("符合条件的总数：" + response.getHits().getTotalHits());
        System.out.println("获取文档最大分数：" + response.getHits().getMaxScore());
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getId());
//            System.out.println(hit.getSourceAsString());
        }
    }

}
