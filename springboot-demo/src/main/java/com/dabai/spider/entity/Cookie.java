package com.dabai.spider.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author
 * @create 2022-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Cookie {

    String domain;
    String name;
    String value;

    public Cookie(String domain, String name, String value) {
        this.domain = domain;
        this.name = name;
        this.value = value;
    }

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }
}