package com.dabai.cms.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author
 * @create 2022-05-21 10:08
 */
//es 7.0需注意地方 shards ,replicas 在document弃用，改用setting注入
//@Setting(shards = 6,replicas = 3)
@Document(indexName = "article_index")
@Data
public class ArticleEntity {
    /** 主键，文章ID */
    @Field(type = FieldType.Integer)
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
