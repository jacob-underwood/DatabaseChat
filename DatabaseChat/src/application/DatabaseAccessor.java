package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Database interfacer to abstract SQL operations.
 *
 */
public class DatabaseAccessor {

	Connection c;
	String table;

	/**
	 * @param c     Connection to database.
	 * @param table Name of the SQL table to be dealt with in the database.
	 */
	public DatabaseAccessor(Connection c, String table) {
		this.c = c;
		this.table = table;
	}

	/**
	 * Adds an entry to the database for the specified table.
	 * 
	 * @param vals The different values to put in the table.
	 * @return true if successful
	 */
	public boolean add(ArrayList<Object> vals) {

		/*
		 * Orders for different tables: authentication: (ID, USERNAME, PASSWORD)
		 * chat_room: (ROOM_ID, NAME, USERS) chat_history: (CHAT_ID, ROOM_ID, CHAT)
		 */

		// Collects all of the ArrayList entries into one String.
		String valsStr = "";

		// Strings in the ArrayList should have '' around them when put into the SQL
		// command. Other data types should have nothing.
		for (Object val : vals) {
			if (val.getClass().equals(String.class)) {
				valsStr += "'" + val.toString() + "', ";
			} else {
				valsStr += val.toString() + ", ";
			}
		}

		// Fix the gate problem/remove the additional comma space at the end of valsStr.
		valsStr = valsStr.substring(0, valsStr.length() - 2);

		// Run PostreSQL command.
		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO " + table + " VALUES(" + valsStr + ");";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return true;
	}

	/**
	 * @param id ID of element to delete.
	 * @return true if successful.
	 */
	public boolean delete(int id) {

		// Count of deleted rows.
		int deleted = 0;

		// Run PostreSQL command.
		try {
			Statement stmt = c.createStatement();
			String sql = "DELETE FROM " + table + " WHERE id = " + id + ";";
			deleted = stmt.executeUpdate(sql);
			System.out.println(deleted);
			stmt.close();
			c.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return deleted > 0;
	}

	/**
	 * @param id ID to get values from.
	 * @return Values to return, including id.
	 */
	public ArrayList<Object> get(int id) {

		ArrayList<Object> vals = new ArrayList<>();

		// Run PostreSQL command.
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM " + table + " WHERE id = " + id + ";";
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					Object val = rs.getObject(i);
					vals.add(val);
				}
			}

			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return vals;
	}

	/**
	 * Gets all of the unique IDs in the table.
	 * 
	 * @return Keys in an ArrayList of Integers.
	 */
	public ArrayList<Integer> getKeys() {

		ArrayList<Integer> keys = new ArrayList<Integer>();

		// Run PostreSQL command.
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT id FROM " + table + ";";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int key = rs.getInt(1);
				keys.add(key);
			}

			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return keys;
	}

	/**
	 * Updates a value in the table at the specified id in the given column.
	 * 
	 * @param id ID of row to update (primary key).
	 * @param column Name of column to update.
	 * @param val New value to set column at id to.
	 * @return true if successful.
	 */
	public boolean update(int id, String column, Object val) {

		// String version of val.
		String valStr = "";

		// Strings in the ArrayList should have '' around them when put into the SQL
		// command. Other data types should have nothing.
		if (val.getClass().equals(String.class)) {
			valStr += "'" + val.toString() + "'";
		} else {
			valStr += val.toString();
		}

		// Run PostreSQL command.
		try {
			Statement stmt = c.createStatement();
			String sql = "UPDATE " + table + " SET " + column + " = " + valStr + " WHERE id = " + id + ";";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return true;
	}

}
