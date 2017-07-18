package customControls;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import application.App;
import eventManager.MouseEventDispatcher;

public class ExpandableJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 379427087736889449L;
	private JLabel label, expander;
	private JPanel labelAreaPanel;
	private JScrollPane controlsAreaScroll;
	private App app;
	private boolean exp;
	private Component comp;
	
	public ExpandableJPanel(App application, String panelLabel, Component C, boolean expanded)
	{
		super(new BorderLayout());
		label = new JLabel(panelLabel);
		app = application;
		exp = expanded;
		comp = C;
		expander = new JLabel();
		expander.addMouseListener(new expandClick(this));
		
		label.setFont(new Font("Calibri", Font.BOLD, 12));
		expander.setFont(new Font("Calibri", Font.BOLD, 12));
		expander.setAlignmentX(RIGHT_ALIGNMENT);
		
		if(expanded) { expander.setText("Ʌ"); }
		else {expander.setText("V"); }
		
		controlsAreaScroll = new JScrollPane();
		//labelAreaPanel = new JPanel(new GridLayout(1,2));
		labelAreaPanel = new JPanel(new BorderLayout());
		labelAreaPanel.add(label, BorderLayout.WEST);
		labelAreaPanel.add(expander, BorderLayout.EAST);
			
		controlsAreaScroll.setViewportView(comp);
		
		this.add(labelAreaPanel, BorderLayout.NORTH);
		if(expanded)
		{
			this.add(controlsAreaScroll, BorderLayout.CENTER);
			this.setMaximumSize(new Dimension(230, (int)labelAreaPanel.getPreferredSize().getHeight()+(int)comp.getPreferredSize().getHeight()));
		} else
		{
			this.setMaximumSize(new Dimension(230, (int)labelAreaPanel.getPreferredSize().getHeight()));			
		}
		
	}
	
	private class expandClick extends MouseAdapter
	{
		private ExpandableJPanel parent;
		
		public expandClick(ExpandableJPanel panel)
		{
			parent = panel;
		}
		
		public void mouseClicked(MouseEvent me)
		{
			if(exp)
			{
				parent.remove(controlsAreaScroll);
				app.getDebugLogger().logMessage("Set panel " + label.getText() + " visible false");
				exp=false;
				expander.setText("V");
				parent.setMaximumSize(new Dimension(230, (int)labelAreaPanel.getPreferredSize().getHeight()));
			}
			else
			{
				parent.add(controlsAreaScroll);
				app.getDebugLogger().logMessage("Set panel " + label.getText() + " visible true");
				exp=true;

				parent.setMaximumSize(new Dimension(230, (int)labelAreaPanel.getPreferredSize().getHeight()+(int)comp.getPreferredSize().getHeight()+15));
				parent.setPreferredSize(new Dimension(230, (int)labelAreaPanel.getPreferredSize().getHeight()+(int)comp.getPreferredSize().getHeight()+15));
				expander.setText("Ʌ");
				app.getDebugLogger().logMessage("Expandable panel preferred height: " + ((int)labelAreaPanel.getPreferredSize().getHeight()+(int)comp.getPreferredSize().getHeight()));
			}
			
			parent.repaint();
			app.getAppWindow().repaint();
		}
	}
	
	
}
