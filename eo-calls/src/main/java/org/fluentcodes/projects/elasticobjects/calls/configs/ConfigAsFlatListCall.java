package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleWriteCall;
import org.fluentcodes.projects.elasticobjects.domain.BaseInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*.{javaHeader}|*/

/**
 * Creates a flat list from all loaded configurations for a specific configType.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 09:31:09 CET 2020
 */
public class ConfigAsFlatListCall extends CallImpl implements SimpleCommand {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    public static final String CONFIG_TYPE = "configType";
    public static final String FIELD_KEYS = "fieldKeys";
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    private String configType;
    private List<String> fieldKeys;
    /*.{}.*/

    public ConfigAsFlatListCall() {
        super();
    }

    public ConfigAsFlatListCall setFieldKeys(String... fieldKeys) {
        this.fieldKeys = Arrays.asList(fieldKeys);
        return this;
    }

    @Override
    public String execute(IEOScalar eo) {
        List<Object> resultAsListMap = (List) new ConfigCall()
                .setConfigType(configType)
                .execute(eo);
        return asString(eo, resultAsListMap, fieldKeys);
    }

    /*.{javaAccessors}|*/

    /**
     * Key for configuration type like ModelConfig, FileConfig, FieldConfig, HostConfig, DbSqlConfig.
     */

    public ConfigAsFlatListCall setConfigType(String configType) {
        this.configType = configType;
        return this;
    }

    public String getConfigType() {
        return this.configType;
    }

    public boolean hasConfigType() {
        return configType != null && !configType.isEmpty();
    }

    /**
     * A list of field keys for the model configuration.
     */

    public ConfigAsFlatListCall setFieldKeys(List<String> fieldKeys) {
        this.fieldKeys = fieldKeys;
        return this;
    }

    public List<String> getFieldKeys() {
        return this.fieldKeys;
    }

    public boolean hasFieldKeys() {
        return fieldKeys != null && !fieldKeys.isEmpty();
    }
    /*.{}.*/

    static List<List<String>> flattenToStringList(final IEOScalar eo, List values, List<String> keys) {
        List<List<String>> result = new ArrayList<>();
        Map<String, Integer> keyPosition = new LinkedHashMap<>();
        boolean externalKey = true;
        if (keys == null || keys.isEmpty()) {
            keyPosition.put(BaseInterface.F_NATURAL_ID, 0);
            externalKey = false;
        } else {
            for (int i = 0; i < keys.size(); i++) {
                keyPosition.put(keys.get(i), i);
            }
        }
        int keyMap = 1;
        for (Object row : values) {
            List<String> rowList = new ArrayList<>(Collections.nCopies(keyPosition.size(), ""));
            Map<String, Object> valueMap = (Map<String, Object>) row;

            for (String key : valueMap.keySet()) {
                Object valueMapValue = valueMap.get(key);
                if (valueMapValue == null) {
                    continue;
                }
                String value = null;
                if (valueMapValue instanceof String) {
                    value = (String) valueMapValue;
                } else if (valueMapValue instanceof Enum) {
                    value = ((Enum) valueMapValue).toString();
                } else if ((valueMapValue instanceof Map)) {
                    if (((Map) valueMapValue).isEmpty()) {
                        value = "";
                    } else {
                        value = new EOToJSON().setSerializationType(JSONSerializationType.STANDARD).toJson(eo.getConfigMaps(), valueMapValue);
                    }
                } else if ((valueMapValue instanceof List)) {
                    if (((List) valueMapValue).isEmpty()) {
                        value = "";
                    } else {
                        value = new EOToJSON().setSerializationType(JSONSerializationType.STANDARD).toJson(eo.getConfigMaps(), valueMapValue);
                    }
                } else if ((valueMapValue instanceof Date) || (valueMapValue instanceof Integer) || (valueMapValue instanceof Float) || (valueMapValue instanceof Double) || (valueMapValue instanceof Long)) {
                    value = valueMapValue.toString();
                } else {
                    value = new EOToJSON().setSerializationType(JSONSerializationType.STANDARD).toJson(eo.getConfigMaps(), valueMapValue);
                }
                try {
                    if (!keyPosition.containsKey(key)) {
                        if (!externalKey) {
                            keyPosition.put(key, keyMap);
                            keyMap++;
                            rowList.add(value);
                        }
                    } else {
                        rowList.set(keyPosition.get(key), value);
                    }
                } catch (Exception e) {
                    System.out.println();
                }
            }
            result.add(rowList);
        }
        result.add(0, new ArrayList<>(keyPosition.keySet()));
        return result;
    }

    static String asString(IEOScalar eo, List values, List<String> keys) {
        List<List<String>> flattened = flattenToStringList(eo, values, keys);
        int max = flattened.get(0).size();
        StringBuilder builder = new StringBuilder();
        for (List<String> row : flattened) {
            for (int i = 0; i < row.size(); i++) {
                builder.append("\"");
                builder.append(
                        row.get(i)
                                .replaceAll("\"", "\"\"")
                                .replaceAll("\n", "\r"));
                builder.append("\"");
                if (i < max) {
                    builder.append(";");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
