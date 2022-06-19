package com.dabai;

import com.dabai.cms.entity.BigEvent;
import com.dabai.cms.es.entity.BigEventEntity;
import com.dabai.cms.es.service.BigEventService;
import com.dabai.cms.service.IBigEventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * @author
 * @create 2022-06-19 12:43
 */
public class BigEventESTests extends SpringbootDemoApplicationTests{

    @Autowired
    private IBigEventService bigEventService;

    @Autowired
    @Qualifier("cmsBigEventService")
    private BigEventService bigEventServiceES;

    // 把数据表cms_bigEvent 中的数据全部加入ES服务器
    @Test
    public void addData() {
        // 获取数据表cms_bigEvent的数据
        List<BigEvent> events = bigEventService.findAllBigEvents();
        System.out.println("数据的总数：" + events.size());
        int i = 0;
        for(BigEvent event : events) {
            BigEventEntity entity = new BigEventEntity();
            entity.setId(event.getId());
            entity.setYear(event.getYear());
            entity.setDate(event.getDate());
            entity.setEvent(event.getEvent());

            bigEventServiceES.save(entity);
            System.out.println("已添加 " + (++i) +" 条记录");


        }
    }
}
