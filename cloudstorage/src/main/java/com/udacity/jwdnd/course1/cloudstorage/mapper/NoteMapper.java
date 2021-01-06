package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid=#{userId}")
    List<Note> getAllNotesByUserId(int userId);

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES(#{noteTitle},#{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES SET " + "notetitle = #{noteTitle}, notedescription = #{noteDescription}" + "WHERE noteid = #{noteId}")
    int updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid= #{noteId}")
    int deleteNote(int noteId);
}


//    CREATE TABLE IF NOT EXISTS NOTES (
//        noteid INT PRIMARY KEY auto_increment,
//        notetitle VARCHAR(20),
//    notedescription VARCHAR (1000),
//    userid INT,
//    foreign key (userid) references USERS(userid)
//        );