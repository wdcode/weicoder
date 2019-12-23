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
 * "double" type.
 * 
 * type of the value object is <code>java.lang.Double</code>.
 * See http://www.w3.org/TR/xmlschema-2/#double for the spec
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class DoubleType extends FloatingNumberType {
    
    public static final DoubleType theInstance = new DoubleType();
    private DoubleType() { super("double"); }
    
    final public XSDatatype getBaseType() {
        return SimpleURType.theInstance;
    }
    
    public Object _createValue( String lexicalValue, ValidationContext context ) {
        return load(lexicalValue);
    }
    
    public static Double load( String lexicalValue ) {
        // TODO : probably the same problems exist as in the case of float
        try {
            if(lexicalValue.equals("NaN"))    return Double.valueOf(Double.NaN);
            if(lexicalValue.equals("INF"))    return Double.valueOf(Double.POSITIVE_INFINITY);
            if(lexicalValue.equals("-INF"))    return Double.valueOf(Double.NEGATIVE_INFINITY);
            
            if(lexicalValue.length()==0
            || !isDigitOrPeriodOrSign(lexicalValue.charAt(0))
            || !isDigitOrPeriodOrSign(lexicalValue.charAt(lexicalValue.length()-1)) )
                return null;
            
            
            // these screening process is necessary due to the wobble of Float.valueOf method
            return Double.valueOf(lexicalValue);
        } catch( NumberFormatException e ) {
            return null;
        }
    }
    
    public String convertToLexicalValue( Object value, SerializationContext context ) {
        if(!(value instanceof Double ))
            throw new IllegalArgumentException();
        
        return save((Double)value);
    }
    
    public static String save( Double value ) {
        double v = value.doubleValue();
        if (Double.isNaN(v)) return "NaN";
        if (v==Double.POSITIVE_INFINITY) return "INF";
        if (v==Double.NEGATIVE_INFINITY) return "-INF";
        return value.toString();
    }
    public Class<Double> getJavaObjectType() {
        return Double.class;
    }

    // serialization support
    private static final long serialVersionUID = 1;    
}
