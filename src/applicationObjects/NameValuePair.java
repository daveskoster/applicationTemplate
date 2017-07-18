package applicationObjects;

public class NameValuePair {

	private String name;
	private String type;
	private Object value;
	
	public NameValuePair(String itemName, Object itemValue)
	{
		name = itemName;
		value = itemValue;
		type = new String("value");
	}
	
	public NameValuePair(String itemName, Object itemValue, String itemType)
	{
		name = itemName;
		value = itemValue;
		type = itemType;
	}
	
	public void setName(String itemName)
	{ name = itemName; }
	
	public void setValue(Object itemValue)
	{ value = itemValue; }
	
	public String getName()
	{ return name; }
		
	public Object getValue()
	{ return value; }
	
	public void setType(String valueType)
	{ type = valueType; }
	
	public String getType()
	{ return type; }
	
}
