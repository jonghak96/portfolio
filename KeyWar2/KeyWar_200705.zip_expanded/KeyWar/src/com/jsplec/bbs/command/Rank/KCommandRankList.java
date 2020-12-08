package com.jsplec.bbs.command.Rank;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.lec.bbs.dto.KDtoMemberCustomer;
import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_rank;

public class KCommandRankList implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		KDao_rank dao = new KDao_rank();
		ArrayList<KDtoMemberCustomer> dtos = dao.mainRankingList();
		
		request.setAttribute("list", dtos);	
		
	}
}