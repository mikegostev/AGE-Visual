package uk.ac.ebi.age.ui.shared.render;

import java.util.List;

import uk.ac.ebi.age.ui.shared.imprint.AttributeImprint;
import uk.ac.ebi.age.ui.shared.imprint.AttributedImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectId;
import uk.ac.ebi.age.ui.shared.imprint.ObjectImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectValue;
import uk.ac.ebi.age.ui.shared.imprint.RelationImprint;
import uk.ac.ebi.age.ui.shared.imprint.Value;

public class ImprintViewHtmlRenderer
{
 public enum RefType
 {
  OBJ,
  QUAL,
  ATTR,
  REL
 }
 
 public static String renderRelations( ObjectImprint obi, String tblClass, String clickTarget, int depth  )
 {
  if(obi.getRelations() == null || obi.getRelations().size() == 0)
   return "";

  String html = "<table style='width: 100%' class='" + tblClass + "'>";

  String path = String.valueOf(RefType.REL.ordinal());

  int reln = -1;

  for(RelationImprint rel : obi.getRelations())
  {
   reln++;

   ObjectId tgtId = rel.getTargetObjectId();

   html += "<tr><td class='relName'>" + rel.getClassImprint().getName() + ":&nbsp;</td>";

   List<AttributeImprint> quals = rel.getAttributes();

   if(quals != null && quals.size() == 0)
    quals = null;

   if(quals != null)
    html += "<td class='relTarget'>";
   else
    html += "<td colspan='2' class='relTarget'>";

   html += "<a href='javascript:linkClicked(\"" + clickTarget + "\"," + "[\"" + tgtId.getClusterId() + "\",\"" + tgtId.getModuleId()
     + "\",\"" + tgtId.getObjectId() + "\"])'>" + rel.getTargetObjectClass().getName() + " (" + rel.getTargetObjectId().getObjectId() + ")</a></td>";

   if(quals != null)
   {

    String ref = path + ","+reln;

    html += "<td style='padding: 0; width: 1%'>";

    if(depth > 0)
     html += representAttributed(rel, 1, "qualifiersEmbedded", ref, clickTarget, depth);
    else
     html += "<a href='javascript:linkClicked(\"" + clickTarget + "\",[" + ref + "])'><img src='AGEVisual/icons/Q.png'></a>";

    html += "</td>";

   }
  }
  html += "</table>";

  return html;
 }
 
 private static String representValue(Value value, int lvl, String pathPfx, String clickTarget, int depth)
 {
  String str = "";
  
  List<AttributeImprint> quals = value.getAttributes();
  
  if( quals != null && quals.size() ==0 )
   quals = null;
  
  if( value instanceof ObjectValue )
  {
   ObjectValue ov = (ObjectValue)value;
   
   ObjectImprint obj = ov.getObjectImprint();
   
   String ref = pathPfx+","+RefType.OBJ.ordinal();
   
   if( ov.getObjectImprint() != null && lvl < depth && quals == null && obj.getAttributes() != null && obj.getAttributes().size() <= 5 )
   {
    str+="<table class='objectValue' style='width: 100%'><tr class='firstRow'><td class='firstCell' style='text-align: center; width: 150px'>";
    str+=ov.getTargetObjectClass().getName()+"</td>";
    str+="<td style='padding: 0'>"+representAttributed(ov.getObjectImprint(),lvl+1, "objectEmbedded", ref, clickTarget, depth)+"</td></tr></table>";
   }
   else
   {
    str+="<div class='valueString'><a href='javascript:linkClicked(\""+clickTarget+"\",["+ref+"])'>"+ov.getTargetObjectClass().getName()+"</a>" +
            "<br>("+ov.getTargetObjectId().getObjectId()+")</div>";
   }
  }
  else
  {
   String val = value.getStringValue();
   
   if( val.length() > 10 && val.substring(0, 5).equalsIgnoreCase("http:") )
    val = "<a target='_blank' href='"+val+"'>"+val+"</a>";
   
   str+="<div class='valueString'>"+val+"</div>";
  }
  
  return str;
 }

 public static String renderAttributed( AttributedImprint ati, String tblClass, String clickTarget, int depth )
 {
  return representAttributed(ati, 0, tblClass, String.valueOf(RefType.ATTR.ordinal()), clickTarget, depth);
 }
 
 private static String representAttributed( AttributedImprint ati, int lvl, String tblClass, String pathPfx, String clickTarget, int depth )
 {
  String str = "<table style='width: 100%' class='"+tblClass+"'>";

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
    
    String path = pathPfx;
    
    path += ","+i+",0";
    
    str += representValue(val, lvl, path, clickTarget, depth);
    
    str+="</td>";
    
    if( quals != null )
    {
     str+="<td style='padding: 0; width: 1%'>";
     
     String ref = path+","+RefType.QUAL.ordinal();
     
     if( lvl < depth )
      str+=representAttributed(val, lvl+1, "qualifiersEmbedded", ref, clickTarget, depth);
     else
      str+="<a href='javascript:linkClicked(\""+clickTarget+"\",["+ref+"])'><img src='AGEVisual/icons/Q.png'></a>";

     str+="</td>";
    }

   }
   else
   {
    str += "<td colspan='2' style='padding: 0'><table class='valuesTable' style='padding: 0; width: 100%; margin: 0; border-collapse: collapse'>";

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

     String path = pathPfx;
     
     path = ","+i+","+valn+","+RefType.QUAL.ordinal();
     
     str += representValue(v, lvl, path, clickTarget, depth);
     
     str+="</td>";
     
     if( quals != null )
     {
      str+="<td style='padding: 0'>";
      
      if( lvl < depth )
       str+=representAttributed(v, lvl+1, "qualifiersEmbedded", path, clickTarget, depth);
      else
       str+="<a href='javascript:linkClicked(\""+clickTarget+"\",["+path+"])'><img src='AGEVisual/icons/Q.png'></a>";

      str+="</td>";
     }

     str+="</tr>";
    }

    str += "</table></td>";
   }

   str += "</tr>";
  }
 
  str += "</table>";

  return str;
 }

}
