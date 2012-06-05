package uk.ac.ebi.age.ui.shared.imprint;


public class NumericValue extends Value
{
 private double value;

 public NumericValue()
 {}
 
 public NumericValue(double valueAsDouble)
 {
  value = valueAsDouble;
 }

 public void setValue( double v )
 {
  value = v;
 }

 public double getValue( )
 {
  return value;
 }

 
 @Override
 public String getStringValue()
 {
  return String.valueOf(value);
 }

}
