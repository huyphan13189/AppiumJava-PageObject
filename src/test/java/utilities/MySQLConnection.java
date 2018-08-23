package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.SQLException;

public class MySQLConnection {
	private static String user = "username";
	private static String password = "password";
	private static String server = "propq-integration.cdkwpxsur0lj.ap-southeast-1.rds.amazonaws.com:3306";
	private static String database = "database";
	private static String connectionURL = "jdbc:mysql://" + server + "/" + database + "?useSSL=false";

	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet resultSet = null;

	public static void startConnection() {
		try {
			conn = DriverManager.getConnection(connectionURL, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection() {
		try {
			resultSet.close();
		} catch (Exception e) {
		}

		try {
			stmt.close();
		} catch (Exception e) {
		}

		try {
			conn.close();
		} catch (Exception e) {
		}
	}
	
	//ex: executeQuery("select * from product");
	public static List<HashMap<String, String>> executeQuery(String query) {
		startConnection();
		
		List<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
		
		try {
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(query);

			while (resultSet.next()) {
				ResultSetMetaData metaData = resultSet.getMetaData();
				int count = metaData.getColumnCount();
				HashMap<String, String> hmData = new HashMap<>();
				for (int i = 1; i <= count; i++) {	
					hmData.put(metaData.getColumnLabel(i),resultSet.getString(i));
				}
				listData.add(hmData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		closeConnection();
		
		return  listData;
	}
}
