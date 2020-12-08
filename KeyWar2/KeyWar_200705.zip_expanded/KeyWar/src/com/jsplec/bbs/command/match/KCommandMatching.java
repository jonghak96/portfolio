package com.jsplec.bbs.command.match;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_match;

public class KCommandMatching implements KCommand {

	public KCommandMatching() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String myId = request.getParameter("myId");
		String gymSeqno = request.getParameter("gymSeqno");
		String rival = request.getParameter("rivalId");
		// ------- 날짜 ------
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String hour = request.getParameter("hour");
		String minute = request.getParameter("minute");
		
		Calendar calendar = Calendar.getInstance();
		String dates = Integer.toString(calendar.get(Calendar.YEAR)) + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
		// -----------------		
		
		KDao_match kaDao_match = new KDao_match();
		kaDao_match.matchInsert(myId, rival, gymSeqno, dates);
		
		
	}
	
}
