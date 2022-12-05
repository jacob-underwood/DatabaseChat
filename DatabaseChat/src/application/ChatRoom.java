package application;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * User view upon entering a chat room. Allows user to send a chat. Checks for
 * and executes help commands. Updates other user activity to chat view.
 */
public class ChatRoom {

	User user;
	String roomName;
	int roomId;
	DatabaseAccessor authenticationAccessor;
	DatabaseAccessor roomInfoAccessor;
	DatabaseAccessor historyAccessor;
	DatabaseAccessor usersAccessor;
	Scanner input;

	boolean leaving = false;
	int lastChatIndex;
	int currentChatIndex;

	/**
	 * @param user User object that dictates who is using the application.
	 * @param roomName Name of the chat room.
	 * @param roomId Id for this chat room.
	 * @param roomInfoAccessor Accessor to chat_room table.
	 * @param historyAccessor Accessor to chat_history table.
	 * @param usersAccessor Accessor to chat_users table.
	 * @param input Shared scanner between classes.
	 */
	public ChatRoom(User user, String roomName, int roomId, DatabaseAccessor authenticationAccessor, DatabaseAccessor roomInfoAccessor,
			DatabaseAccessor historyAccessor, DatabaseAccessor usersAccessor, Scanner input) {

		this.user = user;
		this.roomName = roomName;
		this.roomId = roomId;
		this.authenticationAccessor = authenticationAccessor;
		this.roomInfoAccessor = roomInfoAccessor;
		this.historyAccessor = historyAccessor;
		this.usersAccessor = usersAccessor;

		this.input = input;

		if (historyAccessor.getKeys().size() == 0) {

			this.lastChatIndex = 0;
			this.currentChatIndex = 0;

		} else {

			this.lastChatIndex = historyAccessor.getKeys().size() - 1;
			this.currentChatIndex = historyAccessor.getKeys().size() - 1;

		}

	}

	/**
	 * Takes a command marked by a "/" and executes appropriate method.
	 * 
	 * @param cmd The command inputed.
	 */
	private void parseCommand(String command) {

		String cmd = command.substring(1);
		if (cmd.compareTo("list") == 0)
			list();
		else if (cmd.compareTo("leave") == 0)
			leave();
		else if (cmd.compareTo("history") == 0)
			history();
		else if (cmd.compareTo("help") == 0)
			help();
		else
			System.out.println("\nInvalid command! Type /help to see available commands.\n");
		System.out.println();

	}

	/**
	 * Prints list of users currently in chat room.
	 */
	private void list() {
		
		int counter = 0;
		System.out.println("\nUsers in chatroom " + roomName + " : \n");

		for (int userId : usersAccessor.getKeys()) {
			
			if (usersAccessor.get(userId).get(1).equals(roomId)) {
				
				counter++;
				System.out.println(counter  + ". " + authenticationAccessor.get(userId).get(1));

			}
		}

	}

	/**
	 * User exits chat room into MainView.
	 */
	private void leave() {

		System.out.println("\nLeaving chatroom.\n");

		leaving = true;
		usersAccessor.delete(user.getId());

	}

	/**
	 * @return Boolean that updates whether or not the room should be exited.
	 */
	public boolean leaving() {

		return leaving;

	}

	/**
	 * Prints all past messages for current chat room.
	 */
	private void history() {

		System.out.println("\nDisplaying history for chatroom " + roomName + "\n");

		boolean historyExists = false;

		for (int i = 0; i < historyAccessor.getKeys().size(); i++) {

			if (historyAccessor.get(i).get(1).equals(roomId)) {

				System.out.println(historyAccessor.get(i).get(2));
				historyExists = true;

			}

		}

		if (!historyExists) {

			System.out.println("\nNo history exists for this room.\n");

		}

	}

	/**
	 * Prints list of available help commands.
	 */
	private void help() {

		System.out.println();
		System.out.println("Available commands :\n");
		System.out.println("/list - Prints the list of users currently in this chat room.");
		System.out.println("/leave - Exit the chat room.");
		System.out.println("/history - Print all past messages for this room.");
		System.out.println("/help for help.");
		System.out.println();
	}

	/**
	 * Sends a chat into the chat room.
	 * 
	 * @param msg The user's message in the chat room.
	 */
	public void sendChat(String msg) {

		if (msg.length() > 0) {
			if (msg.substring(0, 1).equals("/"))
				parseCommand(msg.trim());
		}

		lastChatIndex = currentChatIndex;
		currentChatIndex = historyAccessor.getKeys().size();

		update(msg);
	}

	/**
	 * Updates chat history view for other user activity.
	 * @param msg Text entered by user, to be entered into the chat_history table.
	 */
	public void update(String msg) {
		ArrayList<Object> entry = new ArrayList<>();
		int chatId = historyAccessor.getKeys().size();

		entry.add(chatId);
		entry.add(roomId);
		entry.add(user.getUsername() + ": " + msg);

		historyAccessor.add(entry);

	}

	/**
	 * @return Index of the last chat the user sent.
	 */
	public int lastChatIndex() {

		return lastChatIndex;

	}
	
	public int getRoomId() {
		
		return roomId;
		
	}
}
