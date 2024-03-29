/*
 * $Header$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 *
 * Copyright 2000-2002 bob mcwhirter & James Strachan.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 * 
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 * 
 *   * Neither the name of the Jaxen Project nor the names of its
 *     contributors may be used to endorse or promote products derived 
 *     from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 * This software consists of voluntary contributions made by many 
 * individuals on behalf of the Jaxen Project and was originally 
 * created by bob mcwhirter <bob@werken.com> and 
 * James Strachan <jstrachan@apache.org>.  For more information on the 
 * Jaxen Project, please see <http://www.jaxen.org/>.
 * 
 * $Id$
 */



package org.jaxen.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.jaxen.JaxenConstants;
import org.jaxen.Navigator;
import org.jaxen.UnsupportedAxisException;

/**
 * 
 * Represents the XPath <code>preceding-sibling</code> axis. 
 * The "<code>preceding-sibling</code> axis contains all the
 * preceding siblings of the context node; if the context node is an
 * attribute node or namespace node, the <code>preceding-sibling</code>
 * axis is empty."
 * 
 * @version 1.2b12
 *
 */
public class PrecedingSiblingAxisIterator implements Iterator<Object>
{
    private Object    contextNode;
    private Navigator navigator;

    private Iterator<Object>  siblingIter;
    private Object    nextObj;

    /**
     * Create a new <code>preceding-sibling</code> axis iterator.
     * 
     * @param contextNode the node to start from
     * @param navigator the object model specific navigator
     */
    public PrecedingSiblingAxisIterator(Object contextNode,
                                        Navigator navigator) throws UnsupportedAxisException
    {
        this.contextNode = contextNode;
        this.navigator   = navigator;

        init();
        if ( siblingIter.hasNext() )
        {
            this.nextObj = siblingIter.next();
        }
    }

    private void init() throws UnsupportedAxisException
    {
        
        Object parent = this.navigator.getParentNode( this.contextNode );

        if ( parent != null )
        {
            Iterator<?> childIter = this.navigator.getChildAxisIterator( parent );
            LinkedList<Object> siblings = new LinkedList<>();
            
            while ( childIter.hasNext() )
            {
                Object eachChild = childIter.next();
                if ( eachChild.equals(this.contextNode) )
                {
                    break;
                }
                siblings.addFirst( eachChild );
            }
            
            this.siblingIter = siblings.iterator();
            
        }
        else {
            this.siblingIter = JaxenConstants.EMPTY_ITERATOR;
        }
        
    }

    /**
     * Returns true if there are any preceding siblings remaining; false otherwise.
     * 
     * @return true if any preceding siblings remain; false otherwise
     * 
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext()
    {
        return ( this.nextObj != null );
    }

    /**
     * Returns the next preceding sibling.
     * 
     * @return the next preceding sibling
     * 
     * @throws NoSuchElementException if no preceding siblings remain
     * 
     * @see java.util.Iterator#next()
     */
    public Object next() throws NoSuchElementException
    {
        if ( ! hasNext() )
        {
            throw new NoSuchElementException();
        }

        Object obj = this.nextObj;
        if ( siblingIter.hasNext() )
        {
            this.nextObj = siblingIter.next();
        }
        else {
            this.nextObj = null;
        }
        return obj;
    }

    /**
     * This operation is not supported.
     * 
     * @throws UnsupportedOperationException
     */
    public void remove() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

}
