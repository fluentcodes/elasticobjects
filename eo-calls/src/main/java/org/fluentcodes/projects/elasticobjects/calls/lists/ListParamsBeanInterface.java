package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.IEOObject;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;
import org.fluentcodes.projects.elasticobjects.domain.BaseInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 13.9.2020.
 */
public interface ListParamsBeanInterface extends ListParamsInterface {
    String LIST_PARAMS = "listParams";

    ListParamsBean getListParams();

    ListParamsBeanInterface setListParams(ListParamsBean listParams);

    String getTargetPath();

    default Integer getRowHead() {
        return getListParams().getRowHead();
    }

    default void setRowHead(Integer rowHead) {
        getListParams().setRowHead(rowHead);
    }

    default boolean hasListParams() {
        return getListParams() != null;
    }

    default void setRowStart(Integer rowStart) {
        getListParams().setRowStart(rowStart);
    }

    default Integer getRowStart() {
        return getListParams().getRowStart();
    }

    default Integer getRowEnd() {
        return getListParams().getRowEnd();
    }

    default void setRowEnd(Integer rowEnd) {
        getListParams().setRowEnd(rowEnd);
    }

    default List<String> getColKeys() {
        return getListParams().getColKeys();
    }

    default void setColKeys(List<String> colKeys) {
        getListParams().setColKeys(colKeys);
    }

    default Integer getLength() {
        return getListParams().getLength();
    }

    default void setLength(Integer rowEnd) {
        getListParams().setRowStart(rowEnd);
    }

    default void setFilter(String filter) {
        getListParams().setFilter(filter);
    }

    default String getFilter() {
        return getListParams().getFilter();
    }

    default String mapEo(final IEOScalar eo, final List filteredResult) {
        if (filteredResult.isEmpty()) {
            return "";
        }
        String targetPath = getTargetPath();
        boolean isMapped = TemplateMarker.SQUARE.hasStartSequence(targetPath);
        if (!isMapped) {
            eo.set(filteredResult, targetPath);
        } else {
            for (int i = 0; i < filteredResult.size(); i++) {
                Object row = filteredResult.get(i);
                if (isMapped) {
                    String target = Parser.replacePathValues(targetPath, EoRoot.ofValue(eo.getConfigMaps(), row));
                    eo.set(row, target);
                }
            }
        }
        if (targetPath != null && targetPath.equals(Call.TARGET_AS_STRING)) {
            return "TODO asString";
        }
        return "";
    }

    default List toList(final IEOScalar eo) {
        List toWrite = new ArrayList();
        if (eo.isEoEmpty()) {
            eo.warn("Empty adapter -- nothing to write for " + eo.getPath());
            return toWrite;
        }
        List<String> keys = null;
        try {
            if (eo instanceof IEOObject) {
                keys = new ArrayList<>(((IEOObject) eo).keysEo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (keys == null || keys.isEmpty()) {
            return toWrite;
        }
        IEOScalar firstChild = null;
        try {
            firstChild = eo.getEo(keys.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            return toWrite;
        }
        if (firstChild == null || firstChild.isEoEmpty()) {
            return toWrite;
        }

        List<String> colKeys = null;
        try {
            colKeys = new ArrayList<>(((IEOObject) firstChild).keysEo());
        } catch (Exception e) {
            e.printStackTrace();
            return toWrite;
        }
        getListParams().prepare();
        int rowHead = getListParams().getRowHead();
        if (rowHead > -1) {
            for (int i = 0; i < rowHead; i++) {
                toWrite.add(new ArrayList<>());
            }
            toWrite.add(colKeys);
        }
        for (String key : keys) {
            IEOScalar child = null;
            try {
                child = eo.getEo(key);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            if (child == null) {
                continue;
            }
            List row = new ArrayList();
            if (child.isEoEmpty()) {
                toWrite.add(row);
                continue;
            }
            for (String colKey : colKeys) {
                try {
                    row.add(child.get(colKey));
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            toWrite.add(row);
        }
        return toWrite;
    }

    default List<List<String>> flattenToStringList(final IEOScalar eo, List values, List<String> keys) {
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

    default String asString(IEOScalar eo, List values, List<String> keys) {
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
