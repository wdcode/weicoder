package org.gjt.xpp;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

@SuppressWarnings({"rawtypes",   "deprecation"})
public class XmlPullParserFactory {
//	private static final boolean DEBUG                 = false;
	public static final String   DEFAULT_PROPERTY_NAME = "org.gjt.xpp.XmlPullParserFactory";
	private static Object        MY_REF                = new XmlPullParserFactory();

//	private static final String DEFAULT_FULL_IMPL_FACTORY_CLASS_NAME = "org.gjt.xpp.impl.PullParserFactoryFullImpl";
//
//	private static final String DEFAULT_SMALL_IMPL_FACTORY_CLASS_NAME = "org.gjt.xpp.impl.PullParserFactorySmallImpl";
//
//	private static final String DEFAULT_RESOURCE_NAME = "/META-INF/services/org.gjt.xpp.XmlPullParserFactory";
	private static String       foundFactoryClassName = null;

	private boolean namespaceAware;

//	private static final String PREFIX = "DEBUG XPP2 factory: ";

	public static XmlPullParserFactory newInstance()  {
		return newInstance(null, null);
	}

	public static XmlPullParserFactory newInstance(String factoryClassName)  {
		return newInstance(null, factoryClassName);
	}

	public static XmlPullParserFactory newInstance(Class classLoaderCtx)  {
		return newInstance(classLoaderCtx, null);
	}

//	private static void debug(String msg) {
//		throw new RuntimeException("only when DEBUG enabled can print messages");
//	}

	private static XmlPullParserFactory newInstance(Class classLoaderCtx, String factoryClassName)  {
		XmlPullParserFactory factoryImpl = null;
		if (factoryClassName != null) {

			try {
				Class clazz = null;
				if (classLoaderCtx != null) {
					clazz = Class.forName(factoryClassName);
				} else {
					clazz = Class.forName(factoryClassName);
				}
				factoryImpl = (XmlPullParserFactory) clazz.newInstance();

			} catch (Exception ex) {
			}
		}

		if (factoryImpl == null) {

			if (foundFactoryClassName == null) {
				findFactoryClassName(classLoaderCtx);
			}

			if (foundFactoryClassName != null) {

				try {
					Class clazz = null;
					if (classLoaderCtx != null) {
						clazz = Class.forName(foundFactoryClassName);
					} else {
						clazz = Class.forName(foundFactoryClassName);
					}
					factoryImpl = (XmlPullParserFactory) clazz.newInstance();
				} catch (Exception ex) {
				}
			}
		}

		if (factoryImpl == null) {
			try {
				Class clazz = null;
				factoryClassName = "org.gjt.xpp.impl.PullParserFactoryFullImpl";

				if (classLoaderCtx != null) {
					clazz = Class.forName(factoryClassName);
				} else {
					clazz = Class.forName(factoryClassName);
				}
				factoryImpl = (XmlPullParserFactory) clazz.newInstance();

				foundFactoryClassName = factoryClassName;
			} catch (Exception ex2) {
				try {
					Class clazz = null;
					factoryClassName = "org.gjt.xpp.impl.PullParserFactorySmallImpl";

					if (classLoaderCtx != null) {
						clazz = Class.forName(factoryClassName);
					} else {
						clazz = Class.forName(factoryClassName);
					}
					factoryImpl = (XmlPullParserFactory) clazz.newInstance();

					foundFactoryClassName = factoryClassName;
				} catch (Exception ex3) {
					throw new RuntimeException("could not load any factory class (even small or full default implementation)", ex3);
				}
			}
		}

		if (factoryImpl == null)
			throw new RuntimeException("XPP2: internal parser factory error");

		return factoryImpl;
	}

	private static void findFactoryClassName(Class classLoaderCtx) {
		if (foundFactoryClassName != null) {
			throw new RuntimeException("internal XPP2 initialization error");
		}
		InputStream is = null;

		try {
			if (classLoaderCtx != null) {

				is = classLoaderCtx.getResourceAsStream("/META-INF/services/org.gjt.xpp.XmlPullParserFactory");
			}

			if (is == null) {
				Class klass = MY_REF.getClass();

				is = klass.getResourceAsStream("/META-INF/services/org.gjt.xpp.XmlPullParserFactory");
			}

			if (is != null) {
				foundFactoryClassName = readLine(is);

			}

		} catch (Exception ex) {

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	private static String readLine(InputStream input) throws IOException {
		StringBuffer sb = new StringBuffer();

		while (true) {
			int ch = input.read();
			if (ch < 0)
				break;
			if (ch == 10) {
				break;
			}
			sb.append((char) ch);
		}

		int n = sb.length();
		if (n > 0 && sb.charAt(n - 1) == '\r') {
			sb.setLength(n - 1);
		}

		return sb.toString();
	}

	public void setNamespaceAware(boolean awareness)  {
		this.namespaceAware = awareness;
	}

	public boolean isNamespaceAware() {
		return this.namespaceAware;
	}

	public XmlPullParser newPullParser()  {
		throw new RuntimeException("newPullParser() not implemented");
	}

	public XmlEndTag newEndTag()  {
		throw new RuntimeException("newEndTag() not implemented");
	}

	public XmlNode newNode()  {
		throw new RuntimeException("newNode() not implemented");
	}

	public XmlNode newNode(XmlPullParser pp) throws IOException {
		XmlNode node = newNode();
		pp.readNode(node);
		return node;
	}

	public XmlPullNode newPullNode(XmlPullParser pp)  {
		throw new RuntimeException("newPullNode() not implemented");
	}

	public XmlStartTag newStartTag()  {
		throw new RuntimeException("newStartTag() not implemented");
	}

	public XmlFormatter newFormatter()  {
		throw new RuntimeException("newFormatter() not implemented");
	}

	public XmlRecorder newRecorder()  {
		throw new RuntimeException("newRecorder() not implemented");
	}

	public XmlNode readNode(Reader reader, boolean closeAtEnd) throws IOException {
		XmlPullParser pp = newPullParser();
		pp.setInput(reader);
		byte event = pp.next();
		XmlNode doc = newNode();
		if (event == 2) {
			pp.readNode(doc);
		} else if (event != 1) {
			throw new RuntimeException("coul dnot read node tree from input, unexpected parser state" + pp.getPosDesc());
		}

		if (closeAtEnd) {
			reader.close();
		}
		return doc;
	}

	public XmlNode readNode(Reader reader) throws IOException {
		return readNode(reader, false);
	}

	public void writeNode(XmlNode node, Writer writer, boolean closeAtEnd) throws IOException {
		XmlRecorder recorder = newRecorder();
		recorder.setOutput(writer);
		recorder.writeNode(node);
		if (closeAtEnd) {
			writer.close();
		}
	}

	public void writeNode(XmlNode node, Writer writer) throws IOException {
		writeNode(node, writer, false);
	}
}
