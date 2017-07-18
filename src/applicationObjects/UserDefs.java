package applicationObjects;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

import application.App;
import application.Main;
import xmlParser.XMLDoc;

public class UserDefs {

	/* Local app reference */
	private App app;
	private ArrayList <NameValuePair> users;
	private String uGUID;
	
	//COnstructor.
	public UserDefs(App application)
	{
		app = application;
		debugLog("init user defs - no authentication.");	
	}
	
	public String getNewUUID()
	{
		this.debugLog("Getting new UUID.");
		
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		
		this.debugLog("Got new UUID: " + randomUUIDString);
		
		return randomUUIDString;
	}
	
	public boolean authenticate(String username, String clearPassword)
	{
		if(username == null)
		{ debugLog("Load user: username is null"); return false; }

		//uName = username;

		if(clearPassword == null)
		{ debugLog("No authentication provided."); return false; }
		
		//authKey = clearPassword;
		
		debugLog("loaded user: " + username + ", authenticated.");
		return true;
	}
	
	public String permissionsForObject(String objectGUID)
	{
		//if(objectPermissions == null)
		//{ debugLog("No permissions found - does user: " + uName + " exist?"); return ""; }
		
		return "";
	}
	
	public void readUserFile()
	{
		try {
			DataInputStream dIPs = new DataInputStream(new FileInputStream("application/config/users.bin"));
			debugLog(dIPs.readUTF());
			debugLog(dIPs.readUTF());
//			debugLog(""+dIPs.readLong());
			debugLog(dIPs.readUTF());
		} catch (IOException IOE) 
		{ app.logError("Error read user file: " + IOE.getMessage()); }		
	}
	
	public void writeUserFile()
	{
		try {
			DataOutputStream dOPs = new DataOutputStream(new FileOutputStream("application/config/users.bin"));
			/* Write out stuff */
			dOPs.writeUTF("BQAPP");
			dOPs.writeUTF(Main.CURRENT_VERSION);
//			dOPs.writeLong(1);
			dOPs.writeUTF(getNewUUID());
		} catch (IOException IOE) 
		{ app.logError("Error write user file: " + IOE.getMessage()); }
	}
	
	private String makeAuthKey(String username, String clearPassword)
	{
		MessageDigest md;
		String key;

		key=clearPassword;
		
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] passBytes = key.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<digested.length;i++){
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException ex) {
			app.logError(UserDefs.class.getName() + "::" + ex.getMessage());
		}
		
		return "";
	}
	
	
	private void debugLog(String message)
	{
		app.getDebugLogger().logMessage(message);
	}
}
