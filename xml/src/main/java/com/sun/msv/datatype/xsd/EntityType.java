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
 * "ENTITY" type.
 * 
 * type of the value object is <code>java.lang.String</code>.
 * See http://www.w3.org/TR/xmlschema-2/#ENTITY for the spec
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class EntityType extends BuiltinAtomicType {
    public static final EntityType theInstance = new EntityType();
    private EntityType() { super("ENTITY"); }
    
    final public XSDatatype getBaseType() {
        return NcnameType.theInstance;
    }

    /**
     * ENTITY type always returns true. That is, ENTITY is a context-dependent type.
     */
    public boolean isContextDependent() {
        return true;
    }
    
    public final int isFacetApplicable( String facetName ) {
        if( facetName.equals(FACET_LENGTH)
        ||    facetName.equals(FACET_MINLENGTH)
        ||    facetName.equals(FACET_MAXLENGTH)
        ||    facetName.equals(FACET_PATTERN)
        ||  facetName.equals(FACET_WHITESPACE)
        ||    facetName.equals(FACET_ENUMERATION) )
            return APPLICABLE;
        else
            return NOT_ALLOWED;
    }
    
    protected boolean checkFormat( String content, ValidationContext context ) {
        return context.isUnparsedEntity(content);
    }

    public Object _createValue( String content, ValidationContext context ) {
        if(context.isUnparsedEntity(content))    return content;
        else                                    return null;
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

    // serialization support
    private static final long serialVersionUID = 1;    
}
