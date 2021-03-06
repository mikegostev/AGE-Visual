package uk.ac.ebi.age.ui.shared.imprint;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Imprint implements Serializable
{
 private static final long serialVersionUID = 1L;
 
 private List<ObjectImprint>       objects;
 private Map<String, ClassImprint> classes;
 private int totalRecords;

 public List<ObjectImprint> getObjects()
 {
  return objects;
 }

 public void setObjects(List<ObjectImprint> objects)
 {
  this.objects = objects;
 }

 public Map<String, ClassImprint> getClasses()
 {
  return classes;
 }

 public void setClasses(Map<String, ClassImprint> classes)
 {
  this.classes = classes;
 }

 public int getTotalObjects()
 {
  return totalRecords;
 }
 
 public void setTotalObjects(int totalRecords)
 {
  this.totalRecords = totalRecords;
 }
}
