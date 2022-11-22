package application;

/**
 * Provides a screen with a command-line interface that is displayed when opening a new chat application.
 * Checks for authentication of the user accessing the app, and creates a new User with database entry.
 */
public class Authentication {
	
	/**
	 * Gets command-line prompts from the user (username, password) and searches the database.
	 * Checks if inputs exist and determines authentication.
	 * If authenticated, returns a new user with unique id and given parameters.
	 *
	 * @return User that mirrors database entry.
	 */
	public User login() {
		return null;
	}
	
	/**
	 * Gets command-line prompts from the user(username, password) and updates the database.
	 * Checks if inputs are unique.
	 * If unique, returns a new user with unique id and given parameters.
	 *
	 * @return User that mirrors database entry.
	 */
	public User register() {
		return null;
	}

}
