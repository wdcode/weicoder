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

package org.jaxen.expr;

import java.util.Iterator;
import java.util.List;
import org.jaxen.Context;
import org.jaxen.JaxenException;
import org.jaxen.Navigator;
import org.jaxen.function.BooleanFunction;
import org.jaxen.function.NumberFunction;
import org.jaxen.function.StringFunction;

abstract class DefaultEqualityExpr extends DefaultTruthExpr implements EqualityExpr 
  {
  private static final long serialVersionUID = 1L;

DefaultEqualityExpr( Expr lhs, Expr rhs )
    {
    super( lhs, rhs );
    }

  public String toString()
    {
    return "[(DefaultEqualityExpr): " + getLHS() + ", " + getRHS() + "]";
    }
  
  @SuppressWarnings({ "unchecked", "deprecation" })
public Object evaluate( Context context ) throws JaxenException
    {
    Object lhsValue = getLHS().evaluate( context );
    Object rhsValue = getRHS().evaluate( context );
    
    if( lhsValue == null || rhsValue == null ) {
      return Boolean.FALSE;
    }
    
    Navigator nav = context.getNavigator();

    if( bothAreSets(lhsValue, rhsValue) ) {
      return evaluateSetSet( (List<Object>) lhsValue, (List<Object>) rhsValue, nav );
    }
    else if (isSet(lhsValue ) && isBoolean(rhsValue)) {
        Boolean lhsBoolean = ((List<Object>) lhsValue).isEmpty() ? Boolean.FALSE : Boolean.TRUE;
        Boolean rhsBoolean = (Boolean) rhsValue;
        return Boolean.valueOf(evaluateObjectObject( lhsBoolean, rhsBoolean, nav ) );
    }
    else if (isBoolean(lhsValue ) && isSet(rhsValue)) {
        Boolean lhsBoolean = (Boolean) lhsValue;
        Boolean rhsBoolean = ((List<Object>) rhsValue).isEmpty() ? Boolean.FALSE : Boolean.TRUE;
        return Boolean.valueOf(evaluateObjectObject( lhsBoolean, rhsBoolean, nav ) );
    }
    else if (eitherIsSet(lhsValue, rhsValue) ) {
      if (isSet(lhsValue)) {
        return evaluateSetSet( (List<Object>) lhsValue, convertToList(rhsValue), nav );                
      }
      else {
        return evaluateSetSet( convertToList(lhsValue), (List<Object>) rhsValue, nav );                                
      }
    }  
    else {
      return Boolean.valueOf(evaluateObjectObject( lhsValue, rhsValue, nav ) );
    }    
  }
  
  private Boolean evaluateSetSet( List<Object> lhsSet, List<Object> rhsSet, Navigator nav )
    {
      /* If both objects to be compared are node-sets, then the comparison will be 
       * true if and only if there is a node in the first node-set and a node in 
       * the second node-set such that the result of performing the comparison on 
       * the string-values of the two nodes is true */
      if( setIsEmpty( lhsSet ) || setIsEmpty( rhsSet ) ) {
            return Boolean.FALSE;
      }
    
    for( Iterator<Object> lhsIterator = lhsSet.iterator(); lhsIterator.hasNext(); )
      {
      Object lhs = lhsIterator.next();
      
      for( Iterator<Object> rhsIterator = rhsSet.iterator(); rhsIterator.hasNext(); )
        {
        Object rhs = rhsIterator.next();
        
        if( evaluateObjectObject( lhs, rhs, nav ) )
          {
          return Boolean.TRUE;
          }
        }
      }
    
    return Boolean.FALSE;
    }
  
  private boolean evaluateObjectObject( Object lhs, Object rhs, Navigator nav )
    {
    if( eitherIsBoolean( lhs, rhs ) )
      {            
      return evaluateObjectObject( BooleanFunction.evaluate( lhs, nav ),
                                   BooleanFunction.evaluate( rhs, nav ) );
      }
    else if( eitherIsNumber( lhs, rhs ) )
      {
      return evaluateObjectObject( NumberFunction.evaluate( lhs,
                                                            nav ),
                                   NumberFunction.evaluate( rhs,
                                                            nav ) );                                              
      }
    else
      {
      return evaluateObjectObject( StringFunction.evaluate( lhs,
                                                            nav ),
                                   StringFunction.evaluate( rhs,
                                                            nav ) );
      }
    }
  
  protected abstract boolean evaluateObjectObject( Object lhs, Object rhs );
  }
