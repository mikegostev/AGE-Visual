package uk.ac.ebi.age.ui.shared.imprint;

import java.util.List;

public interface AttributedImprint
{
 List<AttributeImprint> getAttributes();
 void addAttribute( AttributeImprint at );
 void setAttributes( List<AttributeImprint> ats );
}
