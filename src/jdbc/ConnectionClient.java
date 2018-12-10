package jdbc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionClient {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Socket socket = new Socket("localhost", 1234);
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("SQL Query: ");
			String msg = sc.nextLine();
			dos.writeUTF(msg);
			dos.flush();
			String st = "";
			do {
				st = dis.readUTF();
				System.out.println(st);
			} while (!st.isEmpty());
			sc = sc.reset();
		}
	}
}
