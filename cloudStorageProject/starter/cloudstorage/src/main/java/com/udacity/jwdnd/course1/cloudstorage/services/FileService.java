package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileService {
    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;
    public FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void uploadFile(MultipartFile file, Integer userid) {
        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            fileMapper.insert(new com.udacity.jwdnd.course1.cloudstorage.models.File(null, file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), userid, file.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }

    public com.udacity.jwdnd.course1.cloudstorage.models.File[] getFiles(Integer userid) {
        return fileMapper.getFile(userid);
    }

    public com.udacity.jwdnd.course1.cloudstorage.models.File getFileById(Integer fileId) {
        return fileMapper.getFileViaId(fileId);
    }

    public int deleteFile(Integer id) {
        return fileMapper.deleteFile(id);
    }

    public String getDataDirectory() {
        return uploadDir + File.separator;
    }

    public boolean isFilenameExist(String filename) {
        return fileMapper.getFileByFilename(filename).length > 0;
    }
}
