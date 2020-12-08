package com.jsplec.bbs.command.board;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_board;

public class KCommandBoardDelete implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		
		
		String bSeqno = request.getParameter("bSeqno");
		
		KDao_board dao = new KDao_board();
		dao.delete(bSeqno);

		
		
		
	}//-----

}
