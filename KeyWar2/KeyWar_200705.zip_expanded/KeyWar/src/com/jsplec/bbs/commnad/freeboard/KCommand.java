package com.jsplec.bbs.commnad.freeboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface KCommand {
	
	public void execute(HttpServletRequest request, HttpServletResponse response);

}//----
