package com.idata.gtd.dao;

import com.idata.gtd.entity.Log;

public interface LogMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table log
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table log
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	int insert(Log record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table log
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	int insertSelective(Log record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table log
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	Log selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table log
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	int updateByPrimaryKeySelective(Log record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table log
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	int updateByPrimaryKey(Log record);
}