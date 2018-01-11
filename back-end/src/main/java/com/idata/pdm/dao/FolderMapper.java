package com.idata.pdm.dao;

import java.util.List;

import com.idata.pdm.entity.Folder;

public interface FolderMapper {

	int deleteFolder(Integer folderId);

	int insertFolder(Folder record);

	Folder selectFolder(Integer folderId);

	int updateFolder(Folder record);

	List<Folder> selectFolderList(int projectId);

}