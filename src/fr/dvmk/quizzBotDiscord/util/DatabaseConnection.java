package fr.dvmk.quizzBotDiscord.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class DatabaseConnection {

	private static DatabaseConnection instance = null;
	private static Logger logger = Logger.getLogger(DatabaseConnection.class);
	private Connection cnx;

	
	private DatabaseConnection() throws Exception
	{
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			ResourceBundle rb = ResourceBundle.getBundle("fr.dvmk.quizzBotDiscord.properties.config");
			String uri = rb.getString("sgbd.uri");
			String login = rb.getString("sgbd.login");
			String pass = rb.getString("sgbd.password");
			
			cnx = DriverManager.getConnection(uri,login ,pass);
		} catch(Exception e) {
			logger.fatal("Connection base de donnée "+e.getMessage());
			throw e;
		}
		

	}
	
	/**
	 * Singleton use to obtain only one access to the database
	 * @return
	 * @throws Exception
	 */
	public static DatabaseConnection getInstance() throws Exception
	{
		if(instance == null)
		{
			instance= new DatabaseConnection();
		}
		return instance;
	}
	
	public Connection getConnection(){
		return cnx;
	}
}
