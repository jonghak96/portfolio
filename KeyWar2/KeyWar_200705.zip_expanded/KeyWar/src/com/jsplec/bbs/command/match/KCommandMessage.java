package com.jsplec.bbs.command.match;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.lec.bbs.dto.KDtoMemberCustomer;
import com.jsp.lec.bbs.dto.KDtoMemberGym;
import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_match;

public class KCommandMessage implements KCommand {
	
	public KCommandMessage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String myId = request.getParameter("myId");
		String match = "false";
		
		KDao_match kDao_match = new KDao_match();
		
		KDtoMemberCustomer member = null;
		KDtoMemberCustomer rival = null;
		KDtoMemberGym gym = null;
		
		if (kDao_match.matchIng(myId)) {
			member = kDao_match.matchMemberInfo(myId);
			rival = kDao_match.matchRivalInfo(myId);
			gym = kDao_match.matchGymInfo(myId);
						
			request.setAttribute("MEMBER", member);
			request.setAttribute("RIVAL", rival);
			request.setAttribute("GYM", gym);
			
			kDao_match.matched(myId);
			match = "true";		
		}
		
		request.setAttribute("match", match);		
	}
}
