package com.jsplec.bbs.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.jsp.lec.bbs.dto.KDtoGym;
import com.jsp.lec.bbs.dto.KDtoGymFile;
import com.jsp.lec.bbs.dto.KDtoMember;
import com.jsp.lec.bbs.dto.KDtoUpdateListGym;

public class KDao_gym {


	// Field
	DataSource dataSource;

	// Constructor
	public KDao_gym() {
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
			String query = "SELECT count(mSeqno) FROM keyWar.member WHERE mType='g'";
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
	
	
	
	public ArrayList<KDtoGym> list(int page){
		ArrayList<KDtoGym> dtos = new ArrayList<KDtoGym>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT mId, mName, gAddress, mSports, gRentalPrice, gNumber FROM keyWar.member AS m, gym AS g"
					+ " WHERE m.mSeqno = g.member_mSeqno ORDER BY gRentalNum DESC LIMIT " + (page-1)*10 + ", 10";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				String mId = resultSet.getString("mId");	// 레이블 가져옴.
				String mName = resultSet.getString("mName");	// 레이블 가져옴.
				String gAddress = resultSet.getString("gAddress");
				String mSports = resultSet.getString("mSports");
				int gRentalPrice = resultSet.getInt("gRentalPrice");
				String gNumber = resultSet.getString("gNumber");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoGym dto = new KDtoGym(mId, mName, gAddress, mSports, gRentalPrice, gNumber);	// 뉴.
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
			
			if(searchCategory.equals("mName")) {
				String query = "SELECT count(mSeqno) FROM keyWar.member WHERE mType='g' AND mName LIKE '%" + searchWord + "%'";
				preparedStatement = connection.prepareStatement(query);
			}
			
			if(searchCategory.equals("gAddress")) {
				String query = "SELECT count(mSeqno) FROM keyWar.member AS m, gym AS g"
						+ " WHERE m.mSeqno = g.member_mSeqno AND mType='g' AND gAddress LIKE '%" + searchWord + "%'";
				preparedStatement = connection.prepareStatement(query);
			}
			
			if(searchCategory.equals("mSports")) {
				String query = "SELECT count(mSeqno) FROM keyWar.member WHERE mType='g' AND mSports = '" + searchWord + "'";
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
	
	
	
	
	public ArrayList<KDtoGym> search(String searchCategory, String searchWord, int page){
		
		ArrayList<KDtoGym> dtos = new ArrayList<KDtoGym>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			
			
			if(searchCategory.equals("mName")) {
				
			String query = "SELECT mId, mName, gAddress, mSports, gRentalPrice, gNumber FROM keyWar.member AS m, gym AS g"
					+ " WHERE m.mSeqno = g.member_mSeqno AND mName LIKE '%" + searchWord + "%' ORDER BY gRentalNum DESC LIMIT " + (page-1)*10 + ", 10";
			preparedStatement = connection.prepareStatement(query);
			}
			if(searchCategory.equals("gAddress")) {
				
				String query = "SELECT mId, mName, gAddress, mSports, gRentalPrice, gNumber FROM keyWar.member AS m, gym AS g"
						+ " WHERE m.mSeqno = g.member_mSeqno AND gAddress LIKE '%" + searchWord + "%' ORDER BY gRentalNum DESC LIMIT " + (page-1)*10 + ", 10";
				preparedStatement = connection.prepareStatement(query);
			}
			
			if(searchCategory.equals("mSports")) {
				String query = "SELECT mId, mName, gAddress, mSports, gRentalPrice, gNumber FROM keyWar.member AS m, gym AS g"
						+ " WHERE m.mSeqno = g.member_mSeqno AND mSports = '" + searchWord + "' ORDER BY gRentalNum DESC LIMIT " + (page-1)*10 + ", 10";
				preparedStatement = connection.prepareStatement(query);
			}
			
		
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				String mId = resultSet.getString("mId");	// 레이블 가져옴.
				String mName = resultSet.getString("mName");	// 레이블 가져옴.
				String gAddress = resultSet.getString("gAddress");
				String mSports = resultSet.getString("mSports");
				int gRentalPrice = resultSet.getInt("gRentalPrice");
				String gNumber = resultSet.getString("gNumber");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoGym dto = new KDtoGym(mId, mName, gAddress, mSports, gRentalPrice, gNumber);	// 뉴.
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
	
	
	
	
	public KDtoGym content(String mIdStr){
		KDtoGym dto = new KDtoGym();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT gPhotoPath, mName, mId, gAddress, gNumber, mSports, gRentalPrice, gRentalNum, mIntro"
					+ " FROM keyWar.member AS m, gym AS g WHERE m.mSeqno = g.member_mSeqno AND mId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, mIdStr);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			if(resultSet.next()) {
				
				String gPhotoPath = resultSet.getString("gPhotoPath");
				String mName = resultSet.getString("mName");
				String mId = resultSet.getString("mId");
				String gAddress = resultSet.getString("gAddress");
				String gNumber = resultSet.getString("gNumber");
				String mSports = resultSet.getString("mSports");
				int gRentalPrice = resultSet.getInt("gRentalPrice");
				int gRentalNum = resultSet.getInt("gRentalNum");
				String mIntro = resultSet.getString("mIntro");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				dto = new KDtoGym(gPhotoPath, mName, mId, gAddress, gNumber, mSports, gRentalPrice, gRentalNum, mIntro);	// 뉴.
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
	
	
	
	public ArrayList<KDtoGymFile> gymFile(String mId){
		ArrayList<KDtoGymFile> dtos = new ArrayList<KDtoGymFile>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT gfPhotoPath FROM gymFile AS gf, keyWar.member AS m WHERE m.mSeqno = gf.member_mSeqno AND m.mId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, mId);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				
				String gfPhotoPath = resultSet.getString("gfPhotoPath");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoGymFile dto = new KDtoGymFile(gfPhotoPath);
				dtos.add(dto);
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
	
//	public ArrayList<KDtoGymFile> gymFile(String mId) {
//
//		ArrayList<KDtoGymFile> dtos = new ArrayList<KDtoGymFile>();
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//
//		try {
//			// 이미 위에서 다 연결해줌.
//			connection = dataSource.getConnection();
//
//			String query = "SELECT gfPhotoPath FROM gymFile AS gf, keyWar.member AS m WHERE m.mSeqno = gf.member_mSeqno AND m.mId = '" + mId + "'";
//			preparedStatement = connection.prepareStatement(query);
//
//			resultSet = preparedStatement.executeQuery();
//
//			// 데이터 베이스 이제 가져와야지
//			while (resultSet.next()) {
//				String gfPhotoPath = resultSet.getString("gfPhotoPath"); // 레이블 가져옴.
//
//				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
//				KDtoGymFile dto = new KDtoGymFile(gfPhotoPath); // 뉴.
//				dtos.add(dto); // 어레이리스트에 들어감. 한줄,한줄 들어감.
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally { // 잘되든 안되든 여기로 온다.
//			try {
//				// 순서 거꾸로 지움.
//				if (resultSet != null)
//					resultSet.close();
//				if (preparedStatement != null)
//					preparedStatement.close();
//				if (connection != null)
//					connection.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return dtos;
//	}
	
	
	
//------------------------------------------------------------------------------------------------------------
	
	
	
	// 체육관 회원가입.
	public void gymInsert(String[] input, ArrayList<String> fileName, ArrayList<FileInputStream> file) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;// java.sql
				
		try {
			connection = dataSource.getConnection();

			String query = " insert into keyWar.member (mId, mPw, mName, mTelno, mEmail, mArea, mIntro, mDate, mSports, mType) " +
					" values(?, ?, ?, ?, ?, ?, ?, now(), ?, ?) ";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, input[0]);	// id
			preparedStatement.setString(2, input[1]);	// pw
			preparedStatement.setString(3, input[2]);	// name				
			String telno = input[11] + "-" + input[12] + "-" + input[13];
			preparedStatement.setString(4, telno);
			String email = input[5] + "@" + input[6];
			preparedStatement.setString(5, email);
			preparedStatement.setString(6, input[16]);	// gArea
			preparedStatement.setString(7, input[18]);	// intro
			preparedStatement.setString(8, input[3]);	// sports	
			preparedStatement.setString(9, input[19]);	// type		

			preparedStatement.executeUpdate();
			
			query = "insert into timeTable (member_mSeqno, tTimeTable1, tTimeTable2) " +
					"values ((select mSeqno from keyWar.member where mId = ?), ?, ?)";

			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, input[0]);	// id
			preparedStatement.setString(2, input[14]);	// timeTable1
			preparedStatement.setString(3, input[15]);	// timeTable2
			
			preparedStatement.executeUpdate();
			
			query = "insert into gym (gNumber, gAddress, gRentalPrice, gPhoto, gPhotoPath, member_mSeqno) " +
					"values (?, ?, ?, ?, ?, (select mSeqno from keyWar.member where mId = ?))";
			preparedStatement = connection.prepareStatement(query);
			
			String number = input[8] + "-" + input[9] + "-" + input[10];
			preparedStatement.setString(1, number);
							// gArea  			// address
			String address = input[16] + " " + input[17];
			preparedStatement.setString(2, address);	// addrsss
			preparedStatement.setInt(3, Integer.parseInt(input[4]));	// price
			preparedStatement.setBinaryStream(4, file.get(0));
			preparedStatement.setString(5, fileName.get(0));
			preparedStatement.setString(6, input[0]);
			
			preparedStatement.executeUpdate();
			
			for (int i = 1 ; i < file.size() ; i++) {
				query = "insert into gymFile (gfPhoto, gfPhotoPath, member_mSeqno) " +
						"values(?, ?, (select mSeqno from keyWar.member where mId = ?))";
				preparedStatement = connection.prepareStatement(query);
							
				preparedStatement.setBinaryStream(1, file.get(i));
				preparedStatement.setString(2, fileName.get(i));
				preparedStatement.setString(3, input[0]);
	
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
	
	
	public ArrayList<KDtoUpdateListGym> listSignUpUpdate(String sessionId) { //컨+스 java.util
		
		
		ArrayList<KDtoUpdateListGym> dtos = new ArrayList<KDtoUpdateListGym>();//dto가 여러개니까 dtos라고 함
		Connection connection = null; //컨+스 java.sql
		PreparedStatement preparedStatement = null;//컨+스 java.sql
		ResultSet resultSet = null;// java.sql
		
		try {
			connection = dataSource.getConnection();
			String query = "SELECT mId, mPw, mName, mSports, gRentalPrice, mEmail, gNumber, mTelno, mArea, gAddress, mIntro FROM keyWar.member AS m, keyWar.gym AS g WHERE m.mSeqno = g.member_mSeqno AND mId = '" + sessionId + "'";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery(); // 검색해서 결과 값 나온 상태
			
			if(resultSet.next()) { 
				String mId = resultSet.getString("mId");
				String mPw = resultSet.getString("mPw");
				String mName = resultSet.getString("mName");
				String mSports = resultSet.getString("mSports");
				int gRentalPrice = resultSet.getInt("gRentalPrice");
				String mEmail = resultSet.getString("mEmail");
				String gNumber = resultSet.getString("gNumber");
				String mTelno = resultSet.getString("mTelno");
				String mArea = resultSet.getString("mArea");
				String gAddress = resultSet.getString("gAddress");
				String mIntro = resultSet.getString("mIntro");
				
				KDtoUpdateListGym dto = new KDtoUpdateListGym(mId, mPw, mName, mSports, gRentalPrice, mEmail, gNumber, mTelno, mArea, gAddress, mIntro);
				dtos.add(dto);
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close(); // 만든 거 순서 거꾸로 지워줘야함
			}catch(Exception e) {
				e.printStackTrace();
			}
		
		}
		return dtos;		
	}
	

	public void gymUpdate(String[] input, ArrayList<String> fileName, ArrayList<FileInputStream> file) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;// java.sql
				
		try {
			connection = dataSource.getConnection();

			String query = "update keyWar.member as m, keyWar.gym as g, keyWar.timeTable as t set m.mPw = ?, m.mName = ?, m.mSports = ?, g.gRentalPrice = ?, m.mEmail = ?, g.gNumber = ?, m.mTelno = ?, t.tTimeTable1 = ?, t.tTimeTable2 = ?, m.mArea = ?, g.gAddress = ?, m.mIntro = ?, g.gPhoto = ?, g.gPhotoPath = ? WHERE m.mSeqno = g.member_mSeqno AND m.mSeqno = t.member_mSeqno AND m.mSeqno = ifnull(mId,0) = ?";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, input[1]);	// pw
			preparedStatement.setString(2, input[2]);	// name
			preparedStatement.setString(3, input[3]);	// sports
			preparedStatement.setInt(4, Integer.parseInt(input[4]));	// price
			preparedStatement.setString(5, input[5]);	// email
			preparedStatement.setString(6, input[6]);	// number
			preparedStatement.setString(7, input[7]);	// telno
			preparedStatement.setString(8, input[8]);	// timetable1
			preparedStatement.setString(9, input[9]);	// timetable2
			preparedStatement.setString(10, input[10]);	// area
			preparedStatement.setString(11, input[11]);	// address
			preparedStatement.setString(12, input[12]); // intro
			preparedStatement.setBinaryStream(13, file.get(0));
			preparedStatement.setString(14, fileName.get(0));
			preparedStatement.setString(15, input[0]); // mId
			
			preparedStatement.executeUpdate();
			
			for (int i = 1 ; i < file.size() ; i++) {
				query = "UPDATE gymFile AS gf, keyWar.member AS m SET gfPhoto = ?, gfPhotoPath = ? WHERE gf.member_mSeqno = m.mSeqno AND m.mId = ?";
				preparedStatement = connection.prepareStatement(query);
							
				preparedStatement.setBinaryStream(1, file.get(i));
				preparedStatement.setString(2, fileName.get(i));
				preparedStatement.setString(3, input[0]);
	
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
	
	
	// 메소드에서 return를 사용하려면 void를 쓰면 안된다.
//	public String findId(String gName, String gTelno, String gEmail) {			
//		String gId = "";
//		
//		Connection connection = null; //컨+스 java.sql
//		PreparedStatement preparedStatement = null;//컨+스 java.sql
//		ResultSet resultSet = null;// java.sql
//			
//		try {
//			connection = dataSource.getConnection();
//			String query = "select gId from KeyWar.gym where gName = '" + gName + "' and gTelno = '" + gTelno + "' and gEmail = '" + gEmail + "'";
//			preparedStatement = connection.prepareStatement(query);
//			resultSet = preparedStatement.executeQuery(); // 검색해서 결과 값 나온 상태
//			
//			if(resultSet.next() ) { 
//				gId = resultSet.getString("gId");
//			}
//		
//		}catch(Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close(); // 만든 거 순서 거꾸로 지워줘야함
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//		
//		}
//		return gId;		
//	}
		
		
//	public String findPw(String gId, String gName, String gTelno, String gEmail) {			
//		String gPw = "";
//		
//		Connection connection = null; //컨+스 java.sql
//		PreparedStatement preparedStatement = null;//컨+스 java.sql
//		ResultSet resultSet = null;// java.sql
//			
//		try {
//			connection = dataSource.getConnection();
//			String query = "select gPw from KeyWar.gym where gId = '" + gId + "' and gName = '" + gName + "' and gTelno = '" + gTelno + "' and gEmail = '" + gEmail + "'";
//			preparedStatement = connection.prepareStatement(query);
//			resultSet = preparedStatement.executeQuery(); // 검색해서 결과 값 나온 상태
//			
//			if(resultSet.next())  { 
//				gPw = resultSet.getString("gPw");
//			}
//		
//		}catch(Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(preparedStatement != null) preparedStatement.close();
//				if(connection != null) connection.close(); // 만든 거 순서 거꾸로 지워줘야함
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//		
//		}
//		return gPw;		
//	}
	
	
}//----
