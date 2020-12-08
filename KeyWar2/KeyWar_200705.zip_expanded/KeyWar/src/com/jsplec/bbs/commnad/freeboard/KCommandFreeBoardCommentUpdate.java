package com.jsplec.bbs.commnad.freeboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_freeboard;

public class KCommandFreeBoardCommentUpdate implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		
		
		String fcSeqno = request.getParameter("fcSeqno");
		String fcContent = request.getParameter("fcContent");
		
		KDao_freeboard dao = new KDao_freeboard();
		dao.commentUpdate(fcSeqno, fcContent);
		
		
	}//----

}
