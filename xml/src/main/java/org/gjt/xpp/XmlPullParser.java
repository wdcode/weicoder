package org.gjt.xpp;

import java.io.IOException;
import java.io.Reader;

public interface XmlPullParser {
  public static final byte END_DOCUMENT = 1;
  
  public static final byte START_TAG = 2;
  
  public static final byte END_TAG = 3;
  
  public static final byte CONTENT = 4;
  
  void setInput(Reader paramReader) ;
  
  void setInput(char[] paramArrayOfchar) ;
  
  void setInput(char[] paramArrayOfchar, int paramInt1, int paramInt2) ;
  
  void reset() ;
  
  boolean isAllowedMixedContent();
  
  void setAllowedMixedContent(boolean paramBoolean) ;
  
  boolean isNamespaceAware();
  
  void setNamespaceAware(boolean paramBoolean) ;
  
  boolean isNamespaceAttributesReporting();
  
  void setNamespaceAttributesReporting(boolean paramBoolean) ;
  
  String getNamespaceUri();
  
  String getLocalName();
  
  String getPrefix();
  
  String getRawName();
  
  String getQNameLocal(String paramString) ;
  
  String getQNameUri(String paramString) ;
  
  int getDepth();
  
  int getNamespacesLength(int paramInt);
  
  void readNamespacesPrefixes(int paramInt1, String[] paramArrayOfString, int paramInt2, int paramInt3) ;
  
  void readNamespacesUris(int paramInt1, String[] paramArrayOfString, int paramInt2, int paramInt3) ;
  
  String getPosDesc();
  
  int getLineNumber();
  
  int getColumnNumber();
  
  byte next() throws IOException;
  
  byte getEventType() ;
  
  boolean isWhitespaceContent() ;
  
  int getContentLength() ;
  
  String readContent() ;
  
  void readEndTag(XmlEndTag paramXmlEndTag) ;
  
  void readStartTag(XmlStartTag paramXmlStartTag) ;
  
  void readNodeWithoutChildren(XmlNode paramXmlNode) ;
  
  byte readNode(XmlNode paramXmlNode) throws IOException;
  
  byte skipNode() throws IOException;
}
