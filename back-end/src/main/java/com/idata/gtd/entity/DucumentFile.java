package com.idata.gtd.entity;

public class DucumentFile {

	private Integer id;
	private String fileNam;
	private String fileMd5;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileNam() {
		return fileNam;
	}

	public void setFileNam(String fileNam) {
		this.fileNam = fileNam;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}
}