package com.jsplec.bbs.homecontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.command.Login.KCommandFindLoginId;
import com.jsplec.bbs.command.Login.KCommandFindLoginPw;
import com.jsplec.bbs.command.Login.KCommandLogin;

/**
 * Servlet implementation class KControllerLogin
 */
@WebServlet("*.lo")
public class KControllerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KControllerLogin() {
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
		// System.out.println(uri);
		String conPath = request.getContextPath();
		// System.out.println(conPath);
		String com = uri.substring(conPath.length());
		//System.out.println(com);

		HttpSession session = request.getSession();
		
		String viewPage = null;	//~.jsp라는 이름으로 얘한테 줄꺼야.
		KCommand command = null;	// 매번 적기 싫어서 만들어놓음.
		
		switch(com) {		
		
		case ("/login/loginCheck.lo"):
			command = new KCommandLogin();
			command.execute(request, response); // 데이터 다불러왔고,
			String loginId = session.getAttribute("loginId").toString();
			System.out.println("loginId = " + loginId);
			if (loginId.equals("") || loginId.equals(null))
				viewPage = "loginFalse.jsp";
			else  {
				response.sendRedirect("../mainScreen.jsp");
				return;
			}
			break;
		case ("/login/findId.lo") :
			command = new KCommandFindLoginId();
			command.execute(request, response);
			System.out.println(request.getAttribute("mId"));
			viewPage = "loginIdFind2.jsp"; //여기로 갈거다
			break;			
		case ("/login/findPw.lo") :
			command = new KCommandFindLoginPw();
			command.execute(request, response);
			System.out.println(request.getAttribute("mPw"));
			viewPage = "loginPwFind2.jsp";
			break;			
		default :			
			viewPage = "../mainScreen.jsp";
			break;			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);	
	}
}
