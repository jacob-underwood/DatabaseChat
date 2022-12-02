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
	ArrayList<User> users = new ArrayList<>();
	DatabaseAccessor roomInfoAccessor;
	DatabaseAccessor historyAccessor;
	Scanner input;
	
	boolean leaving = false;
	
	public ChatRoom(User user, String roomName, int roomId, DatabaseAccessor roomInfoAccessor, DatabaseAccessor historyAccessor, Scanner input) {

		user = this.user;
		roomName = this.roomName;
		roomId = this.roomId;
		roomInfoAccessor = this.roomInfoAccessor;
		historyAccessor = this.historyAccessor;
		input = this.input;
		
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
		
		ArrayList<Integer> userList = 
		
		for(int i = 0; i < userList.size(); i++)
		{
			System.out.println(i+1 + ". " + userList.get(i));
		}
		
	}

	/**
	 * User exits chat room into MainView.
	 */
	private void leave() {
		
		System.out.println("Leaving chatroom.");
		
		leaving = true;
		roomInfoAccessor.delete(user.getId());
		
	}

	/**
	 * Prints all past messages for current chat room.
	 */
	private void history() {
		
		System.out.println("Displaying history for chatroom" + roomName);
		
		if(historyAccessor.getKeys().size() == 0) {
			
			System.out.println("No chat history!");
			
		}
		
		for(int i = 0; i < historyAccessor.getKeys().size(); i++) {
			
			historyAccessor.get(i)
			
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
		
		ArrayList<Integer> entry = new ArrayList<>();
		int chatId = historyAccessor.getKeys().size();
		
		entry.add(chatId, roomId, msg);
		
		historyAccessor.add(entry);
		
	}
}
