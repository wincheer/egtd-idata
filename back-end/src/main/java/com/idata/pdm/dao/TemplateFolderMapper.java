package com.idata.pdm.dao;

import java.util.List;

import com.idata.pdm.entity.TemplateFolder;

public interface TemplateFolderMapper {

    int deleteTemplateFolder(Integer templateFolderId);

    int insertTemplateFolder(TemplateFolder record);

    TemplateFolder selectTemplateFolder(Integer templateFolderId);

    int updateTemplateFolder(TemplateFolder record);

	List<TemplateFolder> selectTemplateFolderList(int templateId);
}