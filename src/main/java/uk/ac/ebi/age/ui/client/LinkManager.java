package uk.ac.ebi.age.ui.client;

import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.JavaScriptObject;

public class LinkManager
{
 static
 {
  instance = new LinkManager();
  
  init();
 }
 
 private static LinkManager instance;
 
 private final Map<String, LinkClickListener> lsnrs = new TreeMap<String, LinkClickListener>();
 private final Map<String, LinkClickListenerJSO> lsnrsJSO = new TreeMap<String, LinkClickListenerJSO>();
 
 private static native void init()
 /*-{
  $wnd.linkClicked = function( tgt, val )
  {
   if( typeof(val) == "string" )
          @uk.ac.ebi.age.ui.client.LinkManager::jsLinkClicked(Ljava/lang/String;Ljava/lang/String;)(tgt,val);
   else
          @uk.ac.ebi.age.ui.client.LinkManager::jsLinkClickedJSO(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(tgt,val);
  }
 }-*/;
 
 public static LinkManager getInstance()
 {
  return instance;
 }
 
 private static void jsLinkClicked( String linkId, String param )
 {
  getInstance().linkClicked(linkId, param);
 }

 private static void jsLinkClickedJSO( String linkId, JavaScriptObject param )
 {
  getInstance().linkClickedJSO(linkId, param);
 }

 private void linkClicked(String linkId, String param)
 {
  LinkClickListener lsn = lsnrs.get(linkId);
  
  if( lsn != null )
   lsn.linkClicked(param);
 }
 
 private void linkClickedJSO(String linkId, JavaScriptObject param)
 {
  LinkClickListenerJSO lsn = lsnrsJSO.get(linkId);
  
  if( lsn != null )
   lsn.linkClicked(param);
 }

 
 public void addLinkClickListener( String linkId, LinkClickListener l )
 {
  lsnrs.put(linkId, l);
 }
 
 public void addLinkClickListener( String linkId, LinkClickListenerJSO l )
 {
  lsnrsJSO.put(linkId, l);
 }

 
 public void removeLinkClickListener( String linkId )
 {
  lsnrs.remove(linkId);
  lsnrsJSO.remove(linkId);
 }

}
