package com.jsplec.bbs.commnad.freeboard;

import java.util.ArrayList;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.lec.bbs.dto.KDtoFreeBoard;
import com.jsp.lec.bbs.dto.KDtoFreeBoardComment;
import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_freeboard;

public class KCommandFreeBoardCommentContent implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		
		String fbSeqno = request.getParameter("fbSeqno");
		
		KDao_freeboard dao = new KDao_freeboard();
		
		ArrayList<KDtoFreeBoardComment> dtos = dao.commentContent(fbSeqno);
		
		request.setAttribute("commentContent", dtos);
		
		
		
		
		
		
	}//----

}
