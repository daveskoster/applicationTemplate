package eventManager;

import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import fileManager.*;
import application.App;
import applicationObjects.UserDefs;
import configManager.ConfigEditor;
import errorlogger.ErrorLogger;
import fileManager.ApplicationFileChooser;

public class EventProcessor {
	
	private App app;
	
	public EventProcessor(App application)
	{
		app = application;
	}
	
	public void processEvent(String eventToProcess)
	{
		app.getDebugLogger().logMessage("Event: " + eventToProcess);
		
		if(eventToProcess.equals("exit"))
		{
			if (JOptionPane.showConfirmDialog(null, 
		            "Are you sure you want to quit?", "Confirm Exit.", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		            System.exit(0);
			}
		}
		if(eventToProcess.equals("test"))
		{
			UserDefs UD = new UserDefs(app);
			UD.writeUserFile();
			UD.readUserFile();
			JOptionPane.showMessageDialog(null, "Ran test", "Component Tester Message.", JOptionPane.OK_OPTION);
		}
		if(eventToProcess.equals("config"))
		{
			ConfigEditor cfgEdit = new ConfigEditor(app);
		}
		
		/* sample code */
		//if(event.equals("settings"))
		//{ windowLauncher.launchConfigurationEditor(); }

		/*
			if(eventToProcess.startsWith("setscript:"))
		 	{
				application.getScriptManager().changeActiveScript(Integer.parseInt(event.substring(10, event.length())));
		 	}
		 */
	}
	
	
	
	
}
