package com.tomaston.etastocks.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Date-time conversion class for converting string to unix dates and vice versa
 */
@Slf4j
public class DateTimeConverter {

    private static Logger log = LoggerFactory.getLogger(DateTimeConverter.class);

    /**
     * @param stringDate in the format YYYY-MM-DD
     * @return unix date time in milliseconds
     */
    public static Long stringToUnix(String stringDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            final Date date = dateFormat.parse(stringDate);
            return date.getTime();
        } catch (ParseException ex) {
            log.warn("Parsing Alpha Vantage string date - format not recognised: {}", stringDate);
            return null;
        }
    }
}
