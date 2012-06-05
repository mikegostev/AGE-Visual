package uk.ac.ebi.age.ui.shared.imprint;

public interface AttributeImprint
{
 ClassImprint getClassImprint();
 void setClassImprint( ClassImprint ci );
 
 Value getValue();
 
 int getValueCount();
 
 void addValue( Value v );
}
