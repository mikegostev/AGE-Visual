package uk.ac.ebi.age.ui.shared.imprint;

import java.io.Serializable;


public class NumericValue extends Value implements Serializable
{

 private static final long serialVersionUID = 1L;
 
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
