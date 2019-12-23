/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2001-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and  use in  source and binary  forms, with  or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * - Redistributions  of  source code  must  retain  the above  copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution  in binary  form must  reproduct the  above copyright
 *   notice, this list of conditions  and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * Neither  the  name   of  Sun  Microsystems,  Inc.  or   the  names  of
 * contributors may be  used to endorse or promote  products derived from
 * this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS  OR   IMPLIED  CONDITIONS,  REPRESENTATIONS   AND  WARRANTIES,
 * INCLUDING  ANY  IMPLIED WARRANTY  OF  MERCHANTABILITY,  FITNESS FOR  A
 * PARTICULAR PURPOSE  OR NON-INFRINGEMENT, ARE HEREBY  EXCLUDED. SUN AND
 * ITS  LICENSORS SHALL  NOT BE  LIABLE  FOR ANY  DAMAGES OR  LIABILITIES
 * SUFFERED BY LICENSEE  AS A RESULT OF OR  RELATING TO USE, MODIFICATION
 * OR DISTRIBUTION OF  THE SOFTWARE OR ITS DERIVATIVES.  IN NO EVENT WILL
 * SUN OR ITS  LICENSORS BE LIABLE FOR ANY LOST  REVENUE, PROFIT OR DATA,
 * OR  FOR  DIRECT,   INDIRECT,  SPECIAL,  CONSEQUENTIAL,  INCIDENTAL  OR
 * PUNITIVE  DAMAGES, HOWEVER  CAUSED  AND REGARDLESS  OF  THE THEORY  OF
 * LIABILITY, ARISING  OUT OF  THE USE OF  OR INABILITY TO  USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 */

package com.sun.msv.datatype.xsd;

import org.relaxng.datatype.ValidationContext;

/**
 * "unsignedInt" type. type of the value object is <code>java.lang.Long</code>. See http://www.w3.org/TR/xmlschema-2/#unsignedInt for the spec We don't have language support for unsigned datatypes, so
 * things are not so easy. UnsignedIntType uses a LongType as a base implementation, for the convenience and faster performance.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class UnsignedIntType extends LongType {

	public static final UnsignedIntType theInstance = new UnsignedIntType();

	private UnsignedIntType() {
		super("unsignedInt", createRangeFacet(UnsignedLongType.theInstance, null, 4294967295L));
	}

	final public XSDatatype getBaseType() {
		return UnsignedLongType.theInstance;
	}

	/** upper bound value. this is the maximum possible valid value as an unsigned int */
	private static final long upperBound = 4294967295L;

	public Object _createValue(String lexicalValue, ValidationContext context) {
		// Implementation of JDK1.2.2/JDK1.3 is suitable enough
		try {
			Long v = (Long) super._createValue(lexicalValue, context);
			if (v == null)
				return null;
			if (v.longValue() < 0)
				return null;
			if (v.longValue() > upperBound)
				return null;
			return v;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	// serialization support
	private static final long serialVersionUID = 1;
}
