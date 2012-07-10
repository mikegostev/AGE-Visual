package uk.ac.ebi.age.ui.client.module;

import uk.ac.ebi.age.ui.client.LinkClickListenerJSO;
import uk.ac.ebi.age.ui.client.LinkManager;
import uk.ac.ebi.age.ui.shared.imprint.AttributedImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectValue;
import uk.ac.ebi.age.ui.shared.imprint.Value;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;

public class ObjectImprintViewerWindow extends Window
{
 private static int count = 0;
 
 private AttributedImprint imprint;
 private String target;
 
 public ObjectImprintViewerWindow( AttributedImprint impr )
 {
  this(impr, 1);
 }

 public ObjectImprintViewerWindow( AttributedImprint impr, int embedLevel )
 {
  imprint = impr;
  
  if( impr instanceof ObjectImprint )
   setTitle( ((ObjectImprint)impr).getClassImprint().getName()+": "+((ObjectImprint)impr).getId().getObjectId() );
  else if( impr instanceof Value )
   setTitle( "Properties of: "+((Value)impr).getStringValue() );
 
  setWidth(1000);  
//  setLeft(offsetLeft);  
  setCanDragReposition(true);  
  setCanDragResize(true);  
  setShowMinimizeButton(false);
  setIsModal(true);
  setShowModalMask(true);
//  setHeight(100);
  setAutoSize(true);
  
  setStyleName("objectImprintViewerWindow");

  target = "ObjectImprintViewerWindow"+count++;
  
  addItem( new ObjectImprintViewPanelHTML(impr,1,target) );

  addCloseClickHandler( new CloseClickHandler()
  {
   @Override
   public void onCloseClick(CloseClientEvent event)
   {
    destroy();
    LinkManager.getInstance().removeLinkClickListener(target);
   }
  });
  
  LinkManager.getInstance().addLinkClickListener(target, new LinkClickListenerJSO()
  {
   @Override
   public void linkClicked(JavaScriptObject param)
   {
    JsArrayInteger intArr = param.cast();

    AttributedImprint cAt = imprint;

    
    for( int i=0; i < intArr.length(); i+=3 )
    {
     cAt = cAt.getAttributes().get(intArr.get(i)).getValues().get(intArr.get(i+1));
     
     if( intArr.get(i+2) == ObjectImprintViewPanelHTML.RefType.OBJ.ordinal() )
      cAt = ((ObjectValue)cAt).getObjectImprint();
    }
    
    new ObjectImprintViewerWindow(cAt).show();
   }
  });
 }
 
 public void show()
 {
  super.show();

  centerInPage();
 }
}
