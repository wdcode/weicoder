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

import com.sun.msv.datatype.SerializationContext;
import org.relaxng.datatype.ValidationContext;

/**
 * "string" type.
 * 
 * type of the value object is <code>java.lang.String</code>.
 * See http://www.w3.org/TR/xmlschema-2/#string for the spec
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class StringType extends BuiltinAtomicType implements Discrete {
    
    public static final StringType theInstance
        = new StringType("string",WhiteSpaceProcessor.thePreserve,true);
    
    /**
     * Value returned from the isAlwaysValid method.
     */
    private final boolean isAlwaysValid;

    protected StringType( String typeName, WhiteSpaceProcessor whiteSpace ) {
        this( typeName, whiteSpace, false );
    }
    
    protected StringType( String typeName, WhiteSpaceProcessor whiteSpace, boolean _isAlwaysValid ) {
        super(typeName,whiteSpace);
        this.isAlwaysValid = _isAlwaysValid;
    }
    
    public XSDatatype getBaseType() {
        return SimpleURType.theInstance;
    }

    protected final boolean checkFormat( String content, ValidationContext context ) {
        // string derived types should use _createValue method to check its validity
        return _createValue(content,context)!=null;
    }
    
    public Object _createValue( String lexicalValue, ValidationContext context ) {
        // for string, lexical space is value space by itself
        return lexicalValue;
    }
    public Class<String> getJavaObjectType() {
        return String.class;
    }

    public String convertToLexicalValue( Object value, SerializationContext context ) {
        if( value instanceof String )
            return (String)value;
        else
            throw new IllegalArgumentException();
    }
    
    public final int countLength( Object value ) {
        // for string-derived types, length means number of XML characters.
        return UnicodeUtil.countLength( (String)value );
    }
    
    public final int isFacetApplicable( String facetName ) {
        if( facetName.equals(FACET_PATTERN)
        ||    facetName.equals(FACET_ENUMERATION)
        ||    facetName.equals(FACET_WHITESPACE)
        ||    facetName.equals(FACET_LENGTH)
        ||    facetName.equals(FACET_MAXLENGTH)
        ||    facetName.equals(FACET_MINLENGTH) )
            return APPLICABLE;
        else
            return NOT_ALLOWED;
    }

    public boolean isAlwaysValid() {
        return isAlwaysValid;
    }

    // serialization support
    private static final long serialVersionUID = 1;    
}
