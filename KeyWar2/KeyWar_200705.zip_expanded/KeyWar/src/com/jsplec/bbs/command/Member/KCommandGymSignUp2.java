package com.jsplec.bbs.command.Member;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jsplec.bbs.dao.KDao_gym;

public class KCommandGymSignUp2 {

	public KCommandGymSignUp2() {
		// TODO Auto-generated constructor stub
	}

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 시간 구하기
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		String time = format.format(date);

		// 저장 장소
		String uploadPath = request.getSession().getServletContext().getRealPath("/gym/imgs/" + time);
				
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

		String CHARSET = "utf-8";								// 인코딩
		String ATTACHES_DIR = uploadPath;						// 저장 장소 (하드디스크)
		int LIMIT_SIZE_BYTES = 1024 * 1024 * 100;				// 파일 받는 크기 제한		
		
		// https://dololak.tistory.com/720?category=636501 파일 업로드 긁어온곳
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding(CHARSET);
        PrintWriter out = null;
		out = response.getWriter();
 
        File attachesDir = new File(ATTACHES_DIR);
  
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        fileItemFactory.setRepository(attachesDir);
        fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES);
        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory); 
        
        // 받은 데이터들을 저장한 배열
        String[] input = new String[20];
        // 받은 데이터들 지정 값
        int num = 0;
        // 파일 저장할 리스트
        ArrayList<FileInputStream> fileInputArrayList = new ArrayList<FileInputStream>();
        ArrayList<String> filePath = new ArrayList<String>();
        
        try {
        	// ServletFileUpload 클래스는 HTTP 요청에 대한 HttpServletRequest 객체로부터 
        	// multipart/form-data형식으로 넘어온 HTTP Body 부분을 다루기 쉽게 변환(parse)해주는 역할을 수행합니다.  
        	// parseRequest()메서드를 수행하면 FileItem이라는 형식으로 변환해줍니다.
            List<FileItem> items = fileUpload.parseRequest(request);
            
        	// 사용자가 업로드한 File데이터나 사용자가 input text에 입력한 일반 요청 데이터에 대한 객체입니다. 
        	// FileItem#isFormField() 메서드의 리턴값이 true이면 text같은 일반 입력 데이터이며, 
        	// false이면 파일 데이터입니다. 즉 리턴값이 false인 경우에만 업로드된 파일인것으로 인지하여 처리하면 됩니다.
            for (FileItem item : items) {
                if (item.isFormField()) {
                	// input text 값이 들어오는 곳
                    System.out.printf("파라미터 명 : %s, 파라미터 값 :  %s \n", item.getFieldName(), item.getString(CHARSET));
                    input[num] = item.getString(CHARSET);
                    num = num + 1;
                } else {
                	// input file이 들어오는 곳
                    System.out.printf("파라미터 명 : %s, 파일 명 : %s,  파일 크기 : %s bytes \n", item.getFieldName(), item.getName(), item.getSize());
                    
                    if (item.getSize() > 0) {
                        String separator = File.separator;
                        int index =  item.getName().lastIndexOf(separator);
                        String fileName = item.getName().substring(index  + 1);
                        // 						  프로젝트 경로 	   루트(/)	   파일 이름
                        File uploadFile = new File(ATTACHES_DIR +  separator + fileName);
                        item.write(uploadFile);
                        
                        fileInputArrayList.add(new FileInputStream(uploadFile));
                        filePath.add("imgs/" + time + "/" + uploadFile.getName());
                    }
                }
            } 
 
            out.println("<h1>파일 업로드 완료</h1>");                        
            
            KDao_gym kaDao_gym = new KDao_gym();
            kaDao_gym.gymInsert(input, filePath, fileInputArrayList);            
            
        } catch (Exception e) {
            // 파일 업로드 처리 중 오류가 발생하는 경우
            e.printStackTrace();
            out.println("<h1>파일 업로드 중 오류가  발생하였습니다.</h1>");
        }
	}
}
