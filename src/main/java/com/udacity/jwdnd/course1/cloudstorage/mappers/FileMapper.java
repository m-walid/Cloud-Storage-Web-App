package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface FileMapper {

    @Results(id = "fileResult", value = {
            @Result(property = "id", column = "fileid"),
            @Result(property = "name", column = "filename"),
            @Result(property = "contentType", column = "contenttype"),
            @Result(property = "size", column = "filesize"),
            @Result(property = "userId", column = "userid"),
            @Result(property = "data", column = "filedata"),
    })
    @Select("SELECT * FROM FILES WHERE fileid = #{id}")
    Optional<File> findById(Integer id);

    @ResultMap("fileResult")
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    File[] findByUserId(Integer userId);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "fileid")
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{name}, #{contentType}, #{size}, #{userId}, #{data})")
    Optional<Integer> add(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{id}")
    void delete(Integer id);


}
