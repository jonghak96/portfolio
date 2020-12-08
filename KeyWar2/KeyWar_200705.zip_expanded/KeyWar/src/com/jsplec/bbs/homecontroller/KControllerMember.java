package com.jsplec.bbs.homecontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.command.Login.KCommandFindLoginId;
import com.jsplec.bbs.command.Login.KCommandFindLoginPw;
import com.jsplec.bbs.command.Login.KCommandLogin;
import com.jsplec.bbs.command.Member.KCommandGymInfo;
import com.jsplec.bbs.command.Member.KCommandGymSignUp2;
import com.jsplec.bbs.command.Member.KCommandGymUpdate;
import com.jsplec.bbs.command.Member.KCommandMemberInfo;
import com.jsplec.bbs.command.Member.KCommandMemberSignUp;
import com.jsplec.bbs.command.Member.KCommandMemberUpdate;
import com.jsplec.bbs.command.Rank.KCommandRankList;
import com.jsplec.bbs.command.fighterpage.KCommandFighterContent;
import com.jsplec.bbs.command.fighterpage.KCommandFighterSearch;
import com.jsplec.bbs.command.gympage.KCommandGymContent;
import com.jsplec.bbs.command.gympage.KCommandGymSearch;

/**
 * Servlet implementation class KControllerMember
 */
@WebServlet("*.mbr")
public class KControllerMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KControllerMember() {
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
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length());
		// System.out.println(com);
		
		String viewPage = null;	//~.jsp라는 이름으로 얘한테 줄꺼야.
		KCommand command = null;	// 매번 적기 싫어서 만들어놓음.
		
		switch(com) {				
		case ("/fighter/fighterSearch.mbr"):
			command = new KCommandFighterSearch();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/fighter/fighterSearch.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;			
		case ("/fighter/fighterContent.mbr"):
			command = new KCommandFighterContent();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/fighter/fighterContent.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;		
		case ("/gym/gymSearch.mbr"):
			command = new KCommandGymSearch();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/gym/gymSearch.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;		
		case ("/gym/gymContent.mbr"):
			command = new KCommandGymContent();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/gym/gymContent.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;		
		case("/member/customerSignUp.mbr"):
			System.out.println("시작");
			command = new KCommandMemberSignUp();
			System.out.println("함수갔다옴");
			command.execute(request, response);
			System.out.println("값주고 뷰페이지 이동.");
			viewPage = "memberSignUpCompelete.jsp";
			break;			
		case("/member/gymSignUp.mbr"):
			KCommandGymSignUp2 command2 = new KCommandGymSignUp2();
			command2.execute(request, response);
			viewPage = "memberSignUpCompelete.jsp";
			break;			
		case("/main/mainScreen.mbr"):
			command = new KCommandRankList();
			command.execute(request, response);
			viewPage = "fighterListTop3.jsp";
			break;		
		case("/main/loginMainScreen.mbr"):
			command = (KCommand) new KCommandLogin();
			command.execute(request, response);
			viewPage = "loginMainScreen.jsp";
			break;			
		case("/main/memberInfo.mbr"):
			command = new KCommandMemberInfo();
			command.execute(request, response);
			viewPage = "memberUpdate.jsp";
			break;			
		case("/member/memberUpdate.mbr"):
			command = new KCommandMemberUpdate();
			command.execute(request, response);
			viewPage = "../member/updateCompelete.jsp";
			break;			
		case("/main/gymInfo.mbr"):
			command = new KCommandGymInfo();
			command.execute(request, response);
			viewPage = "gymUpdate.jsp";
			break;			
		case("/main/gymUpdate.mbr"):
			command = new KCommandGymUpdate();
			command.execute(request, response);
			viewPage = "../member/updateCompelete.jsp";
			break;			
		default :			
			viewPage = "/main/mainScreen.jsp";
			break;			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
	
	}
	
	
}//----
