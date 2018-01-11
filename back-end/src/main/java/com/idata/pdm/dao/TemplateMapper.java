package com.idata.pdm.dao;

import java.util.List;

import com.idata.pdm.entity.Template;

public interface TemplateMapper {
    
	int deleteTemplate(Integer templateId);
    
    int insertTemplate(Template record);
    
    Template selectTemplate(Integer templateId);
    
    int updateTemplate(Template record);

	List<Template> selectTemplateList(); 
}