package eventManager;

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

public class DebugLogger {
	
	private int logType;
	public static int LOG_OFF = 1;
	public static int LOG_TERM = 2;
	public static int LOG_FILE = 3;
	public static int LOG_NOTIFY = 4;
	
	public void DebugLogger()
	{ }
	
	public void setDebuggerState(int setState)
	{ logType = setState; }
	
	public int getDebuggerState()
	{ return logType; }
	
	public void clear()
	{
		FileWriter oStream;
		File file = new File("application/logs/debuggerLog.txt");
		
		try {
			oStream = new FileWriter(file);	
			oStream.write("Log date:" + logDate() + "\n");
			oStream.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"Failure to validate Debug Log - if you're receiving this message, you may have a file permissions problem.", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public void logMessage(String debug_message)
	{
		if(logType == DebugLogger.LOG_FILE)
		{
			FileWriter oStream;
			File file = new File("application/logs/debuggerLog.txt");
		
			try {
				oStream = new FileWriter(file, true);	
				oStream.write("[" + logDate() + "] (" + debug_message + ")\n");
				oStream.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), 
					"Failure to write to debugger log.", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} else if(logType == DebugLogger.LOG_TERM)
		{
			System.out.println(debug_message);
		} else if(logType == DebugLogger.LOG_NOTIFY)
		{
			///JOptionPane.showMessageDialog(null, e.getMessage(), String );
			JOptionPane.showMessageDialog(null, debug_message, "Debug Logger Notify", JOptionPane.OK_OPTION);
		}
	}
	
	protected String logDate()
	{
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		Date dateobj = new Date();
		
		return df.format(dateobj);
	}
	
}
