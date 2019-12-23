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

package com.sun.msv.datatype.xsd.datetime;

import java.io.Serializable;
import java.util.SimpleTimeZone;

/**
 * simple time zone component.
 * 
 * @author Kohsuke KAWAGUCHI
 */
public class TimeZone implements java.io.Serializable {
    /**
     * Difference from GMT in terms of minutes.
     * @deprecated here just for the serialization backward compatibility.
     */
    public int minutes;

    private Object readResolve() {
        // use java.util.TimeZone instead
        return new SimpleTimeZone(minutes*60*1000,"");
    }
    
    /**
     * The {@link java.util.TimeZone} representation that corresponds
     * to the ZERO singleton instance. Once again, using a special
     * instance is a hack to make the round-tripping work OK.
     */
    public static final java.util.TimeZone ZERO = new JavaZeroTimeZone();
    
    /**
     * The {@link java.util.TimeZone} representation that corresponds
     * to the missing time zone.
     */
    public static final java.util.TimeZone MISSING = new JavaMissingTimeZone();
    
    
    // serialization support
    private static final long serialVersionUID = 1;    
    
    
//
// nested inner classes
//    
    /**
     * @deprecated
     *      exists just for the backward serialization compatibility.
     */
    static class ZeroTimeZone extends TimeZone {
        ZeroTimeZone() {
        }
        protected Object readResolve() {
            // use the singleton instance
            return ZERO;
        }
        // serialization support
        private static final long serialVersionUID = 1;    
    }
    
    private static class JavaZeroTimeZone extends SimpleTimeZone implements Serializable {
        JavaZeroTimeZone() {
            super(0, "XSD 'Z' timezone");
        } 
        protected Object readResolve() {
            return ZERO;
        }
        // serialization support
        private static final long serialVersionUID = 1;    
    }
    
    private static class JavaMissingTimeZone extends SimpleTimeZone implements Serializable {
        JavaMissingTimeZone() {
            super(0, "XSD missing timezone");
        } 
        protected Object readResolve() {
            return MISSING;
        }
        // serialization support
        private static final long serialVersionUID = 1;    
    }
}
