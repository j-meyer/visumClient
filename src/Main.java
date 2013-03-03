import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;


public class Main {
	//Grab the file as read only
	//Shove it in to couch
	/**
	 * It's a main method
	 * @param args
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String args[]) throws IOException, ParseException
	{
		String usageString = "main <chatlog directory>";
				
		if(args.length != 1)
		{
			System.out.println(usageString);
			return;
		}
		Path chatPath =  new File(args[0]).toPath();
		new ChatLogHandler(chatPath);
	}
}
