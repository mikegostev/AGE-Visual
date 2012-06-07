package uk.ac.ebi.age.ui.shared.imprint;

import java.util.List;

public interface AttributeImprint
{
 ClassImprint getClassImprint();
 void setClassImprint( ClassImprint ci );
 
 Value getValue();
 List<Value> getValues();
 
 int getValueCount();
 
 void addValue( Value v );
}
