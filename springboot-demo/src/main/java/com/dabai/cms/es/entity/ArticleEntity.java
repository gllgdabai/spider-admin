package com.dabai.cms.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * @Document 将这个类对象转为 es 中的一条文档进行录入
 *  indexName：用来指定文档的所以名称
 *  createIndex：用来指定是否创建索引，默认为true
 * @Id 用来将放入对象的id字段 作为ES中文档的_id 进行映射
 *
 * @author
 * @create 2022-05-21 10:08
 */
@Setting(shards = 5,replicas = 1)
@Document(indexName = "article_index")
@Data
public class ArticleEntity {
    /** 主键，文章ID */
    // @Id
    private String id;

    /** 文章标题 */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

    /** 文章内容 */
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String content;

    /** 原始链接 */
    @Field(type = FieldType.Text)
    private String link;
}
