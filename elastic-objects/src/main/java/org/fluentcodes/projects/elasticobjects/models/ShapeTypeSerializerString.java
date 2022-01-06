package org.fluentcodes.projects.elasticobjects.models;
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
