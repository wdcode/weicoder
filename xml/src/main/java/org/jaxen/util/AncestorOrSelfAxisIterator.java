package org.jaxen.util;

/*
 * $Header$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 *
 * Copyright 2000-2005 bob mcwhirter & James Strachan.
 * All rights reserved.
 *
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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.jaxen.Navigator;
import org.jaxen.UnsupportedAxisException;
import org.jaxen.JaxenRuntimeException;

/**
 * <p>
 * Represents the XPath <code>ancestor-or-self</code> axis. 
 * The "<code>ancestor-or-self</code> axis contains the context node and 
 * the ancestors of the context node; thus, the ancestor axis will 
 * always include the root node."
 * </p>
 * 
 * @version 1.2b12
 */
public class AncestorOrSelfAxisIterator implements Iterator<Object>
{
    
    private Object    contextNode;
    private Navigator navigator;

    /**
     * Create a new <code>ancestor-or-self</code> axis iterator.
     * 
     * @param contextNode the node to start from
     * @param navigator the object model specific navigator
     */
    public AncestorOrSelfAxisIterator(Object contextNode,
                                      Navigator navigator)
    {
        // XXX should we throw a NullPointerException here if contextNode is null?
        this.contextNode = contextNode;
        this.navigator = navigator;
    }

    /**
     * Returns true if there are any nodes remaining 
     * on the ancestor-or-self axis; false otherwise.
     * 
     * @return true if any ancestors or self remain
     * 
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext()
    {
        return contextNode != null;
    }

    /**
     * Returns the next ancestor-or-self node.
     * 
     * @return the next ancestor-or-self node
     * 
     * @throws NoSuchElementException if no ancestors remain
     * 
     * @see java.util.Iterator#next()
     */
    public Object next()
    {
        try
        {
            if (hasNext()) {
                Object result = contextNode;
                contextNode = navigator.getParentNode(contextNode);
                return result;
            }
            throw new NoSuchElementException("Exhausted ancestor-or-self axis");
        }
        catch (UnsupportedAxisException e)
        {
            throw new JaxenRuntimeException(e);
        }
    }

    /**
     * This operation is not supported.
     * 
     * @throws UnsupportedOperationException always
     */
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
    
}
