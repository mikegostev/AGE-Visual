package uk.ac.ebi.age.ui.shared.imprint;

import java.io.Serializable;

public class ClassImprint implements Serializable
{

 private static final long serialVersionUID = 1L;
 
 private String id;
 private String  name;
 private boolean custom;
 private ClassType type;

 public ClassImprint()
 {}
 
 public ClassImprint(String name, boolean custom)
 {
  super();
  this.name = name;
  this.custom = custom;
 }

 public String getName()
 {
  return name;
 }

 public void setName(String name)
 {
  this.name = name;
 }

 public boolean isCustom()
 {
  return custom;
 }

 public void setCustom(boolean custom)
 {
  this.custom = custom;
 }

 public void setType(ClassType type)
 {
  this.type = type;
 }

 public ClassType getType()
 {
  return type;
 }

 public String getId()
 {
  return id;
 }

 public void setId(String id)
 {
  this.id = id;
 }
 
}
