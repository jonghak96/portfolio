package com.jsplec.bbs.command.gympage;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.lec.bbs.dto.KDtoGym;
import com.jsp.lec.bbs.dto.KDtoMember;
import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_gym;
import com.jsplec.bbs.dao.KDao_member;

public class KCommandGymSearch implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub


		// Dao 선언
		KDao_gym dao = new KDao_gym();
		
		// 검색 카테고리
		String searchCategory = "";
		// 검색어
		String searchWord = "";
		
		// 총 컬럼 수 
		double rowTotal = 0;
		// 총 페이지 수 = (총 컬럼 수 / 페이지 당 보여줄 갯수) 올림.
		double pageTotal = 0;
		// 현재 위치 페이지
		int page = 0;
		
		if(request.getParameter("page") == null || request.getParameter("page").equals("")) {
			page = 1;
		}else {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		if(request.getParameter("searchCategory") == null || request.getParameter("searchCategory").equals("") ||
				request.getParameter("searchWord") == null || request.getParameter("searchWord").equals("")) {
		
			request.setAttribute("searchCategory", "");
			request.setAttribute("searchWord", "");
			
			rowTotal = dao.Count_list();
			pageTotal = (double)rowTotal / 10;
			pageTotal = Math.ceil(pageTotal);
			
			ArrayList<KDtoGym> kdto = dao.list(page);
			
			// 페이지 처음 뜰 때, 테이블 출력
			request.setAttribute("search", kdto);
			request.setAttribute("pageTotal", (int)pageTotal);
		} else {
			
			searchCategory = request.getParameter("searchCategory");
			searchWord = request.getParameter("searchWord");
			
			rowTotal = dao.CountSearch(searchCategory, searchWord);
			pageTotal = (double)rowTotal / 10;
			pageTotal = Math.ceil(pageTotal);
			
			ArrayList<KDtoGym> kdto = dao.search(searchCategory, searchWord ,page);
			
			// 페이지 처음 뜰 때, 테이블 출력
			request.setAttribute("search", kdto);
			request.setAttribute("pageTotal", (int)pageTotal);
			request.setAttribute("searchCategory", searchCategory);
			request.setAttribute("searchWord", searchWord);
		}		
		
		int point = 1;
		if (page > 10)
			point = (page-1) / 10 + 1;
		
		int min_num = (point - 1) * 10 + 1;	// 리스트를 표시할 첫번째 수
		int max_num = min_num + 9;	// 보여줄 리스트 수
		
		if (max_num > pageTotal)
			max_num = (int)pageTotal;
		
		int back = 0;
		if (point >= 2 ) {	// 선택한 리스s트가 2번째일경우 뒤로가기를 만든다.
			back = point * 10 - 10;
		}
		
		int go = 0;
		if (point >= 1 && point < ((int)pageTotal / 10 + 1)) {	// 선택한 리스트가 1번째 이면서 전체 레코드수를 10으로 나눈값보다 작을때 앞으로가기를 만든다.
			go = point * 10 + 1;
		}
		
		request.setAttribute("point", point);
		request.setAttribute("page", page);
		request.setAttribute("min_num", min_num);
		request.setAttribute("max_num", max_num);
		request.setAttribute("back", back);
		request.setAttribute("go", go);
		
		
		
		
		
		
		
	}//----

}
