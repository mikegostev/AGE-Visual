package uk.ac.ebi.age.ui.shared.imprint;

import java.util.Collections;
import java.util.List;

public class SingleValueAttributeImprint implements AttributeImprint
{
 private Value value;
 private ClassImprint classImprint;

 public Value getValue()
 {
  return value;
 }

 public void setValue(Value value)
 {
  this.value = value;
 }

 @Override
 public int getValueCount()
 {
  return 1;
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
 public void addValue(Value v)
 {
  value = v;
 }

 @Override
 public List<Value> getValues()
 {
  return Collections.singletonList(value);
 }
 
}
