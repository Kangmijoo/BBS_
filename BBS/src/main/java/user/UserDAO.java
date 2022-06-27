//db접근 객체 -> db에 접근해서 데이터 가져오거나 데이터를 넣는 역할
package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;	//db접근하게 해주는 객체
	private PreparedStatement pstmt;	//sql 인덱션같은 해킹 기법을 방어하기 위함
	private ResultSet rs;	//정보 담을 수 있는 객체
	
	//생성자. 객체로 만들었을 때 자동으로 db connection이 이루어지도록
	//mysql에 접속
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			String dbID = "root";
			String dbPassword = "1234";
			Class.forName("com.mysql.jdbc.Driver");	//Driver -> mysql에 접속할 수 있도록 매개체 역할하는 라이브러리
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);	//접속된 정보 담기
		} catch (Exception e) {
			e.printStackTrace();	//오류 발생시
		}
	}
	
	//로그인 시도하는 함수
	public int login(String userID, String userPassword) {	
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";	//id가 ?에 들어가서 존재여부와 비밀번호 가져오기
		try {
			pstmt = conn.prepareStatement(SQL);	//정해진 sql문장을 db에 삽입
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();	//실행결과 넣어주기
			if (rs.next()) {	//결과가 존재한다면
				if(rs.getString(1).equals(userPassword)) {
					return 1;	//로그인 성공
				}
				else
					return 0;	//비밀번호 불일치
			}	
			return -1;	//id가 없음
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2;	//db오류
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();	//db오류
		}
		return -1;
	}
	
}
