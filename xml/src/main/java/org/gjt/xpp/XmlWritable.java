package org.gjt.xpp;

import java.io.IOException;
import java.io.Writer;

public interface XmlWritable {
  void writeXml(Writer paramWriter) throws IOException;
}
