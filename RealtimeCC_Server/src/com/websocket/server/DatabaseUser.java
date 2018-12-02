package com.websocket.server;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUser {

	public static boolean newUser(User user,Connection con) throws SQLException {
		int rowsAffected=0;
		ResultSet rs = null;
		try {
			

			PreparedStatement p = con.prepareStatement("insert into auth values(?,?,?,now())");

			String hash= sha256((user.getPassword()));
			System.out.println(hash);
			p.setString(1, user.getUsername());
			p.setString(2,hash);
			// System.out.println(user.getType());
			p.setString(3, user.getType());
		
			rowsAffected=p.executeUpdate();
			System.out.println(rowsAffected);
		} catch ( SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * if(rs.next()) { return true; } return false;
		 *
		 * 
		 */
		if(rowsAffected!=0)
		return true;
		else
			return false;

	}

	public static boolean checkUser(User user, Connection con) throws SQLException {
		ResultSet rs = null;
		try {

			PreparedStatement p = con.prepareStatement("select * from auth where username=? and passwordHash=?");

			p.setString(1, user.getUsername());
			
			String hash=sha256(user.getPassword());
			p.setString(2, hash);
			rs = p.executeQuery();
			/// ]con.commit();


		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		if (rs.next()) {
			return true;
		}
		return false;

	}
	public static String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}


}
