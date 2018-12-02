package com.websocket.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {

	static String DRIVERNAME = "com.mysql.jdbc.Driver";
	static String URL = "jdbc:mysql://localhost:3306/texteditor";
	static String PASSWORD = "pankaj";
	static String USERNAME = "root";
	static Connection con;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVERNAME);
		con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		return con;
	}

	public static void closeConnection(Connection con) {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// if the connection is not null,need to close it
	}

	public static void closeStatement(PreparedStatement smt) {
		// if the prepared statement is not null,need to close it

		if (smt != null) {
			try {
				smt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
