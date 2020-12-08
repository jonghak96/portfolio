package com.jsplec.bbs.command.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_member;

public class KCommandLogin implements KCommand{

	
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String mId = request.getParameter("mId");
		String mPw = request.getParameter("mPw");
		KDao_member dao = new KDao_member();
		String loginId = dao.loginId(mId, mPw);
		
		HttpSession session = request.getSession();
	
		session.setAttribute("loginId", loginId);
	}
}