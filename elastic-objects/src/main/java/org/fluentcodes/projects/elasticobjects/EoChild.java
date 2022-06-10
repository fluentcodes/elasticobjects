package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.FieldInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.fluentcodes.projects.elasticobjects.Path.DELIMITER;

public class EoChild extends EoChildScalar implements EO {
    private Map<String, EOInterfaceScalar> eoMap;
    private Object fieldValue;

    EoChild(final Object value, final Models models) {
        super(value, models);
        eoMap = new LinkedHashMap<>();
    }

    public EoChild(final EO parentEo, final String fieldKey, final Object value, final Models fieldModels) {
        super(parentEo, fieldKey, value, fieldModels);
    }

    void setFieldValue(final Object value) {
        this.fieldValue = value;
    }

    void setParentValue(final Object value) {
        if (isRoot()) {
            throw new EoException("Root has no parent!");
        }
        if (!isParentSet()) {
            setFieldValue(value);
            return;
        }
        getParentEo().getModel().set(getFieldKey(), getParent().get(), value);
    }

    @Override
    public boolean hasEo(final String fieldKey) {
        if (eoMap == null) {
            throw new EoInternalException("Null eoMap should never happen!");
        }
        return eoMap.containsKey(fieldKey);
    }

    boolean hasEo(PathElement pathElement) {
        return hasEo(pathElement.getKey());
    }

    @Override
    public EOInterfaceScalar set(Object value, final String... paths) {
        if (value == null) {
            throw new EoException("Null value not allowed: Occured when setting null to + '" + Arrays.stream(paths).collect(Collectors.joining(DELIMITER)) + "' at '" + getPathAsString() + "'.");
        }
        return createChild(new Path(paths), value);
    }

    protected Object getValue(final String fieldName) {
        try {
            return getModel().get(fieldName, get());
        } catch (Exception e) {
            return null;
        }
    }

    boolean hasValue(final String fieldKey) {
        return this.getModel().hasValue(fieldKey, get());
    }

    @Override
    public Object get(final String... pathStrings) {
        if (pathStrings.length == 1 && !pathStrings[0].contains(DELIMITER)) {
            if (!hasEo(pathStrings[0])) {
                throw new EoException("No entry found for '" + pathStrings[0] + "' at '" + getPathAsString() + "'");
            }
            if (pathStrings[0].startsWith("_")) {
                return eoMap.get(pathStrings[0]).get();
            }
            return this.getModel().get(pathStrings[0], get());
        }
        try {
            return getEo(pathStrings).get();
        } catch (EoException e) {
            throw new EoException(String.join("/", pathStrings) + e.getMessage());
        }
    }

    @Override
    public EOInterfaceScalar getEo(String... pathString) {
        return getEo(new Path(pathString));
    }

    public EOInterfaceScalar getEo(Path path) {
        EOInterfaceScalar target = this;
        if (path.isAbsolute()) {
            target = getRoot();
        }
        for (PathElement element : path.getEntries()) {
            if (element.isBack()) {
                target = target.getParent();
            } else if (element.isSame()) {
            } else {
                if (!(target instanceof EoChild)) {
                    throw new EoException("Could not move to path '" + element.getKey() + "' because wrapper is scalar ' for " + getPathAsString());
                }
                target = ((EoChild) target).getEo(element);
            }
        }
        return target;
    }

    public EOInterfaceScalar getEo(final PathElement pathElement) {
        if (!hasEo(pathElement)) {
            throw new EoException("Could not move to path '" + pathElement.getKey() + "' because key '" + pathElement.toString() + "' does not exist on '" + this.getPathAsString() + "'.");
        }
        return eoMap.get(pathElement.getKey());
    }

    @Override
    public EOInterfaceScalar createChild(final String... paths) {
        return createChild(new Path(paths), null);
    }

    EOInterfaceScalar createChild(Path path, Object value) {
        if (path.isEmpty()) {
            map(value);
            return this;
        }
        EOInterfaceScalar parent = this;
        if (path.isAbsolute()) {
            parent = getRoot();
        }
        int counter = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            PathElement pathElement = path.getPathElement(i);
            if (((EoChild) parent).hasEo(pathElement)) {
                parent = ((EoChild) parent).getEo(pathElement);
                continue;
            }
            parent = parent.getModels().createChild((EO) parent, pathElement, null);
            if (!(parent instanceof EO)) {
                throw new EoException("");
            }
        }
        PathElement pathElement = path.getPathElement(path.size() - 1);
        if (((EoChild) parent).hasEo(pathElement)) {
            EoChildScalar child = (EoChildScalar) ((EoChild) parent).getEo(pathElement);
            child.set(value);
            return child;
        }
        try {
            return parent.getModels().createChild((EO) parent, path.getPathElement(path.size() - 1), value);
        }
        catch (Exception e) {
            throw new EoException("Problem creating child at '" + getPathAsString() + "' with key '" + pathElement + "' with value '" + value + "' with message " + e.getMessage());
        }
    }

    EOInterfaceScalar createChild(PathElement element) {
        if (element.isBack()) {
            return getParent();
        } else if (hasEo(element)) {
            return eoMap.get(element.getKey());
        }
        return getModels().createChild(this, element, null);
    }

    EOInterfaceScalar createChild(final PathElement pathElement, final Object childValue) {
        try {
            return getModels().createChild(this, pathElement, childValue);
        }
        catch (Exception e) {
            throw new EoException("Problem creating child at '" + getPathAsString() + "' with key '" + pathElement + "' with value '" + childValue + "' with message " + e.getMessage());
        }
    }

    void removeEo(String fieldName) {
        this.eoMap.remove(fieldName);
    }

    @Override
    public EO remove(final String... path) {
        EoChildScalar eoToRemove = (EoChildScalar)getEo(path);
        EO parent = eoToRemove.remove();
        eoToRemove = null;
        return parent;
    }

    public EOInterfaceScalar overWrite(final Object value, final String... path) {
        remove(path);
        return set(value, path);
    }

    @Override
    public void set(Object value) {
        if (fieldValue == null) {
            fieldValue = getModels().create();
        }
        if (eoMap == null) {
            eoMap = new LinkedHashMap<>();
        }
        if (hasParent()) {
            getParentEo().setValueByModel(getFieldKey(), fieldValue);
        }
        map(value);
    }

    void setValueByModel(String key, Object value) {
        getModel().set(key, get(), value);
    }

    void addEo(String key, EOInterfaceScalar child) {
        if (eoMap == null) {
            eoMap = new LinkedHashMap<>();
        }
        eoMap.put(key, child);
    }

    @Override
    public Object get() {
        return fieldValue;
    }

    void addChildEo(EoChildScalar childEo) {
        eoMap.put(childEo.getFieldKey(), childEo);
    }

    void setModels(String modelString) {
        Models models = new Models(getConfigMaps(), modelString.split(","));
        setModels(models);
        if (models.isCreate()) {
            this.fieldValue = models.create();
            if (hasParent()) {
                getParent().set(this.fieldValue, this.getFieldKey());
            }
        }
    }

    @Override
    public EO map(Object value) {
        if (value == null) {
            return this;
        }
        ModelConfig valueModel = getConfigMaps().findModel(value);

        if (valueModel.isScalar()) {
            if (value instanceof String) {
                if (JSONToEO.JSON_PATTERN.matcher((String) value).find()) {
                    return (EO)new JSONToEO((String) value).createChild(this);
                }
                Object base = createBaseObject(value);
                if (base == null) {
                    throw new EoException("Could not map scalar to container model '" + getModels().toString() + "' '" + this.getPath().toString() + "'");
                }
                value = base;
            } else if (value instanceof Long) {
                Object base = createBaseObject(value);
                if (base == null) {
                    throw new EoException("Could not map scalar to container model '" +
                            getModels().toString() + "' '" +
                            this.getPath().toString() + "'");
                }
                value = base;
            } else {
                throw new EoException("Could not map scalar '" +
                        value.toString() + "'(" +
                        valueModel.getModelKey() + ") to container model '" +
                        getModels().toString() + "' '" +
                        this.getPath().toString() + "'");
            }
        }
        Set<String> fieldKeySet = valueModel.keys(value);
        ModelConfig targetModel = getModel();
        for (String fieldKey : fieldKeySet) {

            if (targetModel.isObject() && !targetModel.hasField(fieldKey)) {
                debug("No fieldKey '" + fieldKey + "' defined for '" + getModelClass().getSimpleName());
                continue;
            }
            PathElement pathElement =null;

            if (valueModel.isObject()) {
                FieldInterface fieldBean = valueModel.getField(fieldKey);
                pathElement = new PathElement(fieldBean.getFieldKey());
                if (fieldBean == null) {
                    continue;
                }
            }
            else {
                 pathElement = new PathElement(fieldKey);
            }

            if (valueModel.isJsonIgnore(fieldKey)) continue;
            if (valueModel.isProperty(fieldKey)) continue;
            if (!valueModel.exists(fieldKey, value)) continue;
            Object childValue = valueModel.get(fieldKey, value);

            if (childValue == null && !hasEo(pathElement)) {
                continue;
            }
            createChild(pathElement, childValue);
        }
        return this;
    }

    private Object createBaseObject(Object value) {
        Object object = getModels().create();
        if (object instanceof ConfigBean) {

            if (value instanceof Long) {
                ((ConfigBean) object).setId((Long) object);
            } else if (value instanceof String) {
                ((ConfigBean) object).setNaturalId((String) object);
            }
            return object;
        }
        return null;
    }

    @Override
    public boolean isEoEmpty() {
        return eoMap == null || eoMap.isEmpty();
    }

    @Override
    public Set<String> keysEo() {
        return eoMap.keySet();
    }

    @Override
    public Set<String> keys() {
        Set<String> filteredSet = new LinkedHashSet<>();
        if (this.eoMap == null || get() == null) {
            return filteredSet;
        }
        for (String key : keysEo()) {
            if (PathElement.isParentNotSet(key)) {
                continue;
            }
            filteredSet.add(key);
        }
        return filteredSet;
    }

    public List<String> keys(String pathString) {
        if (pathString == null || pathString.isEmpty() || ".".equals(pathString)) {
            List<String> keys = new ArrayList<>();
            keys.add(".");
            return keys;
        }
        return keys(new PathPattern(pathString));
    }

    public List<String> keys(PathPattern pathPattern) {
        return pathPattern.filter(keysEo());
    }

    public final Map<String, Object> getKeyValues() {
        if (getModel() == null) {
            throw new EoException("Null model!");
        }
        if (!isContainer()) {
            throw new EoException("Not a container model " + getModel().getModelKey() + "'!");
        }
        return getModel().getKeyValues(get(), new PathPattern("*"));
    }

    @Override
    public List<String> filterPaths(String pathString) {
        if (pathString == null || pathString.isEmpty() || ".".equals(pathString)) {
            List<String> keys = new ArrayList<>();
            keys.add(".");
            return keys;
        }
        return filterPaths(new PathPattern(pathString), "");
    }

    public List<String> filterPaths(PathPattern pathPattern, String path) {
        List<String> result = new ArrayList<>();
        List<String> filter = pathPattern.filter(keysEo());
        for (String key : filter) {
            if (key.equals(".config")) {
                continue;
            }
            String nextPath = path + DELIMITER + key;
            nextPath = nextPath.replaceAll("^" + DELIMITER, "");
            EOInterfaceScalar childAdapter = getEo(key);
            if (childAdapter == null) {
                continue;
            }
            PathPattern childPathPattern = pathPattern.getPathList(key);
            if ((!childPathPattern.isEmpty() || childPathPattern.isAll()) && !childAdapter.isScalar()) {
                List<String> keys = ((EoChild) childAdapter).filterPaths(childPathPattern, nextPath);
                result.addAll(keys);
            } else {
                result.add(nextPath);
            }
        }
        return result;
    }

    @Override
    public EoRoot getRoot() {
        return getParentEo().getRoot();
    }

    @Override
    public Path getPath() {
        return new Path(getPathAsString());
    }

    @Override
    public String getPathAsString() {
        final StringBuilder builder = new StringBuilder();
        getPathAsString(builder);
        return builder.toString();
    }

    void getPathAsString(final StringBuilder builder) {
        builder.insert(0, getFieldKey());
        builder.insert(0, DELIMITER);
        getParentEo().getPathAsString(builder);
    }

    @Override
    public String toString() {
        return "(" + getModels().toString() + ") " + getPathAsString() + " -> " + get().toString() + "";
    }

    public String toJson() {
        return toJson(getSerializationType());
    }

    public String toJson(JSONSerializationType serializationType) {
        if (isScalar()) {
            return get().toString();
        }
        try {
            return new EOToJSON()
                    .setSerializationType(serializationType)
                    .toJson(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getSerialized " + getPath() + ": " + e.getMessage();
        }
    }

    @Override
    protected void compare(final StringBuilder builder, final EOInterfaceScalar other) {
        if (!other.isContainer()) {
            builder.append(getPathAsString() + ": other is not container but '" + other.getModelClass().getSimpleName());
            return;
        }
        List<String> list = new ArrayList<>(this.keys());
        List<String> otherList = new ArrayList<>(((EO) other).keys());
        for (String key : otherList) {
            if (list.contains(key)) {
                continue;
            }
            builder.append(getPathAsString() + DELIMITER + key + ": null <> " + other.getEo(key).getModelClass().getSimpleName() + "\n");
        }
        for (String key : list) {
            if (!other.hasEo(key)) {
                builder.append(getPathAsString() + key + ": " + getEo(key).getModelClass().getSimpleName() + "<> null \n");
                continue;
            }
            EOInterfaceScalar childEo = getEo(key);
            EOInterfaceScalar otherChildEo = other.getEo(key);
            ((EoChildScalar) childEo).compare(builder, otherChildEo);
        }
    }
}
