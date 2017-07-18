package configManager;

import javax.swing.table.AbstractTableModel;


public class ConfigEditorTableModel extends AbstractTableModel {

	  /**
	 * 
	 */
	private static final long serialVersionUID = -4692582845897294225L;
	private Config configg;
	  
	  public ConfigEditorTableModel(Config cfg)
	  {
		  super();
		  configg = cfg;
	  }

	  public void addRow()
	  {
		  configg.addElement("", "");
		  this.fireTableDataChanged();
	  }
	    
	  public Class getColumnClass(int c) {
		  return getValueAt(0, c).getClass();
	  }
	  
	  public boolean isCellEditable(int row, int col) {
		if(col < 0)
		{ return false;} else {return true;}
	  }
	  
	  public int getColumnCount() {
		  return 2;
	  }
	    
	  public int getRowCount() {
		  return configg.getSize();
	  }
	  
	  public String getColumnName(int col)
	  {
	    	if(col == 0)
	    		return configg.getPropertyTitle();
	    	if(col == 1)
	    		return configg.getValueTitle();
	    	
	    	return "NULL";
	  }
	  
	  public Object getValueAt(int row, int col)
	  {
	    	if(col == 0)
	    		return configg.getName(row);
	    	if(col == 1)
	    		return configg.getValue(row);
	    
	    	return null;
	  }
	  
	    /*
	     * Don't need to implement this method unless your table's
	     * data can change.
	     */
	    public void setValueAt(Object value, int row, int col) {

	    	if(col == 0)
	    		configg.setName(row, (String) value);
	    	if(col == 1)
	    		configg.setValue(row, value);
	    	
	    	fireTableCellUpdated(row, col);
	    }
	    
	   public void saveConfigFile()
	   {
		   /* Overwrite existing configuration file with the list of properties we've got listed */
		   configg.saveConfigFile();		   
	   }
}
