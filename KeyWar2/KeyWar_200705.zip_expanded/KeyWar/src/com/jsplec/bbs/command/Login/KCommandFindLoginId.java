package com.jsplec.bbs.command.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_member;

public class KCommandFindLoginId implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	String mName = request.getParameter("mName");
	String mTelno = request.getParameter("mTelno");
	String mEmail = request.getParameter("mEmail");
	KDao_member dao = new KDao_member();
	String mId = dao.findId(mName, mTelno, mEmail);
	
	request.setAttribute("mId", mId);
	
	}

}
