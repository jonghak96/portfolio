package com.jsp.lec.bbs.dto;

import java.sql.Timestamp;

public class KDtoFreeBoardComment {

	// freeBoardComment
	int fcSeqno;
	String fcWriter;
	String fcContent;
	Timestamp fcDate;
	
	
	// Constructor
	public KDtoFreeBoardComment() {
		// TODO Auto-generated constructor stub
	}

	public KDtoFreeBoardComment(int fcSeqno, String fcWriter, String fcContent, Timestamp fcDate) {
		super();
		this.fcSeqno = fcSeqno;
		this.fcWriter = fcWriter;
		this.fcContent = fcContent;
		this.fcDate = fcDate;
	}





	// Method
	
	public int getFcSeqno() {
		return fcSeqno;
	}

	public void setFcSeqno(int fcSeqno) {
		this.fcSeqno = fcSeqno;
	}

	public String getFcContent() {
		return fcContent;
	}

	public void setFcContent(String fcContent) {
		this.fcContent = fcContent;
	}

	public Timestamp getFcDate() {
		return fcDate;
	}

	public void setFcDate(Timestamp fcDate) {
		this.fcDate = fcDate;
	}

	public String getFcWriter() {
		return fcWriter;
	}

	public void setFcWriter(String fcWriter) {
		this.fcWriter = fcWriter;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}//----
