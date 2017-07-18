package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingWorker;

import application.App;
import application.Main;
import errorlogger.ErrorLogger;

public class Splash extends JWindow {
	
	JLabel copyright;
	JLabel loadStatus;
	App main;

	public Splash(App IA)
	{
		copyright = new JLabel("Copyright (c) 2017/2018. Dave S. Koster. See details in help.");
		loadStatus = new JLabel("Initializing...");
		main = IA;
	}
	
	public void launchLoad()
	{
		 JPanel content = (JPanel) getContentPane();
		    content.setBackground(Color.white);
		    
		    // Set the window's bounds, centering the window
		    int width = 450;
		    int height = 300;
		    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		    int x = (screen.width-width)/2;
		    int y = (screen.height-height)/2;
		    setBounds(x,y,width,height);

		    // Build the splash screen
		    JLabel label = new JLabel(new ImageIcon(Main.ICON_PATH + "splashLogo.gif"));
		    copyright.setFont(new Font("Sans-Serif", Font.BOLD, 12));
		    content.add(label, BorderLayout.CENTER);
		    content.add(copyright, BorderLayout.SOUTH);
		    content.add(loadStatus, BorderLayout.NORTH);
		    
		    //Color infColSchm = new Color(30, 125, 30,  255);
		    //content.setBorder(BorderFactory.createLineBorder(infColSchm, 10));

		    // Display it
		    setVisible(true);
		    this.setAlwaysOnTop(true);
		    this.requestFocus();

		   // setVisible(false);
		    
		    try { Thread.sleep(1000); } catch (Exception e) {}

		    loadApp LA = new loadApp(main, this);
		    LA.execute();
	}
	
	/* Use swing worker to pause while we load things */
	class loadApp extends SwingWorker<Void, Void>
	{
		 App app;
		 Splash splashFrame;
		
		 public loadApp(App aa, Splash me)
		 { app = aa; splashFrame = me;}
		 
		 protected Void doInBackground() //throws Exception
		 {
			 ErrorLogger EL = new ErrorLogger();

			loadStatus.setText("Initializing Event Processor");
			splashFrame.repaint();
			try {app.initEventProcessor();} catch (Exception e) {EL.logError("Init Event Processor", e);}
				
			loadStatus.setText("Loading config");
			splashFrame.repaint();
			try {app.loadConfig();} catch (Exception e) {EL.logError("Load Config", e);}
			
			loadStatus.setText("Initializing UI");
			splashFrame.repaint();
			try {app.initUI();} catch (Exception e) {EL.logError("Init UI", e);}

//			loadStatus.setText("Initalizing processor");			
//			splashFrame.repaint();
//			try {m.initProcessor();} catch (Exception e) {EL.logError(e.getMessage());}
			
			return null;
		 }
		 
		 protected void done()
		 {
		    try
		    { 
		    	dispose();
		    } 
		    catch (Exception e) { 
		    	ErrorLogger EL = new ErrorLogger();
		    	EL.logError(e.getMessage());
		    }
		 }
		 
	}
}
