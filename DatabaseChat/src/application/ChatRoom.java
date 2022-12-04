package application;

import java.sql.DriverManager;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * User view upon entering a chat room.
 * Allows user to send a chat.
 * Checks for and executes help commands.
 * Updates other user activity to chat view.
 */
public class ChatRoom {
	
	User user;
	String roomName;
	int roomId;
	DatabaseAccessor roomInfoAccessor;
	DatabaseAccessor historyAccessor;
	DatabaseAccessor usersAccessor;
	Scanner input;
	
	boolean leaving = false;
	
	public ChatRoom(User user, String roomName, int roomId, 
			DatabaseAccessor roomInfoAccessor, DatabaseAccessor historyAccessor, DatabaseAccessor usersAccessor, Scanner input) {

		this.user = user;
		this.roomName = roomName;
		this.roomId = roomId;
		this.roomInfoAccessor = roomInfoAccessor;
		this.historyAccessor = historyAccessor;
		this.usersAccessor = usersAccessor;
		this.input = input;
		
	}
		
	
	/**
	 * Takes a command marked by a "/" and executes appropriate method.
	 * 
	 * @param cmd The command inputed.
	 */
	private void parseCommand(String command) {
		
		String cmd = command.substring(1);
		if(cmd.compareTo("list") == 0)
			list();
		else if(cmd.compareTo("leave") == 0)
			leave();
		else if(cmd.compareTo("history") == 0)
			history();
		else if(cmd.compareTo("help") == 0)
			help();
		else
			System.out.println("Invalid command! Type /help to see available commands.");
			
	}
	
	/**
	 * Prints list of users currently in chat room.
	 */
	private void list() {
		
		System.out.println("Users in chatroom " + roomName + " : ");
		
		ArrayList<Integer> keys = roomInfoAccessor.getKeys();
		
		for(int i = 0; i < usersAccessor.getKeys().size(); i++)
		{
			if(usersAccessor.get(i).get(1).equals(roomId)) {
				
				System.out.println(i+1 + ". " + usersAccessor.get(i).get(1));
				
			}
		}
		
	}

	/**
	 * User exits chat room into MainView.
	 */
	private void leave() {
		
		System.out.println("Leaving chatroom.");
		
		leaving = true;
		usersAccessor.delete(user.getId());
		
	}
	
	
	public boolean leaving() {
		
		return leaving;
		
	}

	/**
	 * Prints all past messages for current chat room.
	 */
	private void history() {
		
		System.out.println("Displaying history for chatroom" + roomName);
		
		boolean historyExists = false;
		
		for(int i = 0; i < historyAccessor.getKeys().size(); i++) {
			
			if(historyAccessor.get(i).get(1).equals(roomName)) {
			
				System.out.println(historyAccessor.get(i).get(2));
				historyExists = true;
				
			}
			
		}
		
		if(!historyExists) {
			
			System.out.println("No history exists for this room.");
			
		}
		
	}

	/**
	 * Prints list of available help commands.
	 */
	private void help() {
		
		System.out.println("Available commands :");
		System.out.println("/list - Prints the list of users currently in this chat room.");
		System.out.println("/leave - Exit the chat room.");
		System.out.println("/history - Print all past messages for this room.");
		System.out.println("/help for help.");
	}
	
	/**
	 * Sends a chat into the chat room.
	 * 
	 * @param msg The user's message in the chat room.
	 */
	public void sendChat(String msg) {
		
		System.out.print(msg);
		
		if(msg.substring(0,1).equals("/"))
			parseCommand(msg);
		
		update(msg);
	}
	
	/**
	 * Updates chat history view for other user activity.
	 */
	public void update(String msg) {
		
		ArrayList<Object> entry = new ArrayList<>();
		int chatId = historyAccessor.getKeys().size();
		
		entry.add(chatId);
		entry.add(roomId);
		entry.add(msg);
		
		historyAccessor.add(entry);
		
	}
}
