package customControls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class ToggleSlider extends JPanel {

	private boolean isSelected;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1021596561939219942L;

	public ToggleSlider(boolean selected)
	{
		isSelected = selected;
		this.setPreferredSize(new Dimension(60, 20));
		this.addMouseListener(new toggleClick(this));
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
	    // draw the rectangle here

		//g.drawRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT)
		if(!isSelected)
		{
			g.setColor(Color.RED);
			g.fillRect(5, 5, 20, 10);
			g.setColor(Color.BLACK);
			g.drawRect(5, 5, 20, 10);
			g.drawRect(6,  2,  10,  16);
			g.setColor(Color.WHITE);
			g.fillRect(6, 2, 10, 16);
		} else {
			g.setColor(Color.GREEN);
			g.fillRect(5, 5, 20, 10);
			g.setColor(Color.BLACK);
			g.drawRect(5, 5, 20, 10);
			g.drawRect(15,  2,  10,  16);
			g.setColor(Color.WHITE);
			g.fillRect(15, 2, 10, 16);
		}
	 }
	
	public boolean getSelectedState()
	{ return isSelected; }
	
	public void setSelectedState(boolean selected)
	{ isSelected = selected; }
	
	public void toggleSelectedState()
	{ if(isSelected) isSelected=false; else isSelected=true; }
	
	private class toggleClick extends MouseAdapter
	{
		ToggleSlider pp;
		
		public toggleClick(ToggleSlider parent)
		{
			pp = parent;
		}
		
		public void mouseClicked(MouseEvent me)
		{
			if(isSelected)
			{ 
				isSelected = false;
			}
			else
			{
				isSelected = true;
			}
			
			pp.repaint();
		}
	}
}
