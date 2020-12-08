package com.jsplec.bbs.command.board;

import java.util.ArrayList;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.lec.bbs.dto.KDtoBoardComment;
import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_board;

public class KCommandBoardCommentContent implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		
		
		
		String bSeqno = request.getParameter("bSeqno");
		
		KDao_board dao = new KDao_board();
		
		ArrayList<KDtoBoardComment> dtos = dao.commentContent(bSeqno);
		
		request.setAttribute("commentContentVideo", dtos);
		
		
		
		
		
		
	}//----

}
