package uk.ac.ebi.age.ui.client.module;

import java.util.List;

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
 private static int count=1;
 
 public enum RefType
 {
  OBJ,
  QUAL
 }
 
 private int depth;
 
 public ObjectImprintViewPanelHTML( final ObjectImprint impr, int depth, String clickTarget )
 {
  this.depth=depth;
  
 
  setOverflow(Overflow.VISIBLE);
  setWidth100();
  setPadding(2);
  
  setContents( representAttributed(impr, 0, "objectView", "", clickTarget) );
 }
 
 private String representValue(Value value, int lvl, String pathPfx, String clickTarget)
 {
  String str = "";
  
  List<AttributeImprint> quals = value.getAttributes();
  
  if( quals != null && quals.size() ==0 )
   quals = null;
  
  if( value instanceof ObjectValue )
  {
   ObjectValue ov = (ObjectValue)value;
   
   ObjectImprint obj = ov.getObjectImprint();
   
   if( ov.getObjectImprint() != null && lvl < depth && quals == null && obj.getAttributes() != null && obj.getAttributes().size() <= 5 )
   {
    str+="<table class='objectValue' style='width: 100%'><tr class='firstRow'><td class='firstCell' style='text-align: center'>";
    str+=ov.getTargetObjectClass().getName()+"</td>";
    str+="<td style='padding: 0'>"+representAttributed(ov.getObjectImprint(),lvl+1, "objectEmbedded", pathPfx, clickTarget)+"</td></tr></table>";
   }
   else
   {
    str+="<div class='valueString'><a href='javascript:linkClicked(\""+clickTarget+"\",["+RefType.OBJ.ordinal()+pathPfx+"])'>"+ov.getTargetObjectClass().getName()+"</a>" +
            "<br>("+ov.getTargetObjectId()+")</div>";
   }
  }
  else
   str+="<div class='valueString'>"+value.getStringValue()+"</div>";
  
  return str;
 }
 
 private String representAttributed( AttributedImprint ati, int lvl, String tblClass, String pathPfx, String clickTarget )
 {
  String str = "";

  str += "<table style='width: 100%' class='"+tblClass+"'>";

  int i = -1;

  for(AttributeImprint at : ati.getAttributes())
  {
   i++;

   if(i == 0)
    str += "<tr class='firstRow'>";
   else
    str += "<tr>";

   str += "<td class='firstCell attrName'>" + at.getClassImprint().getName() + ":&nbsp;</td>";

   
   if(at.getValueCount() == 1)
   {
    Value val = at.getValues().get(0);
    
    List<AttributeImprint> quals = val.getAttributes();
    
    if( quals != null && quals.size() == 0 )
     quals = null;
    
    if( quals != null )
     str+="<td>";
    else
     str+="<td colspan='2'>";
    
    String path = pathPfx+","+i+",0";
    
    str += representValue(val, lvl, path, clickTarget);
    
    str+="</td>";
    
    if( quals != null )
    {
     str+="<td style='padding: 0'>";
     
     if( lvl < depth )
      str+=representAttributed(val, lvl+1, "qualifiersEmbedded", path, clickTarget);
     else
      str+="<a href='javascript:linkClicked(\""+clickTarget+"\",["+RefType.QUAL.ordinal()+path+"])'>Q</a>";

     str+="</td>";
    }

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
     
     List<AttributeImprint> quals = v.getAttributes();
    
     if( quals != null && quals.size() == 0 )
      quals = null;
     
     if( quals != null )
      str+="<td class='firstCell'>";
     else
      str+="<td class='firstCell' colspan='2'>";

     String path = pathPfx+","+i+","+valn;
     
     str += representValue(v, lvl, path, clickTarget);
     
     str+="</td>";
     
     if( quals != null )
     {
      str+="<td style='padding: 0'>";
      
      if( lvl < depth )
       str+=representAttributed(v, lvl+1, "qualifiersEmbedded", path, clickTarget);
      else
       str+="<a href='javascript:linkClicked(\""+clickTarget+"\",["+RefType.QUAL.ordinal()+path+"])'>Q</a>";

      str+="</td>";
     }

     str+="</tr>";
    }

    str += "</table>";
   }

   str += "</tr>";
  }
 
  str += "</table>";

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
