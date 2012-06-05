package uk.ac.ebi.age.ui.shared.imprint;

public class ObjectValue extends Value implements ObjectImprintReference
{
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
