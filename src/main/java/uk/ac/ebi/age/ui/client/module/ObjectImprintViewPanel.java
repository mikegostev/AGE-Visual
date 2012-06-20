package uk.ac.ebi.age.ui.client.module;

import java.util.ArrayList;

import uk.ac.ebi.age.ui.shared.imprint.AttributeImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectImprint;
import uk.ac.ebi.age.ui.shared.imprint.Value;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

public class ObjectImprintViewPanel extends VLayout
{
 public ObjectImprintViewPanel( ObjectImprint impr )
 {
  setOverflow(Overflow.VISIBLE);
  setWidth100();
  setPadding(2);
  
  ListGrid attrTbl = new ListGrid();
  
  attrTbl.setShowAllRecords(true);  
  attrTbl.setWrapCells(true);
  attrTbl.setFixedRecordHeights(false);
  attrTbl.setCellHeight(20);
  attrTbl.setStyleName("objectImprintViewerGridBody");
  
  attrTbl.setShowRollOver(false);
  attrTbl.setShowSelectedStyle(false);
  
  attrTbl.setAlternateRecordStyles(false);

  
  attrTbl.setAutoFetchData(false);
  attrTbl.setShowCustomScrollbars(false);
  
  attrTbl.setBodyOverflow(Overflow.VISIBLE);
  attrTbl.setOverflow(Overflow.VISIBLE);
  
  attrTbl.setLeaveScrollbarGap(false);

  attrTbl.setShowRollOver(false);
  attrTbl.setShowSelectedStyle(false);
  
  attrTbl.setAlternateRecordStyles(false);
  attrTbl.setShowHeader(false);

  ListGridField nmFileld = new ListGridField("name");
  nmFileld.setCellAlign(Alignment.RIGHT);
  nmFileld.setBaseStyle("attrClassCol");
  nmFileld.setAutoFitWidth(true);
  
  ListGridField valFileld = new ListGridField("value");
  valFileld.setBaseStyle("attrValCol");
  
  attrTbl.setFields(nmFileld,valFileld);
  
  if( impr.getAttributes() != null )
  {
   int nAttr = impr.getAttributes().size();

   ArrayList<ListGridRecord> recs = new ArrayList<ListGridRecord>();
   
   int atn=-1;
   for( AttributeImprint ati :  impr.getAttributes() )
   {
    atn++;
    
    String atName = "<span class='ageClassRef";
    
    if( ati.getClassImprint().isCustom() )
     atName += " ageCustomClassRef";
    
    atName+= "'>"+ati.getClassImprint().getName()+"</span>:&nbsp;";
    
    
    int valn=-1;
    for( Value v : ati.getValues() )
    {
     valn++;
     
     ListGridRecord r = new ListGridRecord();
     
     r.setAttribute("_attrN", atn );
     r.setAttribute("_valN", valn );

     r.setAttribute("name", atName );

     String val=v.getStringValue();
     
     if( v.getAttributes() != null && v.getAttributes().size() > 0 )
      val= "<span class='qualifiedValue'>"+val+"</span>";
     
     r.setAttribute("value", val );
     
     recs.add( r ); 
    }
    
   }

   attrTbl.setData( recs.toArray( new ListGridRecord[0] ) );
  }
  
  attrTbl.setWidth100();
  addMember(attrTbl);
 }
}
