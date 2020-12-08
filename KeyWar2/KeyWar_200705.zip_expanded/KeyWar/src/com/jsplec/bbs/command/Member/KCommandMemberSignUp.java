package com.jsplec.bbs.command.Member;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.dao.KDao_member;

public class KCommandMemberSignUp implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		// ---- 이미지 -----------------------------------------------		
		// 현재 시간 구하기
		System.out.println("memberSignUp");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		String time = format.format(date);
		
		String uploadPath = request.getSession().getServletContext().getRealPath("/member/imgs/" + time);
		
		// 폴더 만들기
		File imgsDir = new File(uploadPath);
		if(!imgsDir.exists()) {
			try {
				imgsDir.mkdirs();
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("폴더가 이미 생성되어있습니다.");
		}
		
		int maxSize =1024 *1024 *10;// 한번에 올릴 수 있는 파일 용량 : 10M로 제한
		
		com.oreilly.servlet.MultipartRequest multi =null;
		
		try{
			// request,파일저장경로,용량,인코딩타입,중복파일명에 대한 기본 정책
			multi = new com.oreilly.servlet.MultipartRequest(request,uploadPath,maxSize,"UTF-8",new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());
				
			String mId = multi.getParameter("id");
			String mPw = multi.getParameter("pw");
			String mName = multi.getParameter("name");
			String mSex = multi.getParameter("sex");
			String mAge = multi.getParameter("age");
			String mTelno1 = multi.getParameter("telno");
			String mTelno2 = multi.getParameter("telno2");
			String mTelno3 = multi.getParameter("telno3");
			String mEmail1 = multi.getParameter("email1");
			String mEmail2 = multi.getParameter("email2");
			String mArea = multi.getParameter("area");
			String mIntro = multi.getParameter("intro");
			String mHeight = multi.getParameter("height");
			String mWeight = multi.getParameter("weight");
			double dubMHeight = Double.parseDouble(mHeight);
			double dubMWeight = Double.parseDouble(mWeight);
			String mwClass = multi.getParameter("wClass");
			String mSports = multi.getParameter("sports");
			String rSex = multi.getParameter("rSex");
			String rAge1 = multi.getParameter("rAge1");
			String rAge2 = multi.getParameter("rAge2");
			String rArea = multi.getParameter("rArea");
			String rwClass1 = multi.getParameter("rwClass1");
			String rwClass2 = multi.getParameter("rwClass2");
			String type = multi.getParameter("type");
			String mTelno = mTelno1 + "-" + mTelno2 + "-" + mTelno3;
			String mEmail = mEmail1 + "@" + mEmail2;
	        
			KDao_member dao = new KDao_member();
			
			File file = multi.getFile("profil");
			String filePath = "imgs/" + time + "/" + file.getName();
			FileInputStream input = new FileInputStream(file);			
			
			System.out.println("Command mName = " + mName);
			
			dao.memberInsert(mId, mPw, mName, mSex, mAge, mTelno, input, filePath, mEmail, mArea, mIntro, dubMHeight, dubMWeight, mwClass, mSports, type);
			dao.readyInsert(rSex, rAge1, rAge2, rArea, rwClass1, rwClass2, mId);

			input.close();
	    }catch(Exception e){
	        e.printStackTrace();
	    }	
		// -----------------------------------------------------------------
	}
}
