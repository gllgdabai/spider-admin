package com.dabai.spider.entity;

/** 爬虫异常类
 * @author
 * @create 2022-04-03
 */
public class SpiderException extends RuntimeException {

    public SpiderException(String message) {
        super("[spider]" + message);
    }

    public SpiderException(String message, Throwable cause) {
        super("[spider]" + message, cause);
    }
}
