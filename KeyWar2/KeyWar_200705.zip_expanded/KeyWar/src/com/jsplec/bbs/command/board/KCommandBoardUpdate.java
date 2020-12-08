package com.jsplec.bbs.command.board;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_board;

public class KCommandBoardUpdate implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		
		String bSeqno = request.getParameter("bSeqno");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		KDao_board dao = new KDao_board();
		dao.update(bSeqno, bTitle, bContent);
		
		
		
		
	}//----

}
