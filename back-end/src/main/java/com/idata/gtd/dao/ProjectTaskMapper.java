package com.idata.gtd.dao;

import java.util.List;
import java.util.Map;

import com.idata.gtd.entity.ProjectTask;

public interface ProjectTaskMapper {

	int deleteProjectTaskByPK(Integer id);

	int insertProjectTask(ProjectTask record);
    
	ProjectTask selectProjectTaskByPK(Integer id);

	int updateProjectTask(ProjectTask record);

	List<ProjectTask> selectProjectTaskList(Integer projectId);
	List<ProjectTask> selectProjectStageList(Integer projectId);

	List<ProjectTask> selectMyTaskList(Integer empId);

	List<ProjectTask> selectMyTaskListIn(Integer empId);

	List<ProjectTask> selectMyTaskListOut(Integer empId); 
	
	List<ProjectTask> selectTaskList(ProjectTask record);

	List<Map<String, Object>> selectTaskStateCount4Chart(ProjectTask task);  //项目状态数量，用来绘制统计图表
}