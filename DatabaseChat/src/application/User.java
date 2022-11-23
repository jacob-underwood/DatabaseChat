package application;

/**
 * Creates a new object that holds pertinent user information.
 * Allows access to chat room, determines one's identity when entering a new room.
 */
public class User {
	private int id; // Unique user ID persistent even if username is changed.
	private String username;
	private String password;
	
	/**
	 * Instantiates a new User with unique id, username and password.
	 * This user will be used to determine access to chat rooms.
	 * 
	 * @param id
	 * @param username
	 * @param password
	 */
	public User(int id, String username, String password) {
		
	}
	
	/**
	 * Returns the unique id.
	 * 
	 * @return int that represents user ID.
	 */
	public int getId() {
		return -1;
	}
	
	/**
	 * Returns the username.
	 * 
	 * @return String that represents username.
	 */
	public String getUsername() {
		return "";
	}
	
	/**
	 * Sets the username to the new parameter.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		
	}
	
	/**
	 * Returns the password.
	 * 
	 * @return String that represents user password.
	 */
	public String getPassword() {
		return "";
	}
	
	/**
	 * Sets the password to the new parameter.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		
	}
	
}
