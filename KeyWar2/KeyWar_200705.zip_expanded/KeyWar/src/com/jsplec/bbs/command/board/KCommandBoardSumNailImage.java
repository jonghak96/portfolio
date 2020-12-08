package com.jsplec.bbs.command.board;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsplec.bbs.command.KCommand;
import com.jsplec.bbs.thread.VideoThread;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class KCommandBoardSumNailImage implements KCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {	
		
		// ---- 이미지 -----------------------------------------------		
		// 현재 시간 구하기
		System.out.println("memberSignUp");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		String time = format.format(date);
		
		String uploadPath = request.getSession().getServletContext().getRealPath("/board/imgs/" + time);
		System.out.println("uploadPath = " + uploadPath);
		
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
		
		int maxSize =1024 *1024 *100;// 한번에 올릴 수 있는 파일 용량 : 100M로 제한
		
		MultipartRequest multi =null;
		
		try{
			// request,파일저장경로,용량,인코딩타입,중복파일명에 대한 기본 정책
			multi = new MultipartRequest(request,uploadPath,maxSize,"UTF-8",new DefaultFileRenamePolicy());
			
			String title = multi.getParameter("title");
			
			System.out.println("title = " + title);
			
			File file = multi.getFile("videoFile");
			System.out.println(file.getAbsolutePath());
			
			// Sumnail 만드는 스레드 ----------------------------------------------------

			double plusSize = 1;
			int threadSize = 8;
			
			// thread 생성
			VideoThread[] videoThreads = new VideoThread[threadSize];
			
			for (int i = 0 ; i < videoThreads.length ; i++) {
				videoThreads[i] = new VideoThread(file, threadSize, i, plusSize, uploadPath);
				videoThreads[i].start();
			}
			System.out.println("쓰레드 시작");
			
			for (int i = 0 ; i < videoThreads.length; i++) {
				videoThreads[i].join();
			}
			System.out.println("쓰레드 종료");
			System.out.println("videolength = " + videoThreads.length);
			
			String[] uploadFilePath = new String[5];
			for (int i = 0 ; i < 5 ; i++) {
				uploadFilePath[i] = "imgs/" + time + "/" + i + ".png";
				System.out.println("UploadPath" + i + " = " + uploadFilePath[i]);
			}
			
			request.setAttribute("UPLOADFILEPATH", uploadFilePath);
			
			// ------------------------------------------------------------------------

			
	    }catch(Exception e){
	        e.printStackTrace();
	    }	
		// -----------------------------------------------------------------
		
		
	}
	
}
