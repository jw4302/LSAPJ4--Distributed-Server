package FileSystemApp;


/**
* FileSystemApp/DisFileClientHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from DisFileAPP.idl
* Monday, December 7, 2020 4:33:59 AM EST
*/

abstract public class DisFileClientHelper
{
  private static String  _id = "IDL:FileSystemApp/DisFileClient:1.0";

  public static void insert (org.omg.CORBA.Any a, FileSystemApp.DisFileClient that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static FileSystemApp.DisFileClient extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (FileSystemApp.DisFileClientHelper.id (), "DisFileClient");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static FileSystemApp.DisFileClient read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_DisFileClientStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, FileSystemApp.DisFileClient value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static FileSystemApp.DisFileClient narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof FileSystemApp.DisFileClient)
      return (FileSystemApp.DisFileClient)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      FileSystemApp._DisFileClientStub stub = new FileSystemApp._DisFileClientStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static FileSystemApp.DisFileClient unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof FileSystemApp.DisFileClient)
      return (FileSystemApp.DisFileClient)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      FileSystemApp._DisFileClientStub stub = new FileSystemApp._DisFileClientStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
