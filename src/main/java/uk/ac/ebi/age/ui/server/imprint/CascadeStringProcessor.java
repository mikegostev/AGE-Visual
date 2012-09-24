package uk.ac.ebi.age.ui.server.imprint;

import java.util.Arrays;
import java.util.List;

public class CascadeStringProcessor implements StringProcessor
{
 private final List<StringProcessor> plist;

 public CascadeStringProcessor( StringProcessor ... processors )
 {
  plist = Arrays.asList(processors);
 }
 
 public CascadeStringProcessor( List<StringProcessor> processors )
 {
  plist = processors;
 }

 
 @Override
 public String process(String str)
 {
  for( StringProcessor sp : plist )
   str = sp.process(str);
  
  return str;
 }

}
