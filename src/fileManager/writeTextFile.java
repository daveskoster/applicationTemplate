package fileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import errorlogger.ErrorLogger;

/* 
 * Compartmentalize file writing, with the exception of configuration and error logging.
 *   These are done separately.
 * 
 */

public class writeTextFile {
	
	public void writeTextFile()
	{ }
	
	public void write(File file, String content)
	{
		FileWriter oStream;
		ErrorLogger EL = new ErrorLogger();
		
		try {
			oStream = new FileWriter(file, false); //make sure we overwrite the contents.	
			oStream.write(content);
			oStream.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"Failure to write to file.", JOptionPane.ERROR_MESSAGE);
			EL.logError("Unable to write file(" + file.toString() + ")", e);
			//System.exit(0);
		}
	}
	
}
