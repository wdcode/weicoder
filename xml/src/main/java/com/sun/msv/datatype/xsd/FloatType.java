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
 * "float" type.
 * 
 * type of the value object is <code>java.lang.Float</code>.
 * See http://www.w3.org/TR/xmlschema-2/#float for the spec
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class FloatType extends FloatingNumberType {
    
    public static final FloatType theInstance = new FloatType();
    private FloatType() { super("float"); }
    
    final public XSDatatype getBaseType() {
        return SimpleURType.theInstance;
    }
    
    public Object _createValue( String lexicalValue, ValidationContext context ) {
        return load(lexicalValue);
    }
    
    public static Float load( String s ) {
        
        /* Incompatibilities of XML Schema's float "xfloat" and Java's float "jfloat"
        
            * jfloat.valueOf ignores leading and trailing whitespaces,
              whereas this is not allowed in xfloat.
            * jfloat.valueOf allows "float type suffix" (f, F) to be
              appended after float literal (e.g., 1.52e-2f), whereare
              this is not the case of xfloat.
        
            gray zone
            ---------
            * jfloat allows ".523". And there is no clear statement that mentions
              this case in xfloat. Although probably this is allowed.
            * 
        */
        
        try {
            if(s.equals("NaN"))        return Float.valueOf(Float.NaN);
            if(s.equals("INF"))        return Float.valueOf(Float.POSITIVE_INFINITY);
            if(s.equals("-INF"))    return Float.valueOf(Float.NEGATIVE_INFINITY);
            
            if(s.length()==0
            || !isDigitOrPeriodOrSign(s.charAt(0))
            || !isDigitOrPeriodOrSign(s.charAt(s.length()-1)) )
                return null;
            
            // these screening process is necessary due to the wobble of Float.valueOf method
            return Float.valueOf(s);
        } catch( NumberFormatException e ) {
            return null;
        }
    }
    public Class<Float> getJavaObjectType() {
        return Float.class;
    }
    
    public String convertToLexicalValue( Object value, SerializationContext context ) {
        if(!(value instanceof Float ))
            throw new IllegalArgumentException();
        
        return save( (Float)value );
    }
    
    public static String save( Float value ) {
        float v = value.floatValue();
        if (Float.isNaN(v)) return "NaN";
        if (v==Float.POSITIVE_INFINITY) return "INF";
        if (v==Float.NEGATIVE_INFINITY) return "-INF";
        return value.toString();
    }

    // serialization support
    private static final long serialVersionUID = 1;    
}
