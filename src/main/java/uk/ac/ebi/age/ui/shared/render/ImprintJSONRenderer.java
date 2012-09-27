package uk.ac.ebi.age.ui.shared.render;

import java.io.IOException;

import uk.ac.ebi.age.ui.shared.imprint.AttributeImprint;
import uk.ac.ebi.age.ui.shared.imprint.AttributedImprint;
import uk.ac.ebi.age.ui.shared.imprint.ClassImprint;
import uk.ac.ebi.age.ui.shared.imprint.Imprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectId;
import uk.ac.ebi.age.ui.shared.imprint.ObjectImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectValue;
import uk.ac.ebi.age.ui.shared.imprint.Value;

public class ImprintJSONRenderer
{

 public static void render( Imprint imp, Appendable app ) throws IOException
 {
  app.append("if( typeof dataArrived == \"function\"){\n" +
  		"(function(){\n" +
  		"var classes={\n");
  
  if( imp.getClasses() != null )
  {
   boolean first = true;
   for(ClassImprint cimp : imp.getClasses().values())
   {
    if(first)
     first = false;
    else
     app.append(",\n");

    app.append("\"").append(cimp.getId()).append("\": new ClassImprint(\"").append(cimp.getId()).append("\",\"").append(cimp.getType().name())
      .append("\",").append(cimp.isCustom() ? "true" : "false").append(")");
   }
  }
  app.append("\n}\n\n var data = [\n");
  
  
  if( imp.getObjects() != null )
  {
   boolean first = true;
   for(ObjectImprint oi : imp.getObjects())
   {
    if(first)
     first = false;
    else
     app.append(",\n");

    appendObject(oi, app, "");
   }
  }
  
  app.append("]\n dataArrived(data);\n})();\n}");
 }
 
 private static void appendObject( ObjectImprint oi, Appendable app, String indent) throws IOException
 {
  if( oi == null )
  {
   app.append("null");
   return;
  }
  
  app.append(indent).append("new ObjectImprint(");
  appendObjectId(oi.getId(), app);
  app.append(", classes[\"").append(oi.getClassImprint().getId()).append("\"],\n");
  
  renderAttributesImprint(oi, app, indent+" ");
  
  app.append(")");
 }

 
 private static void appendObjectId( ObjectId id, Appendable app) throws IOException
 {
  app.append("new IdImprint(\"").append(id.getClusterId()).append("\",\"")
  .append(id.getModuleId()).append("\",\"")
  .append(id.getObjectId()).append("\")");

 }
 
 private static void renderAttributesImprint( AttributedImprint att, Appendable app, String indent) throws IOException
 {
  if( att.getAttributes() == null )
  {
   app.append(indent).append("null");
   return;
  }
  
  app.append(indent).append("{");
 
  boolean first = true;
  
  for( AttributeImprint ati : att.getAttributes() )
  {
   if( first )
    first = false;
   else
    app.append(",");
   
   String clsId = ati.getClassImprint().getId();
   
   app.append("\n").append(indent).append("\"");
   app.append(clsId);
   app.append("\": new AttributeImprint(classes[\""+clsId+"\"],[");
   
   boolean firstV = true;
   
   for( Value v : ati.getValues() )
   {
    if( firstV )
     firstV = false;
    else
     app.append(",\n");
    
    if( v instanceof ObjectValue )
    {
     ObjectValue ov = (ObjectValue)v;
     
     app.append(indent).append("new ObjectValueImprint( ");
     appendObjectId(ov.getTargetObjectId(), app);
     app.append(", classes[\"").append( ov.getTargetObjectClass().getId() ).append("\"],\n");
     appendObject(ov.getObjectImprint(), app, indent+" ");
    }
    else
    {
     app.append(indent).append("new ValueImprint(\"");
     appendEscaped(app,v.getStringValue());
    }
    
    
    app.append("\",");
    
    renderAttributesImprint(v,app, indent+" ");
    
    app.append(")");

   }
   
   app.append("])");
  }
  
  app.append("\n}");

  
 }

 private static void appendEscaped( Appendable sb, String str ) throws IOException
 {
  int cPos,ePos=0;
  
  cPos=-1;
  while( ++cPos < str.length() )
  {
   char ch = str.charAt(cPos);
   
   if( ch >= 0x27 || ch == ' ' )
    continue;

   if( ePos < cPos)
    sb.append(str.substring(ePos, cPos));
   
   ePos=cPos+1;
    
   sb.append("&#").append(String.valueOf((int)ch)).append(";");
  }
  
  if( ePos == 0 )
   sb.append(str);
  else if( ePos < ( str.length()-1 ) )
   sb.append( str.substring(ePos) );
 }

 
}
