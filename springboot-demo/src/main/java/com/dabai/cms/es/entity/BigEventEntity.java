package com.dabai.cms.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * @author
 * @create 2022-06-19 12:03
 */
@Setting(shards = 5,replicas = 1)
@Document(indexName = "bigevent_index")
@Data
public class BigEventEntity {
//    @Id
    @Field(type = FieldType.Integer)
    private Long id;

    @Field(type = FieldType.Text)
    private String year;

    @Field(type = FieldType.Text)
    private String date;

    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String event;


}
