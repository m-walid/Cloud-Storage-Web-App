package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface NoteMapper {

    @Results(id = "noteResult", value = {
            @Result(property = "id", column = "noteid"),
            @Result(property = "title", column = "notetitle"),
            @Result(property = "description", column = "notedescription"),
            @Result(property = "userId", column = "userid"),
    })
    @Select("SELECT * FROM NOTES WHERE noteid = #{id}")
    Optional<Note> findById(Integer id);

    @ResultMap("noteResult")
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    Note[] findByUserId(Integer userId);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "noteid")
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{title}, #{description}, #{userId})")
    Optional<Integer> add(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{id}")
    void delete(Integer id);

    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE noteid = #{id}")
    void update(Note note);

}
