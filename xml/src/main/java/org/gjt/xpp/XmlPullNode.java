package org.gjt.xpp;

import java.io.IOException;
import java.util.Enumeration;

@SuppressWarnings({"rawtypes" })
public interface XmlPullNode extends XmlNode {
  void resetPullNode();
  
  XmlNode newNode() ;
  
  XmlPullNode newPullNode(XmlPullParser paramXmlPullParser) ;
  
  boolean isFinished();
  
  XmlPullParser getPullParser() throws IOException;
  
  void setPullParser(XmlPullParser paramXmlPullParser) ;
  
  int getChildrenCountSoFar();
  
  Enumeration children();
  
  Object readNextChild() throws IOException;
  
  void readChildren() throws IOException;
  
  void skipChildren() throws IOException;
}
