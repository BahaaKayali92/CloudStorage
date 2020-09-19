package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.sql.Blob;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid=#{userid}")
    File[] getFile(Integer userid);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File getFileViaId(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename=#{filename}")
    File[] getFileByFilename(String filename);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    int deleteFile(Integer fileId);
}
