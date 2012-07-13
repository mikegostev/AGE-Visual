package uk.ac.ebi.age.ui.client.module;

import uk.ac.ebi.age.ui.shared.imprint.ObjectImprint;
import uk.ac.ebi.age.ui.shared.render.ImprintViewHtmlRenderer;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class ObjectImprintViewPanelHTML extends VLayout
{
 
 public ObjectImprintViewPanelHTML( final ObjectImprint impr, int depth, String clickTargetPopup, String clickTargetGo)
 {
  setWidth100();
  
  Canvas c = new HTMLFlow(ImprintViewHtmlRenderer.renderAttributed(impr, "objectView", clickTargetPopup,depth));
  
  c.setOverflow(Overflow.VISIBLE);
  c.setWidth100();
  c.setPadding(2);

  if( impr.getRelations() != null && impr.getRelations().size() > 0  )
  {
   Label l = new Label("Attributes");
   
   l.setPadding(5);
   l.setHeight(20);
   l.setStyleName("blockLabel");
   
   addMember( l );
   
   addMember( c );
   
   l = new Label("Relations");
   
   l.setPadding(5);
   l.setHeight(20);
   l.setStyleName("blockLabel");
   
   addMember( l );

   c = new HTMLFlow(ImprintViewHtmlRenderer.renderRelations(impr, "relView", clickTargetGo,1));
   
   c.setOverflow(Overflow.VISIBLE);
   c.setWidth100();
   c.setPadding(2);

   addMember( c );
  }
  else
   addMember( c );
 }
 
}
