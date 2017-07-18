package configManager;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import errorlogger.ErrorLogger;
import applicationObjects.NameValuePair;
import xmlParser.XMLDoc;

public class Config {
	
	private ArrayList <NameValuePair> configVars;
	//private	XMLDoc configDat = new XMLDoc(); 
	private String configFileName;
	private String currentVersion;
	
	public Config(String configFile, String applicationVersion)
	{
		configFileName = configFile;
		configVars = new ArrayList<NameValuePair>();
		currentVersion = applicationVersion;
		XMLDoc config = new XMLDoc();
		
		config.loadXML(new File(configFileName));
		config.setIndexRoot();
		
		/* Load configuration variables */
		if(config.getLoadStatus() == XMLDoc.FILE_LOADED)
		{
		    config.setIndexRoot();
			if(config.getIndexTag().equals("config"))
			{
				int indx = config.getIndexPropertyIndex("ver");
				if(config.getIndexPropertyValue(indx).equals(currentVersion))
				{
					/* Pre-load headers */
					configVars.add(new NameValuePair("Property", "Value"));
					while(config.nextIndexEntity() != XMLDoc.EOF){
						if(config.getIndexTag().equals("property"))
						{	
							indx = config.getIndexPropertyIndex("name");
							configVars.add(new NameValuePair(config.getIndexPropertyValue(indx), config.getIndexData()));
						}
					}
				}	
			}
		} else
		{
			ErrorLogger EL = new ErrorLogger();
			EL.logError("Failed to load config file - may not exist");
		}
	}
	
	public void saveConfigFile()
	{
		XMLDoc config = new XMLDoc();

		config = new XMLDoc("config");
		config.addPropertyToCurrent("ver", "1.0");
		
		for(int ii=1; ii < configVars.size(); ii++)
		{
			if(ii==1)
			{
				config.addChild("property");
				config.addPropertyToCurrent("name", configVars.get(ii).getName());
				config.setCurrentData(configVars.get(ii).getValue().toString());
			} else
			{
			   config.addSibling("property");
			   config.addPropertyToCurrent("name", configVars.get(ii).getName());
			   config.setCurrentData(configVars.get(ii).getValue().toString());			   
		   }
		}
		   
		config.writeXMLDoc(new File(configFileName));
		
		JOptionPane.showMessageDialog(null, "Changes will take effect the next time the application is open", "Message", JOptionPane.OK_OPTION);
	}
	
//	public String get
	public int getSize()
	{
		return configVars.size()-1;
	}
	
	public String getPropertyTitle()
	{
		return configVars.get(0).getName();
	}
	public String getValueTitle()
	{
		return configVars.get(0).getValue().toString();
	}
	
	/*
	 * 0 - Header
	 * 1 - Row 0
	 * 2 - Row 1
	 */
	
	public String getName(int index)
	{
		return configVars.get(index+1).getName();
	}
	
	public String getValue(int index)
	{
		return (String) configVars.get(index+1).getValue().toString();
	}
	
	public void setName(int index, String name)
	{
		configVars.get(index+1).setName(name);
	}
	
	public void setValue(int index, Object value)
	{
		configVars.get(index+1).setValue(value);
	}
	
	public void removeElement(int index)
	{
		configVars.remove(index+1);
	}
	
	public void addElement(String name, Object value)
	{
		configVars.add(new NameValuePair(name, value));
	}
	
	public Object getValueByName(String name)
	{
		for(int ii=0; ii < configVars.size(); ii++)
		{
			if(configVars.get(ii).getName().equals(name))
			{ return configVars.get(ii).getValue(); }
		}
		
		ErrorLogger EL = new ErrorLogger();
		EL.logError("Error - missing configuration property ["+ name +"]\n");
		
		return null;
	}
}
