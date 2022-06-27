//db���� ��ü -> db�� �����ؼ� ������ �������ų� �����͸� �ִ� ����
package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;	//db�����ϰ� ���ִ� ��ü
	private PreparedStatement pstmt;	//sql �ε��ǰ��� ��ŷ ����� ����ϱ� ����
	private ResultSet rs;	//���� ���� �� �ִ� ��ü
	
	//������. ��ü�� ������� �� �ڵ����� db connection�� �̷��������
	//mysql�� ����
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			String dbID = "root";
			String dbPassword = "1234";
			Class.forName("com.mysql.jdbc.Driver");	//Driver -> mysql�� ������ �� �ֵ��� �Ű�ü �����ϴ� ���̺귯��
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);	//���ӵ� ���� ���
		} catch (Exception e) {
			e.printStackTrace();	//���� �߻���
		}
	}
	
	//�α��� �õ��ϴ� �Լ�
	public int login(String userID, String userPassword) {	
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";	//id�� ?�� ���� ���翩�ο� ��й�ȣ ��������
		try {
			pstmt = conn.prepareStatement(SQL);	//������ sql������ db�� ����
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();	//������ �־��ֱ�
			if (rs.next()) {	//����� �����Ѵٸ�
				if(rs.getString(1).equals(userPassword)) {
					return 1;	//�α��� ����
				}
				else
					return 0;	//��й�ȣ ����ġ
			}	
			return -1;	//id�� ����
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2;	//db����
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
			e.printStackTrace();	//db����
		}
		return -1;
	}
	
}
