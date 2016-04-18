package uk.ac.ebi.age.ui.client.module;

import uk.ac.ebi.age.ui.client.LinkClickListenerJSO;
import uk.ac.ebi.age.ui.client.LinkManager;
import uk.ac.ebi.age.ui.client.ObjectProviderService;
import uk.ac.ebi.age.ui.shared.imprint.AttributedImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectId;
import uk.ac.ebi.age.ui.shared.imprint.ObjectImprint;
import uk.ac.ebi.age.ui.shared.imprint.ObjectValue;
import uk.ac.ebi.age.ui.shared.imprint.Value;
import uk.ac.ebi.age.ui.shared.render.ImprintViewHtmlRenderer;
import uk.ac.ebi.age.ui.shared.render.ImprintViewHtmlRenderer.RefType;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;

public class ObjectImprintViewerWindow extends Window
{
 private static int count = 0;
 
 private static String WIDTH ="1000";
 
 private AttributedImprint imprint;
// private String target;
 
 public ObjectImprintViewerWindow( AttributedImprint impr )
 {
  this(impr, 1, null);
 }

 public ObjectImprintViewerWindow( AttributedImprint impr, final int embedLevel, final ObjectProviderService objSvc )
 {
  imprint = impr;
  
 
  setWidth(WIDTH);  
//  setLeft(offsetLeft);  
  setCanDragReposition(true);  
  setCanDragResize(true);  
  setShowMinimizeButton(false);
  setIsModal(true);
  setShowModalMask(true);
  setAutoSize( true );
//  setOverflow(Overflow.SCROLL);
  
  setStyleName("objectImprintViewerWindow");

 
  addDrawHandler(new DrawHandler()
  {
   
   @Override
   public void onDraw(DrawEvent event)
   {
    if( getOffsetHeight() > com.google.gwt.user.client.Window.getClientHeight() - 20 )
    {
     setAutoSize( false );
     resizeTo(WIDTH,"95%");
    }
   }
  });
  
  final String target1 = "ObjectImprintViewerWindow"+count++;
  final String target2 = "ObjectImprintViewerWindow"+count++;
  
  if( impr instanceof ObjectImprint )
  {
   setTitle( ((ObjectImprint)impr).getClassImprint().getName()+": "+((ObjectImprint)impr).getId().getObjectId() );
   addItem( new ObjectImprintViewPanelHTML((ObjectImprint)impr,1,target1,target2) );
  }
  else if( impr instanceof Value )
  {
   setTitle( "Properties of: "+((Value)impr).getStringValue() );
   addItem( new AttributedImprintViewPanelHTML( impr, 1, target1 ) );
  }

  addCloseClickHandler( new CloseClickHandler()
  {
   @Override
   public void onCloseClick(CloseClickEvent event)
   {
    destroy();
    LinkManager.getInstance().removeLinkClickListener(target1);
    LinkManager.getInstance().removeLinkClickListener(target2);
   }
  });
  

  LinkManager.getInstance().addLinkClickListener(target1, new LinkClickListenerJSO()
  {
   @Override
   public void linkClicked(JavaScriptObject param)
   {
    JsArrayInteger intArr = param.cast();

    AttributedImprint cAt = imprint;

    int i=1;
    
    if( intArr.get(0) == RefType.REL.ordinal() )
    {
     cAt = ((ObjectImprint)cAt).getRelations().get(intArr.get(1));
     i=2;
    }
    
    for( ; i < intArr.length(); i+=3 )
    {
     cAt = cAt.getAttributes().get(intArr.get(i)).getValues().get(intArr.get(i+1));
     
     if( intArr.get(i+2) == ImprintViewHtmlRenderer.RefType.OBJ.ordinal() )
      cAt = ((ObjectValue)cAt).getObjectImprint();
    }
    
    new ObjectImprintViewerWindow(cAt).show();
   }
  });
  
  LinkManager.getInstance().addLinkClickListener(target2, new LinkClickListenerJSO()
  {
   @Override
   public void linkClicked(JavaScriptObject param)
   {
    if( objSvc == null )
     return;
    
    JsArrayString id = param.cast();

    objSvc.getObject(new ObjectId(id.get(0), id.get(1), id.get(2)), new AsyncCallback<ObjectImprint>()
    {
     
     @Override
     public void onSuccess(ObjectImprint arg0)
     {
      if( arg0 == null )
      {
       SC.warn("Requested object not found");
       return;
      }
      
      ObjectImprintViewerWindow.this.destroy();
      LinkManager.getInstance().removeLinkClickListener(target1);
      LinkManager.getInstance().removeLinkClickListener(target2);
      
      new ObjectImprintViewerWindow(arg0, embedLevel, objSvc).show();
     }
     
     @Override
     public void onFailure(Throwable arg0)
     {
      SC.warn("Can't show requested object: "+arg0.getMessage());
     }
    });
    
//    System.out.println("Object requested: "+id.get(0)+":"+id.get(1)+":"+id.get(2));
   }
  });

 }
 
 
 @Override
 public void show()
 {
//  setLeft(-1000);
  super.show();

//  System.out.println( getOffsetHeight() );
//  
//  if( getOffsetHeight() > 1000 )
//   resizeTo("1000", "90%");
  
  
  
  centerInPage();
 }
}
