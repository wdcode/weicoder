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
 * Formats a {@link Calendar} object to a String.
 * 
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public final class CalendarFormatter extends AbstractCalendarFormatter {
    
    private CalendarFormatter() {} // no instanciation
    
    private static final CalendarFormatter theInstance = new CalendarFormatter();
    
    public static String format( String format, Calendar cal ) {
        return theInstance.doFormat(format,cal);
    }
    
    protected Calendar toCalendar(Object cal) {
        return (Calendar)cal;
    }

    protected void formatYear(Object cal, StringBuffer buf) {
        int year = ((Calendar)cal).get(Calendar.YEAR);
        
        String s;
        if (year <= 0) // negative value
            s = Integer.toString(1 - year);
        else // positive value
            s = Integer.toString(year);

        while (s.length() < 4)
            s = "0" + s;
        if (year <= 0)
            s = "-" + s;
        
        buf.append(s);
    }

    protected void formatMonth(Object cal, StringBuffer buf) {
        formatTwoDigits(((Calendar)cal).get(Calendar.MONTH)+1,buf);
    }

    protected void formatDays(Object cal, StringBuffer buf) {
        formatTwoDigits(((Calendar)cal).get(Calendar.DAY_OF_MONTH),buf);
    }

    protected void formatHours(Object cal, StringBuffer buf) {
        formatTwoDigits(((Calendar)cal).get(Calendar.HOUR_OF_DAY),buf);
    }

    protected void formatMinutes(Object cal, StringBuffer buf) {
        formatTwoDigits(((Calendar)cal).get(Calendar.MINUTE),buf);
    }

    protected void formatSeconds(Object _cal, StringBuffer buf) {
        Calendar cal = (Calendar)_cal;
        formatTwoDigits(cal.get(Calendar.SECOND),buf);
        if (cal.isSet(Calendar.MILLISECOND)) { // milliseconds
            int n = cal.get(Calendar.MILLISECOND);
            if(n!=0) {
                String ms = Integer.toString(n);
                while (ms.length() < 3)
                    ms = "0" + ms; // left 0 paddings.

                buf.append('.');
                buf.append(ms);
            }
        }
    }

}
