package com.jsplec.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.jsp.lec.bbs.dto.KDtoElectroinicDisplay;
import com.jsp.lec.bbs.dto.KDtoGym;
import com.jsp.lec.bbs.dto.KDtoMatch;
import com.jsp.lec.bbs.dto.KDtoMember;
import com.jsp.lec.bbs.dto.KDtoMemberCustomer;
import com.jsp.lec.bbs.dto.KDtoMemberGym;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

public class KDao_match {

	// Field
	static DataSource dataSource;
	
	// Constructor
	public KDao_match() {
		// try 나오면 자바가 아니고 다른애 쓰는구나. (톰캣서버꺼 씀)
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/keyWar");	// 타입 바꿔줘. 생성자 부르면 context에서 연결해서 씀.
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void matchInsert(String myId, String rival, String gymSeqno, String date) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String[] id = {myId, rival};
		int[] seqno = new int[2];
		
		try {
			connection = dataSource.getConnection();	// 연결 끝.
			
			for (int i = 0 ; i < id.length ; i++) {
				String query = "select mSeqno from keyWar.member where mId = ? ";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, id[i]);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					seqno[i] = resultSet.getInt(1);
				}
			}
			String query = "insert into keyWar.match (member_mSeqno, rival_mSeqno, gym_mSeqno, mFightDate, mSuccess) " + 
						   "values (?, ?, ?, ?, ? ) ";
			preparedStatement = connection.prepareStatement(query);
				
			preparedStatement.setInt(1, seqno[0]);
			preparedStatement.setInt(2, seqno[1]);
			preparedStatement.setInt(3, Integer.parseInt(gymSeqno));
			preparedStatement.setString(4, date);
			preparedStatement.setString(5, "0");
				
			preparedStatement.executeUpdate();	// 실행.
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public boolean matchIng(String myId) {
		boolean match = false;
				
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			connection = dataSource.getConnection();	// 연결 끝.
			
			String query = "select mSuccess from keyWar.match "
						 + "where rival_mSeqno = (select mSeqno from keyWar.member where mId = ?) and mSuccess = '0' ";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, myId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				match = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return match;
	}
	
	public KDtoMemberCustomer matchMemberInfo(String myId) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		int memberSeqno = 0;
		
		KDtoMemberCustomer member = null;
				
		try {
			connection = dataSource.getConnection();	// 연결 끝.
			
			String query = "select member_mSeqno from keyWar.match " + 
						   "where member_mSeqno = (select mSeqno from keyWar.member where mId = ?);";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, myId);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				memberSeqno = resultSet.getInt(1);
			}
			
			query = "select m.mId, m.mArea, m.mSports, c.cAge, c.cHeight, c.cWeight, c.cwClass, c.cWin, c.cLose, c.cDraw " + 
					"from keyWar.member as m, customer as c " +
					"where m.mSeqno = ? and c.member_mSeqno = ? ";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, memberSeqno);
			preparedStatement.setInt(2, memberSeqno);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String id = resultSet.getString(1);
				String area = resultSet.getString(2);
				String sports = resultSet.getString(3);
				int age = resultSet.getInt(4);
				double height = resultSet.getInt(5);
				double weight = resultSet.getInt(6);
				String wclass = resultSet.getString(7);
				int win = resultSet.getInt(8);
				int lose = resultSet.getInt(9);
				int draw = resultSet.getInt(10);
				
				member = new KDtoMemberCustomer(id, area, sports, age, height, weight, wclass, win, lose, draw);
			}			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return member;
	}
	
	public KDtoMemberCustomer matchRivalInfo(String Id) {
		KDtoMemberCustomer rival = null;

		int rivalSeqno = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();	// 연결 끝.
						
			String query = "select rival_mSeqno from keyWar.match as k, keyWar.member as m where m.mId = ?"; 
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, Id);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				rivalSeqno = resultSet.getInt(1);
			}
			
			query = "select m.mId, m.mArea, m.mSports, c.cAge, c.cHeight, c.cWeight, c.cwClass, c.cWin, c.cLose, c.cDraw " + 
					"from keyWar.member as m, customer as c " +
					"where m.mSeqno = ? and c.member_mSeqno = ? ";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, rivalSeqno);
			preparedStatement.setInt(2, rivalSeqno);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				rival = new KDtoMemberCustomer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), 
						resultSet.getInt(4), resultSet.getDouble(5), resultSet.getDouble(6), resultSet.getString(7), 
						resultSet.getInt(8), resultSet.getInt(9), resultSet.getInt(10));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return rival;
	}
	
	public KDtoMemberGym matchGymInfo(String myId) {
		KDtoMemberGym gym = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		int gymSeqno = 0;
		
		try {
			connection = dataSource.getConnection();	// 연결 끝.

			String query = "select gym_mSeqno from keyWar.match as k, keyWar.member as m where m.mId = ?"; 
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, myId);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				gymSeqno = resultSet.getInt(1);
			}		
			
			query = "select m.mName, m.mTelno, m.mSports, g.gNumber, gAddress, gRentalPrice " + 
					"from keyWar.member as m, gym as g " + 
					"where m.mSeqno = ? and g.member_mSeqno = ? ";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, gymSeqno);
			preparedStatement.setInt(2, gymSeqno);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				gym = new KDtoMemberGym(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), 
						resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
			}		
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return gym;
	}
	
	public void matched(String myId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();	// 연결 끝.
			String query = "UPDATE keyWar.match as mat, keyWar.member as mem SET mat.mSuccess = 1 WHERE mem.mId = ? ";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, myId);
			
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
	
	public static void matchIngChange(String matchSeqno, String mType) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		int seqno = Integer.parseInt(matchSeqno);
		int type = Integer.parseInt(mType);
		
		type = type + 1;
		
		try {
			connection = dataSource.getConnection();	// 연결 끝.
			String query = "UPDATE keyWar.match SET mSuccess = ? WHERE matchSeqno = ? ";
			preparedStatement = connection.prepareStatement(query);
		
			preparedStatement.setString(1, Integer.toString(type));
			preparedStatement.setInt(2, seqno);
			
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
	
	public static void matchIngCancle(String matchSeqno) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		int seqno = Integer.parseInt(matchSeqno);
		
		try {
			connection = dataSource.getConnection();	// 연결 끝.
			String query = "DELETE FROM keyWar.match WHERE matchSeqno = ?";
			preparedStatement = connection.prepareStatement(query);
		
			preparedStatement.setInt(1, seqno);
			
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
	
	public ArrayList<KDtoMemberCustomer> matchListMember(String myId, String mType) {
		
		ArrayList<KDtoMemberCustomer> memberDtos = new ArrayList<KDtoMemberCustomer>();
		ArrayList<Integer> memberSeqno = new ArrayList<Integer>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
								
		try {
			connection = dataSource.getConnection();	// 연결 끝.
			
			String query = "select member_mSeqno from keyWar.match " + 
					" where mSuccess = ? and " + 
					"(member_mSeqno = (select mSeqno from keyWar.member where mId = ?) or  " +
					"rival_mSeqno = (select mSeqno from keyWar.member where mId = ?)) ";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, mType);
			preparedStatement.setString(2, myId);
			preparedStatement.setString(3, myId);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				memberSeqno.add(resultSet.getInt(1));
			}
			
			for (int i = 0 ; i < memberSeqno.size() ; i++ ) {
				query = "select m.mId, m.mArea, m.mSports, c.cAge, c.cHeight, c.cWeight, c.cwClass, c.cWin, c.cLose, c.cDraw " + 
					"from keyWar.member as m, customer as c " +
					"where m.mSeqno = ? and c.member_mSeqno = ? ";

				preparedStatement = connection.prepareStatement(query);
							
				preparedStatement.setInt(1, memberSeqno.get(i));
				preparedStatement.setInt(2, memberSeqno.get(i));
				
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					String id = resultSet.getString(1);
					String area = resultSet.getString(2);
					String sports = resultSet.getString(3);
					int age = resultSet.getInt(4);
					double height = resultSet.getInt(5);
					double weight = resultSet.getInt(6);
					String wclass = resultSet.getString(7);
					int win = resultSet.getInt(8);
					int lose = resultSet.getInt(9);
					int draw = resultSet.getInt(10);				
	
					KDtoMemberCustomer member = new KDtoMemberCustomer(id, area, sports, age, height, weight, wclass, win, lose, draw);
					memberDtos.add(member);
				}			
			
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return memberDtos;
	}
	
	public ArrayList<KDtoMemberCustomer> matchListRival(String myId, String mType) {
		ArrayList<KDtoMemberCustomer> rivalDtos = new ArrayList<KDtoMemberCustomer>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		ArrayList<Integer> rivalSeqno = new ArrayList<Integer>();
		
		try {
			connection = dataSource.getConnection();	// 연결 끝.

			String query = "select rival_mSeqno from keyWar.match " + 
					" where mSuccess = ? and " + 
					"(member_mSeqno = (select mSeqno from keyWar.member where mId = ?) or  " +
					"rival_mSeqno = (select mSeqno from keyWar.member where mId = ?)) ";
			
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, mType);
			preparedStatement.setString(2, myId);
			preparedStatement.setString(3, myId);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				rivalSeqno.add(resultSet.getInt(1));
			}
			
			for (int i = 0 ; i < rivalSeqno.size() ; i++) {
				query = "select m.mId, m.mArea, m.mSports, c.cAge, c.cHeight, c.cWeight, c.cwClass, c.cWin, c.cLose, c.cDraw " + 
						"from keyWar.member as m, customer as c " +
						"where m.mSeqno = ? and c.member_mSeqno = ? ";
	
				preparedStatement = connection.prepareStatement(query);
	
				preparedStatement.setInt(1, rivalSeqno.get(i));
				preparedStatement.setInt(2, rivalSeqno.get(i));
				
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					KDtoMemberCustomer rival = new KDtoMemberCustomer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), 
							resultSet.getInt(4), resultSet.getDouble(5), resultSet.getDouble(6), resultSet.getString(7), 
							resultSet.getInt(8), resultSet.getInt(9), resultSet.getInt(10));
					rivalDtos.add(rival);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return rivalDtos;
	}
	
	public ArrayList<KDtoMemberGym> matchListGym(String myId, String mType) {
		
		ArrayList<KDtoMemberGym> gymDtos = new ArrayList<KDtoMemberGym>();		
		ArrayList<Integer> gymSeqno = new ArrayList<Integer>();
				
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			connection = dataSource.getConnection();	// 연결 끝.

			String query = "select gym_mSeqno from keyWar.match " + 
					" where mSuccess = ? and " + 
					" (member_mSeqno = (select mSeqno from keyWar.member where mId = ?) or  " +
					" rival_mSeqno = (select mSeqno from keyWar.member where mId = ?)) ";
			
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, mType);
			preparedStatement.setString(2, myId);
			preparedStatement.setString(3, myId);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				gymSeqno.add(resultSet.getInt(1));
			}
			
			for (int i = 0 ; i < gymSeqno.size() ; i++) {
				query = "select m.mName, m.mTelno, m.mSports, g.gNumber, gAddress, gRentalPrice " + 
						"from keyWar.member as m, gym as g " + 
						"where m.mSeqno = ? and g.member_mSeqno = ? ";
	
				preparedStatement = connection.prepareStatement(query);
	
				preparedStatement.setInt(1, gymSeqno.get(i));
				preparedStatement.setInt(2, gymSeqno.get(i));
	
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					KDtoMemberGym gym = new KDtoMemberGym(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), 
							resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
					gymDtos.add(gym);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return gymDtos;
	}
	
	public ArrayList<KDtoMatch> matchListSeqno(String myId, String mType) {
		ArrayList<KDtoMatch> seqno = new ArrayList<KDtoMatch>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			connection = dataSource.getConnection();	// 연결 끝.

			String query = "select matchSeqno, mSuccess from keyWar.match " + 
					" where mSuccess = ? and " + 
					" member_mSeqno = (select mSeqno from keyWar.member where mId = ?) or  " +
					" rival_mSeqno = (select mSeqno from keyWar.member where mId = ?) ";
			
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, mType);
			preparedStatement.setString(2, myId);
			preparedStatement.setString(3, myId);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int matchSeqno = resultSet.getInt(1);
				String mSuccess = resultSet.getString(2);
				KDtoMatch dto = new KDtoMatch(matchSeqno, mSuccess);
				seqno.add(dto);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return seqno;
	}
	// --------------------------
	// ---------- 매칭완료 ---------
	public void matchCompleted(String gym_mId, String member_mId, String rival_mId) {
		
		int matchSeqno = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "SELECT matchSeqno FROM keywar.match WHERE gym_mSeqno= (SELECT mSeqno FROM keywar.member WHERE mId=?) AND member_mSeqno = (SELECT mSeqno FROM keywar.member WHERE mId=?) AND rival_mSeqno = (SELECT mSeqno FROM keywar.member WHERE mId=?) ORDER BY matchSeqno DESC LIMIT 1";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, gym_mId);
			preparedStatement.setString(2, member_mId);
			preparedStatement.setString(3, rival_mId);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				matchSeqno = resultSet.getInt("matchSeqno");
			}
			
			query = "UPDATE keywar.match SET mSuccess = mSuccess+1 WHERE matchSeqno =" + matchSeqno;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	// ---------- 매칭완료끝 -------
	// --------------------------
	
	// --------------------------
	// ---------- 전광판 ----------

	public ArrayList<KDtoElectroinicDisplay> electroinicDisplay(){
		ArrayList<KDtoElectroinicDisplay> dtos = new ArrayList<KDtoElectroinicDisplay>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			connection = dataSource.getConnection();	// 연결 끝.

			String query =  "select mem.mId, riv.mId, gymm.mName, mat.mFightDate, gymm.mArea, gym.gRentalNum " +
							"from gym, keywar.match as mat " +
							"left join keywar.member as mem " +
							"on mat.member_mSeqno = mem.mSeqno " +
							"left join keywar.member as riv " +
							"on mat.rival_mSeqno = riv.mSeqno " +
							"left join keywar.member as gymm " +
							"on mat.gym_mSeqno = gymm.mSeqno " +
							"where mat.mSuccess = 2 and (mat.mFightDate between now() and date_add(now(), interval + 1 month)) and gym.member_mSeqno = mat.gym_mSeqno " +
							"order by gym.gRentalNum desc";

					
			preparedStatement = connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				String challenger = resultSet.getString(1);
				String enemy = resultSet.getString(2);
				String gymName = resultSet.getString(3);
				String mFightDate = resultSet.getString(4);
				String mArea = resultSet.getString(5);
				String gRentalNum = resultSet.getString(6);
				
				dtos.add(new KDtoElectroinicDisplay(challenger, enemy, gymName, mFightDate, mArea, gRentalNum));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}		
		
		return dtos;
	}
	
	// ---------- 전광판 끝 --------
	// --------------------------
	
	
	
}//----
