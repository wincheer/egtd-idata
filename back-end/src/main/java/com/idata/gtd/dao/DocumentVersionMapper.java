package com.idata.gtd.dao;

import com.idata.gtd.entity.DocumentVersion;

public interface DocumentVersionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table document_version
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table document_version
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int insert(DocumentVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table document_version
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int insertSelective(DocumentVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table document_version
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    DocumentVersion selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table document_version
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int updateByPrimaryKeySelective(DocumentVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table document_version
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    int updateByPrimaryKey(DocumentVersion record);
}