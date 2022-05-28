package com.dabai.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 *  自定义实体
 *  用于暂存es中查询到的列表和总行数
 * @author
 * @create 2022-05-28 18:15
 */
@Data
@AllArgsConstructor
public class SearchResult<T> {
    private List<T> list;
    private long total;
}
