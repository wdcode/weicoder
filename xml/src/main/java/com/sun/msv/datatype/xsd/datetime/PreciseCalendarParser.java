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

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 
 * 
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public class PreciseCalendarParser extends AbstractCalendarParser {
    public static BigDateTimeValueType parse( String format, String value ) throws IllegalArgumentException {
        PreciseCalendarParser parser = new PreciseCalendarParser(format,value);
        parser.parse();
        return parser.createCalendar();
    }

    private PreciseCalendarParser( String format, String value ) {
        super(format,value);
    }
    
    private BigDateTimeValueType createCalendar() {
        return new BigDateTimeValueType(year,month,day,hour,minute,second,timeZone);
    }
    
    
    private BigInteger year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private BigDecimal second;
    private java.util.TimeZone timeZone;
        

    protected void parseFractionSeconds() {
        int s = vidx;
        BigInteger bi = parseBigInteger(1,Integer.MAX_VALUE);
        BigDecimal d = new BigDecimal(bi,vidx-s);
        if( second==null)   second = d;
        else                second = second.add(d);
    }

    protected void setTimeZone(java.util.TimeZone tz) {
        if(tz==TimeZone.MISSING)    tz=null;
        this.timeZone = tz;
    }

    protected void setSeconds(int i) {
        BigDecimal d = new BigDecimal(BigInteger.valueOf(i));
        if( second==null)   second = d;
        else                second = second.add(d);
    }

    protected void setMinutes(int i) {
        minute = Integer.valueOf(i);
    }

    protected void setHours(int i) {
        hour = Integer.valueOf(i);
    }

    protected void setDay(int i) {
        day = Integer.valueOf(i-1);     // zero origin
    }

    protected void setMonth(int i) {
        month = Integer.valueOf(i-1);   // zero origin
    }

    protected void setYear(int i) {
        year = BigInteger.valueOf(i);
    }
    
}
