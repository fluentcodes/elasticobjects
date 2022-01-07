package org.fluentcodes.projects.elasticobjects.models;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoChildScalar;
import org.fluentcodes.projects.elasticobjects.EoChildScalarSpecial;
import org.fluentcodes.projects.elasticobjects.EoChildSpecial;
import org.fluentcodes.projects.elasticobjects.IEOObject;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.JSONToEO.JSON_LIST_PATTERN;
import static org.fluentcodes.projects.elasticobjects.JSONToEO.JSON_MAP_PATTERN;
import static org.fluentcodes.projects.elasticobjects.calls.Call.TARGET_AS_STRING;

/**
 * An array of models defining types
 *
 * @author Werner Diwischek
 * @since 20.05.16.
 */

public class Models {
    private static final List<Class<?>> DEFAULT_CLASSES = Arrays.asList(Map.class, LinkedHashMap.class, String.class, Boolean.class, Integer.class);
    private final ModelConfig[] modelConfigs;
    private final boolean hasChildModel;

    public Models(final ModelConfig... modelConfigs) {
        this.modelConfigs = modelConfigs;
        hasChildModel = modelConfigs.length > 1;
    }

    public Models(final ConfigMaps configMaps, final PathElement pathElement) {
        this(configMaps, pathElement.getModelsArray());
    }

    protected Models(final List<ModelConfig> modelConfigs) {
        this.modelConfigs = new ModelConfig[modelConfigs.size()];
        for (int i = 0; i < modelConfigs.size(); i++) {
            if (modelConfigs.get(i) == null) {
                throw new EoInternalException("Null model for " + modelConfigs);
            }
            this.modelConfigs[i] = modelConfigs.get(i);
        }
        hasChildModel = modelConfigs.size() > 1;
    }

    //https://stackoverflow.com/questions/997482/does-java-support-default-parameter-values
    public Models(final ConfigMaps cache, final Class... classes) {
        this(cache, convert(classes));
    }

    public Models(final ConfigMaps cache, final String... modelKeysIn) {
        String[] modelKeys = strip(modelKeysIn);
        this.modelConfigs = new ModelConfig[modelKeys.length];
        for (int i = 0; i < modelKeysIn.length; i++) {
            modelConfigs[i] = cache.findModel(modelKeysIn[i]);
        }
        hasChildModel = modelConfigs.length > 1;
    }

    public Models(final ConfigMaps cache) {
        hasChildModel = false;
        this.modelConfigs = new ModelConfig[]{cache.findModel(Map.class)};
    }

    public static final Models ofValue(final ConfigMaps cache, final Object value) {
        if (value == null) {
            return new Models(cache, Map.class);
        }
        if (value.getClass() == Class.class) {
            return new Models(cache, (Class) value);
        }

        if (value instanceof String) {
            if (JSON_MAP_PATTERN.matcher((String) value).find()) {
                return new Models(cache, Map.class);
            } else if (JSON_LIST_PATTERN.matcher((String) value).find()) {
                return new Models(cache, List.class);
            } else {
                return new Models(cache, String.class);
            }
        }
        return new Models(cache, value.getClass());
    }

    private static String[] convert(Class[] classes) {
        if (classes == null || classes.length == 0 || Object.class.equals(classes[0])) {
            return new String[]{"Map"};
        }
        String[] modelNames = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            modelNames[i] = classes[i].getSimpleName();
        }
        return modelNames;
    }

    private static String[] strip(String[] toBeStripped) {
        if (toBeStripped == null || toBeStripped.length == 0 || "Object".equals(toBeStripped[0])) {
            return new String[]{"Map"};
        }
        return toBeStripped;
    }

    public IEOScalar createChild(IEOObject parent, final PathElement pathElement, Object value) {
        Models childModels = deriveChildModels(pathElement, value);
        if (parent.getSerializationType() == JSONSerializationType.STANDARD &&
                (childModels.isObject() || childModels.isMap())
        ) {
            childModels = new Models(getConfigMaps());
        }
        if (value == null) {
            if (childModels.isCreate()) {
                value = childModels.create();
            }
        } else if (childModels.isScalar() && value.getClass() != childModels.getModelClass()) {
            value = childModels.asObject(value);
        }
        String key = deriveFieldKey(parent, pathElement);
        if (value instanceof Call) {
            if (TARGET_AS_STRING.equals(((Call)value).getTargetPath())) {

            }
            else if (!key.isEmpty() && TARGET_AS_STRING.equals(key)) {
                ((Call)value).setTargetPath(TARGET_AS_STRING);
            }
            else if (!((Call)value).hasTargetPath() && (!key.isEmpty())) {
                if (key.equals(PathElement.SAME)) {
                    ((Call) value).setTargetPath(parent.getPathAsString());
                } else {
                    ((Call) value).setTargetPath(parent.getPathAsString() + Path.DELIMITER + key);
                }
            }
            parent = parent.getCallsEo();
            key = Integer.toString(parent.size());
        }
        if (childModels.isScalar()) {
            if (pathElement.isParentSet()) {
                return new EoChildScalar(parent, key, value, childModels);
            }
            else {
                return new EoChildScalarSpecial(parent, key, value, childModels);
            }
        }
        else {
            if (pathElement.isParentSet()) {
                return new EoChild(parent, key, value, childModels);
            }
            else {
                return new EoChildSpecial(parent, key, value, childModels);
            }
        }
    }

    public ConfigMaps getConfigMaps() {
        return getModel().getConfigMaps();
    }

    final Models deriveChildModels(final PathElement pathElement, final Object childValue) {
        Models childModels = createChild(pathElement);
        if (childModels == null && childValue == null) {
            return new Models(getConfigMaps()); // Map
        }
        if (childValue == null) {
            return childModels;
        }
        Models valueModels = Models.ofValue(getConfigMaps(), childValue);
        if (childValue instanceof String) {
            if ((valueModels.getModelClass() == List.class|| valueModels.getModelClass() == Map.class)
                    && childModels!=null && childModels.getModelClass() == String.class) {
                return childModels;
            }
        }
        if (childValue instanceof Call) {
            return new Models(getConfigMaps(), childValue.getClass());
        }
        if (childModels == null) {
            if (valueModels.hasDefaultImplementation()) {
                return valueModels.getDefaultImplementation();
            }
            /*if (models.isCreate()) {
                return models;
            }
            if (models.isContainer()) {
                return new Models(getConfigMaps(), Map.class);
            }*/
            return valueModels;
        }

        return competeModels(childModels, Models.ofValue(getConfigMaps(), childValue));
    }

    Models createChild(final PathElement pathElement) {
        if (!pathElement.hasModelArray() && !hasChildModels(pathElement)) {
            return null;
        }
        if (!hasChildModels(pathElement)) {
            return new Models(getConfigMaps(), pathElement);
        }
        if (!pathElement.hasModelArray()) {
            return getChildModels(pathElement);
        }
        if (!pathElement.isParentSet()) {
            return new Models(getConfigMaps(), pathElement);
        }
        return competeModels(getChildModels(pathElement), new Models(getConfigMaps(), pathElement));
    }

    private Models competeModels(final Models dominator, final Models descriminator) {
        if (dominator.isScalar() && descriminator.isScalar()) {
            return dominator;
        }
        if (dominator.isMap() && descriminator.isMap()) {
            return dominator;
        }

        if (dominator.isList() && descriminator.isList()) {
            return dominator;
        }

        if (dominator.isObject() && descriminator.isObject()) {
            if (dominator.getModelClass() == descriminator.getModelClass()) {
                return dominator;
            } else {
                throw new EoException("Different classes provided " +
                        dominator.getModelClass().getSimpleName() + " " +
                        descriminator.getModelClass().getSimpleName());
            }
        }
        if (dominator.isObject() && (descriminator.isMap())) {
            return dominator;
        }
        if (dominator.isMap() && (descriminator.isObject())) {
            return dominator;
        }

        throw new EoException("Mismatch for Models: Expected is " +
                dominator.getModelClass().getSimpleName() + " but the other model is " +
                descriminator.getModelClass().getSimpleName());
    }

    private final String deriveFieldKey(IEOObject parentEo, final PathElement pathElement) {
        if (isList() && pathElement.isParentSet() && pathElement.hasKey()) {
            if (pathElement.getKey().matches("\\d+")) {
                return Integer.valueOf(pathElement.getKey()).toString();
            } else {
                return Integer.toString(parentEo.size());
            }
        }
        if (isObject() &&
                pathElement.isParentSet() &&
                !getModel().hasField(pathElement.getKey())) {
            throw new EoException("No fieldConfig '" + pathElement.getKey() + "' defined in model '" + getModelClass().getSimpleName() + "' ! ");
        }
        return pathElement.getKey();
    }

    public boolean isEmpty() {
        return modelConfigs.length == 0;
    }

    public boolean isEmpty(Object source) {
        try {
            return getModel().isEmpty(source);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean hasDefaultImplementation() {
        return getModel().hasDefaultImplementation();
    }

    public Models getDefaultImplementation() {
        return new Models(getConfigMaps(), getModel().getProperties().getDefaultImplementation());
    }

    public boolean hasModel() {
        return getModel() != null && getModel().getModelClass() != Map.class;
    }

    public boolean isCreate() {
        return getModel().isCreate();
    }

    public boolean isList() {
        return getModel().isList();
    }

    public boolean isObject() {
        return getModel().isObject();
    }

    public boolean isEnum() {
        return getModel().isEnum();
    }

    public boolean isMap() {
        return getModel().isMap();
    }

    public boolean isCall() {
        return getModel().isCall();
    }

    public Class<?> getModelClass() {
        return getModel().getModelClass();
    }

    public ModelConfig getModel() {
        return modelConfigs[0];
    }

    public Models getChildModels(PathElement element) {
        if (getModel().hasField(element.getKey())) {
            return getModel().getFieldModels(element.getKey());
        }
        return new Models(getConfigMaps(), getChildModel().getModelClass());
    }

    public boolean hasChildModels(PathElement element) {
        return getModel().hasField(element.getKey()) || hasChildModel();
    }

    public boolean hasChildModel() {
        return hasChildModel;
    }

    public ModelConfig getChildModel() {
        if (!hasChildModel()) {
            return null;
        }
        return modelConfigs[1];
    }

    public int size() {
        return this.modelConfigs.length;
    }

    public ModelConfig get(int i) {
        if (i > size() - 1) {
            throw new EoException("Index out of bounds " + i + ">" + size());
        }
        return this.modelConfigs[i];
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            if (get(i) != null) {
                if (i > 0) {
                    buffer.append(",");
                }
                buffer.append(this.get(i).getModelKey());
            }
        }
        return buffer.toString();
    }

    public String createDirective() {
        if (DEFAULT_CLASSES.contains(getModelClass())) {
            return "";
        }
        return "(" + toString() + ")";
    }

    public boolean isScalar() {
        return getModel().isScalar();
    }


    public boolean isContainer() {
        return getModel().isContainer();
    }

    public Object create() {
        return getModel().create();
    }

    public String asString(Object object) {
        return getModel().asString(object);
    }
    public String asJson(Object object) {
        return getModel().asJson(object);
    }
    public Object asObject(Object object) {
        if (getModelClass() == object.getClass()) {
            return object;
        }
        return getModel().asObject(object);
    }
    public boolean compare(Object left, Object right) {
        return getModel().compare(left, right);
    }
}
