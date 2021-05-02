package org.fluentcodes.projects.elasticobjects.models;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelBeanGen extends ModelBean implements ModelBeanInterface4Java {
    private static final Pattern linkPattern = Pattern.compile("@([^\\s\\.,]*)([\\s\\.,])");
    public ModelBeanGen() {
        super();
    }
    public ModelBeanGen(final String key) {
        this();
        setNaturalId(key);
    }

    public ModelBeanGen(final String key, final Map map) {
        super(key, map);
    }

    public ModelBeanGen(final ModelConfig config) {
        super(config);
    }

    public static String replaceLinks(final String comment) {
        String result = comment.replaceAll("\n", "\n * ");
        if (!result.contains("@")) {
            return result;
        }
        StringBuilder replaced = new StringBuilder();
        Matcher matcher = linkPattern.matcher(comment);
        int end = 0;
        while (matcher.find()) {
            replaced.append(comment.substring(end, matcher.start()));
            end = matcher.end();
            replaced.append("{@link " + matcher.group(1) + "}");
            replaced.append(matcher.group(2));
        }
        replaced.append(comment.substring(end, comment.length()));
        return replaced.toString();
    }

    public ModelBeanGen(final Map values) {
        super(values);
    }

    @Override
    protected void addField(final String fieldKey) {
        addField(new FieldBeanGen(fieldKey));
    }

    @Override
    protected void addField(final FieldConfig fieldConfig) {
        addField(new FieldBeanGen(fieldConfig));
    }

    @Override
    protected void addField(String fieldKey, final Map<String, Object> fieldMap) {
        addField(new FieldBeanGen(fieldKey,  fieldMap));
    }
}
