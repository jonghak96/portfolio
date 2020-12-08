package com.jsplec.bbs.command.gympage;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.lec.bbs.dto.KDtoGym;
import com.jsp.lec.bbs.dto.KDtoGymFile;
import com.jsp.lec.bbs.dto.KDtoMember;
import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_gym;
import com.jsplec.bbs.dao.KDao_member;

public class KCommandGymContent implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		
		String mId = request.getParameter("mId");
		
		KDao_gym dao = new KDao_gym();
		
		KDtoGym dto = dao.content(mId);
		ArrayList<KDtoGymFile> kdto = dao.gymFile(mId);
		
		request.setAttribute("content", dto);
		request.setAttribute("gymphoto", kdto);
		
		
		
	}//----

}
