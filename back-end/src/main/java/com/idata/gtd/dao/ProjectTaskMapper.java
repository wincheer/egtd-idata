package com.idata.gtd.dao;

import com.idata.gtd.entity.ProjectTask;

public interface ProjectTaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_task
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_task
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int insert(ProjectTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_task
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int insertSelective(ProjectTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_task
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    ProjectTask selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_task
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int updateByPrimaryKeySelective(ProjectTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_task
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int updateByPrimaryKey(ProjectTask record);
}