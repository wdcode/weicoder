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
 * "language" type.
 * 
 * type of the value object is <code>java.lang.String</code>.
 * See http://www.w3.org/TR/xmlschema-2/#language for the spec
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class LanguageType extends TokenType {
    
    public static final LanguageType theInstance = new LanguageType();
    private LanguageType() { super("language",false); }
    
    final public XSDatatype getBaseType() {
        return TokenType.theInstance;
    }
    
    public Object _createValue( String content, ValidationContext context ) {
        /*    RFC1766 defines the following BNF
        
             Language-Tag = Primary-tag *( "-" Subtag )
             Primary-tag = 1*8ALPHA
             Subtag = 1*8ALPHA

            Whitespace is not allowed within the tag.
            All tags are to be treated as case insensitive.
        */
        
        final int len = content.length();
        int i=0; int tokenSize=0;
        
        while( i<len ) {
            final char ch = content.charAt(i++);
            if( ('a'<=ch && ch<='z') || ('A'<=ch && ch<='Z') ) {
                tokenSize++;
                if( tokenSize==9 )
                    return null;    // maximum 8 characters are allowed.
            } else
            if( ch=='-' ) {
                if( tokenSize==0 )    return null;    // at least one alphabet preceeds '-'
                tokenSize=0;
            } else
                return null;    // invalid characters
        }
        
        if( tokenSize==0 )    return null;    // this means either string is empty or ends with '-'
        
        return content.toLowerCase();
    }

    // serialization support
    private static final long serialVersionUID = 1;    
}
