package com.jsplec.bbs.homecontroller;

import java.io.IOException;






import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.command.board.KCommandBoardCommentContent;
import com.jsplec.bbs.command.board.KCommandBoardCommentDelete;
import com.jsplec.bbs.command.board.KCommandBoardCommentUpdate;
import com.jsplec.bbs.command.board.KCommandBoardCommentWrite;
import com.jsplec.bbs.command.board.KCommandBoardContent;
import com.jsplec.bbs.command.board.KCommandBoardDelete;
import com.jsplec.bbs.command.board.KCommandBoardLike;
import com.jsplec.bbs.command.board.KCommandBoardSearch;
import com.jsplec.bbs.command.board.KCommandBoardUpdate;
import com.jsplec.bbs.command.board.KCommandBoardWrite;
import com.jsplec.bbs.commnad.freeboard.KCommandFreeBoardCommentContent;
import com.jsplec.bbs.commnad.freeboard.KCommandFreeBoardCommentDelete;
import com.jsplec.bbs.commnad.freeboard.KCommandFreeBoardCommentUpdate;
import com.jsplec.bbs.commnad.freeboard.KCommandFreeBoardCommentWrite;
import com.jsplec.bbs.commnad.freeboard.KCommandFreeBoardContent;
import com.jsplec.bbs.commnad.freeboard.KCommandFreeBoardDelete;
import com.jsplec.bbs.commnad.freeboard.KCommandFreeBoardLike;
import com.jsplec.bbs.commnad.freeboard.KCommandFreeBoardSearch;
import com.jsplec.bbs.commnad.freeboard.KCommandFreeBoardUpdate;
import com.jsplec.bbs.commnad.freeboard.KCommandFreeBoardWrite;



/**
 * Servlet implementation class BFrontController
 */
@WebServlet("*.do")
public class KControllerBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KControllerBoard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet()");
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost()");
		actionDo(request, response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("actionDo()");
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		// System.out.println(uri);
		String conPath = request.getContextPath();
		// System.out.println(conPath);
		String com = uri.substring(conPath.length());
		System.out.println(com);
		
		String viewPage = null;	//~.jsp라는 이름으로 얘한테 줄꺼야.
		KCommand command = null;	// 매번 적기 싫어서 만들어놓음.
		
		
		// com으로 분기시킴.
		switch(com) {		
		case ("/freeboard/freeboardSearch.do"):
			command = new KCommandFreeBoardSearch();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/freeboard/freeboardSearch.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;

		case ("/freeboard/freeboardContent.do"):
			command = new KCommandFreeBoardContent();
			command.execute(request, response); // 데이터 다불러왔고,
			command = new KCommandFreeBoardCommentContent();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/freeboard/freeboardContent.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;

		case ("/freeboard/freeboardDelete.do"):
			command = new KCommandFreeBoardDelete();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/freeboard/freeboardSearch.do"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;

		case ("/freeboard/freeboardUpdate.do"):
			command = new KCommandFreeBoardUpdate();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/freeboard/freeboardSearch.do"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;

		case ("/freeboard/freeboardWrite.do"):
			KCommandFreeBoardWrite command_freeBoardWrite = new KCommandFreeBoardWrite();
			command_freeBoardWrite.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/freeboard/freeboardSearch.do"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;
			
		case ("/freeboard/freeboardcommentWrite.do"):
			command = new KCommandFreeBoardCommentWrite();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "freeboardContent.do"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
		break;
		
		case ("/freeboard/freeboardcommentDelete.do"):
			command = new KCommandFreeBoardCommentDelete();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "freeboardContent.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
		break;
		
		case ("/freeboard/freeboardcommentUpdate.do"):
			command = new KCommandFreeBoardCommentUpdate();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "freeboardContent.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
		break;
		
		case ("/freeboard/freeboardLike.do"):
			command = new KCommandFreeBoardLike();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "freeboardContent.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
		break;
		
		case ("/board/boardSearch.do"):
			command = new KCommandBoardSearch();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/board/boardSearch.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;

		case ("/board/boardWrite.do"):
			KCommandBoardWrite command_boardWrite = new KCommandBoardWrite();
		command_boardWrite.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/board/boardSearch.do"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;

		case ("/board/boardContent.do"):
			command = new KCommandBoardContent();
			command.execute(request, response); // 데이터 다불러왔고,
			command = new KCommandBoardCommentContent();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/board/boardContent.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;			
		case ("/board/boardLike.do"):
			command = new KCommandBoardLike();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "boardContent.jsp"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;

		case ("/board/boardDelete.do"):
			command = new KCommandBoardDelete();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/board/boardSearch.do"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;

		case ("/board/boardUpdate.do"):
			command = new KCommandBoardUpdate();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/board/boardSearch.do"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;
			
			case ("/board/boardcommentUpdate.do"):
				command = new KCommandBoardCommentUpdate();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/board/boardSearch.do"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;
			
			case ("/board/boardcommentDelete.do"):
				command = new KCommandBoardCommentDelete();
			command.execute(request, response); // 데이터 다불러왔고,
			viewPage = "/board/boardSearch.do"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;
			
			case ("/board/boardcommentWrite.do"):
				command = new KCommandBoardCommentWrite();
				command.execute(request, response); // 데이터 다불러왔고,
				viewPage = "/board/boardContent.do"; // 어디로 보낼지 정해주고 ,, setAtrribute니깐 나중에 $로 받아쓰면 됨.
			break;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
	}

}//----
