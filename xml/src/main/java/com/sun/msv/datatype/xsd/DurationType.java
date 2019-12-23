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
import com.sun.msv.datatype.xsd.datetime.BigTimeDurationValueType;
import com.sun.msv.datatype.xsd.datetime.ITimeDurationValueType;
import org.relaxng.datatype.ValidationContext;

/**
 * "duration" type.
 * 
 * type of the value object is {@link ITimeDurationValueType}.
 * See http://www.w3.org/TR/xmlschema-2/#duration for the spec
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public final class DurationType extends BuiltinAtomicType implements Comparator {
    
    public static final DurationType theInstance = new DurationType();
    private DurationType() { super("duration"); }
    
    final public XSDatatype getBaseType() {
        return SimpleURType.theInstance;
    }
    
    protected boolean checkFormat( String content, ValidationContext context ) {
        try {
            new BigTimeDurationValueType(content);
            return true;
        } catch( IllegalArgumentException e ) {
            return false;
        }
    }
    
    public Object _createValue( String content, ValidationContext context ) {
        try {
            return new BigTimeDurationValueType(content);
        } catch( IllegalArgumentException e ) {
            return null;
        }
    }
    public Class<ITimeDurationValueType> getJavaObjectType() {
        return ITimeDurationValueType.class;
    }
    
    /** compare two TimeDurationValueType */
    public int compare( Object lhs, Object rhs ) {
        return ((ITimeDurationValueType)lhs).compare((ITimeDurationValueType)rhs);
    }
    
    public final int isFacetApplicable( String facetName ) {
        if( facetName.equals(FACET_PATTERN)
        ||    facetName.equals(FACET_ENUMERATION)
        ||  facetName.equals(FACET_WHITESPACE)
        ||    facetName.equals(FACET_MAXINCLUSIVE)
        ||    facetName.equals(FACET_MAXEXCLUSIVE)
        ||    facetName.equals(FACET_MININCLUSIVE)
        ||    facetName.equals(FACET_MINEXCLUSIVE) )
            return APPLICABLE;
        else
            return NOT_ALLOWED;
    }
    
    public String convertToLexicalValue( Object value, SerializationContext context ) {
        if(!(value instanceof ITimeDurationValueType))
            throw new IllegalArgumentException();
        
        return value.toString();
    }

    
    
    
    // serialization support
    private static final long serialVersionUID = 1;
}

