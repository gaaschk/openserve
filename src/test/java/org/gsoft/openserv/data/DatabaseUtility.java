package org.gsoft.openserv.data;

import java.io.IOException;
import java.util.Properties;

import org.dbmaintain.DbMaintainer;
import org.dbmaintain.MainFactory;
import org.dbmaintain.structure.clear.DBClearer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtility {
	@Value("${openserv.db.driver}")
	private String dbDriver;
	@Value("${openserv.db.username}")
	private String dbUsername;
	@Value("${openserv.db.password}")
	private String dbPassword;
	@Value("${openserv.db.url}")
	private String dbURL;
	@Value("${openserv.db.schemas}")
	private String dbSchemas;
	
	public void refreshDatabase() throws IOException{
		Properties dbprops = new Properties();
		dbprops.load(ClassLoader.getSystemResourceAsStream("dbmaintain-default.properties"));
		dbprops.setProperty("database.driverClassName", dbDriver);
		dbprops.setProperty("database.userName", dbUsername);
		dbprops.setProperty("database.password",dbPassword);
		dbprops.setProperty("database.url",dbURL);
		dbprops.setProperty("database.schemaNames",dbSchemas);
		dbprops.setProperty("dbMaintainer.script.locations", "src/main/dbscripts");
		dbprops.setProperty("autoCreateDbMaintainScriptsTable", "true");
		MainFactory factory = new MainFactory(dbprops);
		DBClearer clearer = factory.createDBClearer();
		clearer.clearDatabase();
		DbMaintainer dbmaintainer = factory.createDbMaintainer();
		dbmaintainer.updateDatabase(false);
		System.out.println("*************************************************************************************");
		System.out.println("Using database: " + dbprops.getProperty("database.driverClassName") +
				", " + dbprops.getProperty("database.url") +
				", " + dbprops.getProperty("database.userName") + 
				", " + dbprops.getProperty("database.password") +
				", autoCreateDbMaintainScriptsTable=" + dbprops.getProperty("autoCreateDbMaintainScriptsTable"));
		System.out.println("Database refreshed");
	}
}
