package uk.ac.ebi.age.ui.server.imprint;

public class ImprintingHint
{
 private boolean convertAttributes             = true;
 private boolean convertRelations              = true;
 private int     relationQualifiersDepth       = Integer.MAX_VALUE;
 private int     qualifiersDepth               = Integer.MAX_VALUE;
 private int     relationsDepth                = 1;
 private boolean resolveRelationsTarget        = false;
 private boolean resolveObjectAttributesTarget = false;
 private boolean convertImplicitRelations      = false;

 public boolean isConvertAttributes()
 {
  return convertAttributes;
 }

 public void setConvertAttributes(boolean convertAttributes)
 {
  this.convertAttributes = convertAttributes;
 }

 public boolean isConvertRelations()
 {
  return convertRelations;
 }

 public void setConvertRelations(boolean convertRelations)
 {
  this.convertRelations = convertRelations;
 }

 public int getQualifiersDepth()
 {
  return qualifiersDepth;
 }

 public void setQualifiersDepth(int qualifiersDepth)
 {
  this.qualifiersDepth = qualifiersDepth;
 }

 public int getRelationsDepth()
 {
  return relationsDepth;
 }

 public void setRelationsDepth(int relationsDepth)
 {
  this.relationsDepth = relationsDepth;
 }

 public boolean isResolveRelationsTarget()
 {
  return resolveRelationsTarget;
 }

 public void setResolveRelationsTarget(boolean resolveRelationsTarget)
 {
  this.resolveRelationsTarget = resolveRelationsTarget;
 }

 public boolean isResolveObjectAttributesTarget()
 {
  return resolveObjectAttributesTarget;
 }

 public void setResolveObjectAttributesTarget(boolean resolveObjectAttributesTarget)
 {
  this.resolveObjectAttributesTarget = resolveObjectAttributesTarget;
 }

 public int getRelationQualifiersDepth()
 {
  return relationQualifiersDepth;
 }

 public void setRelationQualifiersDepth(int relationQualifiersDepth)
 {
  this.relationQualifiersDepth = relationQualifiersDepth;
 }

 public boolean isConvertImplicitRelations()
 {
  return convertImplicitRelations;
 }

 public void setConvertImplicitRelations(boolean convertImplicitRelations)
 {
  this.convertImplicitRelations = convertImplicitRelations;
 }
}
