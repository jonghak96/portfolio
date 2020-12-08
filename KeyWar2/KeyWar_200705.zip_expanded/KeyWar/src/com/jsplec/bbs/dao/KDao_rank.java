package com.jsplec.bbs.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.jsp.lec.bbs.dto.KDtoMember;
import com.jsp.lec.bbs.dto.KDtoMemberCustomer;

public class KDao_rank {

DataSource dataSource;
	
	 public KDao_rank() {
		// TODO Auto-generated constructor stub
	
		try {
			Context context = new InitialContext(); // 컨+스 javax.naming
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/keyWar"); // context.xml이랑 연결
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	 
	 public ArrayList<KDtoMemberCustomer> mainRankingList() { //컨+스 java.util
			ArrayList<KDtoMemberCustomer> dtos = new ArrayList<KDtoMemberCustomer>();//dto가 여러개니까 dtos라고 함
			Connection connection = null; //컨+스 java.sql
			PreparedStatement preparedStatement = null;//컨+스 java.sql
			ResultSet resultSet = null;// java.sqls
			try {
				connection = dataSource.getConnection();
				String query="select m.mId, c.cWin, c.cLose, c.cDraw, c.cAge, c.cHeight, c.cWeight, m.mSports, cPhotoPath from keyWar.member as m, customer as c where m.mSeqno = c.member_mSeqno order by c.cWin desc limit 3;";
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery(); // 검색해서 결과 값 나온 상태
				
				while(resultSet.next()) { // 데이터가 있을 때까지 다 가져와라
					String mId = resultSet.getString("mId");
					int cWin = Integer.parseInt(resultSet.getString("cWin"));
					int cLose = Integer.parseInt(resultSet.getString("cLose"));
					int cDraw = Integer.parseInt(resultSet.getString("cDraw"));
					int cAge = Integer.parseInt(resultSet.getString("cAge"));
					double cHeight = Double.valueOf(resultSet.getString("cHeight"));
					double cWeight = Double.valueOf(resultSet.getString("cWeight"));
					String mSports = resultSet.getString("mSports");
					String cPhotoPath = resultSet.getString("cPhotoPath");
					KDtoMemberCustomer dto = new KDtoMemberCustomer(mId, mSports, cAge, cHeight, cWeight, cWin, cLose, cDraw, cPhotoPath);
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
	 
}
