package applicationObjects;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


public class DataGridTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6698910227984601220L;
	private DataGrid dg;
	
	public DataGridTableModel(DataGrid datagrid)
	{
		super();
		dg = datagrid;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		//System.out.println("rows: " + dg.getRowCount());
		return dg.getRowCount()-1;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		//System.out.println("Cols: " + dg.getColCount());
		return dg.getColCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return dg.getItem(rowIndex+1, columnIndex+1);
	}
	
	 // public Class getColumnClass(int c) {
	//	  return getValueAt(0, c).getClass();
	 // }
	
    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {

    	dg.setItem((String) value, row+1, col+1);
    	fireTableCellUpdated(row, col);
    }
    
    public void initHeader(ArrayList <String> header)
    {
    	dg.initGrid(header);
		this.fireTableChanged(null);
    }
    
    public void addRow(ArrayList <String> row)
	{
    	dg.appendRow(row);
		this.fireTableDataChanged();
	}
    
	public String getColumnName(int col)
	{
		return dg.getItem(1, col+1);
	}

	public boolean isCellEditable(int row, int col) 
	{
		if(col < 0)
		{ return false;} else {return true;}
	}
	
}
