package com.jsplec.bbs.homecontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.command.fighterpage.KCommandFighterContent;
import com.jsplec.bbs.command.fighterpage.KCommandFighterSearch;

/**
 * Servlet implementation class KControllerFighter
 */
@WebServlet("*.kf")
public class KControllerFighter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KControllerFighter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);	
	}

	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
	
		String uri = request.getRequestURI();
		System.out.println("uri = " + uri);
		String conPath = request.getContextPath();
		System.out.println("conPath = " + conPath);
		String com = uri.substring(conPath.length());
		System.out.println("com = " + com);
		
		String viewPage = null;	//~.jsp라는 이름으로 얘한테 줄꺼야.
		KCommand command = null;	// 매번 적기 싫어서 만들어놓음.
		
		switch(com) {				
		case ("/fighter/fighterPage.jsp") :
			command = new KCommandFighterSearch();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/fighter/fighterSearch.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;			
		case ("/fighter/fighterSearch.kf"):
			command = new KCommandFighterSearch();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/fighter/fighterPage.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;			
		case ("/fighter/fighterContent.kf"):
			command = new KCommandFighterContent();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/fighter/fighterContent.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;	
			
			
		default :			
			//response.sendRedirect("mainScreen.jsp");
			break;			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
	
	}
}
