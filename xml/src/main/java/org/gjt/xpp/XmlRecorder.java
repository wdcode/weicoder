package org.gjt.xpp;

import java.io.IOException;
import java.io.Writer;

public interface XmlRecorder {
  Writer getOutput() ;
  
  void setOutput(Writer paramWriter) ;
  
  void write(Object paramObject) throws IOException;
  
  void writeXml(XmlWritable paramXmlWritable) throws IOException;
  
  void writeContent(String paramString) throws IOException;
  
  void writeEndTag(XmlEndTag paramXmlEndTag) throws IOException;
  
  void writeStartTag(XmlStartTag paramXmlStartTag) throws IOException;
  
  void writeStartTagStart(XmlStartTag paramXmlStartTag) throws IOException;
  
  void writeStartTagAsEndTag(XmlStartTag paramXmlStartTag) throws IOException;
  
  void writeNode(XmlNode paramXmlNode) throws IOException;
}
