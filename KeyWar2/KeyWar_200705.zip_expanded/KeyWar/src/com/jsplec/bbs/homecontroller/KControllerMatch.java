package com.jsplec.bbs.homecontroller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.command.match.KCommandElectronicDisplay;
import com.jsplec.bbs.command.match.KCommandMatching;
import com.jsplec.bbs.command.match.KCommandMatchingList;
import com.jsplec.bbs.command.match.KCommandMessage;

/**
 * Servlet implementation class KControllerMatch
 */
@WebServlet("*.match")
public class KControllerMatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KControllerMatch() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet()");
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
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length());
		System.out.println(com);
		
		String viewPage = null;	//~.jsp라는 이름으로 얘한테 줄꺼야.
		KCommand command = null;	// 매번 적기 싫어서 만들어놓음.
		
		String match = "";
		
		switch(com) {			
		case ("/fighter/matching.match"):
			command = new KCommandMatching();
			command.execute(request, response); // 데이터 다불러왔고,
			response.sendRedirect("http://localhost:8080/KeyWar/fighter/fighterPage.jsp");
			return;
		case ("/login/message.match") :
		case ("/message.match"):
			command = new KCommandMessage();
			command.execute(request, response); // 데이터 다불러왔고,
			match = request.getAttribute("match").toString();
			viewPage = "/match/matchMessage.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;	
		case ("/match/matchingList.match"):
			command = new KCommandMatchingList();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/match/matchingList.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;
		case ("/match/matchingRecord.match"):
			command = new KCommandMatchingList();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/match/matchRecord.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;
		case ("/electronicdisplay.match"):
		case ("/match/electronicdisplay.match"):
			command = new KCommandElectronicDisplay();
			command.execute(request, response);
			viewPage = "/main/electronicDisplay.jsp";
			break;
		}
		
		if (match.equals("false")) {
			PrintWriter out = response.getWriter();
			out.print("false");
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
	}	
}
