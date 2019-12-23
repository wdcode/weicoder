package org.gjt.xpp;

public interface XmlFormatter extends XmlRecorder {
  boolean isEndTagNewLine();
  
  void setEndTagNewLine(boolean paramBoolean);
}
