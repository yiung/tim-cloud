package com.yiung.userprovider.Mapper;

import com.yiung.api.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from t_user")
    public List<User> getUserList();

    @Select("select count(1) from t_user where username = #{user.username}")
    public Integer getUserNumberByUsername(@Param("user")User user);

    @Select("select * from t_user where username = #{username}")
    public User getUserByUsername(@Param("username")String username);

    @Select("select user_code,username,password from t_user where username = #{user.username} and password = #{user.password}")
    public List<User> getUserByUsernameAndPassword(@Param("user") User user);

    @Select("select * from t_user where username = #{user.wechat_open_id}")
    public User getUserByOpenId(@Param("user") User user);


}
