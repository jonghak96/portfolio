package com.jsplec.bbs.command.match;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.lec.bbs.dto.KDtoMatch;
import com.jsp.lec.bbs.dto.KDtoMemberCustomer;
import com.jsp.lec.bbs.dto.KDtoMemberGym;
import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_match;

public class KCommandMatchingList implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

	 	String id = request.getParameter("mId");
	 	String type = request.getParameter("mType");
	 	String matchSeqno = null;

		KDao_match kDao_match = new KDao_match();
		
	 	if (request.getParameter("matchSeqno") != null) {
	 		matchSeqno = request.getParameter("matchSeqno");
	 		KDao_match.matchIngChange(matchSeqno, type);
	 	}
	 	
	 	if (request.getParameter("cancle") != null) {
	 		matchSeqno = request.getParameter("matchSeqno");
	 		KDao_match.matchIngCancle(matchSeqno);
	 	}
		
		ArrayList<KDtoMemberCustomer> member = null;
		ArrayList<KDtoMemberCustomer> rival = null;
		ArrayList<KDtoMemberGym> gym = null;	
		ArrayList<KDtoMatch> matchSeqnoList = null;
		
		member = kDao_match.matchListMember(id, type);
		rival = kDao_match.matchListRival(id, type);
		gym = kDao_match.matchListGym(id, type);
		matchSeqnoList = kDao_match.matchListSeqno(id, type);		
		
		request.setAttribute("MEMBER", member);
		request.setAttribute("RIVAL", rival);
		request.setAttribute("GYM", gym);
		request.setAttribute("MATCHSEQNO", matchSeqnoList);
	}

}
