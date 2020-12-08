package com.jsplec.bbs.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.jsp.lec.bbs.dto.KDtoBoard;
import com.jsp.lec.bbs.dto.KDtoBoardComment;
import com.jsp.lec.bbs.dto.KDtoFreeBoard;
import com.jsp.lec.bbs.dto.KDtoFreeBoardComment;

public class KDao_board {
	
	// Field
	DataSource dataSource;

	// Constructor
	public KDao_board() {
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
			String query = "Select count(bSeqno) from board";
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
	
	public ArrayList<KDtoBoard> list(int page){
		ArrayList<KDtoBoard> dtos = new ArrayList<KDtoBoard>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT fPhotoPath, bSeqno, mId, bTitle, bDate, bView FROM board AS b, keyWar.member AS m, boardFile AS f"
					+ " WHERE b.member_mSeqno = m.mSeqno AND b.bSeqno = f.board_bSeqno ORDER BY bView DESC, bDate DESC LIMIT " + (page-1)*5 + ", 5";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				String fPhotoPath = resultSet.getString("fPhotoPath");	// 레이블 가져옴.
				int bSeqno = resultSet.getInt("bSeqno");	// 레이블 가져옴.
				String mId = resultSet.getString("mId");	// 레이블 가져옴.
				String bTitle = resultSet.getString("bTitle");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bView = resultSet.getInt("bView");	// 레이블 가져옴.
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoBoard dto = new KDtoBoard(fPhotoPath, bSeqno, mId, bTitle, bDate, bView);	// 뉴.
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
			
			if(searchCategory.equals("bTitle")) {
				String query = "select count(bSeqno) from Board where bTitle Like '%" + searchWord + "%';";
				preparedStatement = connection.prepareStatement(query);
			}
			
			if(searchCategory.equals("mId")) {
				String query = "SELECT count(bSeqno) FROM Board AS b, keyWar.member AS m WHERE b.member_mSeqno = m.mSeqno AND m.mId = '" + searchWord + "'";
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
	
	
	public ArrayList<KDtoBoard> search(String searchCategory, String searchWord, int page){
		
		ArrayList<KDtoBoard> dtos = new ArrayList<KDtoBoard>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			
			if(searchCategory.equals("bTitle")) {
				String query = "SELECT fPhotoPath, bSeqno, mId, bTitle, bDate, bView FROM board AS b, keyWar.member AS m, boardFile AS f"
						+ " WHERE b.member_mSeqno = m.mSeqno AND b.bSeqno = f.board_bSeqno AND bTitle LIKE '%" + searchWord + "%' ORDER BY bView DESC, bDate DESC LIMIT " + (page-1)*5 + ", 5;";
				
				preparedStatement = connection.prepareStatement(query);
			}
			
			if(searchCategory.equals("mId")) {
				String query = "SELECT fPhotoPath, bSeqno, mId, bTitle, bDate, bView FROM board AS b, keyWar.member AS m, boardFile AS f"
						+ " WHERE b.member_mSeqno = m.mSeqno AND b.bSeqno = f.board_bSeqno AND m.mId = '" + searchWord + "' ORDER BY bView DESC, bDate DESC LIMIT " + (page-1)*5 + ", 5;";

				preparedStatement = connection.prepareStatement(query);
			}
			
		
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				String fPhotoPath = resultSet.getString("fPhotoPath");	// 레이블 가져옴.
				int bSeqno = resultSet.getInt("bSeqno");	// 레이블 가져옴.
				String mId = resultSet.getString("mId");	// 레이블 가져옴.
				String bTitle = resultSet.getString("bTitle");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bView = resultSet.getInt("bView");	// 레이블 가져옴.
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoBoard dto = new KDtoBoard(fPhotoPath, bSeqno, mId, bTitle, bDate, bView);	// 뉴.
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
	
	
	
	
	
	public void write(String[] input, ArrayList<String> fileName, ArrayList<FileInputStream> file) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			if (input[0].equals("win")) {
				connection = dataSource.getConnection(); // 연결 끝.
				String query = "UPDATE customer AS c, keyWar.member AS m SET cWin = cWin + 1 WHERE c.member_mSeqno = m.mSeqno AND mId = ?";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, input[1]);
				preparedStatement.executeUpdate(); // 실행.
			}
			
			if (input[0].equals("lose")) {
				connection = dataSource.getConnection(); // 연결 끝.
				String query = "UPDATE customer AS c, keyWar.member AS m SET cLose = cLose + 1 WHERE c.member_mSeqno = m.mSeqno AND mId = ?";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, input[2]);
				preparedStatement.executeUpdate(); // 실행.
			}
			
			if (input[0].equals("draw")) {
				connection = dataSource.getConnection(); // 연결 끝.
				String query1 = "UPDATE customer AS c, keyWar.member AS m SET cDraw = cDraw + 1 WHERE c.member_mSeqno = m.mSeqno AND mId = ?";
				preparedStatement = connection.prepareStatement(query1);
				preparedStatement.setString(1, input[1]);
				preparedStatement.executeUpdate(); // 실행.
				
				connection = dataSource.getConnection(); // 연결 끝.
				String query2 = "UPDATE customer AS c, keyWar.member AS m SET cDraw = cDraw + 1 WHERE c.member_mSeqno = m.mSeqno AND mId = ?";
				preparedStatement = connection.prepareStatement(query2);
				preparedStatement.setString(1, input[2]);
				preparedStatement.executeUpdate(); // 실행.
			}

			connection = dataSource.getConnection();

			String query = "INSERT INTO board ( bTitle, bContent, bDate, member_mSeqno) VALUES (?, ?, now(), (SELECT mSeqno FROM keyWar.member WHERE mId = ?))";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, input[4]); // bTitle
			preparedStatement.setString(2, input[5]); // bContent
			preparedStatement.setString(3, input[3]);	// mId

			preparedStatement.executeUpdate();

			query = "INSERT INTO boardFile (fPhotoPath, fVideo, fVideoPath, board_bSeqno)"
					+ " VALUES (?, ?, ?, (SELECT max(bSeqno) FROM board AS b, keyWar.member AS m WHERE b.member_mSeqno = m.mSeqno AND mId = ?))";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, input[6]); // fPhotoPath

			preparedStatement.setBinaryStream(2, file.get(0)); // fVideo
			preparedStatement.setString(3, fileName.get(0)); // fVideoPath

			preparedStatement.setString(4, input[3]);	// mId
			preparedStatement.executeUpdate();

			file.get(0).close();

		} catch (Exception e) {
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
	
	
	
	
	public KDtoBoard content(String bSeqnoStr){
		KDtoBoard dto = new KDtoBoard();
		int bSeqno = Integer.parseInt(bSeqnoStr);
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT bSeqno, fVideoPath, mId, bDate, bLike, bView, bTitle, bContent FROM board AS b, keyWar.member AS m, boardFile AS f"
					+ " WHERE b.member_mSeqno = m.mSeqno AND b.bSeqno = f.board_bSeqno AND bSeqno = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, bSeqno);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			if(resultSet.next()) {
				
				String fVideoPath = resultSet.getString("fVideoPath");
				String mId = resultSet.getString("mId");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bLike = resultSet.getInt("bLike");
				int bView = resultSet.getInt("bView");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				dto = new KDtoBoard(bSeqno, fVideoPath, mId, bDate, bLike, bView, bTitle, bContent);	// 뉴.
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
	
	
	
	public void viewCount(String bSeqno) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection(); // 연결 끝.
			String query = "UPDATE board SET bView=bView+1 WHERE bSeqno= ?";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, Integer.parseInt(bSeqno));

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
	
	
	public void likeCount(String bSeqno) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection(); // 연결 끝.
			String query = "UPDATE board SET bLike=bLike+1 WHERE bSeqno= ?";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, Integer.parseInt(bSeqno));

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
	
	
	
	public void delete(String bSeqno) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection(); // 연결 끝.
			String query = "DELETE FROM board WHERE bSeqno = ?";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, Integer.parseInt(bSeqno));

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
	
	
	
	public void update(String bSeqno, String bTitle, String bContent) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();	// 연결 끝.
			String query = "UPDATE board SET bTitle = ?, bContent = ? WHERE bSeqno= ?";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, bTitle);
			preparedStatement.setString(2, bContent);
			preparedStatement.setInt(3, Integer.parseInt(bSeqno));
			
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
	
	
	
	
	
//------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	
	

	
	public ArrayList<KDtoBoardComment> commentContent(String bSeqno){
		ArrayList<KDtoBoardComment> dtos = new ArrayList<KDtoBoardComment>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT cSeqno, mId AS cWriter, cContent, cDate FROM BoardComment AS c, keyWar.member AS m, Board AS b"
					+ " WHERE m.mSeqno = c.member_mSeqno AND b.bSeqno = c.board_bSeqno AND board_bSeqno = " + bSeqno;
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				int cSeqno = resultSet.getInt("cSeqno");
				String cWriter = resultSet.getString("cWriter");
				String cContent = resultSet.getString("cContent");
				Timestamp cDate = resultSet.getTimestamp("cDate");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoBoardComment dto = new KDtoBoardComment(cSeqno, cWriter, cContent, cDate);	// 뉴.
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
	
	public void commentUpdate(String cSeqno, String cContent) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();	// 연결 끝.
			String query = "UPDATE boardComment SET cContent = ? WHERE cSeqno= ?";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, cContent);
			preparedStatement.setInt(2, Integer.parseInt(cSeqno));
			
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
	
	
	
	public void commentDelete(String cSeqno) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSource.getConnection(); // 연결 끝.
			String query = "DELETE FROM boardComment WHERE cSeqno = ?";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, Integer.parseInt(cSeqno));

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
	
	
	
	public void commentWrite(String cContent, String bSeqno, String mId) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection(); // 연결 끝.

			String query = "INSERT INTO boardComment (cContent, cDate, board_bSeqno, member_mSeqno)"
					+ " VALUES (?, now(), ?, (SELECT mSeqno FROM keyWar.member WHERE mId = ?))";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, cContent);
			preparedStatement.setInt(2, Integer.parseInt(bSeqno));
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
	
	// --------------------------------------------------
	// -------------------- ID Check --------------------
	public int gymIdCheck(String seesionID) {
		int idcheck = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			// 데이터 베이스 연결 (userid, password, url 이 들어간다.)
			connection = dataSource.getConnection();			
			
			String query = "select mType from keywar.member where mId = ?";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, seesionID);
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				if (resultSet.getString(1).equals("g")) {
					idcheck = 1;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
		return idcheck;
	}
	// -------------------- ID Check End ----------------
	// --------------------------------------------------
	
	
}//-----
