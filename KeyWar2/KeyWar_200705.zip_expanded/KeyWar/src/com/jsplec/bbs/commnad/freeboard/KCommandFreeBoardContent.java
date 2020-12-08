package com.jsplec.bbs.commnad.freeboard;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.lec.bbs.dto.KDtoFreeBoard;
import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_freeboard;

public class KCommandFreeBoardContent implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		String fbSeqno = request.getParameter("fbSeqno");
		
		KDao_freeboard dao = new KDao_freeboard();
		
		KDtoFreeBoard dto = dao.content(fbSeqno);
		dao.viewCount(fbSeqno);
		
		ArrayList<KDtoFreeBoard> dtoFreeBoardFile = dao.contentFile(fbSeqno);
		
		request.setAttribute("content", dto);
		request.setAttribute("file", dtoFreeBoardFile);
		
	}

}//----
