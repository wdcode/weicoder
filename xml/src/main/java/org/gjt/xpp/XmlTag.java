package org.gjt.xpp;

public interface XmlTag {
  void resetTag();
  
  String getNamespaceUri();
  
  String getLocalName();
  
  String getPrefix();
  
  String getRawName();
  
  void modifyTag(String paramString1, String paramString2, String paramString3);
}
