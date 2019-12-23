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

import java.util.Collection;
import java.util.Set;

/**
 * "enumeration" facets validator.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class EnumerationFacet extends DataTypeWithValueConstraintFacet {
    protected EnumerationFacet( String nsUri, String typeName, XSDatatypeImpl baseType, Collection _values, boolean _isFixed )
         {
        super(nsUri,typeName,baseType,FACET_ENUMERATION,_isFixed);
        values = new java.util.HashSet( _values );
    }
    
    /** set of valid values */
    public final Set values;

    public Object _createValue( String literal, ValidationContext context ) {
        Object o = baseType._createValue(literal,context);
        if(o==null || !values.contains(o))        return null;
        return o;
    }
    
    protected void diagnoseByFacet(String content, ValidationContext context)  {
        if( _createValue(content,context)!=null )    return;
        
        // TODO: guess which item the user was trying to specify
        
        if( values.size()<=4 ) {
            // if choices are small in number, include them into error messages.
            Object[] members = values.toArray();
            String r="";
            
            if( members[0] instanceof String
            ||  members[0] instanceof Number ) {
                // this will cover 80% of the use case.
                r += "\""+members[0].toString()+"\"";
                for( int i=1; i<members.length; i++ )
                    r+= "/\""+members[i].toString()+"\"";
                
                r = "("+r+")";    // oh, don't tell me I should use StringBuffer.
                
                throw new RuntimeException( 
                    localize(ERR_ENUMERATION_WITH_ARG, r) );
            }
        }
        throw new RuntimeException( 
            localize(ERR_ENUMERATION) );
    }


    // serialization support
    private static final long serialVersionUID = 1;    
}
