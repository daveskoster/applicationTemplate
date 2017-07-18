package xmlParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JOptionPane;

public class XMLDoc {
	
	  XMLEntity current, root;
	  XMLEntity index;
	  int docStatus;
	  
	  //private int lastNextIndexStatus=0;
	  private int lastNextIndexNMovedUp=0;
	  public static final int ERROR=-1;
	  public static final int PARENT_SIBLING=4;
	  public static final int SIBLING=3;
	  public static final int CHILD=2;
	  public static final int EOF=0;
	  public static final int CHECK_STATUS=1;
	  
	  public static final int FILE_LOADED=5;
	  public static final int FILE_LOAD_FAIL=6;
	  public static final int XML_LOADED=7;
	  public static final int XML_NO_DATA=8;

	  public XMLDoc(String firstTag) {
	    root = new XMLEntity(firstTag);
	    current = root;
	    index = root;
	    docStatus = XML_LOADED;
	  }
	  
	  public XMLDoc()
	  {
		  root=null;
		  current=null;
		  index=null;
		  docStatus = XML_NO_DATA;
	  }

	  public void initXMLDoc(String firstTag)
	  {
		  if(root == null)
		  {
			  root=new XMLEntity(firstTag);
			  current=root;
			  index=root;
			  docStatus = XML_LOADED;
		  }
	  }
	  
	  public void initXMLDoc(XMLEntity xe)
	  {
		  root=xe;
		  current=xe;
		  index=xe;
		  docStatus = XML_LOADED;
	  }
	  
	  public XMLEntity getRootNode()
	  { return root; }
	  
	  /* insert child from index -- always adds to the end of the list */
	  public void insertChild(XMLEntity e)
	  {
		  /* if no children, just add it */
		  XMLEntity temp;
		  temp = index;
		  
		  if(index.firstChild == null)
		  { e.parent = index; }
		  else
		  { index = index.firstChild; insertSibling(e); }
		  index = temp;
	  }
	  
	  public void insertSibling(XMLEntity e)
	  {
		  XMLEntity temp = index;
		  
		  while (index.nextSibling != null)
		  { index = index.nextSibling; }
		  
		  index.nextSibling = e;
		  index.nextSibling.parent = index.parent;
		  
		  index = temp;
	  }
	  
	  private FileReader readEntity(FileReader iStream)
	  {
//		  boolean openTag=false;
		  boolean onTag=false;
		  boolean onTermTag=false;
		  boolean lastTagClose=false;
		  boolean createAsChild=false;
		  boolean tagCreated=false;
		  boolean onData=false;
		  boolean onProperty=false, onPropVal=false;
		  boolean singleTag=false;
		  
		  String cTag = new String();
		  String cData = new String();
		  String cProperty = new String();
		  String cPropVal = new String();
		  
		  XMLEntity xe=null;
		  
		  //System.out.println("**********--");
		  
		  int cc;
		  
		  try {
			  while((cc = iStream.read()) != -1)
			  {
				  if((char)cc=='<')
				  {
					  if(onData)
					  {
						  if(xe != null)
						  {
							  xe.setData(cData);
						  }
					  }
					  //System.out.println("OPEN TAG");
					  onTag=true;
					  //openTag=true;
					  onTermTag=false;
					  tagCreated=false;
					  if(lastTagClose) {
						  //System.out.println("Will be Sibling");
						  createAsChild=false;
					  }
					  else {
						  //System.out.println("Will be Child");
						  createAsChild=true;
					  }
					  
					  onData=false;
					  cData="";
				  }
				  
				  if((char)cc=='/')
				  {
					 //System.out.print(" - term tag -");
					  if(cTag.length() > 0)
					  { tagCreated = false; singleTag=true; }
					  else {tagCreated=true; } /*if tag comes after term, then it is a term tag and the required node is already inserted*/
					  /* Don't set onTag=false until later */
					  onTermTag=true;
					  onProperty=false;
				  }
				  
				  if(onTag)
				  {
					  if((char)cc=='>' || (char)cc==' ' || (char)cc=='/')
					  {
						  if(!tagCreated)
						  {
							  //System.out.println("***************************");
							  xe = new XMLEntity(cTag);
							  if(createAsChild)
							  { //System.out.println("Create " + cTag + " as child in memory!"); 
								  this.addChild(xe);
							  }
							  else
							  { 
								  //System.out.println("Create " + cTag + " as sibling in memory!"); 
								  this.addSibling(xe);
							  }
							  //System.out.println("***************************");							  
							  /* If a tag is created, then we are not going to go back up a level yet. */
							  lastTagClose = false;
							  if((char)cc=='/')
							  {
								  lastTagClose=true;
							  }
							  tagCreated=true;
						  }
						  
						  onTag=false;
						  cTag="";
						  
						  if((char)cc=='>')
						  {
							  if(!onTermTag)
							  {
								  //System.out.println("Set data on");
								  onData=true;
							  }
						  }
						  if((char)cc==' ')
						  {
							  onProperty=true;
							  //System.out.println("On Property now");
						  }
					  } else {
						  if((char)cc != '<')
						  {
							  cTag+=(char)cc;
						  }
					  }
				  }
				  
				  if((char)cc=='>')
				  {
					  //System.out.println(" - close tag");
					  //openTag=false;
					  
					  if(onTermTag)
					  {   
						  if(lastTagClose && !singleTag)
						  {
							  if(current.parent != null)
							  {
								  current=current.parent;
							  }
							  //System.out.println("Go back to parent.");
						  }
						  singleTag=false;
						  lastTagClose=true;
						  onTermTag=false;
						  onData=false;
					  }
					  else 
					  {lastTagClose=false; onData=true;}
					  
					  onTermTag=false;
					  onTag=false;
					  onProperty=false;
					  cTag="";
				  }
				  
				  if(onData && (char)cc!='<' && (char)cc!='>')
				  {
					  cData+=(char)cc;
				  }
				  
				  if(onProperty)
				  {
					  //System.out.println("Property Name");
					  if((char)cc=='=')
					  {
						  onProperty=false;
						  onPropVal=true;
					  } else {
						  if((char)cc!=' ') 
						  {
							  cProperty += (char)cc;							  
						  }
					  }
				  } else {
					  if(onPropVal) {
						  //System.out.println("Property Value");
						  if(cPropVal.length()>0 && (char)cc=='"')
						  {
							  onPropVal = false;
							  onProperty = true;
							  xe.addProperty(cProperty, cPropVal);
							  //System.out.println("adding property: " + cProperty + ", " + cPropVal);
							  cProperty="";
							  cPropVal="";
						  } else {
							  if((char)cc != '"')
							  {
								  cPropVal += (char)cc;
							  }
						  }
					  }
				  }
			  } /* End of while loop */
		  } catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), 
							"File Read Error (01)", JOptionPane.ERROR_MESSAGE);
				docStatus = XMLDoc.FILE_LOAD_FAIL;
		  }
		  
		  if(docStatus != XMLDoc.FILE_LOAD_FAIL) docStatus = XMLDoc.FILE_LOADED;
		  return iStream;
	  }
	  
	  public void loadXML(File file) {
		  int cc;
		  FileReader iStream;
		  String xmlHeader = new String();	  
		  // Assume success until failure
		  docStatus = XMLDoc.FILE_LOADED;
		  
		  try {
				iStream = new FileReader(file);
				cc=iStream.read();
				
				do {
					xmlHeader+=(char)cc;
				} while((cc = iStream.read()) != '>');
				xmlHeader+=(char)cc;
				
				//System.out.println(xmlHeader);
				
				//readEntity(iStream);
				readEntity(iStream);
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), 
						"File Read Error (02)", JOptionPane.ERROR_MESSAGE);
				docStatus = XMLDoc.FILE_LOAD_FAIL;
			}
	  }
	  
	  public void loadXML(String fname)
	  {
		  loadXML(new File(fname));
	  }

	  public void addSibling(String tg) {
	    current.nextSibling = new XMLEntity(tg);
	    current.nextSibling.parent = current.parent;
	    current = current.nextSibling;
	  }

	  public void addChild(String tg) {
	    current.firstChild = new XMLEntity(tg);
	    current.firstChild.parent = current;
	    current = current.firstChild;
	  }

	  public void addSibling(XMLEntity xe)
	  {
		  if(root == null) {
			  root=xe;
			  current=xe;
			  index=xe;
		  } else {
			  current.nextSibling = xe;
			  current.nextSibling.parent = current.parent;
			  current = current.nextSibling;			  
		  }
	  }
	  
	  public void addChild(XMLEntity xe)
	  {
		  if(root == null)
		  {
			  root=xe;
			  current=xe;
			  index=xe;
		  } else {
			  current.firstChild = xe;
			  current.firstChild.parent = current;
			  current = current.firstChild;
		  }
	  }
	  
	  public void addPropertyToCurrent(String pName, String pValue)
	  { current.addProperty(pName, pValue); }
	  
	  public String getIndexProperty(int ii)
	  { return index.getPropertyName(ii); }

	  public String getIndexPropertyValue(int ii)
	  { return index.getPropertyValue(ii); }

	  public String getIndexPropertyValue(String propName)
	  {
		  return index.getPropertyValue(propName);
	  }
	  
	  public int getIndexPropertyIndex(String propName)
	  {
		  return index.propertyIndex(propName);
	  }
	  
	  public int getIndexPropertyCount()
	  { return index.getPropertyCount(); }

	  public void setIndexRoot()
	  { index = root; }

	  public String getIndexTag()
	  { return index.getTag(); }
	  
	  public String getIndexData()
	  { return index.getData(); }
	  
	  public void setIndexData(String data)
	  { index.setData(data); }
	  
	  public void setCurrentData(String data)
	  { current.setData(data); }

	  public void setIndexToParent()
	  { current = current.parent; lastNextIndexNMovedUp = 1; }
	  //public int getLastNextIndexStatus()
	  //{ return lastNextIndexStatus; }
	  
	  public int getNIndexParentsTraversed()
	  { return lastNextIndexNMovedUp; }
	  
	  public int nextIndexEntity()
	  {
		lastNextIndexNMovedUp=0;
		if(index.firstChild == null)
	    {
			//System.out.println("no child found.");
		  if(index.nextSibling == null)
	      {
			  //System.out.println("no sibling found");
			/* start stepping through parents, until we find one with a sibling or find root */
			XMLEntity tvs=index;
			do{
				/* if no parent, return EOF -- commented out in previous versions, not sure why.*/
				if(tvs.parent==null) { return XMLDoc.EOF; }
				lastNextIndexNMovedUp++;
				tvs=tvs.parent;
				if(tvs.nextSibling != null)
				{ 
					index = tvs.nextSibling;
					//System.out.println("parent sibling found");
					return XMLDoc.PARENT_SIBLING;
				}
				//System.out.println("couldn't find parent sibling");
			} while(tvs.parent!=null);
			return XMLDoc.EOF;
			/* This means we hit null, so that means we're at the root, so return EOF */
	       } else {
	        	index = index.nextSibling;
		    	return XMLDoc.SIBLING;
	      }
		} else {
		  index = index.firstChild;
		  return XMLDoc.CHILD;
		}
		//return XMLDoc.ERROR;
	  }	
	
	  public void writeXMLDoc(File file)
	  {
		  FileWriter oStream;
		  Vector<String> closeTagsStack = new Vector<String>();
		  int closeTagsIndex = -1;
		  int entityRelation;
		  
		  try
		  {
			  oStream = new FileWriter(file);
			  oStream.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			  
			  this.setIndexRoot();
			  do {
				  
				  oStream.write("<" + this.getIndexTag());
				  closeTagsIndex++;
				  closeTagsStack.add("</" + this.getIndexTag() + ">\n");
				  
				  for(int ii=0; ii<this.getIndexPropertyCount(); ii++)
				  {
					  oStream.write(" " + this.getIndexProperty(ii) + "=" + "\"" + this.getIndexPropertyValue(ii) + "\"");
				  }
				  oStream.write(">");
				  if(!this.getIndexData().isEmpty()) 
				  {oStream.write(this.getIndexData());} else {oStream.write("\n");}
				  
				  entityRelation = this.nextIndexEntity();
				  
				  if(entityRelation == XMLDoc.SIBLING || entityRelation == XMLDoc.PARENT_SIBLING)
				  {
					  for(int jj=getNIndexParentsTraversed();jj>=0;jj--)
					  {
						  oStream.write(closeTagsStack.elementAt(closeTagsIndex));
						  closeTagsStack.removeElementAt(closeTagsIndex);
						  closeTagsIndex--;						  
					  }
				  }
				  
			  } while(entityRelation != XMLDoc.EOF) ;
			  
			  for(int ii=closeTagsIndex; ii > -1; ii--)
			  {
				  oStream.write(closeTagsStack.elementAt(closeTagsIndex) + "\n");
				  closeTagsStack.removeElementAt(ii);
				  closeTagsIndex--;			  
			  }
			  /* Pop everything left on the stack off */  
			  oStream.close();
			  
		  } catch  (IOException ioe) {
				JOptionPane.showMessageDialog(null, ioe.getMessage(), "File write error (03)", JOptionPane.ERROR_MESSAGE);
		  }
	  }
	  
	  public void writeXMLDoc(String fname)
	  { writeXMLDoc(new File(fname)); }
	  
	  public String indentN(int indentCount, int indentWidth)
	  {
		  String indent=new String();
		  
		  for(int ii=0;ii<indentCount;ii++)
			  for(int jj=0; jj < indentWidth; jj++)
				  indent += " ";
		  
		  return indent;
	  }
	  
	  public String renderXMLToText()
	  {
		  return renderXMLToText(2);
	  }
	  
	  public String renderXMLToText(int indent)
	  {
		  Vector<String> closeTagsStack = new Vector<String>();
		  int closeTagsIndex = -1;
		  int entityRelation;
		  String output=new String();
		  
		  this.setIndexRoot();
		  do {
			  
			  closeTagsIndex++;			
			  closeTagsStack.add("</" + this.getIndexTag() + ">\n");
			  output += indentN(closeTagsIndex, indent);
			  
			  output += "<" + this.getIndexTag();  
			  
			  /* Print properties - no need for indentation or anything here */
			  for(int ii=0; ii<this.getIndexPropertyCount(); ii++)
			  {
				  output += " " + this.getIndexProperty(ii) + "=" + "\"" + this.getIndexPropertyValue(ii) + "\"";
			  }
			  output += ">";
			  
			  /* If the tag has data spit it out - don't kick it to a new line; if there's no data, then kick to a new line. */
			  if(!this.getIndexData().isEmpty()) 
			  {output += this.getIndexData();} 
			  else {output += "\n";}
			  
			  /* Tells me where we're going - down, next, up, whatever */
			  entityRelation = this.nextIndexEntity();
			  if(entityRelation == XMLDoc.SIBLING || entityRelation == XMLDoc.PARENT_SIBLING)
			  {
				  /* If we're going up or going right, work out how many parents we've traversed and use that to 
				   * Un-do the indentation */
				  for(int jj=getNIndexParentsTraversed();jj>=0;jj--)
				  {
					  output += indentN(closeTagsIndex, indent) + closeTagsStack.elementAt(closeTagsIndex);
					  closeTagsStack.removeElementAt(closeTagsIndex);
					  closeTagsIndex--;						  
				  }
			  }
			  
		  } while(entityRelation != XMLDoc.EOF);
		  
		  for(int ii=closeTagsIndex; ii >= 0; ii--)
		  {
			  output += indentN(closeTagsIndex, indent) + closeTagsStack.elementAt(closeTagsIndex) + "\n";
			  closeTagsStack.removeElementAt(ii);
			  closeTagsIndex--;			  
		  }
		  
		  return output;  
	  }
	  
	 public int getLoadStatus()
	 { return docStatus; }
	  
}
