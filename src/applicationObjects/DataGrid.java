package applicationObjects;

import java.util.ArrayList;

import errorlogger.ErrorLogger;

/* 
 * The data itself will simply live as a string in an arraylist. The 
 *    column/row configuration will 
 */

public class DataGrid {

	private ArrayList <String> data;
	private int colCount;
	
	public DataGrid()
	{
		data = new ArrayList<String>();
		colCount = 0;
	}
	
	public void initGrid(ArrayList <String> header)
	{
		colCount = header.size();
		
		for(int ii=0;ii<colCount;ii++)
		{
			data.add(header.get(ii));
		}
	}
	
	public int getColCount()
	{ return colCount; }
	
	public int getRowCount()
	{ 
		if(colCount > 0) 
			return data.size() / colCount;
		else
			return 0;
	}
	
	public void rebuildGrid()
	{
		//TODO: Rebuild model & Panel etc... (Not sure it goes here).
	}
	
	public void addColumn(String colTitle)
	{
		/* Add colTitle note: colCount = size() so proper indexing is 0-based. */
		data.add(colCount, colTitle);
		
		for(int ii=colCount+colCount+1;ii<data.size();ii+=colCount)
		{
			data.add(colCount, "");
		}
		
		/* Append the last one - note We're always appending nothing when adding columns.*/
		data.add("");
	}
	
	/*
	 * You have to append a full length row, otherwise it doesn't work.
	 */
	public void appendRow(ArrayList <String>row)
	{		
		if(row.size() != colCount)
		{
			ErrorLogger EL = new ErrorLogger();
			EL.logError("Add row to data grid fails - length mis-match error.");
		} else {
			for(int ii=0;ii<row.size();ii++)
			{	
				data.add(row.get(ii));
			}
		}
	}
	
	public String getItem(int row, int col)
	{
		if(col*row > data.size())
		{
			ErrorLogger EL = new ErrorLogger();
			EL.logError("Attmpt to retrive grid item at invalid index.");
			
			return "";
		}
		
		return data.get(((row-1)*colCount)+col-1);
	}
	
	public void setItem(String value, int row, int col)
	{
		if(col*row > data.size())
		{
			ErrorLogger EL = new ErrorLogger();
			EL.logError("Attmpt to set grid item at invalid index.");
		} else {
			data.set(((row-1)*colCount)+col-1,value);
		}
		
	}
	
}
