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

import java.util.StringTokenizer;

/**
 * List type.
 * 
 * type of the value object is {@link ListValueType}.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public final class ListType extends ConcreteType implements Discrete {
    
    /**
     * derives a new datatype from atomic datatype by list
     */
    public ListType( String nsUri, String newTypeName, XSDatatypeImpl itemType ) {
        super(nsUri,newTypeName);
        
        if(itemType.isFinal( DERIVATION_BY_LIST ))
            // derivation by list is not applicable
            throw new RuntimeException( localize(ERR_INVALID_ITEMTYPE) );
        
        this.itemType = itemType;
    }
    
    /** atomic base type */
    final public XSDatatypeImpl itemType;

    
    public final String displayName() {
        String name = getName();
        if(name!=null)      return name;
        else                return itemType.displayName()+"-list";
    }
    
    /**
     * Variety of the ListType is VARIETY_LIST. So this method always
     * returns VARIETY_LIST.
     */
    public final int getVariety() {
        return VARIETY_LIST;
    }
    
    final public XSDatatype getBaseType() {
        return SimpleURType.theInstance;
    }
    
    /**
     * The list type is context-dependent if its item type is so.
     */
    public boolean isContextDependent() {
        return itemType.isContextDependent();
    }

    public int getIdType() {
        switch(itemType.getIdType()) {
        case ID_TYPE_NULL:        return ID_TYPE_NULL;
        /* we don't support list of IDs.
            The spec of XML Schema Part 1 doesn't seem to support the list of IDs.
            It's probably the corner case of the spec, I guess.
        */
        case ID_TYPE_ID:        return ID_TYPE_NULL;
        case ID_TYPE_IDREF:        return ID_TYPE_IDREFS;
        case ID_TYPE_IDREFS:    return ID_TYPE_IDREFS;
        default:                throw new Error();        // undefined code.
        }
    }
    
    public final boolean isFinal( int derivationType ) {
        // cannot derive by list from list.
        if(derivationType==DERIVATION_BY_LIST)    return true;
        return itemType.isFinal(derivationType);
    }
    
    public final int isFacetApplicable( String facetName ) {
        if( facetName.equals(FACET_LENGTH)
        ||    facetName.equals(FACET_MINLENGTH)
        ||    facetName.equals(FACET_MAXLENGTH)
        ||    facetName.equals(FACET_ENUMERATION)
        ||  facetName.equals(FACET_PATTERN))
            return APPLICABLE;
        else
            return NOT_ALLOWED;
    }
    
    protected final boolean checkFormat( String content, ValidationContext context ) {
        // Are #x9, #xD, and #xA allowed as a separator, or not?
        StringTokenizer tokens = new StringTokenizer(content);
        
        while( tokens.hasMoreTokens() )
            if(!itemType.isValid(tokens.nextToken(),context))    return false;
        
        return true;
    }
    
    public Object _createValue( String content, ValidationContext context ) {
        // StringTokenizer correctly implements the semantics of whiteSpace="collapse"
        StringTokenizer tokens = new StringTokenizer(content);
        
        Object[] values = new Object[tokens.countTokens()];
        int i=0;
        
        while( tokens.hasMoreTokens() ) {
            if( ( values[i++] = itemType._createValue(tokens.nextToken(),context) )==null )
                return null;
        }
            
        return new ListValueType(values);
    }
    public Class<Object[]> getJavaObjectType() {
        return Object[].class;
    }
    
    public final int countLength( Object value ) {
        // for list type, length is a number of items.
        return ((ListValueType)value).values.length;
    }
    
    public String convertToLexicalValue( Object value, SerializationContext context ) {
        if(!(value instanceof ListValueType))
            throw new IllegalArgumentException();
        
        ListValueType lv = (ListValueType)value;
    
        StringBuffer r = new StringBuffer();
        for( int i=0; i<lv.values.length; i++ ) {
            if(i!=0)    r.append(' ');
            r.append( itemType.convertToLexicalValue(lv.values[i],context) );
        }
        return r.toString();
    }
    
    /** The current implementation detects which list item is considered wrong. */
    protected void _checkValid(String content, ValidationContext context) {
        // StringTokenizer correctly implements the semantics of whiteSpace="collapse"
        StringTokenizer tokens = new StringTokenizer(content);
        
        while( tokens.hasMoreTokens() ) {
            String token = tokens.nextToken();
            itemType.checkValid(token,context);
        }
    }


    // serialization support
    private static final long serialVersionUID = 1;    
}
