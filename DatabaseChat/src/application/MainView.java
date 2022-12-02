package application;

import java.util.Scanner;

/**
 * Primary view of application upon authenticating.
 * Allows creation and joining of rooms.
 * Allows update of username or password.
 * Allows logout.
 */
public class MainView {
	
	User user;
	DatabaseAccessor roomInfoAccessor;
	DatabaseAccessor historyAccessor;
	Scanner input = new Scanner(System.in);
	
	boolean activeRoom = false;
	
	/**
	 * Creates new chatroom.
	 * 
	 */
	public void createRoom() {
		
		String name = "";
		boolean valid = false;
		
		while(!valid) {
			System.out.println("Enter a name for your chat room : ");
			name = input.nextLine();
			if(name.equals("/exit")) //exit to main view
				valid = true;
			else if() //name is taken
				System.out.println("A room already exists with that name. Please enter another: ");
			else { 
				//valid room name! create room and enter
				valid = true;
				Chatroom room = new Chatroom(user, name, roomInfoAccessor.getKeys().size(), roomInfoAccessor, historyAccessor, input);
				String msg = "";
				while(!room.leaving()) {
					
					System.out.println("Room " + name + " created!");
					System.out.println(user + ": ");
					msg = input.nextLine();
					room.sendChat(msg);
					
				}
			}
		}
		
		System.out.println("Returning to Main View.");
		
	}

	/**
	 * Adds user to existing chat room. Displays error if room does not exist.
	 * 
	 * @param rName The name of the room to be joined.
	 */
	public void joinRoom(String rName) {
		
		String name = "";
		boolean valid = false;
		
		while(!valid) {
			System.out.println("Enter the name of the room you want to join : ");
			name = input.nextLine();
			if(name.equals("/exit")) //exit to main view
				valid = true;
			else if() //name is taken
				System.out.println("A room already exists with that name. Please enter another: ");
			else { 
				//valid room name! join room
				valid = true;
				Chatroom room = new Chatroom(user, name, roomInfoAccessor.getKeys().size(), roomInfoAccessor, historyAccessor, input);
				String msg = "";
				while(!room.leaving()) {
					
					System.out.println("Room " + name + " created!");
					System.out.println(user + ": ");
					msg = input.nextLine();
					room.sendChat(msg);
					
				}
			}
		}
		
		System.out.println("Returning to Main View.");
		
	}

	/**
	 * Updates user name of current user. Displays error if user name already exists.
	 * 
	 * @param name The new user name.
	 */
	public void updateUser(String name) {
		
		boolean valid = false;
		String name = "";
		
		while(!valid) {
			
			System.out.println("Enter a new username : ");
			name = input.nextLine();
			
			if(name.equals("/exit")) //exit to main view
				valid = true;
			else if() //name is taken
				System.out.println("A user already exists with that name. Please enter another: ");
			else { 
				//valid user name! update name
				valid = true;
				
					
				}
			}
			
		}
		
		System.out.println("Returning to Main View.");
		
	}

	/**
	 * Updates password of current user.
	 * 
	 * @param newPass The new password.
	 */
	public void updatePass(String newPass) {
		
		boolean valid = false;
		String name = "";
		
		while(!valid) {
			
			System.out.println("Enter a new username : ");
			name = input.nextLine();
			
			if(name.equals("/exit")) //exit to main view
				valid = true;
			else if() //name is taken
				System.out.println("A user already exists with that name. Please enter another: ");
			else { 
				//valid user name! update name
				valid = true;
				
					
				}
			}
			
		}
		
		System.out.println("Returning to Main View.");
		
	}

	/**
	 * Logs the current user out. Return to authentication.
	 */
	public void logout() {
		
		
		
	}

}
