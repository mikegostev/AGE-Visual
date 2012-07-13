package uk.ac.ebi.age.ui.client.module;

import uk.ac.ebi.age.ui.shared.imprint.AttributedImprint;
import uk.ac.ebi.age.ui.shared.render.ImprintViewHtmlRenderer;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;

public class AttributedImprintViewPanelHTML extends HTMLFlow
{

 public AttributedImprintViewPanelHTML( AttributedImprint impr, int depth, String clickTargetPopup )
 {
  setOverflow(Overflow.VISIBLE);
  setWidth100();
  setPadding(2);
  
  setContents( ImprintViewHtmlRenderer.renderAttributed(impr, "objectView", clickTargetPopup,depth) );
 }
 

}
