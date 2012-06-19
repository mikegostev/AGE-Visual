package uk.ac.ebi.age.ui.shared.imprint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Value implements AttributedImprint, Serializable
{
 private List<AttributeImprint> attrs;

 
 public abstract String getStringValue();
 
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
}
