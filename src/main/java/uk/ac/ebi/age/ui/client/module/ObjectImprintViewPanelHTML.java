package uk.ac.ebi.age.ui.client.module;

import uk.ac.ebi.age.ui.shared.imprint.AttributeImprint;
import uk.ac.ebi.age.ui.shared.imprint.AttributedImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectId;
import uk.ac.ebi.age.ui.shared.imprint.ObjectImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectValue;
import uk.ac.ebi.age.ui.shared.imprint.Value;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;

public class ObjectImprintViewPanelHTML extends HTMLFlow
{
 private int depth;
 
 public ObjectImprintViewPanelHTML( final ObjectImprint impr, int depth )
 {
  this.depth=depth;
  
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
    html += representValue( ati.getValues().get(0), 0 );
   }
   else
   {
    html+="<table class='valuesTable'>";

    int valn = -1;
    for(Value v : ati.getValues())
    {
     valn++;

     if( valn == 0 )
      html+="<tr class='firstRow'>";
     else
      html+="<tr>";
     
     html+="<td class='firstCell'>"+ representValue( v, 0 )+"</td></tr>";
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
 
 private String representValue(Value value, int lvl)
 {
  String str = "";
  
  if( value instanceof ObjectValue )
  {
   ObjectValue ov = (ObjectValue)value;
   
   if( ov.getObjectImprint() != null && lvl < depth )
   {
    str+="<table class='embeddedObject'><tr class='firstRow'><td class='firstCell'>";
    str+="<a href='javascript:linkClicked(\"showObject\",\""+ov.getTargetObjectId()+"\")'>"+ov.getTargetObjectClass().getName()+"</a>";
    str+="<td>"+representAttributed(ov.getObjectImprint(),lvl+1)+"</td></tr></table>";
   }
  }
  
  return str;
 }
 
 private String representAttributed( AttributedImprint ati, int lvl )
 {
  String str = "";

  str += "<table>";

  int i = -1;

  for(AttributeImprint at : ati.getAttributes())
  {
   i++;

   if(i == 0)
    str += "<tr class='firstRow'>";
   else
    str += "<tr>";

   str += "<td class='firstCell'>" + at.getClassImprint().getName() + ":&nbsp;</td><td>";

   if(at.getValueCount() == 1)
   {
    str += representValue(at.getValues().get(0), lvl+1);
   }
   else
   {
    str += "<table class='valuesTable'>";

    int valn = -1;
    for(Value v : at.getValues())
    {
     valn++;

     if(valn == 0)
      str += "<tr class='firstRow'>";
     else
      str += "<tr>";

     str += "<td class='firstCell'>" + representValue(v, lvl+1) + "</td></tr>";
    }

    str += "</table>";
   }

   str += "</td></tr>";
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
