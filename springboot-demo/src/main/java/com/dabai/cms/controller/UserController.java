package com.dabai.cms.controller;

import com.dabai.cms.entity.User;
import com.dabai.cms.service.IUserService;
import com.dabai.common.Result;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @create 2022-03-21 14:40
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final String SALT = "123456AdBai@!###$$";

    @Autowired
    private IUserService IUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    public Result<?> save(@RequestBody User user) {
        Boolean save = IUserService.save(user);
        return (save) ? Result.success() : Result.error("-999","添加失败");
    }
    @PutMapping
    public Result<?> update(@RequestBody User user) {
        Boolean update = IUserService.update(user);
        return (update) ? Result.success() : Result.error("-999","更新失败");
    }
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        Boolean delete = IUserService.delete(id);
        return (delete) ? Result.success() : Result.error("-999","删除失败");
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        PageInfo<User> pageInfo = IUserService.findUserByPage(pageNum, pageSize, search);
        return Result.success(pageInfo);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User userParam) {
        String username = userParam.getUsername();
        String password = userParam.getPassword();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.error("-999", "用户名或密码不能为空");
        }
        String pwd_salt = password + SALT;  //加盐
        String pwd_md5 = DigestUtils.md5DigestAsHex(pwd_salt.getBytes());  // md5加密
        User userRes = IUserService.login(username, pwd_md5);
        if (userRes == null) {
            return Result.error("-999","用户名或密码错误");
        }
//        //登录成功，使用JWT生成token，返回token，并加入到redis中
//        String token = JWTUtil.createToken(userRes.getId());
//        redisTemplate.opsForValue().set("Token_"+token, JSON.toJSONString(userRes), 1, TimeUnit.DAYS);
        return Result.success(userRes);
    }

    @PostMapping("/register")
    @Transactional //加上事务，一旦中间任一过程出现问题，注册的账户需要回滚
    public Result<?> register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.error("-1","用户名或密码不能为空");
        }
        User res = IUserService.findUserByName(user.getUsername());
        if (res != null) {
            return Result.error("-1", "用户名重复");
        }
        String pwd_salt = password + SALT;  //加盐
        password = DigestUtils.md5DigestAsHex(pwd_salt.getBytes());  // md5加密
        User userInfo = new User();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setNickName("");
        userInfo.setSalt(SALT);

        IUserService.save(userInfo);

//        String token = JWTUtil.createToken(userInfo.getId());
//
//        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(userInfo),1, TimeUnit.DAYS);
        return Result.success();
    }
}
