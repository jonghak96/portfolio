package com.jsplec.bbs.command.Member;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.lec.bbs.dto.KDtoUpdateList;
import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_member;

public class KCommandMemberInfo implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		KDao_member dao = new KDao_member();
		
		// String A라는 놈을 list(A)에 넣고 KDao_member메소드 선언 부분에 String A(A가 필욯ㅈㄷㄹㅎㅁ냉햐)
		String A = request.getParameter("sessionId");
		
		ArrayList<KDtoUpdateList> dtos = dao.listSignUpUpdate(A);
		request.setAttribute("list", dtos);
		
	}

}
