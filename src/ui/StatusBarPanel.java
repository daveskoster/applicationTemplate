package ui;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class StatusBarPanel extends JPanel {

	/* Split pane definitions */
	private JSplitPane sbLeft, sbRight; //Splits the status bar into 3 regions.
	
	/* Panel definitions */
	private JPanel LHSBPanel, MSBPanel, RHSBPanel;
	
	/* Content components */
	private JLabel LHStatusBarMessage, MStatusBarMessage, RHStatusBarMessage;
	private double Col1Wt, Col2Wt, Col3Wt;
	
	/* Default constructor */
	public StatusBarPanel()
	{ super(); }
	
	/*** 
	 * statusBarPanel
	 * int width - width of the status bar
	 * int height - height of the status bar
	 * int pctCol1 - Percent of width consumed by column 1, in decimal form numbers (33% = 0.33) etc...
	 * int pctCol2 - Percent of width consumed by column 2, in decimal form numbers (33% = 0.33) etc...
	 * int pctCol3 - Percent of width consumed by column 3, in decimal form numbers (33% = 0.33) etc...
	 *
	 * preferred constructor ***/
	public StatusBarPanel(int width, int height, double pctCol1, double pctCol2, double pctCol3)
	{
		super();
		
		double newCol1, newCol2, newCol3;
		
		this.setLayout(new GridLayout(0,1));
		this.setPreferredSize(new Dimension(width, height));
		this.setBounds(0, 0, width, height);
		this.setVisible(true);
		
		Col1Wt = pctCol1;
		Col2Wt = pctCol2;
		Col3Wt = pctCol3;
		
		newCol1 = width * Col1Wt;
		newCol2 = width * Col2Wt;
		newCol3 = width * Col3Wt;
		
		LHStatusBarMessage = new JLabel();
		LHStatusBarMessage.setBounds(1,0,(int) newCol1-1, height-1);
		LHStatusBarMessage.setFont(new Font("Serif", Font.BOLD, 11));

		MStatusBarMessage = new JLabel();
		MStatusBarMessage.setBounds(1,0,(int) newCol2-1, height-1);	
		MStatusBarMessage.setFont(new Font("Serif", Font.BOLD, 11));
		
		RHStatusBarMessage = new JLabel();
		RHStatusBarMessage.setBounds(1,0,(int) newCol3-1, height-1);
		RHStatusBarMessage.setFont(new Font("Serif", Font.BOLD, 11));
		
		sbLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sbLeft.setOneTouchExpandable(false);
		sbLeft.setDividerSize(4);
		sbLeft.setDividerLocation((int) newCol1);

		sbLeft.setLocation(0, 0);
		sbLeft.setVisible(true);
		
		sbRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sbRight.setBorder(null);
		sbRight.setOneTouchExpandable(false);
		sbRight.setDividerSize(4);
		sbRight.setDividerLocation((int) newCol1);
		sbRight.setVisible(true);
		
		/* Initialize the left-hand status bar panel */
		LHSBPanel = new JPanel();
		LHSBPanel.setLayout(null);
		/* Try to force the status bar to be 20px in height */
		LHSBPanel.setPreferredSize(new Dimension((int) newCol1-8,height));
		LHSBPanel.setMaximumSize(new Dimension((int) newCol1-8, height));
		LHSBPanel.setMinimumSize(new Dimension((int) newCol1-8, height));
		LHSBPanel.setVisible(true);
		
		/* Initialize the middle status bar panel */
		MSBPanel = new JPanel();
		MSBPanel.setLayout(null);
		MSBPanel.setPreferredSize(new Dimension((int) newCol2-8,height));
		MSBPanel.setMaximumSize(new Dimension((int) newCol2-8,height));
		MSBPanel.setMinimumSize(new Dimension((int) newCol2-8,height));
		MSBPanel.setVisible(true);
		
		/* Initialize the right-hand status bar panel */		
		RHSBPanel = new JPanel();
		RHSBPanel.setLayout(null);
		/* Try to force the status bar to be 20px in height */
		RHSBPanel.setPreferredSize(new Dimension((int) newCol3-8,height));
		RHSBPanel.setMaximumSize(new Dimension((int) newCol3-8,height));
		RHSBPanel.setMinimumSize(new Dimension((int) newCol3-8,height));
		RHSBPanel.setVisible(true);		
		
		sbLeft.add(LHSBPanel);
		sbRight.add(MSBPanel);
		sbRight.add(RHSBPanel);
		sbLeft.add(sbRight);
		
		LHSBPanel.add(LHStatusBarMessage);
		MSBPanel.add(MStatusBarMessage);
		RHSBPanel.add(RHStatusBarMessage);
		
		this.add(sbLeft);
	}
	
	public void windowResized(int newWidth, int newHeight)
	{
		double newCol1, newCol2, newCol3;
		newCol1 = newWidth * Col1Wt;
		newCol2 = newWidth * Col2Wt;
		newCol3 = newWidth * Col3Wt;
		
		this.setPreferredSize(new Dimension(newWidth, newHeight));
		
		//System.out.println((int) (newCol1));
		this.setPreferredSize(new Dimension(newWidth, newHeight));
		sbLeft.setDividerLocation((int) newCol1);
		sbRight.setDividerLocation((int) (newCol2));
		LHSBPanel.setPreferredSize(new Dimension((int) newCol1-4, newHeight));
		RHSBPanel.setPreferredSize(new Dimension((int) newCol3-4, newHeight));
		MSBPanel.setPreferredSize(new Dimension((int) newCol2-4, newHeight));
		sbRight.setPreferredSize(new Dimension((int) (newCol1+newCol2)-8, newHeight));
		sbLeft.setPreferredSize(new Dimension(newWidth, newHeight));
	}
	
	public String getLHStatusMessageValue()
	{ return LHStatusBarMessage.getText(); }
	
	public String getMStatusMessageValue()
	{ return MStatusBarMessage.getText(); }
	
	public String getRHStatusMessageValue()
	{ return RHStatusBarMessage.getText(); }
	
	public void setLHStatusMessageValue(String message)
	{ LHStatusBarMessage.setText(message);}
	
	public void setMStatusMessageValue(String message)
	{ MStatusBarMessage.setText(message); }
	
	public void setRHStatusMessageValue(String message)
	{ RHStatusBarMessage.setText(message); }
	
}
