package com.idata.gtd.entity;

public class DucumentFile {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ducument_file.id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ducument_file.file_nam
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private String fileNam;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ducument_file.file_md5
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private String fileMd5;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ducument_file.id
	 * @return  the value of ducument_file.id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ducument_file.id
	 * @param id  the value for ducument_file.id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ducument_file.file_nam
	 * @return  the value of ducument_file.file_nam
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public String getFileNam() {
		return fileNam;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ducument_file.file_nam
	 * @param fileNam  the value for ducument_file.file_nam
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setFileNam(String fileNam) {
		this.fileNam = fileNam;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ducument_file.file_md5
	 * @return  the value of ducument_file.file_md5
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public String getFileMd5() {
		return fileMd5;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ducument_file.file_md5
	 * @param fileMd5  the value for ducument_file.file_md5
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}
}