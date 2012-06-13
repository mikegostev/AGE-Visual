package uk.ac.ebi.age.ui.shared.imprint;

import java.io.Serializable;

public class FileValue extends StringValue implements Serializable
{

 private static final long serialVersionUID = 1L;
 
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
