package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDeleteApp {

	public static void main(String[] args) {

		// insert into author values(seq_author_id.nextval,'김영하','알쓸신잡');

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		//  ResultSet rs = null;
		int count ;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열로 만들기--> ? 주의
			
			query += " delete from book ";
			query += " where author_id= ? ";
			
			pstmt = conn.prepareStatement(query); // 문자열-> 쿼리문
			pstmt.setString(1 ,"2"); // ? 중 1번째 순서중요
			
			count = pstmt.executeUpdate();
			
			String query2 = "";
			
			query2 += " delete from author ";
			query2 += " where author_id= ? ";
			pstmt2 = conn.prepareStatement(query2); // 문자열-> 쿼리문
			pstmt2.setString(1 ,"2"); // ? 중 1번째 순서중요
			
			count = pstmt2.executeUpdate();// 쿼리문 실행 -->return 으로 성공여부판단
			
			// 4.결과처리
			System.out.println(count+"건이 삭제되었습니다.");
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
			try {
				/*
				if (rs != null) {
					rs.close();
				}
				*/
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

	}
}