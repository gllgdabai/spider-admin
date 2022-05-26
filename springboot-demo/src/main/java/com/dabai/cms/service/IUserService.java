package com.dabai.cms.service;

import com.dabai.cms.entity.User;
import com.github.pagehelper.PageInfo;

/**
 * @author
 * @create 2022-03-21 14:37
 */
public interface IUserService {
    /**
     * 添加用户
     */
    Boolean save(User user);

    /**
     * 更新用户信息
     */
    Boolean update(User user);
    /**
     * 更新用户信息
     */
    Boolean delete(Long id);

    /**
     * 使用账户名+密码 进行登录，登录成功后返回用户信息
     */
    User login(String account, String password);

    User findUserByName(String userName);

    /**
     *  分页查询，可以按昵称进行模糊查询
     * @param pageNum       页码
     * @param pageSize      页面大小
     * @param search        模糊查询关键字
     */
    PageInfo<User> findUserByPage(Integer pageNum, Integer pageSize, String search);


//    List<User> findAllUserByPageF(int pageNum, int pageSize);
//
//    PageInfo<User> findAllUserByPageS(int pageNum, int pageSize);

    User checkToken(String token);

    User getUserInfoByToken(String token);

}
