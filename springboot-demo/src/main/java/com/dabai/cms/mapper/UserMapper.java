package com.dabai.cms.mapper;

import com.dabai.cms.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author
 * @create 2022-03-21 14:16
 */
@Mapper
public interface UserMapper{
    // 根据用户名查询用户密码
    String selectPwdByName(String username);

    User selectUserByName(String username);

    User selectOne(String username, String password);

    List<User> selectUsers();

    List<User> selectUsersBySearch(String search);

    int insertOne(User user);

    int updateById(User user);

    int deleteById(Long id);

}
