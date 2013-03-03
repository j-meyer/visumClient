import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

public class ChatLogHandler {
	private HashMap<String, ChatFile> files;
	// Number of characters after chatroom name
	private int trail = 20;
	private int extension = 4;
	// 20121201_022548
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyyMMdd_kkmmss");
	private String user;
	private final String propertyFile = "settings.properties";

	// Any chat rooms we actually want to listen to
	private Vector<String> interestingChatrooms;

	public ChatLogHandler(Path directory) throws IOException, ParseException {
		files = new HashMap<String, ChatFile>();
		String url;
		String database;
		Integer port;

		// Read in settings from file
		Properties properties = new Properties();
		
		properties.load(ChatLogHandler.class.getResourceAsStream(propertyFile));

		url = properties.getProperty("couchURL");
		database = properties.getProperty("couchDatabase");
		port = Integer.parseInt(properties.getProperty("couchPort"));
		user = properties.getProperty("user");
		String[]  rooms = properties.getProperty("interestingRooms").split(",");

		interestingChatrooms = new Vector<String>();
		for (int i = 0; i < rooms.length; ++i) {
			interestingChatrooms.add(rooms[i]);
		}
		//TODO: Handling the day switching over?
		// For each file in the directory
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			for (Path entry : stream) {
				// Need to rip out //20121201_025333
				// to determine unique chat rooms
				// time/date is when chat room was entered
				File file = new File(entry.toString());
				int filenamelength = entry.getFileName().toString().length();
				String chatRoomName = entry.getFileName().toString()
						.substring(0, filenamelength - trail);
				try {
					String dateString = entry
							.getFileName()
							.toString()
							.substring(filenamelength - trail + 1,
									filenamelength - extension);
					Date date = dateFormatter.parse(dateString);

					// System.out.println(chatRoomName);
					// System.out.println(date);
					if (!files.containsKey(chatRoomName)
							|| !files.get(chatRoomName).getCreated().after(date)) {
						files.put(chatRoomName, new ChatFile(date,
								chatRoomName, file));
					}
				} catch (ParseException err) {
					System.err.println(err);
				}
			}
		}
		for (String chatRoom : files.keySet()) {
			//TODO: Handle making corp/alliance chatroom names
			if (interestingChatrooms.contains(chatRoom)) {
				String name = "";
				
				System.out.println(chatRoom);
				System.out.println(files.get(chatRoom).getCreated());
				ChatListenerThread thread = new ChatListenerThread(
						user, name, files.get(chatRoom), new Couch(
								url, port, database));
				thread.start();
			}
		}
	}
}
