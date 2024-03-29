package FileSystemApp;


/**
* FileSystemApp/DisFileSeverOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from DisFileAPP.idl
* Monday, December 7, 2020 4:33:59 AM EST
*/

public interface DisFileSeverOperations 
{
  String readFile (String title);
  boolean lockForwrite (String title);
  boolean writeToFile (String title, String contents);
  boolean unlockForWrite (String title);
  String lookForFile (String title);
  void shutdown ();
} // interface DisFileSeverOperations
