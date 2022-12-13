package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface UserMapper {

    @Results(id = "userResult", value = {
            @Result(property = "id", column = "userid"),
    })
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    Optional<User> findByUsername(String username);

    @ResultMap("userResult")
    @Select("SELECT * FROM USERS WHERE userid = #{id}")
    Optional<User> findById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "userid")
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    Optional<Integer> add(User user);

}
