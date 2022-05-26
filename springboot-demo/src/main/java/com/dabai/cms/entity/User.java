package com.dabai.cms.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** 用户 user
 * @author
 * @create 2022-03-21 14:17
 */
@Data
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键，用户ID */
    private Long id;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickName;

    /** 年龄 */
    private Integer age;

    /** 性别 */
    private String sex;

    /** 地址 */
    private String address;

    /** 头像 */
    private String avatar;

    /** 加密盐 */
    private String salt;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("username", getUsername())
                .append("password", getPassword())
                .append("nickName", getNickName())
                .append("age", getAge())
                .append("sex", getSex())
                .append("address", getAddress())
                .append("avatar", getAvatar())
                .append("salt", getSalt())
                .toString();
    }

}
