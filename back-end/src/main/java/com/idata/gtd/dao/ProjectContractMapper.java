package com.idata.gtd.dao;

import com.idata.gtd.entity.ProjectContract;

public interface ProjectContractMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_contract
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_contract
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int insert(ProjectContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_contract
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int insertSelective(ProjectContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_contract
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    ProjectContract selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_contract
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int updateByPrimaryKeySelective(ProjectContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_contract
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int updateByPrimaryKey(ProjectContract record);
}