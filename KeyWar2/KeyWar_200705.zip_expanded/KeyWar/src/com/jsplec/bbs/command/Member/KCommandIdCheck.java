package com.jsplec.bbs.command.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_member;

public class KCommandIdCheck implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		String id = request.getParameter("id");
		
		KDao_member kDao_member = new KDao_member();
		String idCheck = kDao_member.idCheck(id);
		
		request.setAttribute("idCheck", idCheck);		
	}
}
