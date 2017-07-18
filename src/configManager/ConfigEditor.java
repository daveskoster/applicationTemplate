package configManager;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

import application.App;


public class ConfigEditor extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6312370618885063250L;
	private Config config;
	private DefaultListModel <String> ConfigSelectorModel; 
	private JList<String> optionsList;
	private ConfigEditorTableModel ICETableModel;
	JTable cfgVarsTable;
	JPanel aboutPanel, configVarsPanel;
	JScrollPane aboutScroll, configVarsScroll;
	JButton cfgAddBtn, cfgSaveBtn, cfgCancelBtn;
	private static int EMPTY_RIGHT = 0;
	private static int CONFIG_VARS = 1;
	private static int ABOUT = 2;
	private int lastConfigWindow;
	private App app;
	
	public ConfigEditor(App application)
	{
		
		super(application.getAppWindow(), "Configuration");
		app = application;
		app.getDebugLogger().logMessage("Loading config editor.");
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		/* Listener to wait to close window */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		config = app.getConfiguration();
		setSize(new Dimension(600,600));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setAlwaysOnTop(true);
		
		JPanel left = new JPanel(new GridLayout(1,0));
		left.setBorder(new BevelBorder(BevelBorder.RAISED));
		left.setPreferredSize(new Dimension(150, this.getHeight()));
		
		this.add(left, BorderLayout.WEST);

		/*
		 * Setup for the left hand configuration items selection pane.
		 */
		ConfigSelectorModel = new DefaultListModel<String>();
		
		ConfigSelectorModel.addElement("Application Defaults");
		ConfigSelectorModel.addElement("About");
		
		optionsList = new JList<String>(ConfigSelectorModel);
		optionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		optionsList.setSelectedIndex(0);
		optionsList.addListSelectionListener(new selectConfigOptionListener());
		optionsList.setVisibleRowCount(5);
		optionsList.setPreferredSize(new Dimension(150-this.getInsets().left-this.getInsets().right, 400-this.getInsets().top-this.getInsets().bottom));
        JScrollPane listScrollPane = new JScrollPane(optionsList);
        listScrollPane.setPreferredSize(new Dimension(150-this.getInsets().left-this.getInsets().right, 400-this.getInsets().top-this.getInsets().bottom));
        left.add(listScrollPane);
        
        /*
         * Set up the Right hand config panel options.
         */
		createConfigVarsPanel();
		createAboutPanel();
		
		this.add(configVarsPanel, BorderLayout.CENTER);
		lastConfigWindow = CONFIG_VARS;
		this.setVisible(true);
	}

	public void createConfigVarsPanel()
	{
		configVarsPanel = new JPanel(new BorderLayout());
		configVarsScroll = new JScrollPane();
		ICETableModel = new ConfigEditorTableModel(config);
		
		cfgVarsTable = new JTable(ICETableModel)
		{
			protected JTableHeader createDefaultTableHeader()
			{
				return new JTableHeader(columnModel) {};
			};
		};
		
		JPanel gridAreaPanel = new JPanel(new GridLayout(1,0));
		JPanel buttonAreaPanel = new JPanel(new GridLayout(2,0));
		buttonAreaPanel.setPreferredSize(new Dimension(getWidth(), 50));
		
		initAddButton();
		initSaveButton();
		initCancelButton();
		buttonAreaPanel.add(cfgAddBtn);
		buttonAreaPanel.add(cfgSaveBtn);
		buttonAreaPanel.add(new JLabel(""));
		buttonAreaPanel.add(cfgCancelBtn);
		
		configVarsScroll.setViewportView(cfgVarsTable);
		//configVarsScroll.setPreferredSize(new Dimension(400,600));
		//configVarsScroll.setBounds(0, 0, 400, 600);
		gridAreaPanel.add(configVarsScroll);

		configVarsPanel.add(buttonAreaPanel, BorderLayout.SOUTH);
		configVarsPanel.add(gridAreaPanel, BorderLayout.CENTER);
	}
	
	public void createAboutPanel()
	{
		aboutPanel = new JPanel(new GridLayout(1,0));

		String aboutText = new String();
		aboutText = "Application Template 1.0 \n Created by D. Koster, Copyright (c) 2017-2018. All Rights Reserved \n";
		aboutText = aboutText + "\n\n This template may be used and distributed non-commercially. Commercial use or adaptation\n";
		aboutText = aboutText + "must be licensed.";
		JTextArea JT = new JTextArea(aboutText);
		JT.setPreferredSize(new Dimension(1000, 800));
		JT.setBounds(160, 1, 1000, 800);
		JT.setWrapStyleWord(true);
		JT.setLineWrap(true);
	
		aboutScroll = new JScrollPane(JT);
		aboutScroll.setPreferredSize(new Dimension(getWidth(), getHeight()));
		aboutScroll.setBounds(160, 1, 1000, 800);

		aboutPanel.add(aboutScroll);
		aboutPanel.setVisible(true);
	}
	
	private void removeLast()
	{
		if(lastConfigWindow == CONFIG_VARS)
		{
			this.remove(configVarsPanel);
		}
		if(lastConfigWindow == ABOUT)
		{
			this.remove(aboutPanel);
		}
	}
	
	private void initSaveButton()
	{
		ActionListener saveClicked = new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{  
				app.getDebugLogger().logMessage("Saving configuration.");
				ICETableModel.saveConfigFile();
				dispose();
			}
		};
		
		cfgSaveBtn = new JButton("Save");
		cfgSaveBtn.addActionListener(saveClicked);
	}
	
	private void initAddButton()
	{
		ActionListener addClicked = new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{  
				app.getDebugLogger().logMessage("Adding configuration item.");
				ICETableModel.addRow();
			}
		};
		
		cfgAddBtn = new JButton ("Add row");
		cfgAddBtn.addActionListener(addClicked);
	}
	
	private void initCancelButton()
	{
		ActionListener cancelClicked = new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{  
				app.getDebugLogger().logMessage("Cancel config editor.");
				dispose();
			}
		};
		
		cfgCancelBtn = new JButton("Cancel");
		cfgCancelBtn.addActionListener(cancelClicked);
	}
	
	
	class selectConfigOptionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			if(!e.getValueIsAdjusting())
			{
			app.getDebugLogger().logMessage("Changing configuration context to: " + optionsList.getSelectedValue() );
			if(optionsList.getSelectedValue().equals("Application Defaults"))
			{
				removeLast();
				add(configVarsPanel, BorderLayout.CENTER);
				lastConfigWindow = CONFIG_VARS;
			}
			if(optionsList.getSelectedValue().equals("About"))
			{
				removeLast();
				add(aboutPanel, BorderLayout.CENTER);
				lastConfigWindow = ABOUT;
			}
			
			repaint();
			validate();
			}

		}
		
	}

}
