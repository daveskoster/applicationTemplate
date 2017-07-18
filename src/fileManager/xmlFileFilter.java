package fileManager;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class xmlFileFilter extends FileFilter{
	public boolean accept(File f)
	{
		if (f.isDirectory())
		{ return true; }

		String s = f.getName();

		return s.endsWith(".xml")|| s.endsWith(".XML");
	}

	public String getDescription() 
	{
		return "*.xml,*.XML";
	}
}

