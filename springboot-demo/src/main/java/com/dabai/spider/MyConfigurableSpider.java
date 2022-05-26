package com.dabai.spider;

import com.dabai.spider.entity.ExitWayEnum;
import com.dabai.spider.entity.SpiderConfig;
import com.dabai.spider.entity.SpiderException;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 爬虫入口
 * @author
 * @create 2022-04-03
 */
public class MyConfigurableSpider extends Spider {

    /**
     * 用来保存正在运行的所有Spider，key要求唯一，一般为用户ID，需要调用方生成
     */
    public static final ConcurrentHashMap<String, MyConfigurableSpider> SPIDER_BUCKET = new ConcurrentHashMap<>();

    private SpiderConfig config;

    /**
     * 唯一的key，一般为用户ID，需要调用方生成
     */
    private String missionId;
    private volatile long startTime = 0L;

    private MyConfigurableSpider(PageProcessor pageProcessor, SpiderConfig config, String missionId) {
        super(pageProcessor);
        this.config = config;
        this.missionId = missionId;
        SPIDER_BUCKET.put(missionId, this);
    }

    public static MyConfigurableSpider create(PageProcessor pageProcessor, SpiderConfig config, String missionId) {
        return new MyConfigurableSpider(pageProcessor, config, missionId);
    }

    public static MyConfigurableSpider getSpider(String missionId) {
        if (StringUtils.isBlank(missionId)) {
            throw new SpiderException("missionId：[" + missionId + "]为空，请指定missionId");
        }
        MyConfigurableSpider spider = SPIDER_BUCKET.get(missionId);
        if (null == spider) {
            throw new SpiderException("当前没有正在运行的爬虫！missionId：[" + missionId + "]");
        }
        return spider;
    }

    @Override
    protected void onSuccess(Request request) {
        super.onSuccess(request);
        if (this.getStatus() == Status.Running && ExitWayEnum.DURATION.toString().equals(config.getExitWay())) {
            if (startTime < System.currentTimeMillis()) {
                this.stop();
            }
        }
    }

    @Override
    public void run() {
        if (ExitWayEnum.DURATION.toString().equals(config.getExitWay())) {
            startTime = System.currentTimeMillis() + config.getCount() * 1000;
        }
        super.run();
    }

    @Override
    protected void onError(Request request) {
        super.onError(request);
    }

    @Override
    public void close() {
        super.close();
        SPIDER_BUCKET.remove(this.missionId);
    }

    @Override
    public void stop() {
        Status status = this.getStatus();
        if (status.equals(Status.Running)) {
            super.stop();
            SPIDER_BUCKET.remove(this.missionId);
        } else if (status.equals(Status.Init)) {
            throw new SpiderException("爬虫正在初始化！missionId：[" + this.missionId + "]");
        } else {
            throw new SpiderException("当前没有正在运行的爬虫！missionId：[" + this.missionId + "]");
        }
    }
}
