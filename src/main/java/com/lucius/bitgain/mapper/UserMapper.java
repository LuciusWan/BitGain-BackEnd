package com.lucius.bitgain.mapper;

import com.lucius.bitgain.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User getUserById(Long id);

    /**
     * 根据用户名查询用户
     */
    @Select("select * from user where username = #{username}")
    User getUserByUsername(String username);

    /**
     * 根据手机号查询用户
     */
    @Select("select * from user where phone = #{phone}")
    User getUserByPhone(String phone);

    /**
     * 插入用户
     */
    @Insert("insert into user(username, password, phone, email_subscribe, create_time, update_time) " +
            "values(#{username}, #{password}, #{phone}, #{emailSubscribe}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    /**
     * 更新用户信息
     */
    @Update("update user set username = #{username}, phone = #{phone}, email = #{email}, profession = #{profession}, skills = #{skills}, goals = #{goals}, email_subscribe = #{emailSubscribe}, update_time = #{updateTime} where id = #{id}")
    int updateUser(User user);

    /**
     * 查询所有开启邮件订阅的用户
     */
    @Select("select * from user where email_subscribe = 1 and deleted = 0 and email is not null")
    List<User> getSubscribedUsers();
}
