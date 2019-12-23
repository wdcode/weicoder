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
 * "length", "minLength", and "maxLength" facet validator.
 * 
 * this class also detects inconsistent facet setting
 * (for example, minLength=100 and maxLength=0)
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class LengthFacet extends DataTypeWithValueConstraintFacet {
    public final int length;
    
    protected LengthFacet( String nsUri, String typeName, XSDatatypeImpl baseType, TypeIncubator facets )
         {
        this(nsUri,typeName,baseType,
            facets.getNonNegativeInteger(FACET_LENGTH),
            facets.isFixed(FACET_LENGTH));
    }
                
    protected LengthFacet( String nsUri, String typeName, XSDatatypeImpl baseType, int _length, boolean _isFixed )
         {
        super(nsUri,typeName,baseType,FACET_LENGTH,_isFixed);
    
        length = _length;
        
        // loosened facet check
        DataTypeWithFacet o = baseType.getFacetObject(FACET_LENGTH);
        if(o!=null && ((LengthFacet)o).length != this.length )
            throw new RuntimeException(
                localize(ERR_LOOSENED_FACET, FACET_LENGTH, o.displayName() ) );
        
        // consistency with minLength/maxLength is checked in XSDatatypeImpl.derive method.
    }
    
    public Object _createValue( String content, ValidationContext context ) {
        Object o = baseType._createValue(content,context);
        if(o==null || ((Discrete)concreteType).countLength(o)!=length)    return null;
        return o;
    }
    
    protected void diagnoseByFacet(String content, ValidationContext context)  {
        Object o = concreteType._createValue(content,context);
        // base type must have accepted this lexical value, otherwise 
        // this method is never called.
        if(o==null)    throw new IllegalStateException();    // assertion
        
        int cnt = ((Discrete)concreteType).countLength(o);
        if(cnt!=length)
            throw new RuntimeException(localize(ERR_LENGTH, cnt, length) );
    }

    // serialization support
    private static final long serialVersionUID = 1;    
}
