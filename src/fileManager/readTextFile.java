package fileManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import errorlogger.ErrorLogger;

public class readTextFile {

	/* Default constructor - does nothing */
	public readTextFile()
	{}
	
	public String readFile(File file)
	{
		FileReader iStream;
		StringBuilder sb = new StringBuilder();
		int cc;
		
		try {
			iStream = new FileReader(file);
			
			while((cc = iStream.read()) != -1)
			{
				sb.append((char) cc);
			}
			
		} catch (IOException ioe)
		{
			ErrorLogger EL = new ErrorLogger();
			EL.logError("File read error: [" + file.toString() + " ] [" + ioe.getMessage() + "\n");
		}
		
		return sb.toString();
	}
	
	
}
