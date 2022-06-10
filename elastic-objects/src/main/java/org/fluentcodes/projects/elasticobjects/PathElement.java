package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * PathElement encapsulate eo local values of eo and its parent.
 */
public class PathElement {
    public static final Pattern modelPattern = Pattern.compile("^\\(([^\\)]*)\\)(.*)");
    public static final String BACK = "..";
    public static final String V_SAME = ".";
    public static final String MATCHER = "*";
    public static final String MATCHER_ALL = "+";
    public static final String V_MODEL = "_rootmodel";
    public static final String LOGS = "_logs";
    public static final String ERROR_LEVEL = "_errorLevel";
    public static final String SERIALIZATION_TYPE = "_serializationType";
    public static final String LOG_LEVEL = "_logLevel";
    public static final String CALLS = "_calls";
    public static final String TEMPLATE = "_template";
    protected static final Map<String, String[]> keyClassMap = initKeyClassMap();

    public static final PathElement OF_ROOT_MODEL = new PathElement(V_MODEL);
    public static final PathElement OF_ERROR_LEVEL = new PathElement(ERROR_LEVEL);
    public static final PathElement OF_LOG_LEVEL = new PathElement(LOG_LEVEL);
    public static final PathElement OF_LOGS = new PathElement(LOGS, List.class);
    public static final PathElement OF_SERIALIZATION_TYPE = new PathElement(SERIALIZATION_TYPE, JSONSerializationType.class);

    private final String key;
    private final String[] modelsArray;
    private Models models;

    public PathElement(final String compositionKey) {
        this(compositionKey, (Class<?>[]) null);
    }

    public PathElement(final String compositionKey, Class<?>... modelClasses) {
        if (PathElement.V_MODEL.equals(compositionKey)) {
            this.key = compositionKey;
            this.modelsArray = keyClassMap.get(key);
            return;
        }

        final Matcher matcher = PathElement.modelPattern.matcher(compositionKey);
        if (matcher.find()) {
            String modelString = matcher.group(1);
            this.key = matcher.group(2);
            if (keyClassMap.containsKey(key)) {
                this.modelsArray = keyClassMap.get(key);
                return;
            }
            if (modelString != null && !modelString.isEmpty()) {
                this.modelsArray = modelString.split(",");
            } else {
                this.modelsArray = new String[]{};
            }
            return;
        }

        this.key = compositionKey;
        if (modelClasses == null || modelClasses.length == 0) {
            this.modelsArray = keyClassMap.get(key);
            return;
        }
        this.modelsArray = new String[modelClasses.length];
        for (int i = 0; i < modelClasses.length; i++) {
            this.modelsArray[i] = modelClasses[i].getSimpleName();
        }
    }

    private static final Map<String, String[]> initKeyClassMap() {
        final Map<String, String[]> keyEnumMap = new HashMap<>();
        keyEnumMap.put(LOG_LEVEL, new String[]{LogLevel.class.getSimpleName()});
        keyEnumMap.put(LOGS, new String[]{List.class.getSimpleName()});
        keyEnumMap.put(CALLS, new String[]{List.class.getSimpleName()});
        keyEnumMap.put(SERIALIZATION_TYPE, new String[]{JSONSerializationType.class.getSimpleName()});
        keyEnumMap.put(ERROR_LEVEL, new String[]{LogLevel.class.getSimpleName()});
        keyEnumMap.put(V_MODEL, new String[]{String.class.getSimpleName()});
        return keyEnumMap;
    }

    /**
     * fieldnames starting with underscores will not mapped to a parent object.
     *
     * @param key the fieldName
     * @return true if empty or starting with "_"
     */
    public static boolean isParentNotSet(final String key) {
        return key == null || key.startsWith("_");
    }

    public static boolean isParentSet(final String key) {
        return !isParentNotSet(key);
    }

    public boolean isParentNotSet() {
        return isParentNotSet(this.key);
    }

    public boolean isParentSet() {
        return !isParentNotSet();
    }

    protected Models getModels() {
        return models;
    }

    protected boolean isCallDirectory() {
        return CALLS.equals(this.key);
    }

    protected boolean isSerializationType() {
        return SERIALIZATION_TYPE.equals(this.key);
    }

    public boolean isBack() {
        return BACK.equals(key);
    }

    public boolean isSame() {
        return V_SAME.equals(key);
    }

    public boolean isFilter() {
        return key.contains(MATCHER) || key.contains(MATCHER_ALL);
    }

    public String[] getModelsArray() {
        return modelsArray;
    }

    public Models getModels(ConfigMaps cache) {
        if (hasModelArray()) {
            return new Models(cache, modelsArray);
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public boolean hasKey() {
        return key != null && !key.isEmpty();
    }

    public boolean hasModelArray() {
        return modelsArray != null && modelsArray.length > 0;
    }

    public boolean isRootModel() {
        return V_MODEL.equals(getKey());
    }

    @Override
    public String toString() {
        if (!hasModelArray()) {
            return key;
        }
        return "(" + Arrays.stream(modelsArray).collect(Collectors.joining(",")) + ")" + key;
    }
}
