package uk.ac.ebi.age.ui.shared.imprint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjectImprint implements AttributedImprint, Serializable
{

 private static final long serialVersionUID = 1L;
 
 private Scope scope;
 
 private ClassImprint classImprint;
 private ObjectId     id;
 private List<AttributeImprint> attrs = new ArrayList<AttributeImprint>();
 private List<RelationImprint> rels;

 public ObjectImprint()
 {
 }
 
 public ClassImprint getClassImprint()
 {
  return classImprint;
 }

 public void setClassImprint(ClassImprint classImprint)
 {
  this.classImprint = classImprint;
 }

 public ObjectId getId()
 {
  return id;
 }

 public void setId(ObjectId id)
 {
  this.id = id;
 }

 @Override
 public List<AttributeImprint> getAttributes()
 {
  return attrs;
 }

 @Override
 public void addAttribute( AttributeImprint v )
 {
  attrs.add(v);
 }
 
 @Override
 public void setAttributes( List<AttributeImprint> vs )
 {
  
  if( ! (vs instanceof ArrayList) )
   attrs = new ArrayList<AttributeImprint>(vs);
  else
   attrs=vs;
 }

 public List<RelationImprint> getRelations()
 {
  return rels;
 }

 public void addRelation( RelationImprint r )
 {
  if( rels == null )
   rels = new ArrayList<RelationImprint>();
  
  rels.add(r);
 }
 
 public void setRelations( List<RelationImprint> vs )
 {
  rels = new ArrayList<RelationImprint>(vs);
 }

 public Scope getScope()
 {
  return scope;
 }

 public void setScope(Scope scope)
 {
  this.scope = scope;
 }
}
