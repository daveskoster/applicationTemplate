package fileManager;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class csvFileFilter extends FileFilter{
	public boolean accept(File f)
	{
		if (f.isDirectory())
		{ return true; }

		String s = f.getName();

		return s.endsWith(".csv")||s.endsWith(".CSV");
	}

	public String getDescription() 
	{
		return "*.csv,*CSV";
	}
}

