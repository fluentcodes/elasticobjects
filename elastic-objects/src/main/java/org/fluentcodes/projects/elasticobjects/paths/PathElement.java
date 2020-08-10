package org.fluentcodes.projects.elasticobjects.paths;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.JSONToEO;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Path creates from a string a special of elements splitted by te delimiter
 * Created by Werner on 4.07.2020.
 */
public class PathElement {
    public static final Pattern modelPattern = Pattern.compile("^\\(([^\\)]*)\\)(.*)");
    public static final String BACK = "..";
    public static final String SAME = ".";
    public static final String MATCHER = "*";
    public static final String MATCHER_ALL = "+";
    public static final String ROOT_MODEL = "_rootmodel";
    public static final String LOGS = "_logs";
    public static final String ERROR_LEVEL = "_errorLevel";
    public static final String SERIALIZATION_TYPE = "_serializationType";
    public static final String LOG_LEVEL = "_logLevel";
    public static final String ROOT_CALLS = "_calls";
    public static final String CALLS = "_calls";
    public static final String CONFIG = "_config";
    private String[] modelsArray;
    private Models models;
    private final String key;

    public PathElement(final String name, EO parentEo, Object value) {
        this(name);
        resolve(parentEo, value);
    }

    public PathElement(final String name, EO parentEo, Class defaultClass) {
        this(name);
        if (!hasModelArray() && defaultClass != null) {
            this.modelsArray = new String[]{defaultClass.getSimpleName()};
            this.models = new Models(parentEo.getConfigsCache(), defaultClass);
        }
        else {
            resolve(parentEo, null);
        }
    }

    public PathElement(final Call call, EO parentEo) {
        this(Integer.valueOf(parentEo.sizeEo()).toString());
        this.modelsArray = new String[]{call.getClass().getSimpleName()};
    }

    public PathElement(final String name, String... modelsArray) {
        this(name);
        if (!hasModels()) {
            if (modelsArray.length>0) {
                this.modelsArray = modelsArray;
            }
            else {
                this.modelsArray = new String[]{Map.class.getSimpleName()};
            }
        }
    }

    public PathElement(final String name, Class... modelsArray) {
        this(name);
        if (!hasModels()) {
            if (modelsArray.length>0) {
                this.modelsArray = new String[modelsArray.length];
                int counter = 0;
                for (Class modelClass: modelsArray) {
                    this.modelsArray[counter] = modelsArray[counter].getSimpleName();
                    counter++;
                }
            }
            else {
                this.modelsArray = new String[]{Map.class.getSimpleName()};
            }
        }
    }

    public PathElement(final String name) {
        final Matcher matcher = PathElement.modelPattern.matcher(name);
        String modelKey = null;
        if (matcher.find()) {
            modelKey = matcher.group(1);
            this.key = matcher.group(2);
        }
        else {
            this.key = name;
        }

        if (LOG_LEVEL.equals(key) ) {
            modelKey = LogLevel.class.getSimpleName();
        }
        else if (ERROR_LEVEL.equals(key) ) {
            modelKey = LogLevel.class.getSimpleName();
        }
        else if (CALLS.equals(key) ) {
            modelKey = List.class.getSimpleName();
        }
        else if (SERIALIZATION_TYPE.equals(key) ) {
            modelKey = JSONSerializationType.class.getSimpleName();
        }
        if (modelKey == null || modelKey.isEmpty()) {
            modelsArray = new String[]{};
            return;
        }
        modelsArray = modelKey.split(",");
    }

    public void resolve(EO parentEo, Object value) {
        if (hasModels()) {
            return;
        }
        Models valueModels = null;
        Models childModels = parentEo.getModels().getChildModels(parentEo,this);
        if (childModels == null) {
            if (!hasModelArray()) {
                if (value == null) {
                    modelsArray = new String[]{Map.class.getSimpleName()};
                }
                else {
                    if (value instanceof String) {
                        if (JSONToEO.jsonListPattern.matcher((String) value).find()) {
                            modelsArray = new String[]{List.class.getSimpleName()};
                        } else if (JSONToEO.jsonMapPattern.matcher((String) value).find()) {
                            modelsArray = new String[]{Map.class.getSimpleName()};
                        }
                        else {
                            modelsArray = new String[]{String.class.getSimpleName()};
                            valueModels = new Models(parentEo.getConfigsCache(), String.class);
                        }
                    }
                    else {
                        if (value.getClass().isEnum()) {
                            modelsArray = new String[]{String.class.getSimpleName()};
                        }
                        else {
                            valueModels = new Models(parentEo.getConfigsCache(), value.getClass());
                            if (valueModels.isCreate() || valueModels.isScalar()) {
                                modelsArray = new String[]{value.getClass().getSimpleName()};
                            } else if (valueModels.isEnum()) {
                                modelsArray = new String[]{String.class.getSimpleName()};
                            } else {
                                modelsArray = new String[]{Map.class.getSimpleName()};
                            }
                        }
                    }
                }
            }
            this.models = new Models(parentEo.getConfigsCache(), getModelsArray());
        }
        else {
            if (value != null) {
                valueModels = new Models(parentEo.getConfigsCache(), value.getClass());
            }
            if (!hasModelArray()) {
                this.models = childModels;
            }
            else {
                Models setModels = new Models(parentEo.getConfigsCache(), getModelsArray());
                if (childModels.toString().contains(setModels.toString())) {
                    this.models = setModels;
                }
            }
        }
        if (valueModels == null) {
            return;
        }
        if (models.isScalar() && !valueModels.isScalar()) {
                throw new EoException("Problem setting non scalar value ("
                        + valueModels.getModel().getNaturalId() + ") for field name '"
                        + key + "'. Expected is "
                        + models.getModel().getNaturalId() + "!");
        }
        else if (!models.isScalar() && valueModels.isScalar()) {
            throw new EoException("Problem setting scalar value ("
                    + valueModels.getModel().getNaturalId() + ") for field name '"
                    + key + "'. Expected is "
                    + models.getModel().getNaturalId() + "!");
        }
    }

    public static final PathElement OF_SERIALIZATION_TYPE() {
        return new PathElement(SERIALIZATION_TYPE);
    }

    public static final PathElement OF_LOG_LEVEL() {
        return new PathElement(LOG_LEVEL);
    }

    public static final PathElement OF_ERROR_LEVEL() {
        return new PathElement(ERROR_LEVEL);
    }
    public static final PathElement OF_LOGS() {
        return new PathElement(LOGS);
    }
    public static final PathElement OF_CALLS() {
        return new PathElement(CALLS);
    }


    /**
     * fieldnames starting with underscores will not mapped to a parent object.
     * @param fieldName the fieldName
     * @return true if empty or starting with "_"
     */
    public static boolean isParentNotSet(final String fieldName) {
        return fieldName == null || fieldName.isEmpty() || fieldName.startsWith("_");
    }

    public boolean isParentNotSet() {
        return key == null || key.isEmpty() || key.startsWith("_");
    }

    public Object create() {
        return getModels().create();
    }

    public boolean isCall() {
        return getModels().isCall();
    }

    public boolean isBack() {
        return BACK.equals(key);
    }
    public boolean isSame() {
        return SAME.equals(key);
    }

    public String[] getModelsArray() {
        return modelsArray;
    }

    public Models getModels() {
        return models;
    }

    public String getKey() {
        return key;
    }
    public boolean hasKey() {
        return key != null && !key.isEmpty();
    }

    public boolean hasModels() {
        return models != null && !models.isEmpty();
    }

    public boolean hasModelArray() {
        return modelsArray != null && modelsArray.length>0;
    }

    public boolean isRootModel() {
        return ROOT_MODEL.equals(getKey());
    }

    @Override
    public String toString(){
        if (!hasModelArray()) {
            return key;
        }
        return "(" + String.join(",", modelsArray) + ")" + key;
    }
}
