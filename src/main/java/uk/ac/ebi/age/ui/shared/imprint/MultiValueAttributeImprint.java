package uk.ac.ebi.age.ui.shared.imprint;

import java.util.ArrayList;
import java.util.List;

public class MultiValueAttributeImprint implements AttributeImprint
{
 private List<Value> values = new ArrayList<Value>();
 private ClassImprint classImprint;

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

 public ClassImprint getClassImprint()
 {
  return classImprint;
 }

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
