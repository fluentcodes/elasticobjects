package org.fluentcodes.projects.elasticobjects.models;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.regex.Pattern;

public class ShapeTypeSerializerString implements ShapeTypeSerializerInterface<String> {
    public static final Pattern NEWLINE_PATTERN = Pattern.compile("\n");
    public static final Pattern ESCAPE_PATTERN = Pattern.compile("\"");
    public static final Pattern REMOVE_PATTERN = Pattern.compile("\r");

    @Override
    public String asJson(String value) {
        String string = NEWLINE_PATTERN
                .matcher(new ShapeTypeSerializerString().asObject(asString(value)))
                .replaceAll("\\\\n");
        string = ESCAPE_PATTERN
                .matcher(string)
                .replaceAll("\\\\\"");
        return "\"" + REMOVE_PATTERN
                .matcher(string)
                .replaceAll("") + "\"";
    }

    @Override
    public String asObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        if (object instanceof byte[]) {
            return new String((byte[])object);
        }
        return asObject(object.toString());
    }

    @Override
    public boolean isValid(final Object object, final FieldConfigProperties properties) {
        if (properties == null) {
            return true;
        }
        if (object == null){
            if (!properties.hasMax() || properties.getMax() == -1) {
                return true;
            }
            /*else if (properties.getLength() > -1) {
                throw new EoException("String value is null and has length " + properties.getLength() + " property.");
            }*/
            else {
                return true;
            }
        }
        String value = (String)object;
        if (properties.hasMax() && properties.getMax() < value.length()) {
            throw new EoException("String value for field '" + value + "' has size " + value.length() + " bigger than max length " + properties.getMax() + ".");
        }
        return true;
    }

    public String asObject(String object, final String defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        return asObject(object);
    }

    public String asObject(String object) {
        if (object == null) {
            return "";
        }
        return object;
    }

}
