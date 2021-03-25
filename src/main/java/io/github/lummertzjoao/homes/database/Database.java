package io.github.lummertzjoao.homes.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.lummertzjoao.homes.Main;

public class Database {

	private static Connection connection = null;

	private static String hostname, port, database, username, password;

	public static Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(
						"jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false", username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadProperties(Main main) {
		hostname = main.getConfig().getString("mysql.hostname");
		port = main.getConfig().getString("mysql.port");
		database = main.getConfig().getString("mysql.database");
		username = main.getConfig().getString("mysql.username");
		password = main.getConfig().getString("mysql.password");
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
