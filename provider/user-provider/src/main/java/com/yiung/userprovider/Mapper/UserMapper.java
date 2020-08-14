package com.yiung.userprovider.Mapper;

import entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from t_user")
    public List<User> getUserList();

    @Select("select count(1) from t_user where username = #{user.username}")
    public Integer getUserNumberByUsername(@RequestBody User user);

    @Select("select user_code,username,password from t_user where username = #{user.username} and password = #{user.password}")
    public List<User> getUserByUsernameAndPassword(@RequestBody User user);


}
