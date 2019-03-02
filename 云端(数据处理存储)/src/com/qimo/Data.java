package com.qimo;

import java.sql.*;

public class Data {
	
	private static Connection getConn() {
	    String driver = "com.mysql.cj.jdbc.Driver";
	    String url = "jdbc:mysql://qimo.lanyile.cn:3306/qimo?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
	    String username = "qimo";
	    String password = "48GC8RBifLxMGwPN";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	public  int insert(float val,long addTime) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "insert into dev_data (value,add_time) values(?,?)";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setFloat(1, val);
	        pstmt.setLong(2, addTime);
	       
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	
}
