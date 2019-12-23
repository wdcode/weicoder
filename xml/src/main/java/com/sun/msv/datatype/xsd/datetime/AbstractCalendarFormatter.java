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

import java.util.Calendar;

/**
 * 
 * 
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
abstract class AbstractCalendarFormatter {
    public String doFormat( String format, Object cal ) throws IllegalArgumentException {
        int fidx = 0;
        int flen = format.length();
        StringBuffer buf = new StringBuffer();
        
        while(fidx<flen) {
            char fch = format.charAt(fidx++);
            
            if(fch!='%') {  // not a meta character
                buf.append(fch);
                continue;
            }
            
            // seen meta character. we don't do error check against the format
            switch (format.charAt(fidx++)) {
            case 'Y' : // year
                formatYear(cal, buf);
                break;

            case 'M' : // month
                formatMonth(cal, buf);
                break;

            case 'D' : // days
                formatDays(cal, buf);
                break;

            case 'h' : // hours
                formatHours(cal, buf);
                break;

            case 'm' : // minutes
                formatMinutes(cal, buf);
                break;

            case 's' : // parse seconds.
                formatSeconds(cal, buf);
                break;

            case 'z' : // time zone
                formatTimeZone(cal,buf);
                break;
                
            default :
                // illegal meta character. impossible.
                throw new InternalError();
            }
        }
        
        return buf.toString();
    }

    
    protected abstract Calendar toCalendar( Object cal );
    protected abstract void formatYear( Object cal, StringBuffer buf );
    protected abstract void formatMonth( Object cal, StringBuffer buf );
    protected abstract void formatDays( Object cal, StringBuffer buf );
    protected abstract void formatHours( Object cal, StringBuffer buf );
    protected abstract void formatMinutes( Object cal, StringBuffer buf );
    protected abstract void formatSeconds( Object cal, StringBuffer buf );
    
    /** formats time zone specifier. */
    private void formatTimeZone(Object _cal,StringBuffer buf) {
        Calendar cal = toCalendar(_cal);
        java.util.TimeZone tz = cal.getTimeZone();

        // TODO: is it possible for the getTimeZone method to return null?
        if (tz == null)      return;
        
        // look for special instances
        if(tz==TimeZone.MISSING)    return;
        if(tz==TimeZone.ZERO) {
            buf.append('Z');
            return;
        }
        
        // otherwise print out normally.
        int offset;
        if (tz.inDaylightTime(cal.getTime())) {
            offset = tz.getRawOffset() + (tz.useDaylightTime()?3600000:0);
        } else {
            offset = tz.getRawOffset();
        }

        if (offset >= 0)
            buf.append('+');
        else {
            buf.append('-');
            offset *= -1;
        }

        offset /= 60 * 1000; // offset is in milli-seconds

        formatTwoDigits(offset / 60, buf);
        buf.append(':');
        formatTwoDigits(offset % 60, buf);
    }
    
    /** formats Integer into two-character-wide string. */
    protected final void formatTwoDigits(int n,StringBuffer buf) {
        // n is always non-negative.
        if (n < 10) buf.append('0');
        buf.append(n);
    }
    
}
