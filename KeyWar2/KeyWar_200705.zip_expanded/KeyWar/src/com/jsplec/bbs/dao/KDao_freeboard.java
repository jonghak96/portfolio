package com.jsplec.bbs.dao;

import java.io.FileInputStream;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.jsp.lec.bbs.dto.KDtoFreeBoard;
import com.jsp.lec.bbs.dto.KDtoFreeBoardComment;


public class KDao_freeboard {

	// Field
	DataSource dataSource;

	
	// Constructor
	public KDao_freeboard() {
		// try 나오면 자바가 아니고 다른애 쓰는구나. (톰캣서버꺼 씀)
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/keyWar");	// 타입 바꿔줘. 생성자 부르면 context에서 연결해서 씀.
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Method
	
	public int Count_list(){
		int count = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "Select count(fbSeqno) from freeBoard ";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			if(resultSet.next()) {
				count = resultSet.getInt(1);	// 레이블 가져옴.							
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {	// 잘되든 안되든 여기로 온다.
			try {
				// 순서 거꾸로 지움.
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public ArrayList<KDtoFreeBoard> list(int page){
		ArrayList<KDtoFreeBoard> dtos = new ArrayList<KDtoFreeBoard>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT fbSeqno, mId, fbTitle, fbDate, fbLike, fbView"
					+ " FROM freeBoard AS fb, keyWar.member AS m WHERE fb.member_mSeqno = m.mSeqno ORDER BY fbSeqno DESC LIMIT " + (page-1)*5 + ", 5";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				int fbSeqno = resultSet.getInt("fbSeqno");	// 레이블 가져옴.
				String mId = resultSet.getString("mId");	// 레이블 가져옴.
				String fbTitle = resultSet.getString("fbTitle");
				Timestamp fbDate = resultSet.getTimestamp("fbDate");
				int fbLike = resultSet.getInt("fbLike");	// 레이블 가져옴.
				int fbView = resultSet.getInt("fbView");	// 레이블 가져옴.
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoFreeBoard dto = new KDtoFreeBoard(fbSeqno, mId, fbTitle, fbDate, fbLike, fbView);	// 뉴.
				dtos.add(dto);	// 어레이리스트에 들어감. 한줄,한줄 들어감.				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {	// 잘되든 안되든 여기로 온다.
			try {
				// 순서 거꾸로 지움.
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}	
	
	public int CountSearch(String searchCategory, String searchWord){
		int count = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			
			if(searchCategory.equals("fbTitle")) {
				String query = "select count(fbSeqno) from freeBoard where fbTitle Like '%" + searchWord + "%'";
				preparedStatement = connection.prepareStatement(query);
			}
			
			if(searchCategory.equals("mId")) {
				String query = "SELECT count(fbSeqno) FROM freeBoard AS fb, keyWar.member AS m WHERE fb.member_mSeqno = m.mSeqno AND m.mId = " + searchWord;
				preparedStatement = connection.prepareStatement(query);
			}
			
		
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			if(resultSet.next()) {
				count = resultSet.getInt(1);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {	// 잘되든 안되든 여기로 온다.
			try {
				// 순서 거꾸로 지움.
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	
	public ArrayList<KDtoFreeBoard> search(String searchCategory, String searchWord, int page){
		
		ArrayList<KDtoFreeBoard> dtos = new ArrayList<KDtoFreeBoard>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			
			if(searchCategory.equals("fbTitle")) {
				String query = "SELECT fbSeqno, mId, fbTitle, fbDate, fbLike, fbView"
						+ " FROM freeBoard, keyWar.member WHERE fbTitle LIKE '%" + searchWord + "%' ORDER BY fbSeqno DESC LIMIT " + (page-1)*5 + ", 5";
				
				preparedStatement = connection.prepareStatement(query);
			}
			
			if(searchCategory.equals("mId")) {
				String query = "SELECT fbSeqno, mId, fbTitle, fbDate, fbLike, fbView"
						+ " FROM freeBoard AS fb, keyWar.member AS m WHERE fb.member_mSeqno = m.mSeqno AND mId = '" + searchWord + "' ORDER BY fbSeqno DESC LIMIT " + (page-1)*5 + ", 5";

				preparedStatement = connection.prepareStatement(query);
			}
			
		
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				int fbSeqno = resultSet.getInt("fbSeqno");	// 레이블 가져옴.
				String mId = resultSet.getString("mId");	// 레이블 가져옴.
				String fbTitle = resultSet.getString("fbTitle");
				Timestamp fbDate = resultSet.getTimestamp("fbDate");
				int fbLike = resultSet.getInt("fbLike");	// 레이블 가져옴.
				int fbView = resultSet.getInt("fbView");	// 레이블 가져옴.
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoFreeBoard dto = new KDtoFreeBoard(fbSeqno, mId, fbTitle, fbDate, fbLike, fbView);	// 뉴.
				dtos.add(dto);	// 어레이리스트에 들어감. 한줄,한줄 들어감.				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {	// 잘되든 안되든 여기로 온다.
			try {
				// 순서 거꾸로 지움.
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}	
	
	
	public KDtoFreeBoard content(String fbSeqnoStr){
		KDtoFreeBoard dto = new KDtoFreeBoard();
		int fbSeqno = Integer.parseInt(fbSeqnoStr);
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT mId, fbDate, fbLike, fbView, fbTitle, fbContent FROM freeBoard AS fb, keyWar.member AS m, freeBoardFile AS ff"
					+ " WHERE fb.member_mSeqno = m.mSeqno AND fb.fbSeqno = ff.freeBoard_fbSeqno AND fbSeqno = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, fbSeqno);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			if(resultSet.next()) {
				
				String mId = resultSet.getString("mId");
				Timestamp fbDate = resultSet.getTimestamp("fbDate");
				int fbLike = resultSet.getInt("fbLike");
				int fbView = resultSet.getInt("fbView");
				String fbTitle = resultSet.getString("fbTitle");
				String fbContent = resultSet.getString("fbContent");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				dto = new KDtoFreeBoard(fbSeqno, mId, fbDate, fbLike, fbView, fbTitle, fbContent);	// 뉴.
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {	// 잘되든 안되든 여기로 온다.
			try {
				// 순서 거꾸로 지움.
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}	
	

	public ArrayList<KDtoFreeBoard> contentFile(String fbSeqno){
		
		ArrayList<KDtoFreeBoard> dtos = new ArrayList<KDtoFreeBoard>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();

			String query = "SELECT ffPhotoPath FROM freeBoard AS fb, freeBoardFile AS ff WHERE fb.fbSeqno = ff.freeBoard_fbSeqno AND fbSeqno = " + fbSeqno;

			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				String ffPhotoPath = resultSet.getString("ffPhotoPath");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoFreeBoard dto = new KDtoFreeBoard(ffPhotoPath);	// 뉴.
				dtos.add(dto);	// 어레이리스트에 들어감. 한줄,한줄 들어감.				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {	// 잘되든 안되든 여기로 온다.
			try {
				// 순서 거꾸로 지움.
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}	
	
	
	
	
	public void delete(String fbSeqno) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection(); // 연결 끝.
			String query = "DELETE FROM freeBoard WHERE fbSeqno = ?";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, Integer.parseInt(fbSeqno));

			preparedStatement.executeUpdate(); // 실행.

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	public void update(String fbSeqno, String fbTitle, String fbContent) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();	// 연결 끝.
			String query = "UPDATE freeBoard SET fbTitle = ?, fbContent = ? WHERE fbSeqno= ?";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, fbTitle);
			preparedStatement.setString(2, fbContent);
			preparedStatement.setInt(3, Integer.parseInt(fbSeqno));
			
			preparedStatement.executeUpdate();	// 실행.
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	// 체육관 회원가입.
	public void write(String[] input) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = dataSource.getConnection();

			String query = "INSERT INTO freeBoard (fbTitle, fbContent, fbDate, member_mSeqno)"
					+ " VALUES (?, ?, now(), (SELECT mSeqno FROM keyWar.member WHERE mId = ?))";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, input[1]);	// fbTitle
			preparedStatement.setString(2, input[2]);	// fbContent
			preparedStatement.setString(3, input[0]);	// mId
			
			preparedStatement.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int fbSeqnoChk(String[] input){
		
		int fbSeqnoMax = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT max(fbSeqno) FROM freeBoard AS fb, keyWar.member AS m WHERE mId = ? ";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, input[0]);
			
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			if(resultSet.next()) {
				fbSeqnoMax = resultSet.getInt(1);	// 레이블 가져옴.							
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {	// 잘되든 안되든 여기로 온다.
			try {
				// 순서 거꾸로 지움.
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return fbSeqnoMax;
	}
	
	
	
	
	
	public void writeFile(String[] input, ArrayList<String> fileName, ArrayList<FileInputStream> file, int fbSeqnoMax) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = dataSource.getConnection();
			
			for (int i = 0 ; i < file.size() ; i++) {
				String query = "INSERT INTO freeBoardFile (ffPhoto, ffPhotoPath, freeBoard_fbSeqno) VALUES (?, ?, " + fbSeqnoMax + ")";
				
				preparedStatement = connection.prepareStatement(query);
							
				preparedStatement.setBinaryStream(1, file.get(i));
				preparedStatement.setString(2, fileName.get(i));
	
				preparedStatement.executeUpdate();
				
				file.get(i).close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	

	
	
	public ArrayList<KDtoFreeBoardComment> commentContent(String fbSeqno){
		ArrayList<KDtoFreeBoardComment> dtos = new ArrayList<KDtoFreeBoardComment>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT fcSeqno, mId AS fcWriter, fcContent, fcDate FROM freeBoardComment AS fc, keyWar.member AS m, freeBoard AS fb"
					+ " WHERE m.mSeqno = fc.member_mSeqno AND fb.fbSeqno = fc.freeBoard_fbSeqno AND freeBoard_fbSeqno = " + fbSeqno;
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				int fcSeqno = resultSet.getInt("fcSeqno");
				String fcWriter = resultSet.getString("fcWriter");
				String fcContent = resultSet.getString("fcContent");
				Timestamp fcDate = resultSet.getTimestamp("fcDate");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoFreeBoardComment dto = new KDtoFreeBoardComment(fcSeqno, fcWriter, fcContent, fcDate);	// 뉴.
				dtos.add(dto);	// 어레이리스트에 들어감. 한줄,한줄 들어감.				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {	// 잘되든 안되든 여기로 온다.
			try {
				// 순서 거꾸로 지움.
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dtos;
	}	
	
	
	
	
	
	public void commentWrite(String fcContent, String fbSeqno, String mId) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection(); // 연결 끝.

			String query = "INSERT INTO freeBoardComment (fcContent, fcDate, freeBoard_fbSeqno, member_mSeqno)"
					+ " VALUES (?, now(), ?, (SELECT mSeqno FROM keyWar.member WHERE mId = ?))";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, fcContent);
			preparedStatement.setInt(2, Integer.parseInt(fbSeqno));
			preparedStatement.setString(3, mId);

			preparedStatement.executeUpdate(); // 실행.
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void commentDelete(String fcSeqno) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection(); // 연결 끝.
			String query = "DELETE FROM freeBoardComment WHERE fcSeqno = ?";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, Integer.parseInt(fcSeqno));

			preparedStatement.executeUpdate(); // 실행.

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void commentUpdate(String fcSeqno, String fcContent) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		System.out.println(fcSeqno + fcContent);
		
		try {
			connection = dataSource.getConnection();	// 연결 끝.
			String query = "UPDATE freeBoardComment SET fcContent = ? WHERE fcSeqno= ?";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, fcContent);
			preparedStatement.setInt(2, Integer.parseInt(fcSeqno));
			
			preparedStatement.executeUpdate();	// 실행.
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void viewCount(String fbSeqno) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection(); // 연결 끝.
			String query = "UPDATE freeBoard SET fbView=fbView+1 WHERE fbSeqno= ?";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, Integer.parseInt(fbSeqno));

			preparedStatement.executeUpdate(); // 실행.

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void likeCount(String fbSeqno) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection(); // 연결 끝.
			String query = "UPDATE freeBoard SET fbLike=fbLike+1 WHERE fbSeqno= ?";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, Integer.parseInt(fbSeqno));

			preparedStatement.executeUpdate(); // 실행.

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	
	
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	


}//----
