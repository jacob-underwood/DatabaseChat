package application;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Database interfacer to abstract SQL operations.
 *
 */
public class DatabaseAccessor {
	
	Connection c;
	String table;
	
	/**
	 * @param c Connection to database.
	 * @param table Name of the SQL table to be dealt with in the database.
	 */
	public DatabaseAccessor(Connection c, String table) {
		
	}
	
	/**
	 * Adds an entry to the database for the specified table.
	 * 
	 * @param vals The different values to put in the table.
	 * @return true if successful
	 */
	public boolean add(ArrayList<Object> vals) {
		return false;
	}
	
	/**
	 * @param id ID of element to delete.
	 * @return true if successful.
	 */
	public boolean delete(int id) {
		return false;
	}
	
	/**
	 * @param id ID to get values from.
	 * @return true if successful.
	 */
	public ArrayList<Object> get(int id) {
		return null;
	}
	
}
