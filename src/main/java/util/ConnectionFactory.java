package util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	private static ConnectionFactory cf = null;
	private static boolean build = true;

	private ConnectionFactory() {
	}

	public static synchronized ConnectionFactory getInstance() {
		if (build) {
			cf = new ConnectionFactory();
			build = false;
		}
		return cf;
	}

	public Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			ClassLoader cl = getClass().getClassLoader();
			prop.load(new FileReader(cl.getResource("datasource.properties").getFile()));

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
					prop.getProperty("password"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
