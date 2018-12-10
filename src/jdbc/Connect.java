package jdbc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Connect {

	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println("Ket noi CSDL");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/khachhang", "root", "");
			Statement stmt = (Statement) conn.createStatement();
			String sql21 = "INSERT INTO Table1(TenKH,DiaChi,Luong) VALUES ('Bích','Da Nang','50000')";
			stmt.executeUpdate(sql21);
//			String sql22 = "UPDATE Table1 SET Luong = Luong*0.1";
//			stmt.executeUpdate(sql22);
			String sql33 = "SELECT ID,TenKH,DiaChi,Luong FROM Table1";
			ResultSet rs = stmt.executeQuery(sql33);
			while (rs.next()) {
					int id = rs.getInt("ID");
					double luong = rs.getDouble("Luong");
					String name = rs.getString("TenKH");
					String dc = rs.getString("DiaChi");
					System.out.println("ID: " + id + " TenKH: " + name + " Dia Chi: " + dc + " Luong: " + luong);
			}
		} catch (SQLException e) {
				System.out.println("Loi");
		}
	}
}