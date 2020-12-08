package com.jsplec.bbs.command.fighterpage;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.lec.bbs.dto.KDtoFreeBoard;
import com.jsp.lec.bbs.dto.KDtoFreeBoardComment;
import com.jsp.lec.bbs.dto.KDtoMember;
import com.jsp.lec.bbs.dto.KDtoMemberGym;
import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_freeboard;
import com.jsplec.bbs.dao.KDao_member;

public class KCommandFighterContent implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub


		String mId = request.getParameter("mId");
		
		KDao_member dao = new KDao_member();
		
		KDtoMember dto = dao.content(mId);
		
		ArrayList<KDtoMemberGym> dtos = dao.gymList();
		
		request.setAttribute("content", dto);
		request.setAttribute("GYMLIST", dtos);
		
		
		
	}//----

}
