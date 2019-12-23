package org.gjt.xpp;

public interface XmlStartTag extends XmlTag {
  void resetStartTag();
  
  int getAttributeCount();
  
  String getAttributeNamespaceUri(int paramInt);
  
  String getAttributeLocalName(int paramInt);
  
  String getAttributePrefix(int paramInt);
  
  String getAttributeRawName(int paramInt);
  
  String getAttributeValue(int paramInt);
  
  String getAttributeValueFromRawName(String paramString);
  
  String getAttributeValueFromName(String paramString1, String paramString2);
  
  boolean isAttributeNamespaceDeclaration(int paramInt);
  
  void addAttribute(String paramString1, String paramString2, String paramString3, String paramString4) ;
  
  void addAttribute(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean) ;
  
  void ensureAttributesCapacity(int paramInt) ;
  
  void removeAttributes() ;
  
  boolean removeAttributeByName(String paramString1, String paramString2) ;
  
  boolean removeAttributeByRawName(String paramString) ;
}
