package fileManager;

import javax.swing.JFileChooser;

public class ApplicationFileChooser extends JFileChooser {
	
	public static int SCRIPT = 1;
	public static int PROJECT = 2;
	public static int DATA = 3;
	public static int CSV = 4;
	public static int LOOKUP = 5;
	public static int MODULE = 6;
	
	public static int OPEN = 1;
	public static int SAVE = 2;

	private int fileChooserFileType;
	private String fileChooserType = new String();
	private String dialogTitle = new String();
	
	public ApplicationFileChooser()
	{ super(); }

	/*** 
	 * @param infigFileType infigFileChooser.SCRIPT | .PROJECT | .DATA | .CSV | .LOOKUP | .MODULE
	 * @param chooserType infigFileChooser.OPEN | infigFileChooser.SAVE
	 */
	public ApplicationFileChooser(int infigFileType, int chooserType)
	{
		setChooserFileType(infigFileType, chooserType);
	}

	/*** 
	 * @param infigFileType infigFileChooser.SCRIPT | .PROJECT | .DATA | .CSV | .LOOKUP | .MODULE
	 * @param chooserType infigFileChooser.OPEN | infigFileChooser.SAVE
	 */
	public void setChooserFileType(int infigFileType, int chooserType)
	{
		fileChooserFileType = infigFileType;
		
		setAcceptAllFileFilterUsed(false);
		
		if(infigFileType == SCRIPT)
		{
			//Not implemented
		} else if (infigFileType == PROJECT)
		{
			//Not implemented
		} else if (infigFileType == DATA)
		{
			//Not implemented
		} 
		if (infigFileType == CSV)
		{
			addChoosableFileFilter(new csvFileFilter());
			addChoosableFileFilter(new txtFileFilter());
			dialogTitle = ".csv CSV file";
		} else if (infigFileType == LOOKUP)
		{
			//Not implemented
		} else if (infigFileType == MODULE)
		{
			//Not implemented
		}

		if(chooserType == OPEN)
		{ 
			fileChooserType = "Open";
			setApproveButtonText("Open"); 
		}
		else if(chooserType == SAVE)
		{ 
			fileChooserType = "Save";
			setApproveButtonText("Save"); 
		}
			
		setDialogTitle(fileChooserType + " " + dialogTitle);
		setAcceptAllFileFilterUsed(true);		
	}
	
	public void setToOpenChooser()
	{
		fileChooserType = "Open";
		setApproveButtonText("Open");
		setDialogTitle(fileChooserType + " " + dialogTitle);
	}
	
	public void setToSaveChooser()
	{
		fileChooserType = "Save";
		setApproveButtonText("Save");
		setDialogTitle(fileChooserType + " " + dialogTitle);		
	}
	
	
}
