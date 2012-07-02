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
 public interface StringProcessor
 {
  String process( String str );
 }
 
 private static ImprintingHint defaultHint = new ImprintingHint();
 
 private Map<AgeObject,ObjectImprint> objMap = new HashMap<AgeObject, ObjectImprint>();
 private Map<Object,ClassImprint> classMap = new HashMap<Object,ClassImprint>();

 private StringProcessor classNameProcessor;
 private StringProcessor valueProcessor;
 private StringProcessor idProcessor;
 private StringProcessor fileNameProcessor;

 public ImprintBuilder()
 {}

 public ImprintBuilder( StringProcessor classNameProcessor,
                        StringProcessor valueProcessor,
                        StringProcessor idProcessor,
                        StringProcessor fileNameProcessor)
 {
  this.classNameProcessor=classNameProcessor;
  this.valueProcessor=valueProcessor;
  this.idProcessor=idProcessor;
  this.fileNameProcessor=fileNameProcessor;
 }
 
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
  if( ageObj.getRelations() == null || ageObj.getRelations().size() == 0 )
   return;
  
  for( AgeRelation rel : ageObj.getRelations() )
  {
   ClassImprint rlClass=getClassImprint( rel.getAgeElClass() );
   
   RelationImprint rimp = new RelationImprint();
   
   rimp.setClassImprint(rlClass);

   ObjectId tgtId = new ObjectId();
   
   AgeObject tgObj = rel.getTargetObject();
   
   tgtId.setObjectId(tgObj.getId());
   rimp.setTargetObjectClass(getClassImprint( tgObj.getAgeElClass() ) );
   
   ModuleKey mk = tgObj.getModuleKey();
   
   tgtId.setClusterId( mk.getClusterId() );
   tgtId.setModuleId(  mk.getModuleId() );

   if( idProcessor != null )
    processId(tgtId);
   
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
  if( ageAtd.getAttributes() == null || ageAtd.getAttributes().size() == 0 )
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
    
    attrMap.put(attr.getAgeElClass(), atImp);
    impr.addAttribute(atImp);
   }
   else
    atClass = atImp.getClassImprint();
   
   Value val = null;
   
   if( atClass.getType() == ClassType.ATTR_STRING )
   {
    String str = valueProcessor != null ? valueProcessor.process(attr.getValue().toString()) : attr.getValue().toString();
   
    atImp.addValue( val = new StringValue( str ) );
   }
   else if( atClass.getType() == ClassType.ATTR_NUM )
   {
    atImp.addValue( val = new NumericValue( attr.getValueAsDouble() ) );
   }
   else if( atClass.getType() == ClassType.ATTR_FILE )
   {
    AgeFileAttribute fAttr = (AgeFileAttribute)attr;
    
    String fName = fileNameProcessor!=null?fileNameProcessor.process(fAttr.getFileId()):fAttr.getFileId();
    
    atImp.addValue( val = new FileValue( fName, fAttr.isResolvedGlobal() ) );
   }
   else if( atClass.getType() == ClassType.ATTR_OBJECT )
   {
    ObjectId tgtId = new ObjectId();
    
    tgtId.setObjectId(((AgeObjectAttribute)attr).getTargetObjectId());
    
    ModuleKey mk = ((AgeObjectAttribute)attr).getTargetResolveScope() == ResolveScope.MODULE ?
      attr.getMasterObject().getModuleKey():((AgeObjectAttribute)attr).getValue().getModuleKey();
    
    tgtId.setClusterId( mk.getClusterId());
    tgtId.setModuleId(  mk.getModuleId() );
    
    if( idProcessor != null )
     processId(tgtId);
    
    ObjectValue objv = new ObjectValue( tgtId ) ;
    objv.setTargetObjectClass( getClassImprint(((AgeObjectAttribute)attr).getValue().getAgeElClass()) );
    
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

 private void processId(ObjectId tgtId)
 {
  tgtId.setClusterId(idProcessor.process(tgtId.getClusterId()));
  tgtId.setModuleId(idProcessor.process(tgtId.getModuleId()));
  tgtId.setObjectId(idProcessor.process(tgtId.getObjectId()));
 }

 private ClassImprint getClassImprint( AgeClass aCls )
 {
  ClassImprint clImp = classMap.get(aCls);
  
  if( clImp == null )
  {
   clImp = new ClassImprint();
   
   clImp.setId( (aCls.isCustom()?"CC":"DC")+aCls.getName() );
   clImp.setName( classNameProcessor==null?aCls.getName() : classNameProcessor.process(aCls.getName()) );
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
   
   clImp.setId( (aCls.isCustom()?"RCC":"RDC")+aCls.getName() );
   clImp.setName( classNameProcessor==null?aCls.getName() : classNameProcessor.process(aCls.getName()) );
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
  
  clImp.setName( classNameProcessor==null?ageElClass.getName() : classNameProcessor.process(ageElClass.getName()) );
  clImp.setId( (ageElClass.isCustom()?"ACC":"ADC")+ageElClass.getName() );
  clImp.setCustom(ageElClass.isCustom());
  
  classMap.put(ageElClass, clImp);
  
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
