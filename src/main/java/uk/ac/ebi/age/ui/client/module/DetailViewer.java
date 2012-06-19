package uk.ac.ebi.age.ui.client.module;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class DetailViewer extends ListGrid
{

 public DetailViewer( DataSource ds, Record[] recs )
 {
  setShowAllRecords(true);  
  setWrapCells(true);
  setFixedRecordHeights(false);
  setCellHeight(20);
  
  setAutoFetchData(true);
  setShowCustomScrollbars(false);
  
  setBodyOverflow(Overflow.VISIBLE);
  setOverflow(Overflow.VISIBLE);
  
  setLeaveScrollbarGap(false);

  setShowRollOver(false);
  setShowSelectedStyle(false);
  
  setAlternateRecordStyles(false);
  setShowHeader(false);

  setFields(new ListGridField("name"),new ListGridField("value"));
 }
}
