package application;

import application.App;
import errorlogger.ErrorLogger;
import ui.Splash;

public class Main {

	public static String CURRENT_VERSION = "1.0";
	public static String ICON_PATH = "application/icons/";
	public static String CONFIG_PATH = "application/config/";
	public static App IA;
	public static Splash splash;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* Clearing error log */
		ErrorLogger EL = new ErrorLogger();
		EL.clear();
		
		/* Instantiate main application class */
		IA = new App("Infiguratum v1.0");
		
		/* Instantiate splash screen while we load */
		splash = new Splash(IA);
		
		/* launch splash screen */
		splash.launchLoad();
				
		/* launch application now */
		IA.launchApplication();

	}

}
