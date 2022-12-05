package application;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// Scanner
		// TODO: Pass this Scanner in to other classes.

		Scanner scan = null;

		try {
			scan = new Scanner(System.in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("Enter PostgreSQL password: ");
		String databasePass = scan.nextLine();

		// Create Connection to database.

		String database = "databasechat";

		Connection c = null;

		try {

			Statement stmt = null;

			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database, "postgres", databasePass);

			c.setAutoCommit(false);
			stmt = c.createStatement();

			DatabaseMetaData md = c.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			int numTables = 0;

			while (rs.next()) {
				if (rs.getString(2).equals("public") && !rs.getString(3).contains("pkey")) {
					numTables++;
				}
			}
			stmt.close();

			if (numTables == 0) {
				String sql;
				stmt = c.createStatement();
				sql = "CREATE TABLE authentication " + "(id INT PRIMARY KEY NOT NULL," + " username TEXT NOT NULL,"
						+ " password TEXT NOT NULL);";
				stmt.executeUpdate(sql);
				stmt.close();

				stmt = c.createStatement();
				sql = "CREATE TABLE chat_room " + "(id INT PRIMARY KEY NOT NULL," + " name TEXT NOT NULL);";
				stmt.executeUpdate(sql);
				stmt.close();

				stmt = c.createStatement();
				sql = "CREATE TABLE chat_history " + "(id INT PRIMARY KEY NOT NULL," + " room_id INT NOT NULL,"
						+ " chat TEXT NOT NULL);";
				stmt.executeUpdate(sql);
				stmt.close();

				stmt = c.createStatement();
				sql = "CREATE TABLE chat_users " + "(id INT PRIMARY KEY NOT NULL," + " room_id INT NOT NULL);";
				stmt.executeUpdate(sql);
				stmt.close();

				c.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		DatabaseAccessor authenticationAccessor = new DatabaseAccessor(c, "authentication");
		DatabaseAccessor chatAccessor = new DatabaseAccessor(c, "chat_room");
		DatabaseAccessor historyAccessor = new DatabaseAccessor(c, "chat_history");
		DatabaseAccessor usersAccessor = new DatabaseAccessor(c, "chat_users");

		Authentication authenticationScreen = new Authentication(authenticationAccessor, scan);

		User user = authenticationScreen.authScreen();

		if (user != null) {

			MainView mainScreen = new MainView(user, authenticationAccessor, chatAccessor, historyAccessor,
					usersAccessor, scan);

			do {

				mainScreen.execute(user);

				user = authenticationScreen.authScreen();

			} while (user != null);

		}

		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
