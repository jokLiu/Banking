package bank.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//class for connecting to postgresql database
public class Connect {

	private Connection conn;

	public Connect(Connection conn) {
		this.conn = conn;
	}

	//method for making connection and getting the connection object
	public Connection getConnection() {
		registerDriver();
		connectServer();
		return conn;
	}

	/**
	 * Register postgreSQL driver.
	 */
	private void registerDriver() {
		System.out.println("Setting up database.");
		System.out.println("Register postgreSQL driver");
		try {

			// Load the PostgreSQL JDBC driver
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException ex) {

			// If driver not found exit
			System.err.println("Driver not found");
			System.exit(1);
		}

		System.out.println("PostgreSQL driver registered.");
	}

	/**
	 * Connect to database server.
	 */
	private void connectServer() {
		System.out.println("Connecting database");
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "postgres", "258453Ak");
		} catch (SQLException e) {
			System.err.println("Ooops, couldn't get a connection");
			System.err.println("Check that <username> & <password> are right");
			System.exit(1);
		}

		System.out.println(conn != null ? "Database accessed!" : "Failed to make connection");
		if (conn == null)
			System.exit(1);
	}

}