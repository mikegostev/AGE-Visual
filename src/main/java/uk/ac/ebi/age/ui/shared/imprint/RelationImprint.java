package uk.ac.ebi.age.ui.shared.imprint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelationImprint implements ObjectImprintReference, AttributedImprint, Serializable
{

 private static final long serialVersionUID = 1L;
 
 private ClassImprint classImprint;
 
 private ObjectId id;
 private ObjectImprint object;

 private List<AttributeImprint> attrs;

 
 @Override
 public List<AttributeImprint> getAttributes()
 {
  return attrs;
 }

 public void addAttribute( AttributeImprint v )
 {
  if( attrs == null )
   attrs = new ArrayList<AttributeImprint>();
  
  attrs.add(v);
 }
 
 public void setAttributes( List<AttributeImprint> vs )
 {
  if( ! (vs instanceof ArrayList) )
   attrs = new ArrayList<AttributeImprint>(vs);
  else
   attrs=vs;
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
 
 public ClassImprint getClassImprint()
 {
  return classImprint;
 }

 public void setClassImprint(ClassImprint classImprint)
 {
  this.classImprint = classImprint;
 }

 public void setTargetObjectId(ObjectId tgtId)
 {
  id = tgtId;
 }
}
