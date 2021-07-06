package com.javaex.ex05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	public void getConnection() {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
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

	public int bookDelete(int bookId) {

		int count = -1;

		this.getConnection();

		try {

			String query = "";
			query += " delete from book ";
			query += " where book_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);

			count = pstmt.executeUpdate();
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.close();

		return count;
	}

	public int bookUpdate(BookVo bookVo) {

		int count = -1;

		this.getConnection();

		try {

			String query = "";
			query += " update book ";
			query += " set title = ?, ";
			query += "       pubs = ?, ";
			query += "       pub_date = ?, ";
			query += "       author_id = ? ";
			query += " where book_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());
			pstmt.setInt(5, bookVo.getBookId());

			count = pstmt.executeUpdate();

			System.out.println(count + "건 수정");

		} catch (SQLException e) {
			System.out.println("error:" + e);

		}

		this.close();

		return count;
	}

	public int bookInsert(BookVo bookVo) {

		int count = -1;

		this.getConnection();

		try {

			String query = "";
			query += " insert into book ";
			query += " values(seq_book_id.nextval, ?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());

			count = pstmt.executeUpdate();
			System.out.println("책 " + count + "건 등록");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}

	public List<BookVo> getBookList() {

		List<BookVo> bookList = new ArrayList<BookVo>();

		this.getConnection();

		try {

			String query = "";
			query += " select  bo.book_id, ";
			query += "         bo.title, ";
			query += "         bo.pubs, ";
			query += "         to_char(bo.pub_date, 'YYYY-MM-DD') pubDate, ";
			query += "         bo.author_id, ";
			query += "         au.author_name, ";
			query += "         au.author_desc ";
			query += " from book bo, author au ";
			query += " where bo.author_id = au.author_id ";
			query += " order by book_id asc ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pubDate");
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");

				BookVo bookVo = new BookVo(bookId, title, pubs, pubDate, authorId, authorName, authorDesc);

				bookList.add(bookVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return bookList;

	}

	public List<BookVo> getBookList(String search) {

		List<BookVo> bookList = new ArrayList<BookVo>();

		this.getConnection();

		try {

			String query = "";
			query += " select  bo.book_id, ";
			query += "         bo.title, ";
			query += "         bo.pubs, ";
			query += "         to_char(bo.pub_date, 'YYYY-MM-DD') pubDate, ";
			query += "         bo.author_id, ";
			query += "         au.author_name, ";
			query += "         au.author_desc ";
			query += " from book bo, author au ";
			query += " where bo.author_id = au.author_id ";
			query += " and (bo.title || bo.pubs || au.author_name) like " + "'%" + search + "%' ";
			query += " order by book_id asc ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pubDate");
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");

				BookVo bookVo = new BookVo(bookId, title, pubs, pubDate, authorId, authorName, authorDesc);

				bookList.add(bookVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return bookList;

	}
}