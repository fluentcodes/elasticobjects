package org.fluentcodes.projects.elasticobjects.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ShapeTypeSerializerDate implements ShapeTypeSerializerInterface<Date> {
    private static final Set<SimpleDateFormat> DATE_FORMAT_SET = createDateFormatSet();

    private static Set<SimpleDateFormat> createDateFormatSet() {
        Set<SimpleDateFormat> dateFormatSet = new HashSet<>();
        dateFormatSet.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        dateFormatSet.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        dateFormatSet.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        return dateFormatSet;
    }
    @Override
    public String asString(Date value) {
        if (value == null) {
            throw new EoException("Null date value ");
        }
        if (value instanceof Date) {
            return Long.toString(value.getTime());
        }
        throw new EoException("Not instance of Date but " + value.getClass());
    }

    @Override
    public String asJson(final Date object) {
        return asString(object);
    }

    @Override
    public Date asObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Date) {
            return (Date) object;
        }
        if (object instanceof Number) {
            Long longValue = ((Number)object).longValue();
            return new Date(longValue);
        }
        if (object instanceof LocalDateTime) {
            return Timestamp.valueOf((LocalDateTime) object);
        }
        if (object instanceof String) {
            try{
                return new Date(new ShapeTypeSerializerLong().asObject(object));
            }
            catch (EoException e) {

            }
            for (SimpleDateFormat dateFormat: DATE_FORMAT_SET) {
                try {
                    return dateFormat.parse((String)object);
                } catch (ParseException e) {

                }
            }
        }
        throw new EoInternalException("Not a parsable input object for " + object.getClass());
    }

    @Override
    public Date asObject(String object) {
        if (object == null) {
            throw new EoException("Null input string ");
        }
        return new Date();
    }

    @Override
    public boolean isValid(final Object object, final FieldConfigProperties properties) {
        return true;
    }

    @Override
    public String getSqlType(Integer max) {
        return "date";
    }
}
