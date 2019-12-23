package org.gjt.xpp;

import java.util.Enumeration;

@SuppressWarnings({"rawtypes" })
public interface XmlNode extends XmlStartTag {
  void resetNode();
  
  XmlNode newNode() ;
  
  XmlNode newNode(String paramString1, String paramString2) ;
  
  XmlNode getParentNode();
  
  void setParentNode(XmlNode paramXmlNode) ;
  
  Enumeration children();
  
  int getChildrenCount();
  
  Object getChildAt(int paramInt);
  
  void appendChild(Object paramObject) ;
  
  void insertChildAt(int paramInt, Object paramObject) ;
  
  void removeChildAt(int paramInt) ;
  
  void replaceChildAt(int paramInt, Object paramObject) ;
  
  void ensureChildrenCapacity(int paramInt) ;
  
  void removeChildren() ;
  
  String getQNameLocal(String paramString) ;
  
  String getQNameUri(String paramString) ;
  
  String prefix2Namespace(String paramString) ;
  
  String namespace2Prefix(String paramString) ;
  
  String getDefaultNamespaceUri();
  
  void setDefaultNamespaceUri(String paramString) ;
  
  int getDeclaredNamespaceLength();
  
  void readDeclaredNamespaceUris(String[] paramArrayOfString, int paramInt1, int paramInt2) ;
  
  void readDeclaredPrefixes(String[] paramArrayOfString, int paramInt1, int paramInt2) ;
  
  void ensureDeclaredNamespacesCapacity(int paramInt) ;
  
  void addNamespaceDeclaration(String paramString1, String paramString2) ;
  
  void addDeclaredNamespaces(String[] paramArrayOfString1, int paramInt1, int paramInt2, String[] paramArrayOfString2) ;
  
  void removeDeclaredNamespaces() ;
}
