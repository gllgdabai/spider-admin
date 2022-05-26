package com.dabai.spider.resolver;

import com.dabai.spider.entity.SpiderConfig;
import us.codecraft.webmagic.Page;

/**
 * 页面解析器
 * @author
 * @create 2022-04-03
 */
public interface Resolver {
    void process(Page page, SpiderConfig spiderConfig);
}
