import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatListenerThread extends Thread {
	private ChatFile file;
	private String name;
	private String submitter;
	// private Pattern cleanerPattern = Pattern.compile("[^\\s]\\s[^\\s]");
	private Couch couch;

	public ChatListenerThread(String submitter, String name, ChatFile file, Couch couch) {
		this.file = file;
		this.name = name;
		this.submitter = submitter;
		this.couch = couch;
	}

	@Override
	public void run() {
		boolean keepReading = true;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file.getFileName()), "UnicodeLittle"));

			while (keepReading) {
				if (!reader.ready()) {
					Thread.sleep(1000);
				} else {
					String line = reader.readLine();
					if (line.length() > 1)
						line = line.substring(1);
					System.out.println(line);
					ChatMessage msg = new ChatMessage(submitter, name,
							file.getChatroomName(), line);
					if (msg.getSpeaker() != null)
						couch.post(msg.toJSON());
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
