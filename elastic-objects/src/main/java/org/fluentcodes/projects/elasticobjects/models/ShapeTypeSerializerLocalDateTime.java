package org.fluentcodes.projects.elasticobjects.models;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.TimeZone;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

public class ShapeTypeSerializerLocalDateTime implements ShapeTypeSerializerInterface<LocalDateTime> {
    static final LocalDateTime EXCEL_START = LocalDateTime.of(1899, 12, 30, 0, 0);
    @Override
    public String asString(LocalDateTime value) {
        if (value == null) {
            throw new EoException("Null date value ");
        }
        if (value instanceof LocalDateTime) {
            return value.toString();
        }
        throw new EoException("Not instance of LocalDateTime but " + value.getClass());
    }

    @Override
    public String asJson(final LocalDateTime object) {
        return "\"" + asString(object) + "\"";
    }


    public static String defaultDateStringFormat(final LocalDateTime object) {
        return object.getDayOfMonth() + ". " +
            object.getMonth().getValue() + ". " +
            object.getYear() + " " +
            object.getHour() + ":" +
            object.getMinute() ;
    }

    @Override
    public LocalDateTime asObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof LocalDateTime) {
            return (LocalDateTime) object;
        }
        else if (object instanceof Double) { // excel form
            Double doubleValue = (Double) object;
            long days = doubleValue.longValue();
            long seconds = Math.round((doubleValue - days) * 24 * 60 * 60);
            LocalDateTime converted = EXCEL_START
                .plusDays(days)
                .plusSeconds(seconds);
            return converted;
        }
        else if (object instanceof Number) {
            Long longValue = ((Number) object).longValue();
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(longValue), TimeZone.getDefault().toZoneId());
        }
        else if (object instanceof String) {
            try {
                return LocalDateTime.parse((String) object);
            } catch (EoException e) {

            }
        }
        throw new EoInternalException("Not a parsable input object for " + object.getClass());
    }

    @Override
    public LocalDateTime asObject(String object) {
        if (object == null) {
            throw new EoException("Null input string ");
        }
        return LocalDateTime.parse(object);
    }

    @Override
    public boolean isValid(final Object object, final FieldConfigProperties properties) {
        return true;
    }

    @Override
    public String getSqlType(Integer max) {
        return "datetime";
    }
}
