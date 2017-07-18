package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import application.App;
import customControls.ExpandableJPanel;
import eventManager.WindowEventDispatcher;
import ui.AppMenu;

public class AppWindow extends JFrame {

	/* Application pointer */
	private App app;
	public WindowEventDispatcher WindowEvents;
	
	/* UI Vars */
	private JPanel contentPanel;
	private StatusBarPanel statusbar;
	private JTabbedPane navigation, editorWindows;
	private JPanel resultWindow;
	private AppMenu mainMenu;
	//private InfigCSVPanel icp;
	//private InfigGridPanel igp;
	//private CSVParsingPropertiesPanel csvParsingProperties;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4324868632485788982L;

	
	public AppWindow(App application, String title, int sizeX, int sizeY)
	{
		super(title);

		app = application;
		this.setSize(1200, 700);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		WindowEvents = new WindowEventDispatcher(application);
		
	}
	
	public void initAppWindow()
	{
        mainMenu = new AppMenu(this, app);
        mainMenu.init();
        this.setJMenuBar(mainMenu);
        
		contentPanel = new JPanel(new GridLayout(1,0));      
		statusbar = new StatusBarPanel(this.getWidth(), 22, 0.33, 0.33, 0.34);
		statusbar.setLHStatusMessageValue("Ready");
		
		this.add(contentPanel);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(statusbar, BorderLayout.SOUTH);
        
        /* Add Left-hand navigation pane elements */
        //main nav-pane
        navigation = new JTabbedPane();
        
        /* Content */
        JTextArea nTTA = new JTextArea("Area for Properties - incomplete");
        JTextArea nTTA1 = new JTextArea("Area for OTHER - incomplete");
        JTextArea nTTA2 = new JTextArea("Area for main window items - incomplete");
        
        /*        
        /* Scroll panes */
        JScrollPane lhScroll1 = new JScrollPane(nTTA);
        JScrollPane lhScroll2 = new JScrollPane(nTTA1);
        JScrollPane rhScroll1 = new JScrollPane(nTTA2);
        
        /* Individual tabs. */
        navigation.addTab("Properties", lhScroll1);
        navigation.addTab("Other", lhScroll2);
        
		editorWindows = new JTabbedPane();
		resultWindow = new JPanel(new GridLayout(1, 0));
		//contentNavigator.addChangeListener(new tabListener(contentNavigator));
		
		editorWindows.addTab("Content", new JTextArea("Content goes here"));
		
		JSplitPane contentSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		contentSplit.setDividerLocation(250);
		contentSplit.setOneTouchExpandable(true);
		contentSplit.setDividerSize(10);
		
		contentSplit.add(navigation);
		contentSplit.add(editorWindows);

		contentPanel.add(contentSplit);
        contentPanel.setVisible(true);
        
        statusbar.setBorder(BorderFactory.createRaisedBevelBorder());
        statusbar.setVisible(true);
	}
	
	
	public StatusBarPanel getStatusBar()
	{ return statusbar; }
	

}
