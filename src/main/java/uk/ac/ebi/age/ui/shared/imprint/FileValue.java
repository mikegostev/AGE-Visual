package uk.ac.ebi.age.ui.shared.imprint;

public class FileValue extends StringValue
{
 private boolean global;

 public FileValue(String fileId, boolean resolvedGlobal)
 {
  super(fileId);
  
  global=resolvedGlobal;
 }

 public boolean isGlobal()
 {
  return global;
 }

 public void setGlobal(boolean global)
 {
  this.global = global;
 }
}
