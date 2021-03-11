package addressbook;

import java.io.*;
import java.util.ArrayList;
import java.sql.*;

@SuppressWarnings("unused")

/*
 * 프로그램 이름 : AddressBook.java 프로그램 설명 : 클래스 AddressBook 정의  마지막 프로그램 작성일 : 2020-11-29
 * 작성자 : 김지원
 */

public class AddressBook {

	Connection conn = null; // 데이터베이스를 연결하기 위해
	Statement stmt = null; // statement 객체
	PreparedStatement pstmt = null; // preparedStatement 객체
	ResultSet rs = null; // 데이터를 읽어오기 위해

	void disconnect() {
		if (stmt != null) {
			try {
				stmt.close(); // statement 닫기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close(); // preparedStatement 닫기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close(); // resultset 닫기
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public AddressBook(String url, Connection conn) { // 객체 생성
		try {
			conn = DriverManager.getConnection(url); // 데이터베이스 연결
			stmt = conn.createStatement(); // Statement 객체 얻기

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.close(); // connection 닫기
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			disconnect();
		}
	}

	// 등록된 사람 수 - 완성
	public int getCount() {
		int count = 0;
		try {

			rs = stmt.executeQuery("select count(*) from addressbook"); // 주소록 갯수 가져오기
			if (rs.next())
				count = rs.getInt(1); // 최종 id값 가져오기

			// System.out.println(count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("주소록 갯수를 가져오는데 오류가 발생했습니다.");
		}
		return count;
	}

	// 주소록 등록 메소드 - 완성
	public void addAddress(Person p) throws Exception {
		int result = 0;
		String sql = "insert into addressbook values(?,?,?,?,?)"; // 주소록 추가

		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, getId()); // 다음 들어갈 id값 가져와서(getId()) id에 넣어주기
		pstmt.setString(2, p.getName());
		pstmt.setString(3, p.getPhoneNum());
		pstmt.setString(4, p.getAddress());
		pstmt.setString(5, p.getEmail()); // p에 접근하여 이름,번호,주소,이메일 가져오기
		result = pstmt.executeUpdate(); // 데이터 값을 읽어와서 주소록 등록해주기

	}

	// 이름으로 주소록 번호 검색 메소드 - 완성
	public int searchName(String name) throws SQLException {
		int id = 0;
		String sql = "select * from addressbook where name like '%" + name + "%'"; // name의 값을 가지고 있는 주소록 찾기

		//stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);

		while (rs.next()) {
			id = rs.getInt("id"); // id값 반환 위해(삭제,조회,수정)
		}

		return id;

	}

	// 전화번호로 주소록 번호 검색 메소드 - 완성
	public int searchPhoneNum(String phoneNum) throws SQLException {
		int id = 0;
		String sql = "select * from addressbook where phone like '%" + phoneNum + "%'"; // phone의 값을 가지고 있는 주소록 찾기

		//stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);

		while (rs.next()) {
			id = rs.getInt("id"); // id값 반환 위해(삭제,조회,수정)
		}

		// System.out.println(id);
		return id;

	}

	// 주소록 수정 메소드 - 완성
	public void modify(int id, Person p) throws SQLException {
		int result = 0;
		String sql = "update addressbook set name=?, phone=?, address=?, email=? where id= " + id; // 해당 id값 가지고 있는 주소록
																									// 수정하기

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, p.getName());
		pstmt.setString(2, p.getPhoneNum());
		pstmt.setString(3, p.getAddress());
		pstmt.setString(4, p.getEmail()); // 객체 p에 접근하여 값 받아와서 수정하기

		result = pstmt.executeUpdate();

	}

	// 주소록 삭제 메소드 - 완성
	public void delete(int id) throws SQLException {

		String sql = "delete from addressbook where id =" + id;

		//stmt = conn.createStatement();
		stmt.executeUpdate(sql);

	}

	// ->삭제시 마지막 주소록의 id 다음 값를 부여하고자
	public int getId() throws SQLException {
		int id = 0;
		String sql = "select * from addressbook"; // addressbook 전체 조회 후

		//stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);

		while (rs.next()) {
			id = rs.getInt("id"); // 데이터가 없을 때까지 마지막 행의 id값을 받아옴
		}

		return id + 1; // 마지막 행의 id값 +1 = 다음행의 id값

	}

	// Person 객체 넘겨주는 메소드 - 완성
	public Person getPerson(int index) throws SQLException {

		String sql = "select * from addressbook where id = " + index; // index에 해당하는 id값을 가진 주소록 조회

		Person p = null;
		String n1 = null;
		String p1 = null;
		String a1 = null;
		String e1 = null;

		rs = stmt.executeQuery(sql);

		while (rs.next()) {
			n1 = rs.getString("name");
			p1 = rs.getString("phone");
			a1 = rs.getString("address");
			e1 = rs.getString("email");// 이름,전화번호,주소,이메일 값 받아오기
		}
		p = new Person(n1, p1, a1, e1); // p객체에 넣어주기

		return p;
	}

	// id 값이 있는지 확인 id값이 있으면 true, 없으면 false
	public boolean getIdByCheck(int id) throws SQLException {
		boolean result = true;
		String sql = "select * from addressbook where id = " + id; // id값에 해당하는 주소록 찾기

		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		if (rs.next())
			result = false;// id가 있으면 false, 없으면 true

		return result;

	}

}
