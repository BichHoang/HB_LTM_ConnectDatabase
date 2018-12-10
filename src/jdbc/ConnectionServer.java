package jdbc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionServer {

	private static Statement stmt;

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		ServerSocket server = new ServerSocket(1234);
		System.out.println("Server started");

		Socket socket = server.accept();
		Connection conn = mysqlConnectorLib.MySQLConnUtils.getMySQLConnection();
		stmt = conn.createStatement();

		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());

		while (true) {
			String st = dis.readUTF();
			SQLquery(st, dos);
			dos.flush();
		}
	}

	private static String SQLquery(String query, DataOutputStream dos) throws SQLException, IOException {

		ResultSet rs = stmt.executeQuery(query);
		String result = "";
		while (rs.next()) {
			int id = rs.getInt("ID");
			double luong = rs.getDouble("Luong");
			String name = rs.getString("TenKH");
			String dc = rs.getString("DiaChi");
			result = "ID: " + id + " TenKH: " + name + " Dia Chi: " + dc + " Luong: " + luong;
			dos.writeUTF(result);
		}
		return result;
	}
}