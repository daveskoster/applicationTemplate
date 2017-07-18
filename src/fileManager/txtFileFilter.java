package fileManager;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class txtFileFilter extends FileFilter{
	public boolean accept(File f)
	{
		if (f.isDirectory())
		{ return false; }

		String s = f.getName();

		return s.endsWith(".txt")||s.endsWith(".TXT");
	}

	public String getDescription() 
	{
		return "*.txt,*.TXT";
	}
}

