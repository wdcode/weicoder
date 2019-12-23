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
 * 'fractionDigits' facet. this class holds these facet information and performs validation.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class FractionDigitsFacet extends DataTypeWithLexicalConstraintFacet {

	/** maximum number of fraction digits */
	public final int scale;

	public FractionDigitsFacet(String nsUri, String typeName, XSDatatypeImpl baseType, int _scale, boolean _isFixed) {
		super(nsUri, typeName, baseType, FACET_FRACTIONDIGITS, _isFixed);

		scale = _scale;

		// loosened facet check
		DataTypeWithFacet o = baseType.getFacetObject(FACET_FRACTIONDIGITS);
		if (o != null && ((FractionDigitsFacet) o).scale < this.scale)
			throw new RuntimeException(localize(ERR_LOOSENED_FACET, FACET_FRACTIONDIGITS, o.displayName()));

		// consistency with precision is checked in XSDatatypeImpl.derive method.
	}

	protected boolean checkLexicalConstraint(String content) {
		return countScale(content) <= scale;
	}

	protected void diagnoseByFacet(String content, ValidationContext context) {
		final int cnt = countScale(content);
		if (cnt <= scale)
			return;

		throw new RuntimeException(localize(ERR_TOO_MUCH_SCALE, cnt, scale));
	}

	/**
	 * count the number of fractional digits. this method can assume that the given literal is appropriate as an decimal value. "the number of fractional digits" is defined in
	 * http://www.w3.org/TR/xmlschema-2/#number
	 */
	final protected static int countScale(String literal) {
		final int len = literal.length();
		boolean skipMode = true;

		int count = 0;
		int trailingZero = 0;

		for (int i = 0; i < len; i++) {
			final char ch = literal.charAt(i);
			if (skipMode) {
				if (ch == '.')
					skipMode = false;
			} else {
				if (ch == '0')
					trailingZero++;
				else
					trailingZero = 0;

				if ('0' <= ch && ch <= '9')
					count++;
			}
		}

		return count - trailingZero;
	}

	// serialization support
	private static final long serialVersionUID = 1;
}
