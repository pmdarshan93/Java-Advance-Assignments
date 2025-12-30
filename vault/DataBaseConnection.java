package vault;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DataBaseConnection {
private static DataBaseConnection instant;
private  Connection connection;
private static final Logger logger=LogManager.getLogger();

private DataBaseConnection() {
	try {
		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/Vault","root","D@rshan123");
		logger.info("JDBC Connection successful");
	}catch(SQLException e) {
		logger.fatal("JDBC Connection Failed");
		System.out.println(e.getMessage());
	}
}

public static DataBaseConnection getInstant() {
	if(instant==null) 
		instant=new DataBaseConnection();
	logger.info("accessed getInstant of dbConnection");
	return instant;
}

public Connection getConnection() {
	logger.info("accessed get connection of dbConnection");
	return connection;
}
}
