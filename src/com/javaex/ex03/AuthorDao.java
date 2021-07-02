package com.javaex.ex03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
	// field

	// 생성자

	// method g/s

	// method

	public List<AuthorVo> getAuthorList() {
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			// 3. SQL문 준비 / 바인딩 / 실행
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

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
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

		return authorList;
	}

	public void getAuthorListOne() {

	}

	public int authorInsert(AuthorVo arthorVo) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		// ResultSet rs = null;
		int count = 0;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열로 만들기--> ? 주의
			query += " insert into author ";
			query += " values(seq_author_id.nextval,?,?) ";

			pstmt = conn.prepareStatement(query); // 문자열-> 쿼리문
			pstmt.setString(1, arthorVo.getAuthorName()); // ? 중 1번째 순서중요
			pstmt.setString(2, arthorVo.getAuthorDesc()); // ? 중 2번째 순서중요
			count = pstmt.executeUpdate(); // 쿼리문 실행 -->return 으로 성공여부판단

			// 4.결과처리
			System.out.println(count + "건이 저장되었습니다.");
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
			try {
				/*
				 * if (rs != null) { rs.close(); }
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
		return count;

	}

	public int authorUpdate(AuthorVo authorVo) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count=0;
		// ResultSet rs = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			// 3. SQL문 준비 / 바인딩 / 실행
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
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
			try {
				/*
				 * if (rs != null) { rs.close(); }
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
		return count;

	}

	public void authorDelete(int authorId) {
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
					pstmt.setInt(1 ,authorId); // ? 중 1번째 순서중요
					
					count = pstmt.executeUpdate();
					
					String query2 = "";
					
					query2 += " delete from author ";
					query2 += " where author_id= ? ";
					pstmt2 = conn.prepareStatement(query2); // 문자열-> 쿼리문
					pstmt2.setInt(1 ,authorId); // ? 중 1번째 순서중요
					
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
