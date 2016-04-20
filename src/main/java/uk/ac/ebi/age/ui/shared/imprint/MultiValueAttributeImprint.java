package uk.ac.ebi.age.ui.shared.imprint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultiValueAttributeImprint implements AttributeImprint, Serializable
{

 private static final long serialVersionUID = 1L;
 
 private List<Value> values = new ArrayList<Value>();
 private ClassImprint classImprint;

 public MultiValueAttributeImprint()
 {
 }
 
 @Override
 public Value getValue()
 {
  if( values.size() == 0 )
   return null;
  
  return values.get(0);
 }

 public void setValue(Value value)
 {
  values.clear();
  values.add( value );
 }

 @Override
 public void addValue( Value v )
 {
  values.add(v);
 }
 
 public void setValues( List<Value> vs )
 {
  
  if( ! (vs instanceof ArrayList) )
   values = new ArrayList<Value>(vs);
  else
   values=vs;
 }

 
 @Override
 public int getValueCount()
 {
  return values.size();
 }

 @Override
 public ClassImprint getClassImprint()
 {
  return classImprint;
 }

 @Override
 public void setClassImprint(ClassImprint classImprint)
 {
  this.classImprint = classImprint;
 }

 @Override
 public List<Value> getValues()
 {
  return values;
 }

}
