package uk.ac.ebi.age.ui.client.module;

import uk.ac.ebi.age.ui.shared.imprint.AttributeImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectId;
import uk.ac.ebi.age.ui.shared.imprint.ObjectImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectValue;
import uk.ac.ebi.age.ui.shared.imprint.Value;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;

public class ObjectImprintViewPanelHTML extends HTMLFlow
{
 public ObjectImprintViewPanelHTML( final ObjectImprint impr )
 {
  String html = "<table style='width: 100%' class='objectViewTable'>"
    +"<colgroup>"
    +"<col/>"
    +"<col/>"
    +"<col/>"
    +"</colgroup>";
  
  int atn=-1;
  for( AttributeImprint ati :  impr.getAttributes() )
  {
   atn++;
   
   html+="<tr";
   
   if( atn == 0 )
    html+=" class='firstRow'";
   
   html+="><td class='firstCell ";
   
   
   if( ati.getClassImprint().isCustom() )
    html += "ageCustomClassRef";
   else
    html += "ageDefinedClassRef";
   
   html+= "'>"+ati.getClassImprint().getName()+":&nbsp;</td><td>";
   
   if( ati.getValueCount() == 1 )
   {
    html += representValue( ati.getValues().get(0) );
   }
   else
   {
    html+="<table class='valuesTable'>";

    int valn = -1;
    for(Value v : ati.getValues())
    {
     valn++;

     html+="<tr><td>"+ representValue( v )+"</td></tr>";
    }
    
    html+="</table>";
   }
   
   html+="</td></tr>";
  }
  
  html += "</table>";
  
  
  setOverflow(Overflow.VISIBLE);
  setWidth100();
  setPadding(2);
  
  setContents( html );
 }
 
 private String representValue(Value value)
 {
  String str = "";
  
  if( value instanceof ObjectValue )
  {
   ObjectValue ov = (ObjectValue)value;
   
   if( ov.getObjectImprint() != null )
   {}
  }
  
  return str;
 }

 private String makeRepresentationString(  ObjectId objectId, ObjectImprint obj, String theme )
 {
  String repstr = "<div style='float: left' class='briefObjectRepString'>";
  
  int cCount = 0;
  
  if( obj != null )
  {
   extloop: for( AttributeImprint attr : obj.getAttributes() )
   {
    String atName = attr.getClassImprint().getName();
    
    for( Value v : attr.getValues() )
    {
     if( cCount > 200 )
      break extloop;
     
     repstr += "<b>"+atName+"</b>"; 
     
     repstr += ": "+v.getStringValue()+"; ";
     
     cCount+=atName.length()+v.getStringValue().length();
    }
    
   }
  }
  else
   repstr=objectId.toString();
  

  repstr += "</div><div><a class='el' href='javascript:linkClicked(&quot;"+obj.getId()+"&quot;,&quot;"+theme+"&quot;)'>more</a></div>";
  
  return repstr;
 }
}
