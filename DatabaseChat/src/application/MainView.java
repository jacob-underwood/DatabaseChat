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
	DatabaseAccessor usersAccessor;
	Scanner input;
	
	boolean leavingRoom;
	boolean leavingMain;
	
	public MainView(User user, DatabaseAccessor authenticationAccessor, 
			DatabaseAccessor roomInfoAccessor, DatabaseAccessor historyAccessor, DatabaseAccessor usersAccessor, Scanner input)
	{
		
		this.user = user;
		this.authenticationAccessor = authenticationAccessor;
		this.roomInfoAccessor = roomInfoAccessor;
		this.historyAccessor = historyAccessor;
		this.usersAccessor = usersAccessor;
		this.input = input;
		this.leavingRoom = false;
		this.leavingMain = false;
		
	}
	
	public void execute() {
		
		String entry = "";
		
		while(!leavingMain) {
			
			leavingMain = false;
			System.out.println("\nType /create to create a chat room");
			System.out.println("/join to join a chat room");
			System.out.println("/updatename to update your username");
			System.out.println("/updatepass to update your password");
			System.out.println("/logout to log out\n");
			
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
			else {
				System.out.println("\nInvalid: Unknown command.\n");
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
			System.out.println("\nEnter a name for your chat room or type /exit to return to Main View : ");
			name = input.nextLine();
			taken = false;
			
			for(int i = 0; i < roomInfoAccessor.getKeys().size(); i++) {
				
				if(roomInfoAccessor.get(i).get(1).equals(name)) {
					
					taken = true;
					
				}
				
			}
			
			if(name.equals("/exit")) //exit to main view
				valid = true;
			else if(taken) //name is taken
				System.out.println("\nA room already exists with that name. Please enter another.");
			else { 
				//valid room name! create room and enter
				valid = true;
				
				ChatRoom room = new ChatRoom(user, name, roomInfoAccessor.getKeys().size(), roomInfoAccessor, 
						historyAccessor, usersAccessor, input);
				
				//update chat_room table
				ArrayList<Object> roomEntry = new ArrayList<>();
				roomEntry.add(roomInfoAccessor.getKeys().size());
				roomEntry.add(name);
				
				roomInfoAccessor.add(roomEntry);
				
				//update chat_users table
				ArrayList<Object> userEntry = new ArrayList<>();
				userEntry.add(user.getId());
				userEntry.add(usersAccessor.getKeys().size());
				
				usersAccessor.add(userEntry);
				
				System.out.println("\nRoom " + name + " created!\n");
				beginChatting(room, name);
			}
		}
		
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
			System.out.println("\nEnter the name of the room or type /exit to return to Main View : ");
			name = input.nextLine();
			exists = false;
			int index = 0;
			
			for(int i = 0; i < roomInfoAccessor.getKeys().size(); i++) {
				
				if(roomInfoAccessor.get(i).get(1).equals(name)) {
					
					exists = true;
					index = i;
					
				}
				
			}
			
			if(name.equals("/exit")) //exit to main view
				valid = true;
			else if(!exists) //name is taken
				System.out.println("\nNo room exists with that name. Please enter another.");
			else { 
				//valid room name! join room
				valid = true;
				ChatRoom room = new ChatRoom(user, name, index, 
						roomInfoAccessor, historyAccessor, usersAccessor, input);
				
				//update chat_users table
				ArrayList<Object> userEntry = new ArrayList<>();
				userEntry.add(user.getId());
				userEntry.add(usersAccessor.getKeys().size());
				
				usersAccessor.add(userEntry);
				
				
				System.out.println("\nRoom " + name + " joined!\n");
				beginChatting(room, name);
			}
		}
		
	}
	
	public void beginChatting(ChatRoom room, String name) {
		
		leavingRoom = false;
		String msg = "";
		while(!leavingRoom) {
			
			//System.out.println("last index (+1): " + room.lastChatIndex() + "size (-1): "+ historyAccessor.getKeys().size());
			for(int i = room.lastChatIndex() + 1; i < historyAccessor.getKeys().size()-1; i++) {
				
				//if(!user.getUsername().equals(historyAccessor.get(i).get(2).substring(0,user.getUsername().length())))
					System.out.println(historyAccessor.get(i).get(2));
				//System.out.println("i: " + i + " - I checked!");
			}
			
			System.out.print(user.getUsername() + ": ");
			msg = input.nextLine();
			room.sendChat(msg);
			
			leavingRoom = room.leaving();
			
		}
		
		System.out.println("\nReturning to Main View...\n");
		
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
			
			System.out.println("\nEnter a new username : ");
			name = input.nextLine();
			taken = false;
			
			for(int i = 0; i < authenticationAccessor.getKeys().size(); i++) {
				
				if(authenticationAccessor.get(i).get(1).equals(name)) {
					
					taken = true;
					
				}
				
			}
			
			if(name.equals("/exit")) //exit to main view
				valid = true;
			else if(taken) //name is taken
				System.out.println("\nA user already exists with that name. Please enter another: ");
			else { 
				//valid user name! update name
				valid = true;
				updated = authenticationAccessor.update(user.getId(), "USERNAME", name);
				System.out.println("\nUsername updated.\n");
					
				}
			}
		
		System.out.println("Returning to Main View...");
		
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
			
			System.out.println("\nEnter your new password or type /exit to return to Main View: ");
			pass = input.nextLine();
			
			if(pass.equals("/exit")) //exit to main view
				valid = true;
			else if(pass.equals("")) //no pass entered
				System.out.println("\nYou cannot have a blank password.\n");
			else { 
				//valid! update pass
				valid = true;
				
				updated = authenticationAccessor.update(user.getId(), "PASSWORD", pass);
				System.out.println("\nPassword updated.\n");
				}
			}
		
		System.out.println("\nReturning to Main View...\n");
		
	}

	/**
	 * Logs the current user out. Return to authentication.
	 */
	public void logout() {
		
		leavingMain = true;
		System.out.println("\nLogged out. Returning to authentication...\n");
		
	}
	
	public boolean leaving() {
		
		return leavingMain;
		
	}

}

