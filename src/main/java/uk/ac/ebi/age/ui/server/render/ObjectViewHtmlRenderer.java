package uk.ac.ebi.age.ui.server.render;

import java.util.Collection;

import uk.ac.ebi.age.model.AgeAttribute;
import uk.ac.ebi.age.model.AgeAttributeClass;
import uk.ac.ebi.age.model.AgeObject;
import uk.ac.ebi.age.model.AgeRelation;
import uk.ac.ebi.age.model.Attributed;
import uk.ac.ebi.age.model.DataType;

public class ObjectViewHtmlRenderer
{
 public enum RefType
 {
  OBJ,
  QUAL,
  ATTR,
  REL
 }
 
 public static String renderRelations( AgeObject obi, String tblClass, String clickTarget, int depth, boolean showInferred  )
 {
  if(obi.getRelations() == null || obi.getRelations().size() == 0)
   return "";

  StringBuilder sb = new StringBuilder(2000);
  
  String path = String.valueOf(RefType.REL.ordinal());

  int reln = -1;

  for(AgeRelation rel : obi.getRelations())
  {
   reln++;

   if( rel.isInferred() && ! showInferred )
    continue;
   
   if( sb.length() == 0 )
    sb.append("<table class='").append(tblClass).append("'>");
  
   sb.append("<tr><td class='relName'>").append( rel.getAgeElClass().getName()).append(":&nbsp;</td>");

   Collection<? extends AgeAttribute> quals = rel.getAttributes();

   if(quals != null && quals.size() == 0)
    quals = null;

   if(quals != null)
    sb.append("<td class='relTarget'>");
   else
    sb.append("<td colspan='2' class='relTarget'>");

   AgeObject tgtObj = rel.getTargetObject();
   
   if( clickTarget !=null )
    sb.append("<a href='javascript:linkClicked(\"").append(clickTarget).append("\",[\"").append(tgtObj.getModuleKey().getClusterId() )
    .append("\",\"").append(tgtObj.getModuleKey().getModuleId())
    .append("\",\"").append(tgtObj.getId()).append("\"])'>");
   
   sb.append(tgtObj.getAgeElClass().getName()).append(" (").append( rel.getTargetObjectId() ).append( ")");
   
   if( clickTarget != null )
    sb.append("</a>");
   
    sb.append("</td>");

   if(quals != null)
   {

    String ref = path + ","+reln;

    sb.append("<td style='padding: 0; width: 1%'>");

    if(depth > 0)
     representAttributed(rel, 1, "qualifiersEmbedded", ref, clickTarget, depth, sb);
    else
     sb.append("<a href='javascript:linkClicked(\"").append( clickTarget).append("\",[" ).append(ref ).append("])'><img src='AGEVisual/icons/Q.png'></a>");

     sb.append("</td>");

   }
  }
  if( sb.length() > 0 )
   sb.append("</table>");

  return sb.toString();
 }
 
 private static void representValue(AgeAttribute value, int lvl, String pathPfx, String clickTarget, int depth, StringBuilder sb)
 {
  
  Collection< ? extends AgeAttribute> quals = value.getAttributes();
  
  if( quals != null && quals.size() ==0 )
   quals = null;
  
  if( value.getAgeElClass().getDataType() == DataType.OBJECT )
  {
   AgeObject obj = (AgeObject)value.getValue();
   
   String ref = pathPfx+","+RefType.OBJ.ordinal();
   
   if( obj != null && lvl < depth && quals == null && obj.getAttributes() != null && obj.getAttributes().size() <= 5 )
   {
    sb.append("<table class='objectValue' style='width: 100%'><tr class='firstRow'><td class='firstCell' style='text-align: center; width: 150px'>");
    sb.append(obj.getAgeElClass().getName()).append("</td>");
    sb.append("<td style='padding: 0'>");
    representAttributed(obj,lvl+1, "objectEmbedded", ref, clickTarget, depth,sb);
    sb.append("</td></tr></table>");
   }
   else
   {
    sb.append("<div class='valueString'>");
    
    if( clickTarget != null )
     sb.append("<a href='javascript:linkClicked(\"").append(clickTarget).append("\",[").append(ref).append("])'>");
    
    sb.append(obj.getAgeElClass().getName());
    
    if( clickTarget != null )
     sb.append("</a>");
   
    sb.append("<br>(").append(obj.getId()).append(")</div>");
   }
  }
  else
  {
   String val = value.getValue().toString();
   
   sb.append("<div class='valueString'>");
   
   if( val.length() > 10 && val.substring(0, 5).equalsIgnoreCase("http:") )
    sb.append("<a target='_blank' href='").append(val).append("'>").append(val).append("</a>");
   else
    sb.append(val);
   
   sb.append("</div>");
  }
 }

 public static String renderAttributed( Attributed ati, String tblClass, String clickTarget, int depth )
 {
  StringBuilder sb = new StringBuilder(2000);
  
  representAttributed(ati, 0, tblClass, String.valueOf(RefType.ATTR.ordinal()), clickTarget, depth, sb);
 
  return sb.toString();
 }
 
 private static void representAttributed( Attributed ati, int lvl, String tblClass, String pathPfx, String clickTarget, int depth, StringBuilder sb )
 {
  sb.append("<table class='").append(tblClass).append("'>");

  int i = -1;

  for(AgeAttributeClass atcls : ati.getAttributeClasses())
  {
   i++;

   Collection< ? extends AgeAttribute> attrs = ati.getAttributesByClass(atcls, false);

   if(i == 0)
    sb.append("<tr class='firstRow'>");
   else
    sb.append("<tr>");

   sb.append( "<td class='firstCell attrName'>").append(atcls.getName()).append(":&nbsp;</td>");

   if(attrs.size() == 1)
   {
    AgeAttribute val = attrs.iterator().next();

    Collection< ? extends AgeAttribute> quals = val.getAttributes();

    if(quals != null && quals.size() == 0)
     quals = null;

    if(quals != null)
     sb.append("<td>");
    else
     sb.append("<td colspan='2'>");

    String path = pathPfx;

    path += "," + i + ",0";

    representValue(val, lvl, path, clickTarget, depth, sb);

    sb.append("</td>");

    if(quals != null)
    {
     sb.append("<td style='padding: 0; width: 1%'>");

     String ref = path + "," + RefType.QUAL.ordinal();

     if(lvl < depth)
      representAttributed(val, lvl + 1, "qualifiersEmbedded", ref, clickTarget, depth,sb);
     else if(clickTarget != null)
      sb.append("<a href='javascript:linkClicked(\"").append( clickTarget ).append("\",[").append( ref ).append("])'><img src='AGEVisual/icons/Q.png'></a>");

     sb.append("</td>");
    }

   }
   else
   {
    sb.append("<td colspan='2' style='padding: 0'><table class='valuesTable' style='padding: 0; width: 100%; margin: 0; border-collapse: collapse'>");

    int valn = -1;
    for(AgeAttribute v : attrs)
    {
     valn++;

     if(valn == 0)
      sb.append("<tr class='firstRow'>");
     else
      sb.append("<tr>");

     Collection< ? extends AgeAttribute> quals = v.getAttributes();

     if(quals != null && quals.size() == 0)
      quals = null;

     if(quals != null)
      sb.append("<td class='firstCell'>");
     else
      sb.append("<td class='firstCell' colspan='2'>");

     String path = pathPfx;

     path = "," + i + "," + valn + "," + RefType.QUAL.ordinal();

     representValue(v, lvl, path, clickTarget, depth, sb);

     sb.append("</td>");

     if(quals != null)
     {
      sb.append("<td style='padding: 0'>");

      if(lvl < depth)
       representAttributed(v, lvl + 1, "qualifiersEmbedded", path, clickTarget, depth,sb);
      else
       sb.append("<a href='javascript:linkClicked(\"").append(clickTarget).append("\",[").append( path ).append("])'><img src='AGEVisual/icons/Q.png'></a>");

      sb.append("</td>");
     }

     sb.append("</tr>");
    }

    sb.append("</table></td>");
   }

   sb.append("</tr>");
  }

  sb.append("</table>");

 }

}
