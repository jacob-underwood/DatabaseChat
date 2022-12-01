package application;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// Create Connection to database.
		
		String database = "databasechat";

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database, "postgres",
					"the2@d7idoNedwCYoE3k");
			
			c.setAutoCommit(false);
			stmt = c.createStatement();
			
			DatabaseMetaData md = c.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			int numTables = 0;
//			if (rs != null) {
//				rs.last();
//				numTables = rs.getRow();
//			}
			while(rs.next()) {
				if (rs.getString(2).equals("public") && !rs.getString(3).contains("pkey")) {
					numTables++;
				}
			}
			System.out.println(numTables);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
//		try {
//			stmt = c.createStatement();
//			String sql = "CREATE TABLE authentication " +
//					"(id INT PRIMARY KEY NOT NULL," +
//					" username TEXT NOT NULL," +
//					" password TEXT NOT NULL);";
//			stmt.executeUpdate(sql);
//			stmt.close();
//			c.close();
//			System.out.println("Table was created");
//		} catch(Exception e) {
//			e.printStackTrace();
//			System.err.print(e.getClass().getName() + ": " + e.getMessage());
//			System.exit(0);
//		}
		
//		try {
//			stmt = c.createStatement();
//			String sql = "CREATE TABLE chat_room " +
//					"(room_id INT PRIMARY KEY NOT NULL," +
//					" name TEXT NOT NULL," + 
//					" users TEXT[] NOT NULL);";
//			stmt.executeUpdate(sql);
//			stmt.close();
//			c.commit();
//			c.close();
//			System.out.println("Table was created");
//		} catch(Exception e) {
//			e.printStackTrace();
//			System.err.print(e.getClass().getName() + ": " + e.getMessage());
//			System.exit(0);
//		}
		
//		try {
//			stmt = c.createStatement();
//			String sql = "CREATE TABLE chat_history " +
//					"(chat_id INT PRIMARY KEY NOT NULL," +
//					" room_id INT NOT NULL," + 
//					" chat TEXT NOT NULL);";
//			stmt.executeUpdate(sql);
//			stmt.close();
//			c.commit();
//			c.close();
//			System.out.println("Table was created");
//		} catch(Exception e) {
//			e.printStackTrace();
//			System.err.print(e.getClass().getName() + ": " + e.getMessage());
//			System.exit(0);
//		}

		DatabaseAccessor authenticationAccessor = new DatabaseAccessor(c, "authentication");
		DatabaseAccessor chatAccessor = new DatabaseAccessor(c, "chat_room");
		DatabaseAccessor historyAccessor = new DatabaseAccessor(c, "chat_history");
		
		// EXAMPLE OF USAGE.
		
//		ArrayList<Object> test = new ArrayList<>();
//		test.add(30);
//		test.add("jacob1");
//		test.add("password123");
//		
//		authenticationAccessor.add(test);
//		
//		authenticationAccessor.delete(30);
//		
//		authenticationAccessor.update(30, "username", "jacob2");
//		
//		ArrayList<Object> returned = new ArrayList<>();
//		
//		returned = authenticationAccessor.get(30);
//		
//		System.out.println(returned);
//		
//		System.out.println(authenticationAccessor.getKeys());

		// OUTDATED:
		
		// while loop -> Start Authentication screen.

		// User object to be used throughout program.

		// while loop -> MainView screen.

		// while loop -> ChatRoom screens.

		

		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
