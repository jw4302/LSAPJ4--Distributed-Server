package FileSystemApp;


/**
* FileSystemApp/DisFileClientOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from DisFileAPP.idl
* Monday, December 7, 2020 4:33:59 AM EST
*/

public interface DisFileClientOperations 
{
  String readFile (String title);
  boolean lockForwrite (String title);
  void shutdown ();
} // interface DisFileClientOperations
