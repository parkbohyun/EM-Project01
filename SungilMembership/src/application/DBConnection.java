package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public Connection dbConn;
	
	public Connection getConnection() {
		String dbDriver = "oracle.jdbc.driver.OracleDriver";
		String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String dbUser = "SYSTEM";
		String dbPassword = "1234";
		
		try {
			Class.forName(dbDriver);
			dbConn = DriverManager.getConnection(
					dbUrl, dbUser, dbPassword
			);
			//System.out.println("디비 연결 완료~!");
		} catch(Exception e) {
			//System.out.println("디비 연결 실패..");
			e.printStackTrace();
		}
		
		return dbConn;
	}
}
