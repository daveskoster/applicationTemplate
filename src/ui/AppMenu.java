package ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import application.App;

//import application.InfiguratumApp.MenuItemListener;

public class AppMenu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4720833765771400017L;
	//private JMenuBar main;
	private AppWindow appWin;
	private App app;
	//private JMenuBar mainMenu;
	
	public AppMenu(AppWindow appWindow, App application)
	{
		appWin = appWindow;
		app = application;
	}
	
	public JMenuBar init()
	{
		app.getDebugLogger().logMessage("Begin Init Menu Bar");
		JMenu appMenu, optionsMenu, helpMenu, dataMenu;
		
		//JMenu appMenu, fileMenu, helpMenu;
		//MenuItemListener miListener = new MenuItemListener();
		
		//Application
		//  +- Repository
		//Options
		//  - Config
		//Help
		//  - About
		
		appMenu = new JMenu("Application");
		optionsMenu = new JMenu("Options");
		dataMenu = new JMenu("Data");
		helpMenu = new JMenu("Help");
		
		dataMenu.setName("data");
		appMenu.setName("application");
		helpMenu.setName("help");

		/* Add Application choices  */
		JMenuItem item;
		
		item = new JMenuItem("Exit");
		item.setActionCommand("exit");
		item.addActionListener(app.getActionEventByName("exit"));
		appMenu.add(item);
		
		/* Add items to options menu */
		item = new JMenuItem("Configuration");
		item.setActionCommand("config");
		item.addActionListener(app.addActionEvent("config"));
		optionsMenu.add(item);
								
		item = new JMenuItem("About");
		item.setActionCommand("about");
		item.addActionListener(app.addActionEvent("about"));
		helpMenu.add(item);
		
		this.add(appMenu);
		this.add(dataMenu);
		this.add(optionsMenu);
		this.add(helpMenu);
		
		this.setVisible(true);
		
		return this;
	}
		
}
