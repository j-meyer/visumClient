import java.io.File;
import java.util.Date;

public class ChatFile {
	private Date created;
	private String chatroomName;
	private File fileName;
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getChatroomName() {
		return chatroomName;
	}
	public void setChatroomName(String chatroomName) {
		this.chatroomName = chatroomName;
	}
	public File getFileName() {
		return fileName;
	}
	public void setFileName(File fileName) {
		this.fileName = fileName;
	}
	
	ChatFile(Date created, String chatroomName, File fileName)
	{
		this.created = created;
		this.chatroomName = chatroomName;
		this.fileName = fileName;
	}
}
