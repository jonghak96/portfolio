package com.jsplec.bbs.command.match;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.lec.bbs.dto.KDtoElectroinicDisplay;
import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_match;

public class KCommandElectronicDisplay implements KCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		KDao_match kDao_match = new KDao_match();
		ArrayList<KDtoElectroinicDisplay> dtoElectroinicDisplays = kDao_match.electroinicDisplay();
		request.setAttribute("ELECTROINIC", dtoElectroinicDisplays);
	}	
}
