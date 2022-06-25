package com.dabai.spider.service.impl;

import cn.hutool.core.convert.Convert;
import com.dabai.spider.backend.SpiderBackendService;
import com.dabai.spider.common.SpiderConstants;
import com.dabai.spider.entity.SpiderMission;
import com.dabai.spider.es.entity.ArticleEntity;
import com.dabai.spider.es.service.ArticleService;
import com.dabai.spider.mapper.SpiderArticleMapper;
import com.dabai.spider.mapper.SpiderConfigMapper;
import com.dabai.spider.mapper.SpiderFieldMapper;
import com.dabai.spider.mapper.SpiderMissionMapper;
import com.dabai.spider.service.ISpiderMissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ICallBack;
import com.ruoyi.common.utils.Guid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author
 * @create 2022-04-03
 */
@Service
public class SpiderMissionServiceImpl implements ISpiderMissionService {

    @Autowired
    private SpiderMissionMapper spiderMissionMapper;

    @Autowired
    private SpiderConfigMapper spiderConfigMapper;

    @Autowired
    private SpiderFieldMapper spiderFieldMapper;

    @Autowired
    private SpiderArticleMapper  spiderArticleMapper;

    @Autowired
    @Qualifier("spiderArticleService")
    private ArticleService articleService;

    @Override
    public SpiderMission selectSpiderMissionById(Long missionId) {
        return spiderMissionMapper.selectSpiderMissionById(missionId);
    }

    @Override
    @DataScope(deptAlias = "a", userAlias = "a")
    public List<SpiderMission> selectSpiderMissionList() {
        return spiderMissionMapper.selectSpiderMissionList();
    }

    @Override
    @DataScope(deptAlias = "a", userAlias = "a")
    public PageInfo<SpiderMission> selectSpiderMissionListByPage(Integer pageNum, Integer pageSize, String search) {
        PageHelper.startPage(pageNum, pageSize);
        List<SpiderMission> spiderMissions = spiderMissionMapper.selectSpiderMissionListBySearch(search);
        PageInfo<SpiderMission> pageInfo = new PageInfo<>(spiderMissions);
        return pageInfo;
    }

    @Override
    public int insertSpiderMission(SpiderMission spiderMission) {
//        spiderMission.setUserId();
//        spiderMission.setDeptId();
        spiderMission.setStatus(SpiderConstants.SPIDER_MISSION_STATUS_WAIT);
//        spiderMission.setCreateBy();
        spiderMission.setCreateTime(new Date());
        return spiderMissionMapper.insertSpiderMission(spiderMission);
    }

    @Override
    public int updateSpiderMission(SpiderMission spiderMission) {
        return spiderMissionMapper.updateSpiderMission(spiderMission);
    }

    @Override
    public int deleteSpiderMissionByIds(String ids) {
        return spiderMissionMapper.deleteSpiderMissionByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteSpiderMissionById(Long missionId) {
        return spiderMissionMapper.deleteSpiderMissionById(missionId);
    }

    @Override
    public AjaxResult runSpiderMission(String missionId) {
        SpiderMission mission = this.selectSpiderMissionById(Long.valueOf(missionId));

        if (mission != null) {
            if (SpiderConstants.SPIDER_MISSION_STATUS_RUNNING.equals(mission.getStatus())) {
                return AjaxResult.error("该任务正在运行中!");
            }
            // 创建回调对象
            SpiderCallBack spiderCallBack = new SpiderCallBack();
            // 创建线程对象去执行爬虫任务
            SpiderBackendService spiderBackendService = new SpiderBackendService(missionId, spiderCallBack);
            spiderBackendService.start();
        }
        return AjaxResult.success();
    }


    // 回调类
    public class SpiderCallBack implements ICallBack {
        // HashMap非线程并发安全，Hashtable线程并发安全的，但它的读取、添加等方法都添加了同步锁，效率太低。
        // 并发Map映射ConcurrentMap
        Map params = Maps.newConcurrentMap();

        // 回调函数：统计爬虫文章的数量，输出到控制台, 文章保存到数据库
        @Override
        @Transactional
        public void onSuccess() {
            System.out.println(">>>>>>>>>>>>>done>>>>>>>>>>>>>>");
            CopyOnWriteArrayList<LinkedHashMap<String, String>> datas = (CopyOnWriteArrayList<LinkedHashMap<String, String>>) params.get("datas");
            System.out.println(">>>>>>>>>>>>>" + datas.size() + ">>>>>>>>>>>>>>");

            datas.forEach(data -> {
                // 文章爬虫配置，一定要配置title(标题)和content(内容)字段
                String title = data.get("title");
                String content = data.get("content");
                String link = data.get("link");     // 爬虫会记录爬取的链接link
                // 过滤数据：过滤掉标题为空或者内容为空的数据
                if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(content)) {
                    HashMap map = new HashMap();
                    String id = Guid.get();
                    Date date = new Date();
                    map.put("id", id);
                    map.put("create_time", date);
                    // 遍历字段，加入到map
                    for (String key : data.keySet()) {
                        String value = data.get(key);
                        map.put(key, value);
                    }
                    int rows = spiderArticleMapper.insertArticle(map);  // 文章加入到数据库

                    if (rows > 0) {
                        // 确保文章成功添加到数据库，才能加入es
                        ArticleEntity articleEntity = new ArticleEntity();
                        articleEntity.setId(id);
                        // 只添加title、content、link字段，其他字段不加入es
                        articleEntity.setTitle(title);
                        articleEntity.setContent(content);
                        articleEntity.setLink(link);
                        articleService.save(articleEntity); // 存入es
                    }
                }
            });

        }

        @Override
        public void onFail() {

        }

        @Override
        public Map setParams(Map map) {
            params.clear();
            params.putAll(map);
            return params;
        }
    }
}
