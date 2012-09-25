package uk.ac.ebi.age.ui.shared.imprint;

import java.io.Serializable;


public class StringValue extends Value implements Serializable
{

 private static final long serialVersionUID = 1L;
 
 private String value;

 public StringValue()
 {}

 public StringValue(String string)
 {
  value = string;
 }

 public void setValue( String v )
 {
  value = v;
 }

 @Override
 public String getStringValue()
 {
  return value;
 }

 @Override
 public String toString()
 {
  return value;
 }
}
