package uk.ac.ebi.age.ui.shared.imprint;


public class StringValue extends Value
{
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

}
