package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface CredentialMapper {

    @Results(id = "credentialResult", value = {
            @Result(property = "id", column = "credentialid"),
            @Result(property = "userId", column = "userid"),
    })
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{id}")
    Optional<Credential> findById(Integer id);

    @ResultMap("credentialResult")
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} ORDER BY credentialid DESC")
    Credential[] findByUserId(Integer userId);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "credentialid")
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    Integer add(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{id}")
    void delete(Integer id);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{id}")
    void update(Credential credential);
}
