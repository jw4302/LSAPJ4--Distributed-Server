package DisFileServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;

import FileSystemApp.DisFileSever;
import FileSystemApp.DisFileSeverHelper;
import FileSystemApp.DisFileSeverPOA;

import org.omg.PortableServer.*;

public class DisFileServerServer{

	public static void main(String args[]) {
		String dumb[]= {"-ORBInitialPort ","1054", "-port",  "1055"};
		
		try {
			ORB orb = ORB.init(args, null);
			POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootPOA.the_POAManager().activate();
			
			DisFileServerImpl dfsi = new DisFileServerImpl();
			dfsi.setORB(orb);
			
			org.omg.CORBA.Object ref = rootPOA.servant_to_reference(dfsi);
			DisFileSever href = DisFileSeverHelper.narrow(ref);
			
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			
			String name = "DisFileClient";
			NameComponent path [] = ncRef.to_name(name);
			ncRef.rebind(path, href);
			
			System.out.println("Server ready");
			
			orb.run();
		}
		catch (Exception e) {
			System.err.println("Dis File     Error" + e);
			e.printStackTrace(System.out);
		}
	}
}

