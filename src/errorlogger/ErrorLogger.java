package errorlogger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

import javax.swing.JOptionPane;

/* 
 * Infiguratum ALWAYS writes to a log file called errorLog.txt in the execution folder.
 * it only keeps the most recent session's errors.
 * 
 */

public class ErrorLogger {

	
	public void ErrorLogger()
	{ }
	
	public void clear()
	{
		FileWriter oStream;
		File file = new File("application/logs/errorLog.txt");
		
		try {
			oStream = new FileWriter(file);	
			oStream.write("Log date:" + logDate() + "\n");
			oStream.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"Failure to validate Error Log - if you're receiving this message, you may have a file permissions problem.", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public void logError(String error_message)
	{
		FileWriter oStream;
		File file = new File("application/logs/errorLog.txt");
		
		try {
			oStream = new FileWriter(file, true);	
			oStream.write("[" + logDate() + "] (" + error_message + ")\n");
			oStream.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"Failure to write to error log.", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public void logError(String location, Exception err)
	{
		logError("Error in: " + location);
		logError(err.toString());
		logError(err.getMessage());
		logError(err.getLocalizedMessage());
	}
	
	protected String logDate()
	{
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		Date dateobj = new Date();
		
		return df.format(dateobj);
	}
	
}
