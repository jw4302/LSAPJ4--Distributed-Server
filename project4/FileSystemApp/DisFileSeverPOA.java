package FileSystemApp;


/**
* FileSystemApp/DisFileSeverPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from DisFileAPP.idl
* Monday, December 7, 2020 4:33:59 AM EST
*/

public abstract class DisFileSeverPOA extends org.omg.PortableServer.Servant
 implements FileSystemApp.DisFileSeverOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("readFile", new java.lang.Integer (0));
    _methods.put ("lockForwrite", new java.lang.Integer (1));
    _methods.put ("writeToFile", new java.lang.Integer (2));
    _methods.put ("unlockForWrite", new java.lang.Integer (3));
    _methods.put ("lookForFile", new java.lang.Integer (4));
    _methods.put ("shutdown", new java.lang.Integer (5));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // FileSystemApp/DisFileSever/readFile
       {
         String title = in.read_string ();
         String $result = null;
         $result = this.readFile (title);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // FileSystemApp/DisFileSever/lockForwrite
       {
         String title = in.read_string ();
         boolean $result = false;
         $result = this.lockForwrite (title);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // FileSystemApp/DisFileSever/writeToFile
       {
         String title = in.read_string ();
         String contents = in.read_string ();
         boolean $result = false;
         $result = this.writeToFile (title, contents);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 3:  // FileSystemApp/DisFileSever/unlockForWrite
       {
         String title = in.read_string ();
         boolean $result = false;
         $result = this.unlockForWrite (title);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 4:  // FileSystemApp/DisFileSever/lookForFile
       {
         String title = in.read_string ();
         String $result = null;
         $result = this.lookForFile (title);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // FileSystemApp/DisFileSever/shutdown
       {
         this.shutdown ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:FileSystemApp/DisFileSever:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public DisFileSever _this() 
  {
    return DisFileSeverHelper.narrow(
    super._this_object());
  }

  public DisFileSever _this(org.omg.CORBA.ORB orb) 
  {
    return DisFileSeverHelper.narrow(
    super._this_object(orb));
  }


} // class DisFileSeverPOA
