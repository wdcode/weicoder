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
 * "boolean" type.
 * 
 * type of the value object is <code>java.lang.Boolean</code>.
 * See http://www.w3.org/TR/xmlschema-2/#boolean for the spec
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class BooleanType extends BuiltinAtomicType {
    public static final BooleanType theInstance = new BooleanType();
    
    private BooleanType()    { super("boolean"); }
    
    final public XSDatatype getBaseType() {
        return SimpleURType.theInstance;
    }
    
    protected boolean checkFormat( String content, ValidationContext context ) {
        return "true".equals(content) || "false".equals(content)
            || "0".equals(content) || "1".equals(content);
    }
    
    public Object _createValue( String lexicalValue, ValidationContext context ) {
        // for string, lexical space is value space by itself
        return load(lexicalValue);
    }
    
    public static Boolean load( String s ) {
        if( s.equals("true") )        return Boolean.TRUE;
        if( s.equals("1") )            return Boolean.TRUE;
        if( s.equals("0") )            return Boolean.FALSE;
        if( s.equals("false") )        return Boolean.FALSE;
        return null;
    }

    public String convertToLexicalValue( Object value, SerializationContext context ) {
        if( value instanceof Boolean )
            return save( (Boolean)value );
        else
            throw new IllegalArgumentException();
    }

    public static String save( Boolean b ) {
        if( b.booleanValue()==true )    return "true";
        else                            return "false";
    }
    
    public int isFacetApplicable( String facetName ) {
        if(facetName.equals(FACET_PATTERN)
        || facetName.equals(FACET_ENUMERATION)
        || facetName.equals(FACET_WHITESPACE))
            return APPLICABLE;
        return NOT_ALLOWED;
    }
    public Class<Boolean> getJavaObjectType() {
        return Boolean.class;
    }

    // serialization support
    private static final long serialVersionUID = 1;    
}
