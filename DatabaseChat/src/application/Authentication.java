package application;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Provides a screen with a command-line interface that is displayed when
 * opening a new chat application. Checks for authentication of the user
 * accessing the app, and creates a new User with database entry.
 */
public class Authentication {

	DatabaseAccessor db;
	Scanner scan;

	boolean leavingLogin;
	boolean leavingAuth;
	int userId;

	/**
	 * Constructs the new Authentication screen. Assigns parameter to variable.
	 * 
	 * @param db   DatabaseAccessor taken from main to be used in Authentication
	 *             methods.
	 * @param scan Scanner to read inputs.
	 */
	public Authentication(DatabaseAccessor db, Scanner scan) {

		this.db = db;
		this.scan = scan;

		this.leavingLogin = false;
		userId = -1;

	}

	/**
	 * Initial screen that calls either register() or login() screens.
	 * 
	 * @return User taken from either register() or login() to be used in
	 *         determining authentication in other screens.
	 */
	public User authScreen() {

		User user = null;
		boolean run = true;
		leavingLogin = false;
		
		while (run) {
			System.out.print("\nWelcome to *generic chat app name*! Enter a command:\n - /register \n - /login \n - /exit \n");

			System.out.print("\n\n> ");
			String s = scan.nextLine();

			if (s.equals("/register")) {
				user = register();
				
				if(user != null)
					run = false;
				
			} else if (s.equals("/login")) {
				user = login();
				
				if(user != null)
					run = false;
				
			} else if (s.equals("/exit")) {
				return null;
				
			} else {
				System.out.print("\nInvalid: Unknown Command.");
			}
		}

		return user;
	}

	/**
	 * Gets command-line prompts from the user (username, password) and searches the
	 * database. Checks if inputs exist and determines authentication. If
	 * authenticated, returns a new user with unique id and given parameters.
	 *
	 * @return User that mirrors database entry.
	 */
	public User login() {

		User user = null;
		boolean done = false;
		int tempid = -1;

		while (!done) {

			boolean exists = false;
			System.out.print("\nUsername or /exit to leave: ");
			String un = scan.nextLine();
			Object[] keys = db.getKeys().toArray();

			if (un.equals("/exit")) {

				return null;

			}

			// check that user exists
			for (int i = 0; i < keys.length; i++) {
				
				if (db.get(i).get(1).equals(un)) {

					exists = true;
					tempid = (int) keys[i];
					userId = i;

				}
			}

			if (exists) {

				boolean valid = false;
				while (!valid) {

					System.out.print("Password or /exit to leave: ");
					String p = scan.nextLine();
					Object[] names = db.get(tempid).toArray();
					
					if (p.equals("/exit")) {

						return null;

					}

					// check that password matches user name
					if (db.get(userId).get(2).equals(p)) {

						user = new User(userId, un, p);
						System.out.println("\nLogging you in...\n");
						valid = true;
						done = true;

					} else {

						System.out.println("\nInvalid: Incorrect Password.\n");

					}
				}
			} else {

				System.out.print("\nInvalid: Username doesn't exist.\n");

			}
		}

		return user;
	}

	/**
	 * Gets command-line prompts from the user(username, password) and updates the
	 * database. Checks if inputs are unique. If unique, returns a new user with
	 * unique id and given parameters.
	 *
	 * @return User that mirrors database entry.
	 */
	public User register() {

		User user = null;
		boolean done = false;

		System.out.println("\nRegistering new user...");

		while (!done) {

			boolean valid = true;
			System.out.print("\nUsername or /exit to leave: ");
			String un = scan.nextLine();

			Object[] keys = db.getKeys().toArray();
			
			if (un.equals("/exit")) {

				return null;

			}
			
			// check if user name is taken
			for (int i = 0; i < keys.length; i++) {
				
				if (db.get(i).get(1).equals(un)) 
					valid = false;

			}
			if (valid) {

				System.out.print("Password or /exit to leave: ");
				String p = scan.nextLine();
				ArrayList<Object> entry = new ArrayList<Object>();
				
				if (p.equals("/exit")) {

					return null;

				}

				if (keys.length == 0)
					entry.add(0);
				else
					entry.add(keys.length);
				entry.add(un);
				entry.add(p);

				// update users database
				db.add(entry);
				// create new User
				user = new User(keys.length, un, p);

				System.out.println("\nSuccessfully registered. Logging you in...\n");
				done = true;

			} else {

				System.out.print("\nInvalid: Username already taken.\n");

			}
		}
		return user;
	}

}
