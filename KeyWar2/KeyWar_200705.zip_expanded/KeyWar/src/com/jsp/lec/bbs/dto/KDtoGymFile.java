package com.jsp.lec.bbs.dto;

import java.io.FileInputStream;

public class KDtoGymFile {
	
	int member_mSeqno;

	int gfSeqno;
	FileInputStream gfPhoto;
	String gfPhotoPath;
	
	
	
	// Constructor
	public KDtoGymFile() {
		// TODO Auto-generated constructor stub
	}


	// 체육관 정보 컨텐트 / 이미지 파일들 가져오는 생성자.
	public KDtoGymFile(String gfPhotoPath) {
		super();
		this.gfPhotoPath = gfPhotoPath;
	}


	
	// Method.
	public String getGfPhotoPath() {
		return gfPhotoPath;
	}


	public void setGfPhotoPath(String gfPhotoPath) {
		this.gfPhotoPath = gfPhotoPath;
	}
	
	
	
	
	
}
