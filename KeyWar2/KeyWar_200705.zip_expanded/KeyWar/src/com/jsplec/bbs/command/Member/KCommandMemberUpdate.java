package com.jsplec.bbs.command.Member;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_member;


public class KCommandMemberUpdate implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		// ---- 이미지 -----------------------------------------------
		String uploadPath = "/Users/tj/Documents/member";
		int maxSize =1024 *1024 *10;// 한번에 올릴 수 있는 파일 용량 : 10M로 제한
		
		com.oreilly.servlet.MultipartRequest multi =null;
		
		try{
			// request,파일저장경로,용량,인코딩타입,중복파일명에 대한 기본 정책
			multi = new com.oreilly.servlet.MultipartRequest(request,uploadPath,maxSize,"utf-8",new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());		

		String mId = multi.getParameter("id");
		String mPw = request.getParameter("pw");
		String mName = request.getParameter("name");
		String mSex = request.getParameter("sex");
		String mAge = request.getParameter("age");
		String mTelno1 = request.getParameter("telno");
		String mTelno2 = request.getParameter("telno2");
		String mTelno3 = request.getParameter("telno3");
		String mEmail1 = request.getParameter("email1");
		String mEmail2 = request.getParameter("email2");
		String mAddress = request.getParameter("area");
		String mIntro = request.getParameter("intro");
		String mHeight = request.getParameter("height");
		String mWeight = request.getParameter("weight");
		double dubMHeight = Double.parseDouble(mHeight);
		double dubMWeight = Double.parseDouble(mWeight);
		String mwClass = request.getParameter("wClass");
		String mSports = request.getParameter("sports");
		String rSex = request.getParameter("rSex");
		String rAge1 = request.getParameter("rAge1");
		String rAge2 = request.getParameter("rAge2");
		String rArea = request.getParameter("rArea");
		String rwClass1 = request.getParameter("rwClass1");
		String rwClass2 = request.getParameter("rwClass2");
		String mTelno = mTelno1 + "-" + mTelno2 + "-" + mTelno3;
		String mEmail = mEmail1 + "@" + mEmail2;
		System.out.println("?");
		System.out.println(mId);
		System.out.println(mPw);
		System.out.println(mTelno);
        
		KDao_member dao = new KDao_member();
		
		File file = multi.getFile("profil");
		FileInputStream input = new FileInputStream(file);
		
		// 파일이 존재하면 삭제해라
		if (file.exists())
			file.delete();
		
		dao.memberUpdate(mId, mPw, mName, mSex, mAge, mTelno, input, mEmail, mAddress, mIntro, dubMHeight, dubMWeight, mwClass, mSports);
		dao.readyUpdate(rSex, rAge1, rAge2, rArea, rwClass1, rwClass2, mId);
		
		input.close();
	    }catch(Exception e){
	        e.printStackTrace();
	    }	
	
	}

}
