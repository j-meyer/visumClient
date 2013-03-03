import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.gson.JsonObject;

public class ChatMessage {
	private String speaker;
	private Date timestamp;
	private String message;
	private String channel;
	private String submitter;
	//Alliance or corp name
	private String name;

	// private Pattern username;

	public ChatMessage(String submitter, String name, String channel, String line) {
		// [ 2012.12.02 04:41:07 ] User Name > awesome, thanks
		try {
			this.submitter = submitter;
			this.name = name;
			this.channel = channel;
			this.timestamp = parseDate(line);
			this.speaker = parseUsername(line);
			this.message = parseMessage(line);
		} catch (StringIndexOutOfBoundsException err) {
			System.err.println(err);
		} catch (ParseException err) {
			System.err.println(err);
		}

	}

	private Date parseDate(String line) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy.MM.dd HH:mm:ssZ");
		String date = line.substring(2, 21).trim();
		// So the formatter correctly lists it as GMT
		date += "-0000";
		return formatter.parse(date);
	}

	private String parseUsername(String line) {
		String result = "";
		for (int i = 0; i < line.length(); ++i) {
			if (line.charAt(i) == '>') {
				result = line.substring(23, i).trim();
				break;
			}
		}
		return result;
	}

	private String parseMessage(String line) {
		String result = "";
		for (int i = 0; i < line.length(); ++i) {
			if (line.charAt(i) == '>') {
				result = line.substring(i + 1).trim();
				break;
			}
		}
		return result;
	}

	public JsonObject toJSON() {

		JsonObject response = new JsonObject();

		try

		{
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ssZ");

			response.addProperty("speaker", speaker);
			response.addProperty("timestamp", formatter.format(timestamp));
			response.addProperty("message", message);
			response.addProperty("channel", channel);
			response.addProperty("submitter", submitter);
			response.addProperty("name", name);
			response.addProperty("type", "chatMessage");
			String id = timestamp.getTime() + "_" + submitter + "_" + channel + "_" + name + "_chatMessage";
			response.addProperty("_id", id);
		} catch (NullPointerException err) {
			System.err.println(err);
		}
		return response;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
