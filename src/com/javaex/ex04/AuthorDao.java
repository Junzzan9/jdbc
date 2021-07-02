package com.javaex.ex04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
	// field
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	// 생성자

	// method g/s

	// method
	public void getConnection() { // <--DB연결 메소드
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void closed() { // <-- 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
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

	public List<AuthorVo> getAuthorList() {
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();

		// 0. import java.sql.*;
		getConnection();
		try {
			String query = "";
			query += "select author_id, ";
			query += "		 author_name, ";
			query += "		 author_desc ";
			query += " from author ";
			query += " order by author_id asc ";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				/*
				 * int authorId=rs.getInt("author_id"); String
				 * authorName=rs.getString("author_name"); String
				 * authorDesc=rs.getString("author_desc");
				 */

				int authorId = rs.getInt(1);
				String authorName = rs.getString(2);
				String authorDesc = rs.getString(3);

				AuthorVo authorVo = new AuthorVo(authorId, authorName, authorDesc);
				authorList.add(authorVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		closed();
		return authorList;

	}

	public void getAuthorListOne() {

	}

	public int authorInsert(AuthorVo arthorVo) {
		getConnection();
		int count = 0;

		try {
			String query = ""; // 쿼리문 문자열로 만들기--> ? 주의
			query += " insert into author ";
			query += " values(seq_author_id.nextval,?,?) ";

			pstmt = conn.prepareStatement(query); // 문자열-> 쿼리문
			pstmt.setString(1, arthorVo.getAuthorName()); // ? 중 1번째 순서중요
			pstmt.setString(2, arthorVo.getAuthorDesc()); // ? 중 2번째 순서중요
			count = pstmt.executeUpdate(); // 쿼리문 실행 -->return 으로 성공여부판단

			// 4.결과처리
			System.out.println(count + "건이 저장되었습니다.");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		closed();
		return count;

	}

	public int authorUpdate(AuthorVo authorVo) {

		int count = 0;
		getConnection();
		try {
			String query = ""; // 쿼리문 문자열로 만들기--> ? 주의
			query += " update author ";
			query += " set author_name = ?, author_desc = ? where author_id = ? ";

			pstmt = conn.prepareStatement(query); // 문자열-> 쿼리문
			pstmt.setInt(3, authorVo.getAuthorId()); // ? 중 1번째 순서중요
			pstmt.setString(2, authorVo.getAuthorName()); // ? 중 2번째 순서중요
			pstmt.setString(1, authorVo.getAuthorDesc());
			count = pstmt.executeUpdate(); // 쿼리문 실행 -->return 으로 성공여부판단
			// 4.결과처리
			System.out.println(count + "건이 수정되었습니다.");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		closed();
		return count;

	}

	public void authorDelete(int authorId) {
		int count;
		getConnection();
		try {
			String query = ""; // 쿼리문 문자열로 만들기--> ? 주의

			query += " delete from book ";
			query += " where author_id= ? ";

			pstmt = conn.prepareStatement(query); // 문자열-> 쿼리문
			pstmt.setInt(1, authorId); // ? 중 1번째 순서중요

			count = pstmt.executeUpdate();

			String query2 = "";

			query2 += " delete from author ";
			query2 += " where author_id= ? ";
			pstmt = conn.prepareStatement(query2); // 문자열-> 쿼리문
			pstmt.setInt(1, authorId); // ? 중 1번째 순서중요

			count = pstmt.executeUpdate();// 쿼리문 실행 -->return 으로 성공여부판단

			// 4.결과처리
			System.out.println(count + "건이 삭제되었습니다.");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 5. 자원정리
		closed();
	}

}
