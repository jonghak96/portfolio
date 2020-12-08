package com.jsplec.bbs.homecontroller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.command.Member.KCommandIdCheck;

/**
 * Servlet implementation class KControllerAjaxId
 */
@WebServlet("/member/KControllerAjax")
public class KControllerAjaxId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KControllerAjaxId() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		KCommand command = null;
		
		command = new KCommandIdCheck();
		command.execute(request, response);
		
		String idCheck = request.getAttribute("idCheck").toString();
		
		PrintWriter out = response.getWriter();
		
		String str = "";
		if (idCheck.equals(request.getParameter("id")))	{
			str = "true";
		} else {
			str = "false";
		}
			
		out.print(str);
		
	}
}
