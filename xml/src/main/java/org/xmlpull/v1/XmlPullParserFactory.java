package org.xmlpull.v1;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

@SuppressWarnings({"rawtypes","unchecked"})
public class XmlPullParserFactory {
	static final Class          referenceContextClass;
	public static final String  PROPERTY_NAME = "org.xmlpull.v1.XmlPullParserFactory";
//	private static final String RESOURCE_NAME = "/META-INF/services/org.xmlpull.v1.XmlPullParserFactory";
	protected Vector            parserClasses;
	protected String            classNamesLocation;
	protected Vector            serializerClasses;

	static {
		XmlPullParserFactory f = new XmlPullParserFactory();
		referenceContextClass = f.getClass();
	}

	protected Hashtable features = new Hashtable();

	public void setFeature(String name, boolean state)  {
		this.features.put(name, Boolean.valueOf(state));
	}

	public boolean getFeature(String name) {
		Boolean value = (Boolean) this.features.get(name);
		return (value != null) ? value.booleanValue() : false;
	}

	public void setNamespaceAware(boolean awareness) {
		this.features.put("http://xmlpull.org/v1/doc/features.html#process-namespaces", Boolean.valueOf(awareness));
	}

	public boolean isNamespaceAware() {
		return getFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces");
	}

	public void setValidating(boolean validating) {
		this.features.put("http://xmlpull.org/v1/doc/features.html#validation", Boolean.valueOf(validating));
	}

	public boolean isValidating() {
		return getFeature("http://xmlpull.org/v1/doc/features.html#validation");
	}

	public XmlPullParser newPullParser()  {
		if (this.parserClasses == null)
			throw new RuntimeException("Factory initialization was incomplete - has not tried " + this.classNamesLocation);

		if (this.parserClasses.size() == 0)
			throw new RuntimeException("No valid parser classes found in " + this.classNamesLocation);

		StringBuffer issues = new StringBuffer();

		for (int i = 0; i < this.parserClasses.size(); i++) {
			Class ppClass = (Class) this.parserClasses.elementAt(i);
			try {
				XmlPullParser pp = (XmlPullParser) ppClass.getDeclaredConstructor().newInstance();

				for (Enumeration e = this.features.keys(); e.hasMoreElements();) {
					String key = (String) e.nextElement();
					Boolean value = (Boolean) this.features.get(key);
					if (value != null && value.booleanValue()) {
						pp.setFeature(key, true);
					}
				}
				return pp;
			} catch (Exception ex) {

				issues.append(ppClass.getName() + ": " + ex.toString() + "; ");
			}
		}

		throw new RuntimeException("could not create parser: " + issues);
	}

	public XmlSerializer newSerializer()  {
		if (this.serializerClasses == null) {
			throw new RuntimeException("Factory initialization incomplete - has not tried " + this.classNamesLocation);
		}

		if (this.serializerClasses.size() == 0) {
			throw new RuntimeException("No valid serializer classes found in " + this.classNamesLocation);
		}

		StringBuffer issues = new StringBuffer();

		for (int i = 0; i < this.serializerClasses.size(); i++) {
			Class ppClass = (Class) this.serializerClasses.elementAt(i);
			try {
				XmlSerializer ser = (XmlSerializer) ppClass.getDeclaredConstructor().newInstance();

				return ser;
			} catch (Exception ex) {

				issues.append(ppClass.getName() + ": " + ex.toString() + "; ");
			}
		}

		throw new RuntimeException("could not create serializer: " + issues);
	}

	public static XmlPullParserFactory newInstance()  {
		return newInstance(null, null);
	}

	public static XmlPullParserFactory newInstance(String classNames, Class context)  {
		if (context == null) {

			context = referenceContextClass;
		}

		String classNamesLocation = null;

		if (classNames == null || classNames.length() == 0 || "DEFAULT".equals(classNames)) {
			try {
				InputStream is = context.getResourceAsStream("/META-INF/services/org.xmlpull.v1.XmlPullParserFactory");

				if (is == null)
					throw new RuntimeException("resource not found: /META-INF/services/org.xmlpull.v1.XmlPullParserFactory make sure that parser implementing XmlPull API is available");

				StringBuffer sb = new StringBuffer();

				while (true) {
					int ch = is.read();
					if (ch < 0)
						break;
					if (ch > 32)
						sb.append((char) ch);
				}
				is.close();

				classNames = sb.toString();
			} catch (Exception e) {

				throw new RuntimeException(e);
			}
			classNamesLocation = "resource /META-INF/services/org.xmlpull.v1.XmlPullParserFactory that contained '" + classNames + "'";
		} else {
			classNamesLocation = "parameter classNames to newInstance() that contained '" + classNames + "'";
		}

		XmlPullParserFactory factory = null;
		Vector parserClasses = new Vector();
		Vector serializerClasses = new Vector();
		int pos = 0;

		while (pos < classNames.length()) {
			int cut = classNames.indexOf(',', pos);

			if (cut == -1)
				cut = classNames.length();
			String name = classNames.substring(pos, cut);

			Class candidate = null;
			Object instance = null;

			try {
				candidate = Class.forName(name);

				instance = candidate.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
			}

			if (candidate != null) {
				boolean recognized = false;
				if (instance instanceof XmlPullParser) {
					parserClasses.addElement(candidate);
					recognized = true;
				}
				if (instance instanceof XmlSerializer) {
					serializerClasses.addElement(candidate);
					recognized = true;
				}
				if (instance instanceof XmlPullParserFactory) {
					if (factory == null) {
						factory = (XmlPullParserFactory) instance;
					}
					recognized = true;
				}
				if (!recognized) {
					throw new RuntimeException("incompatible class: " + name);
				}
			}
			pos = cut + 1;
		}

		if (factory == null) {
			factory = new XmlPullParserFactory();
		}
		factory.parserClasses = parserClasses;
		factory.serializerClasses = serializerClasses;
		factory.classNamesLocation = classNamesLocation;
		return factory;
	}
}
