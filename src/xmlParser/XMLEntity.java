package xmlParser;

import java.util.Vector;

public class XMLEntity {
	String tag, data;
	  Vector<XMLProperty> properties;
	  XMLEntity firstChild=null, nextSibling=null, parent=null;

	  public XMLEntity() {
		    tag = new String();
		    data = new String();
		    properties = new Vector<XMLProperty>();		  
	  }
	  
	  public XMLEntity(String tg) { 
	    tag = new String(tg);
	    data = new String();
	    properties = new Vector<XMLProperty>();
	  }

	  public void addProperty(String pName, String pValue) {
		 properties.add(new XMLProperty(pName, pValue));
	  }

	  public void setTag(String tg)
	  { this.tag=tg; }
	  
	  public void setData(String sd)
	  { this.data = sd; }
	  
	  public String getData()
	  { return this.data; }
	  
	  public String getTag() 
	  { return this.tag; }

	  public int getPropertyCount() {
	  	return properties.size();
	  }

	  public String getPropertyName(int ii) {
		return properties.elementAt(ii).getName();
	  }

	  public String getPropertyValue(int ii) {
	  	return properties.elementAt(ii).getValue();
	  }

	  public int propertyIndex(String propertyName)
	  {
		  for(int ii=0; ii< this.getPropertyCount(); ii++)
		  {
			  if(this.getPropertyName(ii).equals(propertyName))
			  { return ii; }
		  }
		  return -1;
	  }
	  
	  public String getPropertyValue(String propertyName) {
		for(int ii = 0; ii < this.getPropertyCount(); ii++)
	        {
		  if(getPropertyName(ii).equals(propertyName)) {
			return getPropertyValue(ii);
		  }
		}
		return null;
	  }
}
