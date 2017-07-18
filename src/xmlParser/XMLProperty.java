package xmlParser;

public class XMLProperty {
	   String name;
	   String value;

	   public XMLProperty(String nm, String val) {
		name = new String(nm);
		value = new String(val);
	   }

	   public String getName() {
		return name;
	   }

	   public String getValue() {
	   	return value;
	   }
}
