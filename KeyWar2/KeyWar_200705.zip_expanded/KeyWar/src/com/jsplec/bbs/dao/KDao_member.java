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

import com.jsp.lec.bbs.dto.KDtoFreeBoard;
import com.jsp.lec.bbs.dto.KDtoMember;
import com.jsp.lec.bbs.dto.KDtoMemberGym;
import com.jsp.lec.bbs.dto.KDtoUpdateList;

public class KDao_member {

	// Field
	DataSource dataSource;

	// Constructor
	public KDao_member() {
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
			String query = "SELECT count(mSeqno) FROM keyWar.member WHERE mType='c'";
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
	
	public ArrayList<KDtoMember> list(int page){
		ArrayList<KDtoMember> dtos = new ArrayList<KDtoMember>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT mId, cWin, cDraw, cLose, mArea, cHeight, cWeight, mSports FROM keyWar.member AS m, customer AS c"
					+ " WHERE m.mSeqno = c.member_mSeqno ORDER BY cWin DESC LIMIT " + (page-1)*10 + ", 10";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				String mId = resultSet.getString("mId");	// 레이블 가져옴.
				int cWin = resultSet.getInt("cWin");	// 레이블 가져옴.
				int cDraw = resultSet.getInt("cDraw");	// 레이블 가져옴.
				int cLose = resultSet.getInt("cLose");	// 레이블 가져옴.
				String mArea = resultSet.getString("mArea");
				double cHeight = resultSet.getInt("cHeight");	// 레이블 가져옴.
				double cWeight = resultSet.getInt("cWeight");	// 레이블 가져옴.
				String mSports = resultSet.getString("mSports");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoMember dto = new KDtoMember(mId, cWin, cDraw, cLose, mArea, cHeight, cWeight, mSports);	// 뉴.
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
			
			if(searchCategory.equals("mId")) {
				String query = "SELECT count(mSeqno) FROM keyWar.member WHERE mType='c' AND mId = '" + searchWord + "'";
				preparedStatement = connection.prepareStatement(query);
			}
			
			if(searchCategory.equals("mArea")) {
				String query = "SELECT count(mSeqno) FROM keyWar.member WHERE mType='c' AND mArea LIKE '%" + searchWord + "%'";
				preparedStatement = connection.prepareStatement(query);
			}
			
			if(searchCategory.equals("mSports")) {
				String query = "SELECT count(mSeqno) FROM keyWar.member WHERE mType='c' AND mSports = '" + searchWord + "'";
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
	
	
	public ArrayList<KDtoMember> search(String searchCategory, String searchWord, int page){
		
		ArrayList<KDtoMember> dtos = new ArrayList<KDtoMember>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			
			if(searchCategory.equals("mId") | searchCategory.equals("mArea")) {
				String query = "SELECT mId, cWin, cDraw, cLose, mArea, cHeight, cWeight, mSports FROM keyWar.member AS m, customer AS c"
						+ " WHERE m.mSeqno = c.member_mSeqno AND " + searchCategory + " LIKE '%" + searchWord + "%' ORDER BY cWin DESC LIMIT " + (page-1)*10 + ", 10";
				preparedStatement = connection.prepareStatement(query);
			}
			
			if(searchCategory.equals("mSports")) {
				String query = "SELECT mId, cWin, cDraw, cLose, mArea, cHeight, cWeight, mSports FROM keyWar.member AS m, customer AS c"
						+ " WHERE m.mSeqno = c.member_mSeqno AND mSports = '" + searchWord + "' ORDER BY cWin DESC LIMIT " + (page-1)*10 + ", 10";
				preparedStatement = connection.prepareStatement(query);
			}
			
		
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {
				String mId = resultSet.getString("mId");	// 레이블 가져옴.
				int cWin = resultSet.getInt("cWin");	// 레이블 가져옴.
				int cDraw = resultSet.getInt("cDraw");	// 레이블 가져옴.
				int cLose = resultSet.getInt("cLose");	// 레이블 가져옴.
				String mArea = resultSet.getString("mArea");
				double cHeight = resultSet.getInt("cHeight");	// 레이블 가져옴.
				double cWeight = resultSet.getInt("cWeight");	// 레이블 가져옴.
				String mSports = resultSet.getString("mSports");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				KDtoMember dto = new KDtoMember(mId, cWin, cDraw, cLose, mArea, cHeight, cWeight, mSports);	// 뉴.
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
	
	
	
	
	
	public KDtoMember content(String mIdStr){
		KDtoMember dto = new KDtoMember();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			String query = "SELECT cPhotoPath, mId, mName, cSex, cAge, mArea, cWin, cDraw, cLose, cHeight, cWeight, cwClass, mSports, mIntro"
					+ " FROM keyWar.member AS m, customer AS c WHERE m.mSeqno = c.member_mSeqno AND mId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, mIdStr);
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			if(resultSet.next()) {
				
//				FileInputStream cPhoto = (FileInputStream) resultSet.getBinaryStream("cPhoto");
				String cPhotoPath = resultSet.getString("cPhotoPath");
				String mId = resultSet.getString("mId");
				String mName = resultSet.getString("mName");
				String cSex = resultSet.getString("cSex");
				int cAge = resultSet.getInt("cAge");
				String mArea = resultSet.getString("mArea");
				int cWin = resultSet.getInt("cWin");
				int cDraw = resultSet.getInt("cDraw");
				int cLose = resultSet.getInt("cLose");
				Double cHeight = resultSet.getDouble("cHeight");
				Double cWeight = resultSet.getDouble("cWeight");
				String cwClass = resultSet.getString("cwClass");
				String mSports = resultSet.getString("mSports");
				String mIntro = resultSet.getString("mIntro");
				
				// 저장해야지 : 빈 사용해서 레코드 만듬. 배열로 데이터 들어있음.
				dto = new KDtoMember(cPhotoPath, mId, mName, cSex, cAge, mArea, cWin, cDraw, cLose, cHeight, cWeight, cwClass, mSports, mIntro);	// 뉴.
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
	
//-----------------------------------------------------------------------------------------------------
	
	
	
	
	// 메소드에서 return를 사용하려면 void를 쓰면 안된다.
	public String findId(String mName, String mTelno, String mEmail) {			
		String mId = "";
		
		Connection connection = null; //컨+스 java.sql
		PreparedStatement preparedStatement = null;//컨+스 java.sql
		ResultSet resultSet = null;// java.sql
			
		try {
			connection = dataSource.getConnection();
			String query = "select mId from KeyWar.member where mName = '" + mName + "' and mTelno = '" + mTelno + "' and mEmail = '" + mEmail + "'";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery(); // 검색해서 결과 값 나온 상태
			
			if(resultSet.next() ) { 
				mId = resultSet.getString("mId");
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
		return mId;		
	}
	
	public String findPw(String mId, String mName, String mTelno, String mEmail) {			
		String mPw = "";
		
		Connection connection = null; //컨+스 java.sql
		PreparedStatement preparedStatement = null;//컨+스 java.sql
		ResultSet resultSet = null;// java.sql
			
		try {
			connection = dataSource.getConnection();
			String query = "select mPw from KeyWar.member where mId = '" + mId + "' and mName = '" + mName + "' and mTelno = '" + mTelno + "' and mEmail = '" + mEmail + "'";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery(); // 검색해서 결과 값 나온 상태
			
			if(resultSet.next())  { 
				mPw = resultSet.getString("mPw");
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
		return mPw;		
	}

	public void memberInsert(String mId, String mPw, String mName, String mSex, String mAge, 
			String mTelno, InputStream file, String filePath, String mEmail, String mArea, String mIntro, double dubMHeight, double dubMWeight, 
			String mwClass, String mSports, String type) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		System.out.println("DAO mName = " + mName);
		
		try {
			connection = dataSource.getConnection();
			
			String query = " insert into keyWar.member (mId, mPw, mName, mTelno, mEmail, mArea, mIntro, mDate, mSports, mType) " +
					" values(?, ?, ?, ?, ?, ?, ?, now(), ?, ?) ";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, mId);
			preparedStatement.setString(2, mPw);
			preparedStatement.setString(3, mName);
			preparedStatement.setString(4, mTelno);
			preparedStatement.setString(5, mEmail);
			preparedStatement.setString(6, mArea);
			preparedStatement.setString(7, mIntro);	
			preparedStatement.setString(8, mSports);
			preparedStatement.setString(9, type);

			preparedStatement.executeUpdate();
			
			query = "insert into customer (cSex, cAge, cPhoto, cPhotoPath, cHeight, cWeight, cwClass, member_mSeqno) " +
					"values (?, ?, ?, ?, ?, ?, ?, (select m.mSeqno from keyWar.member as m where m.mId = ? )) ";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, mSex);
			preparedStatement.setString(2, mAge);
			preparedStatement.setBinaryStream(3, file);
			preparedStatement.setString(4, filePath);		
			preparedStatement.setDouble(5, dubMHeight);
			preparedStatement.setDouble(6, dubMWeight);
			preparedStatement.setString(7, mwClass);
			preparedStatement.setString(8, mId);
					
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
	
	public void readyInsert(String rSex, String rAge1, String rAge2, String rArea, String rwClass1, String rwClass2, String mId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select mSeqno from keyWar.member where mId = '" + mId + "'";
			String mSeqno = "";
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery(); // 검색해서 결과 값 나온 상태
			System.out.println(mSeqno);
			if(resultSet.next())  { 
				mSeqno = resultSet.getString(1);
			}
			
			query = "insert into keyWar.ready (member_mSeqno, rSex, rAge1, rAge2, rArea, rwClass1, rwClass2) values (?, ?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, mSeqno);
			preparedStatement.setString(2, rSex);
			preparedStatement.setString(3, rAge1);
			preparedStatement.setString(4, rAge2);
			preparedStatement.setString(5, rArea);
			preparedStatement.setString(6, rwClass1);
			preparedStatement.setString(7, rwClass2);
			
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
	
	public String idCheck(String id) {
		
		String idCheck = "";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			// 데이터 베이스 연결 (userid, password, url 이 들어간다.)
			connection = dataSource.getConnection();			
			
			String query = "select mId from keyWar.member where mId = '" + id + "'";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				idCheck = resultSet.getString(1);
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
		return idCheck;
	}
	
	public ArrayList<KDtoUpdateList> listSignUpUpdate(String A) { //컨+스 java.util
	
		
		ArrayList<KDtoUpdateList> dtos = new ArrayList<KDtoUpdateList>();//dto가 여러개니까 dtos라고 함
		Connection connection = null; //컨+스 java.sql
		PreparedStatement preparedStatement = null;//컨+스 java.sql
		ResultSet resultSet = null;// java.sql
		
		try {
			connection = dataSource.getConnection();
			String query = "SELECT mId, mPw, mName, cSex, cAge, mEmail, mTelno, cHeight, cWeight, mSports, mArea, rSex, rAge1, rAge2, rArea, rwClass1, rwClass2, mIntro FROM keyWar.member AS m, customer AS c, ready AS r WHERE m.mSeqno = c.member_mSeqno AND m.mSeqno = r.member_mSeqno AND mId = '" + A + "'";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery(); // 검색해서 결과 값 나온 상태
			
			if(resultSet.next()) { 
				String mId = resultSet.getString("mId");
				String mPw = resultSet.getString("mPw");
				String mName = resultSet.getString("mName");
				String cSex = resultSet.getString("cSex");
				String cAge = resultSet.getString("cAge");
				String mEmail = resultSet.getString("mEmail");
				String mTelno = resultSet.getString("mTelno");
				double cHeight = Double.valueOf(resultSet.getString("cHeight"));
				double cWeight = Double.valueOf(resultSet.getString("cWeight"));
				String mSports = resultSet.getString("mSports");
				String mArea = resultSet.getString("mArea");
				String rSex = resultSet.getString("rSex");
				int rAge1 = Integer.parseInt(resultSet.getString("rAge1"));
				int rAge2 = Integer.parseInt(resultSet.getString("rAge2"));
				String rArea = resultSet.getString("rArea");
				String rwClass1 = resultSet.getString("rwClass1");
				String rwClass2 = resultSet.getString("rwClass2");
				String mIntro = resultSet.getString("mIntro");
				
				KDtoUpdateList dto = new KDtoUpdateList(mId, mPw, mName, cSex, cAge, mEmail, mTelno, cHeight, cWeight, mSports, mArea, rSex, rAge1, rAge2, rArea, rwClass1, rwClass2, mIntro);
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
	
	
	public void memberUpdate(String mId, String mPw, String mName, String mSex, String mAge, String mTelno, InputStream file, String mEmail, String mAddress, String mIntro, double dubMHeight, double dubMWeight, String mwClass, String mSports) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "update board set mPw=?, mName=?, mSex=?, mAge=?, mTelno=?, mPhoto=?, mEmail=?, mAddress=?, mIntro=?, mHeight=?, mWeight=?, mwClass=?, mSprots=? where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, mPw);
			preparedStatement.setString(2, mName);
			preparedStatement.setString(3, mSex);
			preparedStatement.setString(4, mAge);
			preparedStatement.setString(5, mTelno);
			preparedStatement.setBinaryStream(6, file);
			preparedStatement.setString(7, mEmail);
			preparedStatement.setString(8, mAddress);
			preparedStatement.setString(9, mIntro);			
			preparedStatement.setDouble(10, dubMHeight);
			preparedStatement.setDouble(11, dubMWeight);
			preparedStatement.setString(12, mwClass);
			preparedStatement.setString(13, mSports);
			preparedStatement.setString(14, mId);
			
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
	
	public void readyUpdate(String rSex, String rAge1, String rAge2, String rArea, String rwClass1, String rwClass2, String mId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "update board set bName=?, bTitle=?, bContent=? where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, rSex);
			preparedStatement.setString(2, rAge1);
			preparedStatement.setString(3, rAge2);
			preparedStatement.setString(4, rArea);
			preparedStatement.setString(5, rwClass1);
			preparedStatement.setString(6, rwClass2);
			preparedStatement.setString(7, mId);
			
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
	
	public ArrayList<KDtoMemberGym> gymList() {
		ArrayList<KDtoMemberGym> dtos = new ArrayList<KDtoMemberGym>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// 이미 위에서 다 연결해줌.
			connection = dataSource.getConnection();
			
			String query = "select m.mSeqno, m.mName, g.gAddress, g.gRentalPrice, t.tTimeTable1, t.tTimeTable2 "
					+ "from keyWar.member as m, gym as g, timeTable as t "
					+ "where m.mSeqno = g.member_mSeqno and t.member_mSeqno = m.mSeqno;";
			preparedStatement = connection.prepareStatement(query);			
		
			resultSet = preparedStatement.executeQuery();
			
			// 데이터 베이스 이제 가져와야지
			while(resultSet.next()) {				
				int mSeqno = resultSet.getInt(1);		
				String mName = resultSet.getString(2);
				String gAddress = resultSet.getString(3);
				int gRentalPrice = resultSet.getInt(4);
				String tTimeTable1 = resultSet.getString(5);
				String tTimeTable2 = resultSet.getString(6);
				
				KDtoMemberGym dto = new KDtoMemberGym(mSeqno, mName, gAddress, gRentalPrice, tTimeTable1, tTimeTable2);
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
	
	
	
	public String loginId(String mId, String mPw) {
		
		String loginId = "";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			// 데이터 베이스 연결 (userid, password, url 이 들어간다.)
			connection = dataSource.getConnection();			
			
			String query = "select mId from keyWar.member where mId = '" + mId + "' and mPw = '" + mPw + "'";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				loginId = resultSet.getString(1);
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
		return loginId;
	}
	
}//----
