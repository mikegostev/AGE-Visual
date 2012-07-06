package uk.ac.ebi.age.ui.client.module;

import uk.ac.ebi.age.ui.shared.imprint.ObjectImprint;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;

public class ObjectImprintViewerWindow extends Window
{
 public ObjectImprintViewerWindow( ObjectImprint impr )
 {
  setTitle( impr.getClassImprint().getName()+": "+impr.getId().getObjectId() );  
  setWidth(750);  
//  setLeft(offsetLeft);  
  setCanDragReposition(true);  
  setCanDragResize(true);  
  setShowMinimizeButton(false);
  setIsModal(true);
  setShowModalMask(true);
//  setHeight(100);
  setAutoSize(true);
  
  setStyleName("objectImprintViewerWindow");

  addItem( new ObjectImprintViewPanelHTML(impr,3,"target") );

  centerInPage();

  addCloseClickHandler( new CloseClickHandler()
  {
   @Override
   public void onCloseClick(CloseClientEvent event)
   {
    destroy();
   }
  });
 }
}
