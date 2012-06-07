package uk.ac.ebi.age.ui.server.imprint;

import java.util.HashMap;
import java.util.Map;

import uk.ac.ebi.age.model.AgeAttribute;
import uk.ac.ebi.age.model.AgeAttributeClass;
import uk.ac.ebi.age.model.AgeClass;
import uk.ac.ebi.age.model.AgeFileAttribute;
import uk.ac.ebi.age.model.AgeObject;
import uk.ac.ebi.age.model.AgeObjectAttribute;
import uk.ac.ebi.age.model.AgeRelation;
import uk.ac.ebi.age.model.AgeRelationClass;
import uk.ac.ebi.age.model.Attributed;
import uk.ac.ebi.age.model.IdScope;
import uk.ac.ebi.age.model.ModuleKey;
import uk.ac.ebi.age.model.ResolveScope;
import uk.ac.ebi.age.ui.shared.imprint.AttributeImprint;
import uk.ac.ebi.age.ui.shared.imprint.AttributedImprint;
import uk.ac.ebi.age.ui.shared.imprint.ClassImprint;
import uk.ac.ebi.age.ui.shared.imprint.ClassType;
import uk.ac.ebi.age.ui.shared.imprint.FileValue;
import uk.ac.ebi.age.ui.shared.imprint.MultiValueAttributeImprint;
import uk.ac.ebi.age.ui.shared.imprint.NumericValue;
import uk.ac.ebi.age.ui.shared.imprint.ObjectId;
import uk.ac.ebi.age.ui.shared.imprint.ObjectImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectValue;
import uk.ac.ebi.age.ui.shared.imprint.RelationImprint;
import uk.ac.ebi.age.ui.shared.imprint.Scope;
import uk.ac.ebi.age.ui.shared.imprint.StringValue;
import uk.ac.ebi.age.ui.shared.imprint.Value;

public class ImprintBuilder
{
 private static ImprintingHint defaultHint = new ImprintingHint();
 
 private Map<AgeObject,ObjectImprint> objMap = new HashMap<AgeObject, ObjectImprint>();
 private Map<Object,ClassImprint> classMap = new HashMap<Object,ClassImprint>();

 public ObjectImprint convert( AgeObject ageObj)
 {
  return convert( ageObj, 0, 0, defaultHint );
 }
 
 public ObjectImprint convert( AgeObject ageObj, ImprintingHint hint)
 {
  return convert( ageObj, 0, 0, hint );
 }

 public ObjectImprint convert( AgeObject ageObj, int atLevel, int rlLevel, ImprintingHint hint )
 {
  ObjectImprint impr = objMap.get( ageObj );
  
  if( impr != null )
   return impr;
  
  ClassImprint clImp = getClassImprint(ageObj.getAgeElClass());
  
  impr = new ObjectImprint();
  
  ModuleKey mk = ageObj.getModuleKey();
  
  ObjectId id = new ObjectId( mk.getClusterId(), mk.getModuleId(), ageObj.getId() );
  impr.setId(id);
  
  impr.setClassImprint(clImp);
  
  impr.setScope( convertScope(ageObj.getIdScope() ) );
  
  
  if( hint.isConvertAttributes() )
   convertAttributes( impr, ageObj, atLevel, hint );
  
  if( hint.isConvertRelations() )
   convertRelations( impr, ageObj, rlLevel, hint );
  
  
  return impr;
 }
 
 private void convertRelations(ObjectImprint impr, AgeObject ageObj, int level, ImprintingHint hint )
 {
  if( ageObj.getRelations() == null )
   return;
  
  for( AgeRelation rel : ageObj.getRelations() )
  {
   ClassImprint rlClass=getClassImprint( rel.getAgeElClass() );
   
   RelationImprint rimp = new RelationImprint();
   
   rimp.setClassImprint(rlClass);

   ObjectId tgtId = new ObjectId();
   
   AgeObject tgObj = rel.getTargetObject();
   
   tgtId.setObjectId(tgObj.getId());
   
   ModuleKey mk = tgObj.getModuleKey();
   
   tgtId.setClusterId( mk.getClusterId() );
   tgtId.setModuleId(  mk.getModuleId() );

   rimp.setTargetObjectId(tgtId);

   if( hint.isResolveRelationsTarget() || level < hint.getRelationsDepth() )
    rimp.setObjectImprint( convert(tgObj, 0, level+1, hint) );

   if( hint.getRelationQualifiersDepth() > 0 )
   {
    int atDep = hint.getQualifiersDepth();
    hint.setQualifiersDepth( hint.getRelationQualifiersDepth() );

    convertAttributes(rimp, rel, 0, hint);

    hint.setQualifiersDepth(atDep);
   }
   
   impr.addRelation(rimp);
  }
 }

 private void convertAttributes(AttributedImprint impr, Attributed ageAtd, int level, ImprintingHint hint )
 {
  if( ageAtd.getAttributes() == null )
   return;
  
  Map<AgeAttributeClass, AttributeImprint> attrMap = new HashMap<AgeAttributeClass, AttributeImprint>();
  
  for( AgeAttribute attr : ageAtd.getAttributes() )
  {
   AttributeImprint atImp = attrMap.get(attr.getAgeElClass());
   
   ClassImprint atClass=null;
   
   if( atImp == null )
   {
    atImp = new MultiValueAttributeImprint();
    
    atClass = getClassImprint( attr.getAgeElClass() );
    
    atImp.setClassImprint(atClass);
   }
   else
    atClass = atImp.getClassImprint();
   
   Value val = null;
   
   if( atClass.getType() == ClassType.ATTR_STRING )
    atImp.addValue( val = new StringValue( attr.getValue().toString() ) );
   else if( atClass.getType() == ClassType.ATTR_NUM )
    atImp.addValue( val = new NumericValue( attr.getValueAsDouble() ) );
   else if( atClass.getType() == ClassType.ATTR_FILE )
   {
    AgeFileAttribute fAttr = (AgeFileAttribute)attr;
    
    atImp.addValue( val = new FileValue( fAttr.getFileId(), fAttr.isResolvedGlobal() ) );
   }
   else if( atClass.getType() == ClassType.ATTR_OBJECT )
   {
    ObjectId tgtId = new ObjectId();
    
    tgtId.setObjectId(((AgeObjectAttribute)attr).getTargetObjectId());
    
    ModuleKey mk = ((AgeObjectAttribute)attr).getTargetResolveScope() == ResolveScope.MODULE ?
      attr.getMasterObject().getModuleKey():((AgeObjectAttribute)attr).getValue().getModuleKey();
    
    tgtId.setClusterId( mk.getClusterId());
    tgtId.setModuleId(  mk.getModuleId() );
    
    ObjectValue objv = new ObjectValue( tgtId ) ;
    
    val = objv;
    
    atImp.addValue( objv );
    
    if( hint.isResolveObjectAttributesTarget() )
    {
     boolean cvRls = hint.isConvertRelations();
     hint.setConvertRelations(false);
     objv.setObjectImprint( convert(((AgeObjectAttribute)attr).getValue(), level+1, 0, hint) );
     hint.setConvertRelations(cvRls);
    }
   }
   
   if( level < hint.getQualifiersDepth() )
    convertAttributes( val, attr, level+1, hint );

  }
  
 }

 private ClassImprint getClassImprint( AgeClass aCls )
 {
  ClassImprint clImp = classMap.get(aCls);
  
  if( clImp == null )
  {
   clImp = new ClassImprint();
   
   clImp.setId( (aCls.isCustom()?"CC":"DC")+aCls.getName() );
   clImp.setName( aCls.getName() );
   clImp.setCustom(aCls.isCustom());
   clImp.setType(ClassType.OBJECT);
   
   classMap.put(aCls, clImp);
  }
  
  return clImp;
 }
 
 private ClassImprint getClassImprint( AgeRelationClass aCls )
 {
  ClassImprint clImp = classMap.get(aCls);
  
  if( clImp == null )
  {
   clImp = new ClassImprint();
   
   clImp.setName( aCls.getName() );
   clImp.setCustom(aCls.isCustom());
   clImp.setType(ClassType.RELATION);
   
   classMap.put(aCls, clImp);
  }
  
  return clImp;
 }

 
 private ClassImprint getClassImprint(AgeAttributeClass ageElClass)
 {
  ClassImprint clImp = classMap.get(ageElClass);
  
  if( clImp != null )
   return clImp;
  
  clImp = new ClassImprint();
  
  clImp.setName( ageElClass.getName() );
  clImp.setCustom(ageElClass.isCustom());
  
  switch(ageElClass.getDataType())
  {
   case STRING:
   case TEXT:
   case URI:
   case BOOLEAN:
    clImp.setType(ClassType.ATTR_STRING);
    break;

   case INTEGER:
   case REAL:
    clImp.setType(ClassType.ATTR_NUM);
    break;
    
   case FILE:
   clImp.setType(ClassType.ATTR_FILE);
   break;

   case OBJECT:
    clImp.setType(ClassType.ATTR_OBJECT);
    break;
   
   default:
    break;
  }
  
  return clImp;
 }

 private Scope convertScope( IdScope scp )
 {
  if( scp == IdScope.GLOBAL )
   return Scope.GLOBAL;

  if( scp == IdScope.CLUSTER )
   return Scope.CLUSTER;

  return Scope.MODULE;
}
 
}
