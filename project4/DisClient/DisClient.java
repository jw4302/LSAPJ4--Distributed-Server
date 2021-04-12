package DisClient;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

import DisFileServer.DisFileServerImpl;
import DisFileServer.DisFileServerServer;

import java.io.File;

import org.omg.CORBA.*;

import FileSystemApp.DisFileClient;
import FileSystemApp.DisFileClientHelper;
import FileSystemApp.DisFileSever;
import FileSystemApp.DisFileSever.*;
import FileSystemApp.DisFileSeverHelper;

public class DisClient {
	private static DisFileSever disFileserverImpl;
	static String title;
	
	public DisClient(String[] args) {
		runClient(args);
		System.out.println("Client running");
	}
	
	public static void main(String args[]) {
		runClient(args);
		readFile("text.txt");
		System.out.println("Client running");
	}
	
	/**
	 * Starts the Client
	 * @param args
	 */
	public static void runClient(String[] args) {
		try {
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is
			// part of the Interoperable naming Service.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// resolve the Object Reference in Naming
			String name = "DisFileClient";
			disFileserverImpl = DisFileSeverHelper.narrow(ncRef.resolve_str(name));

			System.out.println("Obtained a handle on server object: " + disFileserverImpl);
			//title = "text.txt";
			// title = "";
			//System.out.println(disFileserverImpl.lookForFile(title));
			// disFileserverImpl.shutdown();

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}
	
	/**
	 * Looks for a file within the file system
	 * @param title
	 * @return
	 */
	public static String readFile(String title) {
		return disFileserverImpl.lookForFile(title);
	}
	
	/**
	 * Obtains a lock on the given file
	 * @param title
	 * @return true if successful
	 */
	public static boolean lockFile(String title) {
		if (!disFileserverImpl.lockForwrite(title)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Writes the contents then unlocks the file
	 * @param title
	 * @param contents
	 * @return true if successful
	 */
	public static boolean writeFile(String title, String contents) {
		if(!disFileserverImpl.writeToFile(title, contents)) {
			return false;
		}
		if(!disFileserverImpl.unlockForWrite(title)) {
			return false;
		}
		return true;
	}
}
