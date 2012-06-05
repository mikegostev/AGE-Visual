package uk.ac.ebi.age.ui.shared.imprint;

public class ObjectId
{
 private String clusterId;
 private String moduleId;
 private String objectId;
 
 public ObjectId()
 {}

 public ObjectId(String clusterId, String moduleId, String objectId)
 {
  super();
  this.clusterId = clusterId;
  this.moduleId = moduleId;
  this.objectId = objectId;
 }

 public String getClusterId()
 {
  return clusterId;
 }

 public void setClusterId(String clusterId)
 {
  this.clusterId = clusterId;
 }

 public String getModuleId()
 {
  return moduleId;
 }

 public void setModuleId(String moduleId)
 {
  this.moduleId = moduleId;
 }

 public String getObjectId()
 {
  return objectId;
 }

 public void setObjectId(String objectId)
 {
  this.objectId = objectId;
 }
 
 public String toString()
 {
  return clusterId+":"+moduleId+":"+objectId;
 }
}
