package application;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import configManager.Config;
import errorlogger.ErrorLogger;
import eventManager.ActionDispatcher;
import eventManager.DebugLogger;
import eventManager.EventProcessor;
import ui.AppWindow;

public class App {

	private AppWindow aWin;
	private DebugLogger debugLogger;
	private Map<String, ActionDispatcher> ActionEvents;
	private EventProcessor eventProcessor;
	private Config configuration;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2363595518469271338L;

	/* Constructor */
	public App(String title)
	{
		aWin = new AppWindow(this, title, 1200, 700);
	}
	
	public void launchApplication()
	{
		aWin.setVisible(true);
		aWin.repaint();
	}
	
	/* Called from splash screen */
	public void loadConfig()
	{
		configuration = new Config(Main.CONFIG_PATH + "config.xml", Main.CURRENT_VERSION);
		
		if(configuration.getValueByName("DEBUG_FLAG").equals("TERM"))
		{ 
			debugLogger.setDebuggerState(DebugLogger.LOG_TERM);
			debugLogger.logMessage("DEBUG TERMINAL MODE ON - Messages will be sent to the terminal.");
		} else if (configuration.getValueByName("DEBUG_FLAG").equals("FILE"))
		{
			debugLogger.setDebuggerState(DebugLogger.LOG_FILE);
			debugLogger.logMessage("DEBUG FILE LOGGER MODE ON - Messages will be sent to this log file.");
		} else if (configuration.getValueByName("DEBUG_FLAG").equals("NOTIFY"))
		{
			debugLogger.setDebuggerState(DebugLogger.LOG_NOTIFY);
		}
		else {
			debugLogger.setDebuggerState(DebugLogger.LOG_OFF);
		}
	}
	
	public void initUI()
	{
		aWin.initAppWindow();
	}
	
	public void initEventProcessor()
	{
		ActionEvents = new HashMap<String, ActionDispatcher>();
		debugLogger = new DebugLogger();
		debugLogger.clear();
		eventProcessor = new EventProcessor(this);
		
		aWin.addWindowListener(aWin.WindowEvents);
		ActionEvents.put("exit", new ActionDispatcher(this, "exit"));
	}
	
	public EventProcessor getEventProcessor()
	{ return eventProcessor; }
	
	public void logError(String message)
	{
		 ErrorLogger EL = new ErrorLogger();
		 EL.logError(message);
	}
	
	public AppWindow getAppWindow()
	{ return aWin; }
	
	public Config getConfiguration()
	{ return configuration; }
	
	public DebugLogger getDebugLogger()
	{ return debugLogger; }
	
	public ActionListener getActionEventByName(String EventName)
	{
		return ActionEvents.get(EventName);
	}
	
	public ActionListener addActionEvent(String EventName)
	{
		ActionEvents.put(EventName, new ActionDispatcher(this, EventName));
		return getActionEventByName(EventName);
	}
}
