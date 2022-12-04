package application;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Primary view of application upon authenticating.
 * Allows creation and joining of rooms.
 * Allows update of username or password.
 * Allows logout.
 */
public class MainView {
	
	User user;
	DatabaseAccessor authenticationAccessor;
	DatabaseAccessor roomInfoAccessor;
	DatabaseAccessor historyAccessor;
	Scanner input;
	
	boolean leaving;
	
	public MainView(User user, DatabaseAccessor authenticationAccessor, 
			DatabaseAccessor roomInfoAccessor, DatabaseAccessor historyAccessor, Scanner input)
	{
		
		user = this.user;
		authenticationAccessor = this.authenticationAccessor;
		roomInfoAccessor = this.roomInfoAccessor;
		historyAccessor = this.historyAccessor;
		input = this.input;
		leaving = false;
		
	}
	
	public void execute() {
		
		System.out.println("Welcome, " + user.getUsername() + "!");
		System.out.println("Type /create to create a chat room");
		System.out.println("/join to join a chat room");
		System.out.println("/updatename to update your username");
		System.out.println("/updatepass to update your password");
		System.out.println("/logout to log out");
		
		String entry = "";
		
		while(!leaving) {
			
			entry = input.nextLine();
			if(entry.equals("/create")) {
				createRoom();
			}
			else if(entry.equals("/join")) {
				joinRoom();
			}
			else if(entry.equals("/updatename")) {
				updateUser();
			}
			else if(entry.equals("/updatepass")) {
				updatePass();
			}
			else if(entry.equals("/logout")) {
				logout();
			}
		}
		
	}
	
	
	
	/**
	 * Creates new chatroom.
	 * 
	 */
	public void createRoom() {
		
		String name = "";
		boolean valid = false;
		boolean taken = false;
		
		while(!valid) {
			System.out.println("Enter a name for your chat room or type /exit to return to Main View : ");
			name = input.nextLine();
			taken = false;
			
			for(int i = 0; i < roomInfoAccessor.getKeys(); i++) {
				
				if(roomInfoAccessor.get(i).get(1).equals(name)) {
					
					taken = true;
					
				}
				
			}
			
			if(name.equals("/exit")) //exit to main view
				valid = true;
			else if(taken) //name is taken
				System.out.println("A room already exists with that name. Please enter another.");
			else { 
				//valid room name! create room and enter
				valid = true;
				ChatRoom room = new ChatRoom(user, name, roomInfoAccessor.getKeys().size(), roomInfoAccessor, historyAccessor, input);
				ArrayList<Object> entry = new ArrayList<>();
				entry.add(roomInfoAccessor.getKeys().size());
				entry.add(name);
				entry.add(user.getUsername());
				
				roomInfoAccessor.add(entry);
				
				System.out.println("Room " + name + " created!");
				String msg = "";
				while(!room.leaving()) {
					
					System.out.print(user.getUsername() + ": ");
					msg = input.next();
					room.sendChat(user.getUsername() + ": " + msg);
					
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
	public void joinRoom() {
		
		String name = "";
		boolean valid = false;
		boolean exists = false;
		
		while(!valid) {
			System.out.println("Enter the name of the room or type /exit to return to Main View : ");
			name = input.nextLine();
			exists = false;
			
			for(int i = 0; i < roomInfoAccessor.getKeys(); i++) {
				
				if(roomInfoAccessor.get(i).get(1).equals(name)) {
					
					exists = true;
					
				}
				
			}
			
			if(name.equals("/exit")) //exit to main view
				valid = true;
			else if(!exists) //name is taken
				System.out.println("No room exists with that name. Please enter another.");
			else { 
				//valid room name! join room
				valid = true;
				System.out.println("Room " + name + " joined!");
				String msg = "";
				while(!room.leaving()) {
					
					System.out.print(user.getUsername() + ": ");
					msg = input.next();
					room.sendChat(user.getUsername() + ": " + msg);
					
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
	public void updateUser() {
		
		boolean valid = false;
		boolean taken = false;
		
		boolean updated = false;
		
		String name = "";
		
		while(!valid) {
			
			System.out.println("Enter a new username : ");
			name = input.nextLine();
			taken = false;
			
			for(int i = 0; i < roomInfoAccessor.getKeys(); i++) {
				
				if(roomInfoAccessor.get(i).get(1).equals(name)) {
					
					taken = true;
					
				}
				
			}
			
			if(name.equals("/exit")) //exit to main view
				valid = true;
			else if(taken) //name is taken
				System.out.println("A user already exists with that name. Please enter another: ");
			else { 
				//valid user name! update name
				valid = true;
				updated = authenticationAccessor.update(user.getId(), "USERNAME", name);
				System.out.println("Password updated.");
					
				}
			}
		
		System.out.println("Returning to Main View.");
		
	}

	/**
	 * Updates password of current user.
	 * 
	 * @param newPass The new password.
	 */
	public void updatePass() {
		
		boolean valid = false;
		String pass = "";
		
		boolean updated = false;
		
		while(!valid) {
			
			System.out.println("Enter your new password or type /exit to return to Main View: ");
			pass = input.nextLine();
			
			if(pass.equals("/exit")) //exit to main view
				valid = true;
			else if(pass.equals("")) //no pass entered
				System.out.println("You cannot have a blank password.");
			else { 
				//valid! update pass
				valid = true;
				
				updated = authenticationAccessor.update(user.getId(), "PASSWORD", pass);
				System.out.println("Password updated.");
				}
			}
		
		System.out.println("Returning to Main View.");
		
	}

	/**
	 * Logs the current user out. Return to authentication.
	 */
	public void logout() {
		
		leaving = true;
		System.out.println("Logged out. Returning to authentication.");
		
	}
	
	public boolean leaving() {
		
		return leaving;
		
	}

}

