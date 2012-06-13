package uk.ac.ebi.age.ui.shared.imprint;

import java.io.Serializable;

public class ObjectValue extends Value implements ObjectImprintReference, Serializable
{

 private static final long serialVersionUID = 1L;
 
 private ObjectId id;
 private ObjectImprint object;

 public ObjectValue()
 {}
 
 public ObjectValue(ObjectId tgtId)
 {
  id = tgtId;
 }

 @Override
 public String getStringValue()
 {
  return id.toString();
 }

 @Override
 public ObjectImprint getObjectImprint()
 {
  return object;
 }

 @Override
 public void setObjectImprint(ObjectImprint obj)
 {
  object = obj;
 }

 @Override
 public ObjectId getTargetObjectId()
 {
  return id;
 }

}
