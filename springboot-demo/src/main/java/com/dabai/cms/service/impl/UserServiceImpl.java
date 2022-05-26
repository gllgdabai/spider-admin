package com.dabai.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.dabai.cms.entity.User;
import com.dabai.cms.mapper.UserMapper;
import com.dabai.cms.service.IUserService;
import com.dabai.common.utils.JWTUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2022-03-21 14:38
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Boolean save(User user) {
        if (user.getPassword() == null) {
            user.setPassword("123456"); //密码默认为123456
        }
        int i = userMapper.insertOne(user);
        return (i > 0) ? true : false;
    }
    @Override
    public Boolean update(User user) {
        int i = userMapper.updateById(user);
        return (i > 0) ? true : false;
    }



    @Override
    public Boolean delete(Long id) {
        int i = userMapper.deleteById(id);
        return (i > 0) ? true : false;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.selectOne(username, password);
        return user;
    }

    @Override
    public User findUserByName(String userName) {
        User user = userMapper.selectUserByName(userName);
        return user;
    }


    @Override
    public PageInfo<User> findUserByPage(Integer pageNum, Integer pageSize, String search) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectUsersBySearch(search);
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        return pageInfo;
    }

//    @Override
//    public List<User> findAllUserByPageF(int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<User> users = userMapper.selectUsers();
//        return users;
//    }
//
//    @Override
//    public PageInfo<User> findAllUserByPageS(int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<User> users = userMapper.selectUsers();
//        PageInfo<User> pageInfo = new PageInfo<User>(users);
//        return pageInfo;
//    }

    @Override
    public User checkToken(String token) {
        if(StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> map = JWTUtil.checkToken(token);
        if(map == null) {
            return null;
        }

        String userJson = (String) redisTemplate.opsForValue().get("Token_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        return JSON.parseObject(userJson, User.class);
    }

    @Override
    public User getUserInfoByToken(String token) {
        Map<String, Object> map = JWTUtil.checkToken(token);
        if (map == null){
            return null;
        }
        String userJson = (String) redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        User user = JSON.parseObject(userJson, User.class);
        return user;
    }

}
