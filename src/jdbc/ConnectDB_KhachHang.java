package jdbc;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

public class ConnectDB_KhachHang extends JFrame {
	private JTable tblkh;
	private DefaultTableModel model;
	private JTextField tfURL;
	private JTextField tfSQLquery;

	public ConnectDB_KhachHang() throws ClassNotFoundException {
		// TODO Auto-generated constructor stub
		getContentPane().setLayout(null);
		tblkh = new JTable();
		tblkh.setPreferredSize(new Dimension(500, 180));
		JScrollPane scrollPane = new JScrollPane(tblkh);
		scrollPane.setBounds(10, 170, 452, 183);
		getContentPane().add(scrollPane);
		
		JLabel lblConnectionDatabase = new JLabel("Connection Database");
		lblConnectionDatabase.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		lblConnectionDatabase.setBounds(140, 10, 170, 22);
		getContentPane().add(lblConnectionDatabase);
		
		JLabel lblUrl = new JLabel("URL Database:");
		lblUrl.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblUrl.setBounds(10, 48, 129, 22);
		getContentPane().add(lblUrl);
		
		JLabel lblCuTruyVn = new JLabel("Câu truy vấn SQL:");
		lblCuTruyVn.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblCuTruyVn.setBounds(10, 95, 129, 22);
		getContentPane().add(lblCuTruyVn);
		
		tfURL = new JTextField();
		tfURL.setEditable(false);
		tfURL.setFont(new Font("Times New Roman", Font.ITALIC, 13));
		tfURL.setText("\"jdbc:mysql://localhost:3306/khachhang\", \"root\", \"\"");
		tfURL.setBounds(140, 48, 318, 25);
		getContentPane().add(tfURL);
		tfURL.setColumns(10);
		
		tfSQLquery = new JTextField();
		tfSQLquery.setColumns(10);
		tfSQLquery.setBounds(140, 95, 318, 25);
		getContentPane().add(tfSQLquery);
		
		JButton btnQuery = new JButton("Query");
		btnQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String sql = tfSQLquery.getText();
				if(sql.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Bạn chưa nhập câu truy vấn");
				}else {
					int key = 0;
					String rs = "";
					try {
						Connection conn = mysqlConnectorLib.MySQLConnUtils.getMySQLConnection();
						if((sql.toUpperCase()).indexOf("SELECT") >= 0) {
							key = 1;
						}
						if((sql.toUpperCase()).indexOf("UPDATE") >= 0) {
							key = 2;
						}
						if((sql.toUpperCase()).indexOf("INSERT") >= 0) {
							key = 3;
						}
						if((sql.toUpperCase()).indexOf("DELETE") >= 0) {
							key = 4;
						}
						switch (key) {
						case 1:
							selectQuery(conn, sql);
							break;
						case 2:
							updateQuery(conn, sql);
							break;
						case 3:
							updateQuery(conn, sql);
							break;
						case 4:
							deleteQuery(conn, sql);
							break;
						default:
							break;
						}
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			private void deleteQuery(Connection conn, String sql) throws ClassNotFoundException {
				try {
					Statement stmt = (Statement) conn.createStatement();
					stmt.execute(sql);
					loadDataIntoJTable();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Câu truy vấn sai");
					e.printStackTrace();
				}
			}

			private void updateQuery(Connection conn, String sql) throws ClassNotFoundException {
				try {
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.executeUpdate();
					loadDataIntoJTable();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Câu truy vấn sai");
					e.printStackTrace();
				}
				
			}

			private void selectQuery(Connection conn,String sql) throws SQLException {
				try {
					PreparedStatement pst = conn.prepareStatement(sql);
					ResultSet rs = pst.executeQuery();
					List<KhachHang> list = new ArrayList<KhachHang>();
					while (rs.next()) {
						KhachHang kh = new KhachHang(rs.getInt("ID"), rs.getString("TenKH"), rs.getString("DiaChi"),
								rs.getFloat("Luong"));
						list.add(kh);
					}
					model = new DefaultTableModel();
					// Set Column Title
					Vector column = new Vector();
					column.add("ID");
					column.add("Name");
					column.add("Address");
					column.add("Salary");
					model.setColumnIdentifiers(column);
					for (int i = 0; i < list.size(); i++) {
						KhachHang kh = (KhachHang) list.get(i);
						Vector row = new Vector();
						row.add(kh.getId());
						row.add(kh.getName());
						row.add(kh.getAddress());
						row.add(kh.getSalary());
						model.addRow(row);
					}

					tblkh.setModel(model);
					
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Câu truy vấn sai");
					e.printStackTrace();
				}
			}
		});
		btnQuery.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnQuery.setBounds(377, 130, 85, 25);
		getContentPane().add(btnQuery);
		
		JLabel lblDatabaseKhachhang = new JLabel("Database: KhachHang - Table: Table1");
		lblDatabaseKhachhang.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		lblDatabaseKhachhang.setBounds(10, 130, 257, 33);
		getContentPane().add(lblDatabaseKhachhang);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(482, 400);
		loadDataIntoJTable();
	}

	private void loadDataIntoJTable() throws ClassNotFoundException {
		model = new DefaultTableModel();
		// Set Column Title
		Vector column = new Vector();
		column.add("ID");
		column.add("Name");
		column.add("Address");
		column.add("Salary");
		model.setColumnIdentifiers(column);
		List<KhachHang> list = getListKH();
		for (int i = 0; i < list.size(); i++) {
			KhachHang kh = (KhachHang) list.get(i);
			Vector row = new Vector();
			row.add(kh.getId());
			row.add(kh.getName());
			row.add(kh.getAddress());
			row.add(kh.getSalary());
			model.addRow(row);
		}

		tblkh.setModel(model);
	}

	private static List<KhachHang> getListKH() throws ClassNotFoundException {
		try {
			Connection conn = mysqlConnectorLib.MySQLConnUtils.getMySQLConnection();
			String sql = "SELECT ID,TenKH,DiaChi,Luong FROM Table1";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			List<KhachHang> list = new ArrayList<KhachHang>();
			while (rs.next()) {
				KhachHang kh = new KhachHang(rs.getInt("ID"), rs.getString("TenKH"), rs.getString("DiaChi"),
						rs.getFloat("Luong"));
				list.add(kh);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws ClassNotFoundException {
		new ConnectDB_KhachHang();
	}
}