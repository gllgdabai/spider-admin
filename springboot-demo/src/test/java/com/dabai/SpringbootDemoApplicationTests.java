package com.dabai;

import com.dabai.cms.entity.User;
import com.dabai.cms.mapper.UserMapper;
import com.dabai.cms.service.IUserService;
import com.dabai.spider.entity.SpiderMission;
import com.dabai.spider.mapper.SpiderMissionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootDemoApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService IUserService;

    @Autowired
    private SpiderMissionMapper spiderMissionMapper;

    @Test
    void contextLoads() {
    }
    // 测试userMapper方法
    @Test
    public void testSelectByName() {
        String s = userMapper.selectPwdByName("dabai");
        System.out.println(s);
    }
    @Test
    public void testSelectOne() {
        User dabai = userMapper.selectOne("dabai", "123456");
        System.out.println(dabai);
    }
    @Test
    public void testSelectUsers() {
        List<User> users = userMapper.selectUsers();
        System.out.println("用户数量为：" + users.size());
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void testSelectUsersBySearch() {
        List<User> users = userMapper.selectUsersBySearch("");
        System.out.println("用户数量为：" + users.size());
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("ljs");
        user.setPassword("123456789");
        user.setNickName("大白");
        user.setAge(23);
        user.setSex("男");
        user.setAddress("广州市");

        int insert = userMapper.insertOne(user);
        System.out.println("是否成功插入，返回值为 " + insert);
    }
    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(18L);
        user.setUsername("ljs123");
        user.setPassword("123456");
        user.setNickName("大白123");
        user.setAge(24);
        user.setSex("男");
        user.setAddress("广州市");

        int update = userMapper.updateById(user);
        System.out.println("是否成功更新，返回值为 " + update);
    }
    @Test
    public void testDeleteById() {
        int i = userMapper.deleteById(18L);
        System.out.println("是否成功删除，返回值为 " + i);
    }

    // 测试分页
//    @Test
//    public void testFindAllUserByPageF() {
//        List<User> users = userService.findAllUserByPageF(2, 3);
//        System.out.println("用户数量为：" + users.size());
//        for (User user : users) {
//            System.out.println(user);
//        }
//    }
//    @Test
//    public void testFindAllUserByPageS() {
//        PageInfo<User> pageInfo = userService.findAllUserByPageS(1, 3);
//
//        System.out.println(pageInfo);
//
//    }

    // 测试spiderMissonMapper
    @Test
    public void testSelectSpiderMissionListBySearch() {
        List<SpiderMission> spiderMissions = spiderMissionMapper.selectSpiderMissionListBySearch("");

        System.out.println("爬虫任务数量为：" + spiderMissions.size());
        for (SpiderMission spiderMission : spiderMissions) {
            System.out.println(spiderMission);
        }
    }

}
