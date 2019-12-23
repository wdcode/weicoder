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

import java.math.BigInteger;

/**
 * "integer" type.
 * 
 * type of the value object is {@link IntegerValueType}.
 * See http://www.w3.org/TR/xmlschema-2/#integer for the spec
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class IntegerType extends IntegerDerivedType {
    
    /** Singleton instance. */
    public static final IntegerType theInstance;
    
    static {
        try {
            theInstance = new IntegerType("integer",
                new FractionDigitsFacet(null,null,NumberType.theInstance,0,true) );
        } catch( Exception e ) {
            throw new InternalError();  // assertion failure
        }
    }

    
    
    protected IntegerType(String typeName,XSDatatypeImpl baseFacets) {
        super(typeName,baseFacets);
    }
    
    
    public XSDatatype getBaseType() {
        return NumberType.theInstance;
    }
    
    public Object _createValue( String lexicalValue, ValidationContext context ) {
        return IntegerValueType.create(lexicalValue);
    }
    
    public Object _createJavaObject( String literal, ValidationContext context ) {
        IntegerValueType o = (IntegerValueType)_createValue(literal,context);
        if(o==null)        return null;
        return new BigInteger(o.toString());
    }
    
    public static BigInteger load( String s ) {
        IntegerValueType o = IntegerValueType.create(s);
        if(o==null)        return null;
        return new BigInteger(o.toString());
    }
    public static String save( BigInteger v ) {
        return v.toString();
    }
    
    // the default implementation of the serializeJavaObject method works correctly.
    // so there is no need to override it here.
    
    public Class<BigInteger> getJavaObjectType() {
        return BigInteger.class;
    }
    

    
    // serialization support
    private static final long serialVersionUID = 1;    
}
