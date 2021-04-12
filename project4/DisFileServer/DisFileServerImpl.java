package DisFileServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import FileSystemApp.DisFileSever;
import FileSystemApp.DisFileSeverHelper;
import FileSystemApp.DisFileSeverPOA;

public class DisFileServerImpl extends DisFileSeverPOA {
	private ORB orb;
	private static DisFileSever disFileserverImpl;

	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	@Override
	public String readFile(String title) {
		System.out.println("Reading " + title);
		String filePath = new File("").getAbsolutePath();
		String fp = filePath.concat("/" + title);
		// lookForFile(title);
		// TODO Auto-generated method stuff
		String data = "";
		try {
			data = new String(Files.readAllBytes(Paths.get(title)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File not here");
			// e.printStackTrace();
		}
		System.out.println("File Found");
		return data;

		// return title;
	}

	/**
	 * Checks all of the servers for locks on the given file. If not it
	 * locks the file and returns true
	 */
	@Override
	public boolean lockForwrite(String title) {
		String[] location = readConfig();
		
		//searching through the servers to see if this file is locked
		for (int i = 0; i < location.length; i++) {
			try {
				String arguments[] = { "-ORBInitialPort", "1054", "-port", "1055", "-ORBInitialHost", location[i] };
				// create and initialize the ORB
				ORB orb = ORB.init(arguments, null);

				// get the root naming context
				org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
				// Use NamingContextExt instead of NamingContext. This is
				// part of the Interoperable naming Service.
				NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

				// resolve the Object Reference in Naming
				String name = "DisFileClient";
				disFileserverImpl = DisFileSeverHelper.narrow(ncRef.resolve_str(name));

				System.out.println("Obtained a handle on server object: " + disFileserverImpl);
				//title = "/home/team07/lsa-project-4/project4/DisFileServer/text.txt";
				
				//get the current locks of the connected server
				String locks = disFileserverImpl.readFile("locks.txt");
				
				//search through the locks for the given file
				if (locks.indexOf(title) == -1) {
					System.out.println("This file is not locked here");
					continue;
				} else {
					return false;
				}
			} catch (Exception e) {
				System.out.println("ERROR : " + e);
				e.printStackTrace(System.out);
			}
		}
		
		//append the lock to the locks.txt file
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("locks.txt", true));
			writer.write(title + "\n");
			writer.close();
			System.out.println("File locked");
		} catch (IOException e) {
			System.out.println("Failed to write to lock file");
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * Writes contents to given file
	 */
	@Override
	public boolean writeToFile(String title, String contents) {
		try {
			Files.newBufferedWriter(Paths.get(title), StandardOpenOption.TRUNCATE_EXISTING);
			FileWriter writer = new FileWriter(title, false);
			System.out.println("Contents: " + contents);
			writer.write(contents);
			writer.close();
			System.out.println("File written");
		} catch (IOException e) {
			System.out.println("Failed to write to file");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Changes the lock file to unlock the given file
	 */
	@Override
	public boolean unlockForWrite(String title) {
		String locks = readFile("locks.txt");
		locks.replaceAll(title, "");
		try {
			FileWriter writer = new FileWriter(title, false);
			writer.write(locks);
			writer.close();
			System.out.println("File unlocked");
		} catch (IOException e) {
			System.out.println("Failed to unlock file");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Shutdown the server
	 */
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub'
		orb.shutdown(false);

	}

	/**
	 * Look through all of the servers in the config to get a file
	 */
	@Override
	public String lookForFile(String title) {
		String data = "";
		String[] location = readConfig();
		System.out.println(location);
		data = readFile(title);
		if (data.isEmpty()) {
			for (int i = 0; i < location.length; i++) {
				try {
					String arguments[] = { "-ORBInitialPort", "1054", "-port", "1055", "-ORBInitialHost", location[i] };
					// create and initialize the ORB
					ORB orb = ORB.init(arguments, null);

					// get the root naming context
					org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
					// Use NamingContextExt instead of NamingContext. This is
					// part of the Interoperable naming Service.
					NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

					// resolve the Object Reference in Naming
					String name = "DisFileClient";
					disFileserverImpl = DisFileSeverHelper.narrow(ncRef.resolve_str(name));

					System.out.println("Obtained a handle on server object: " + disFileserverImpl);
					//title = "/home/team07/lsa-project-4/project4/DisFileServer/text.txt";
					
					data = disFileserverImpl.readFile(title);
					
					if (data.length() != 0) {
						System.out.println("this file contains" + data);
						try {
							BufferedWriter writer = new BufferedWriter(new FileWriter(title));
							writer.write(data);
							writer.close();
							System.out.println("File Received");
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						continue;
					}
					return data;
					// disFileserverImpl.shutdown();

				} catch (Exception e) {
					System.out.println("ERROR : " + e);
					e.printStackTrace(System.out);
				}
			}
		}
		return "Nothing here";
	}

	public String[] readConfig() {
		String filePath = new File("").getAbsolutePath();
		String fp = filePath.concat("/DisFileServer/config.txt");
		List<String> locationsList = new ArrayList<>();
		try {
			Scanner scan = new Scanner(new File(fp));
			while (scan.hasNextLine()) {
				locationsList.add(scan.nextLine());
			}

			String[] locations = new String[locationsList.size()];
			locations = locationsList.toArray(locations);
			//System.out.println(locationsList);
			return locations;
		} catch (FileNotFoundException e) {
			System.out.println("Config file not found");
			e.printStackTrace();
		}

		String[] nothing = new String[0];
		return nothing;
	}

	
	
}