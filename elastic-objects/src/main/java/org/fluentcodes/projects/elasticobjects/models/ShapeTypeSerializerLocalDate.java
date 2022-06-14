package org.fluentcodes.projects.elasticobjects.models;

import static org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerLocalDateTime.EXCEL_START;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

public class ShapeTypeSerializerLocalDate implements ShapeTypeSerializerInterface<LocalDate> {
    public static final Pattern D_M_PATTERN = Pattern.compile("(\\d{1,2})[\\.\\-]\\s*(\\d{1,2})[\\.]*");
    @Override
    public String asString(LocalDate value) {
        if (value == null) {
            throw new EoException("Null date value ");
        }
        if (value instanceof LocalDate) {
            return value.toString();
        }
        throw new EoException("Not instance of LocalDate but " + value.getClass());
    }

    @Override
    public String asJson(final LocalDate object) {
        return "\"" + asString(object) + "\"";
    }

    @Override
    public LocalDate asObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof LocalDate) {
            return (LocalDate) object;
        }
        if (object instanceof String) {
            try {
                return LocalDate.parse((String) object);
            } catch (Exception e) {
               Matcher mdMatcher = D_M_PATTERN.matcher((String)object);
               if (mdMatcher.find()) {
                   int day = Integer.parseInt(mdMatcher.group(1));
                   int month = Integer.parseInt(mdMatcher.group(2));
                   return LocalDate.of(LocalDate.now().getYear(), month, day);
               }
            }
        }
        if (object instanceof Double) { // Excel
            Double doubleValue = (Double) object;
            long days = doubleValue.longValue();
            LocalDate converted = EXCEL_START.toLocalDate()
                .plusDays(days);
            return converted;
        }
        throw new EoInternalException(object.getClass() + " is not a parsable input object for LocalDate.");
    }

    @Override
    public LocalDate asObject(String object) {
        if (object == null) {
            throw new EoException("Null input string ");
        }
        return LocalDate.parse(object);
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
