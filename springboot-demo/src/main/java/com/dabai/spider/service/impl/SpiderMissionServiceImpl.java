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
                return AjaxResult.error("????????????????????????!");
            }
            // ??????????????????
            SpiderCallBack spiderCallBack = new SpiderCallBack();
            // ???????????????????????????????????????
            SpiderBackendService spiderBackendService = new SpiderBackendService(missionId, spiderCallBack);
            spiderBackendService.start();
        }
        return AjaxResult.success();
    }


    // ?????????
    public class SpiderCallBack implements ICallBack {
        // HashMap????????????????????????Hashtable????????????????????????????????????????????????????????????????????????????????????????????????
        // ??????Map??????ConcurrentMap
        Map params = Maps.newConcurrentMap();

        // ???????????????????????????????????????????????????????????????, ????????????????????????
        @Override
        @Transactional
        public void onSuccess() {
            System.out.println(">>>>>>>>>>>>>done>>>>>>>>>>>>>>");
            CopyOnWriteArrayList<LinkedHashMap<String, String>> datas = (CopyOnWriteArrayList<LinkedHashMap<String, String>>) params.get("datas");
            System.out.println(">>>>>>>>>>>>>" + datas.size() + ">>>>>>>>>>>>>>");

            datas.forEach(data -> {
                // ????????????????????????????????????title(??????)???content(??????)??????
                String title = data.get("title");
                String content = data.get("content");
                String link = data.get("link");     // ??????????????????????????????link
                // ???????????????????????????????????????????????????????????????
                if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(content)) {
                    HashMap map = new HashMap();
                    String id = Guid.get();
                    Date date = new Date();
                    map.put("id", id);
                    map.put("create_time", date);
                    // ????????????????????????map
                    for (String key : data.keySet()) {
                        String value = data.get(key);
                        map.put(key, value);
                    }
                    int rows = spiderArticleMapper.insertArticle(map);  // ????????????????????????

                    if (rows > 0) {
                        // ???????????????????????????????????????????????????es
                        ArticleEntity articleEntity = new ArticleEntity();
                        articleEntity.setId(id);
                        // ?????????title???content???link??????????????????????????????es
                        articleEntity.setTitle(title);
                        articleEntity.setContent(content);
                        articleEntity.setLink(link);
                        articleService.save(articleEntity); // ??????es
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
