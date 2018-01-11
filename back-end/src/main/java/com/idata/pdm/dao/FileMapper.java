package com.idata.pdm.dao;

import com.idata.pdm.entity.File;

public interface FileMapper {

	int deleteFile(Integer fileId);

    int insertFile(File record);

    File selectFile(Integer fileId);
    File selectFileByMd5(String fileMd5);

    int updateFile(File record);

}